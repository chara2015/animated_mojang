$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$source = Join-Path $script:WorkspaceRoot 'decompiled-v1_21_11-named\sources\net\labymod\v1_21_11'
$target = Join-Path $script:WorkspaceRoot 'src-named-overlay\java\net\labymod\v1_21_11'

if (-not (Test-Path $source)) {
    throw "named bytecode sources not found: $source"
}

if (Test-Path $target) {
    Remove-Item -LiteralPath $target -Recurse -Force
}

New-Item -ItemType Directory -Force -Path $target | Out-Null
robocopy $source $target /E /NFL /NDL /NJH /NJS /NP | Out-Null

$count = (Get-ChildItem -Recurse -Filter *.java $target | Measure-Object).Count
Write-Host "synced named bytecode sources: $count"
