$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$overlay = Join-Path $script:WorkspaceRoot 'src-named-overlay\java\net\labymod\v1_21_11'
$reportPath = Join-Path $script:WorkspaceRoot 'reports\short-class-overlay-replacements.csv'

$map = [ordered]@{
    'yh' = 'net.minecraft.network.chat.Component'
    'yw' = 'net.minecraft.network.chat.MutableComponent'
    'zf' = 'net.minecraft.network.chat.Style'
    'is' = 'net.minecraft.core.BlockPos'
    'jd' = 'net.minecraft.core.Holder'
    'wu' = 'net.minecraft.network.Connection'
    'iz' = 'net.minecraft.core.Direction'
}

$rows = New-Object System.Collections.Generic.List[object]

Get-ChildItem -Recurse -Filter *.java $overlay | ForEach-Object {
    $path = $_.FullName
    $relative = $path.Substring($overlay.Length + 1)
    $text = Get-Content $path -Raw
    $original = $text

    foreach ($entry in $map.GetEnumerator()) {
        $name = [regex]::Escape($entry.Key)
        $target = $entry.Value

        $patterns = @(
            @{ Pattern = "(?<![A-Za-z0-9_$.])$name(?=\s+[A-Za-z_$])"; Replacement = $target; Label = 'type-declaration' },
            @{ Pattern = "(?<![A-Za-z0-9_$.])$name(?=<)"; Replacement = $target; Label = 'generic-type' },
            @{ Pattern = "(?<![A-Za-z0-9_$.])$name(?=\.class\b)"; Replacement = $target; Label = 'class-literal' },
            @{ Pattern = "(?<![A-Za-z0-9_$.])$name(?=\.)"; Replacement = $target; Label = 'static-access' },
            @{ Pattern = "(?<=\()$name(?=\))"; Replacement = $target; Label = 'cast' }
        )

        foreach ($item in $patterns) {
            $count = [regex]::Matches($text, $item.Pattern).Count
            if ($count -gt 0) {
                $text = [regex]::Replace($text, $item.Pattern, $item.Replacement)
                $rows.Add([pscustomobject]@{
                    file = $relative
                    obfuscated = $entry.Key
                    named = $target
                    kind = $item.Label
                    count = $count
                })
            }
        }
    }

    if ($text -ne $original) {
        Set-Content -Encoding UTF8 -Path $path -Value $text
    }
}

$rows | Sort-Object file, obfuscated, kind | Export-Csv -NoTypeInformation -Encoding UTF8 $reportPath
Write-Host "short class replacements: $($rows | Measure-Object -Property count -Sum | Select-Object -ExpandProperty Sum)"
Write-Host "report: $reportPath"
