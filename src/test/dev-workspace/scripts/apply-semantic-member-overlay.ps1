$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$overlay = Join-Path $script:WorkspaceRoot 'src-named-overlay\java\net\labymod\v1_21_11'
$reportPath = Join-Path $script:WorkspaceRoot 'reports\semantic-member-overlay-replacements.csv'
$rows = New-Object System.Collections.Generic.List[object]

function Update-JavaFile {
    param(
        [string] $RelativePath,
        [hashtable] $Replacements
    )

    $path = Join-Path $overlay $RelativePath
    if (-not (Test-Path -LiteralPath $path)) {
        return
    }

    $text = Get-Content -Raw -LiteralPath $path
    $original = $text

    foreach ($entry in $Replacements.GetEnumerator()) {
        $old = [regex]::Escape($entry.Key)
        $new = $entry.Value
        $before = $text
        $text = [regex]::Replace($text, "(?<![A-Za-z0-9_$])$old(?![A-Za-z0-9_$])", { param($m) $new })
        if ($text -ne $before) {
            $rows.Add([pscustomobject]@{ file = $RelativePath; obfuscated = $entry.Key; named = $new })
        }
    }

    if ($text -ne $original) {
        Set-Content -Encoding UTF8 -LiteralPath $path -Value $text
    }
}

Update-JavaFile 'mixins\client\renderer\entity\player\MixinLivingEntityRenderer.java' @{
    'a' = 'addLayer'
}

$rows | Sort-Object file, obfuscated | Export-Csv -NoTypeInformation -Encoding UTF8 $reportPath
Write-Host "semantic member replacements: $($rows.Count)"
Write-Host "report: $reportPath"
