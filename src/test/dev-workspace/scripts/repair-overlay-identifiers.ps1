$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$overlay = Join-Path $script:WorkspaceRoot 'src-named-overlay\java\net\labymod\v1_21_11'
$reportPath = Join-Path $script:WorkspaceRoot 'reports\overlay-identifier-repairs.csv'
$rows = New-Object System.Collections.Generic.List[object]

function Apply-Repair {
    param(
        [string] $Text,
        [string] $Relative,
        [string] $Pattern,
        [string] $Replacement,
        [string] $Name
    )

    $count = [regex]::Matches($Text, $Pattern).Count
    if ($count -gt 0) {
        $script:rows.Add([pscustomobject]@{
            file = $Relative
            repair = $Name
            count = $count
        })
        return [regex]::Replace($Text, $Pattern, $Replacement)
    }

    return $Text
}

Get-ChildItem -Recurse -Filter *.java $overlay | ForEach-Object {
    $path = $_.FullName
    $relative = $path.Substring($overlay.Length + 1)
    $text = Get-Content $path -Raw
    $original = $text

    $text = Apply-Repair $text $relative '(\b(?:public|private|protected)\s+(?:<[^>{}]+>\s+)?[A-Za-z_$][\w$<>, ?.\[\]]*\s+)net\.minecraft(?:\.[A-Za-z_$][\w$]*)+(\s*\()' '${1}mappedMethod${2}' 'method-name-fqcn'
    $text = Apply-Repair $text $relative '(\b(?:boolean|int|long|float|double|byte|short|char|[A-Za-z_$][\w$]*(?:<[^;(){}]*>)?(?:\[\])?)\s+)net\.minecraft(?:\.[A-Za-z_$][\w$]*)+(\s*[,)=])' '${1}mappedParam${2}' 'param-name-fqcn'
    $text = Apply-Repair $text $relative '(\b(?:final\s+)?(?:boolean|int|long|float|double|byte|short|char|[A-Za-z_$][\w$]*(?:<[^;(){}]*>)?(?:\[\])?)\s+)net\.minecraft(?:\.[A-Za-z_$][\w$]*)+(\s*[=;])' '${1}mappedLocal${2}' 'local-name-fqcn'

    if ($text -ne $original) {
        Set-Content -Encoding UTF8 -Path $path -Value $text
    }
}

$rows | Sort-Object file, repair | Export-Csv -NoTypeInformation -Encoding UTF8 $reportPath
Write-Host "identifier repairs: $($rows | Measure-Object -Property count -Sum | Select-Object -ExpandProperty Sum)"
Write-Host "report: $reportPath"
