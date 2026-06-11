$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$sourceDir = Join-Path $script:WorkspaceRoot 'minecraft-named-core'
$workDir = Join-Path $script:WorkspaceRoot 'tmp\minecraft-named-core-sources'
$jarPath = Join-Path $script:WorkspaceRoot 'libs\minecraft-client-1.21.11-core-sources.jar'

if (-not (Test-Path $sourceDir)) {
    Write-Host "No core Minecraft sources found: $sourceDir"
    return
}

if (Test-Path $workDir) {
    Remove-Item -LiteralPath $workDir -Recurse -Force
}

New-Item -ItemType Directory -Force -Path $workDir | Out-Null

Get-ChildItem -Filter *.java $sourceDir | ForEach-Object {
    $text = Get-Content $_.FullName -Raw
    if ($text -match '(?m)^package\s+([^;]+);') {
        $packagePath = $matches[1] -replace '\.', '\'
        $targetDir = Join-Path $workDir $packagePath
    }
    else {
        $targetDir = $workDir
    }

    New-Item -ItemType Directory -Force -Path $targetDir | Out-Null
    Copy-Item -LiteralPath $_.FullName -Destination (Join-Path $targetDir $_.Name) -Force
}

if (Test-Path $jarPath) {
    Remove-Item -LiteralPath $jarPath -Force
}

Push-Location $workDir
try {
    & (Join-Path $script:Jdk21 'bin\jar.exe') --create --file $jarPath .
}
finally {
    Pop-Location
}

Write-Host "source jar: $jarPath"
