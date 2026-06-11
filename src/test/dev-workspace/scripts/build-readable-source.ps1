$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$raw = Join-Path $script:WorkspaceRoot 'src\main\java'
$overlay = Join-Path $script:WorkspaceRoot 'src-named-overlay\java'
$readable = Join-Path $script:WorkspaceRoot 'src-readable\java'

if (Test-Path $readable) {
    Remove-Item -LiteralPath $readable -Recurse -Force
}

New-Item -ItemType Directory -Force -Path $readable | Out-Null
robocopy $raw $readable /E /NFL /NDL /NJH /NJS /NP | Out-Null

$readableVersion = Join-Path $readable 'net\labymod\v1_21_11'
if (Test-Path $readableVersion) {
    Remove-Item -LiteralPath $readableVersion -Recurse -Force
}

robocopy (Join-Path $overlay 'net\labymod\v1_21_11') $readableVersion /E /NFL /NDL /NJH /NJS /NP | Out-Null

$count = (Get-ChildItem -Recurse -Filter *.java $readable | Measure-Object).Count
Write-Host "readable sources: $count"
Write-Host "source root: $readable"
