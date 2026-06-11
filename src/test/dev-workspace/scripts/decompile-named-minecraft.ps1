$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$output = Join-Path $script:WorkspaceRoot 'minecraft-named'

if (-not (Test-Path $script:Jadx)) {
    throw "jadx not found: $script:Jadx"
}

if (Test-Path $output) {
    Write-Host "Output already exists: $output"
    Write-Host 'Delete it first if you want a clean decompile.'
    return
}

& $script:Jadx -d $output $script:NamedMinecraftJar
