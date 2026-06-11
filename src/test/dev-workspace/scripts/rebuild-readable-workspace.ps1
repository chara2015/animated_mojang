$ErrorActionPreference = 'Stop'

& "$PSScriptRoot\remap-v1_21_11-bytecode.ps1"
& "$PSScriptRoot\decompile-v1_21_11-named.ps1"
& "$PSScriptRoot\sync-named-bytecode-sources.ps1"
& "$PSScriptRoot\apply-short-class-overlay.ps1"
& "$PSScriptRoot\apply-core-member-overlay.ps1"
& "$PSScriptRoot\apply-ui-member-overlay.ps1"
& "$PSScriptRoot\apply-shadow-member-overlay.ps1"
& "$PSScriptRoot\apply-semantic-member-overlay.ps1"
& "$PSScriptRoot\build-readable-source.ps1"
& "$PSScriptRoot\package-minecraft-core-sources.ps1"
& "$PSScriptRoot\setup-idea-project.ps1"
& "$PSScriptRoot\verify.ps1"
