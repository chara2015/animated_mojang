$ErrorActionPreference = 'Stop'

$script:WorkspaceRoot = Split-Path -Parent $PSScriptRoot
$script:ProjectRoot = 'F:\.idea\antiobf'
$script:Jdk21 = 'D:\Java\jdk-21'
$script:Jadx = Join-Path $script:ProjectRoot 'obf\jadx-1.5.5\bin\jadx.bat'
$script:NamedMinecraftJar = Join-Path $script:WorkspaceRoot 'libs\minecraft-client-1.21.11-named.jar'
$script:LabyModCoreJar = Get-Content (Join-Path $script:WorkspaceRoot 'classpath.txt') |
    Where-Object { $_ -like '*\net\labymod\LabyMod\4\LabyMod-4.jar' } |
    Select-Object -First 1
$script:MappingFile = Join-Path $script:ProjectRoot 'obf\labymod-neo\mappings\1.21.11-mojang.proguard'

$env:JAVA_HOME = $script:Jdk21
$env:Path = (Join-Path $script:Jdk21 'bin') + ';' + $env:Path

function Get-DevClasspath {
    Get-Content (Join-Path $script:WorkspaceRoot 'classpath.txt')
}

function Find-MojangClass {
    param(
        [Parameter(Mandatory = $true)]
        [string] $Name
    )

    Import-Csv (Join-Path $script:ProjectRoot 'deobf-output\mojang_class_map_1.21.11.csv') |
        Where-Object { $_.obfuscated -eq $Name -or $_.named -like "*$Name*" }
}

function Find-MojangMember {
    param(
        [Parameter(Mandatory = $true)]
        [string] $Owner,

        [string] $Name = '*'
    )

    Import-Csv (Join-Path $script:ProjectRoot 'deobf-output\mojang_member_map_1.21.11.csv') |
        Where-Object {
            ($_.owner_obfuscated -eq $Owner -or $_.owner_named -like "*$Owner*") -and
            ($Name -eq '*' -or $_.obfuscated -eq $Name -or $_.named -like "*$Name*")
        }
}

Write-Host "Dev env loaded: $script:WorkspaceRoot"
Write-Host "JAVA_HOME=$env:JAVA_HOME"
