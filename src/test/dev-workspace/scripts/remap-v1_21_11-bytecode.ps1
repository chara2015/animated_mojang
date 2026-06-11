$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$librariesRootInfo = (Get-Item $script:LabyModCoreJar).Directory
while ($librariesRootInfo -and $librariesRootInfo.Name -ne 'libraries') {
    $librariesRootInfo = $librariesRootInfo.Parent
}
if (-not $librariesRootInfo) {
    throw "could not locate libraries root from: $script:LabyModCoreJar"
}
$librariesRoot = $librariesRootInfo.FullName

$toolClasspath = @(
    Join-Path $librariesRoot 'net\labymod\AutoRenamingTool\0.1.4.1.0.2\AutoRenamingTool-0.1.4.1.0.2.jar'
    Join-Path $librariesRoot 'net\sf\jopt-simple\jopt-simple\5.0.4\jopt-simple-5.0.4.jar'
    Join-Path $librariesRoot 'net\minecraftforge\srgutils\0.5.4\srgutils-0.5.4.jar'
    Join-Path $librariesRoot 'com\google\code\gson\gson\2.13.2\gson-2.13.2.jar'
    Join-Path $librariesRoot 'net\neoforged\installertools\cli-utils\2.1.2\cli-utils-2.1.2.jar'
    Join-Path $librariesRoot 'org\ow2\asm\asm\9.9.1\asm-9.9.1.jar'
    Join-Path $librariesRoot 'org\ow2\asm\asm-tree\9.9.1\asm-tree-9.9.1.jar'
    Join-Path $librariesRoot 'org\ow2\asm\asm-commons\9.9.1\asm-commons-9.9.1.jar'
    Join-Path $librariesRoot 'org\ow2\asm\asm-util\9.9.1\asm-util-9.9.1.jar'
    Join-Path $librariesRoot 'org\ow2\asm\asm-analysis\9.9.1\asm-analysis-9.9.1.jar'
) | Where-Object { Test-Path $_ }

$input = Join-Path $script:WorkspaceRoot 'libs\LabyMod-4-v1_21_11-obf.jar'
$output = Join-Path $script:WorkspaceRoot 'libs\LabyMod-4-v1_21_11-named.jar'
$obfuscatedMinecraft = Join-Path $script:ProjectRoot 'LabyMod-4-1.21.11-production.jar'

$argsList = @(
    '-cp', ($toolClasspath -join ';'),
    'net.minecraftforge.fart.Main',
    '--input', $input,
    '--output', $output,
    '--names', $script:MappingFile,
    '--reverse',
    '--threads', '1',
    '--strip-sigs',
    '--record-fix',
    '--ann-fix',
    '--ids-fix', 'ALL',
    '-e', $obfuscatedMinecraft,
    '-e', $script:LabyModCoreJar
)

foreach ($lib in (Get-DevClasspath)) {
    if ((Test-Path $lib) -and
        ($lib -ne $script:NamedMinecraftJar) -and
        ($lib -ne $script:LabyModCoreJar)) {
        $argsList += @('-e', $lib)
    }
}

& java @argsList

if (-not (Test-Path $output)) {
    throw "remapped jar was not created: $output"
}

Write-Host "remapped jar: $output"
