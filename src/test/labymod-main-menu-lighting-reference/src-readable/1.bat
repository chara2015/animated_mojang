@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

set "ROOT=F:\Animated_Mojang\src\test\labymod-main-menu-lighting-reference\src-readable"
set "OUT=%ROOT%\laby_lighting_sources.txt"

echo Exporting important lighting sources...

(
echo ==========================================
echo LabyMod Lighting Important Sources
echo ==========================================
echo.

for %%F in (
"java\net\labymod\api\laby3d\shaders\block\SchematicUniformBlock.java"
"java\net\labymod\api\laby3d\laby3d\shaders\block\SchematicUniformBlock.java"
"java\net\labymod\core\client\render\schematic\SchematicRenderer.java"
"java\net\labymod\core\client\render\schematic\ShaderCamera.java"
"java\net\labymod\core\client\render\schematic\block\BlockRenderer.java"
"java\net\labymod\core\client\render\schematic\block\Face.java"
"java\net\labymod\core\client\render\schematic\lighting\LightSource.java"
"java\net\labymod\core\client\render\schematic\lighting\LightSourceRegistry.java"
"java\net\labymod\core\client\render\schematic\lighting\PointLightSource.java"
"java\net\labymod\core\client\render\schematic\lighting\legacy\ColoredLegacyLightEngine.java"
"java\net\labymod\core\client\render\schematic\lighting\legacy\DefaultLegacyLightEngine.java"
"java\net\labymod\core\client\render\schematic\lighting\legacy\LegacyLightEngine.java"
"java\net\labymod\core\client\render\schematic\block\material\material\TorchMaterial.java"
"java\net\labymod\core\client\render\schematic\block\material\material\LavaMaterial.java"
"java\net\labymod\core\client\render\schematic\block\material\material\GlowstoneMaterial.java"
) do (
    echo.
    echo ==================================================
    echo FILE: %%~F
    echo ==================================================
    if exist "%ROOT%\%%~F" (
        type "%ROOT%\%%~F"
    ) else (
        echo MISSING: %ROOT%\%%~F
    )
)

) > "%OUT%"

echo Done.
echo Output:
echo %OUT%
pause