$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$overlay = Join-Path $script:WorkspaceRoot 'src-named-overlay\java\net\labymod\v1_21_11'
$reportPath = Join-Path $script:WorkspaceRoot 'reports\ui-member-overlay-replacements.csv'
$rows = New-Object System.Collections.Generic.List[object]
$minecraftGetInstance = '(?:net\.minecraft\.client\.)?Minecraft\.getInstance\(\)'

function Replace-Pattern {
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
            pattern = $Name
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

    $guiReceiver = "((?:$minecraftGetInstance\.gui)|(?:this\.gui)|(?:chatGui)|(?:gui))"
    $text = Replace-Pattern $text $relative "$guiReceiver\.e\(\)" '${1}.getChat()' 'Gui.e() -> getChat()'
    $text = Replace-Pattern $text $relative "$guiReceiver\.d\(\)" '${1}.clearTitles()' 'Gui.d() -> clearTitles()'
    $text = Replace-Pattern $text $relative "$guiReceiver\.i\(\)" '${1}.getTabList()' 'Gui.i() -> getTabList()'
    $text = Replace-Pattern $text $relative "$guiReceiver\.c\(((?:\(yh\)\s*)?[^;\n]+)\)" '${1}.setTitle(${2})' 'Gui.c(Component) -> setTitle(Component)'
    $text = Replace-Pattern $text $relative "$guiReceiver\.b\(((?:\(yh\)\s*)?[^;\n]+)\)" '${1}.setSubtitle(${2})' 'Gui.b(Component) -> setSubtitle(Component)'
    $text = Replace-Pattern $text $relative "$guiReceiver\.a\((title\.getFadeInTicks\(\)),\s*(title\.getStayTicks\(\)),\s*(title\.getFadeOutTicks\(\))\)" '${1}.setTimes(${2}, ${3}, ${4})' 'Gui.a(title times) -> setTimes(...)'

    $windowReceiver = "($minecraftGetInstance\.getWindow\(\)|window)"
    $text = Replace-Pattern $text $relative "$windowReceiver\.h\(\)" '${1}.handle()' 'Window.h() -> handle()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.k\(\)" '${1}.getWidth()' 'Window.k() -> getWidth()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.l\(\)" '${1}.getHeight()' 'Window.l() -> getHeight()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.m\(\)" '${1}.getScreenWidth()' 'Window.m() -> getScreenWidth()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.n\(\)" '${1}.getScreenHeight()' 'Window.n() -> getScreenHeight()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.o\(\)" '${1}.getGuiScaledWidth()' 'Window.o() -> getGuiScaledWidth()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.p\(\)" '${1}.getGuiScaledHeight()' 'Window.p() -> getGuiScaledHeight()'
    $text = Replace-Pattern $text $relative "$windowReceiver\.s\(\)" '${1}.getGuiScale()' 'Window.s() -> getGuiScale()'

    $rendererReceiver = "($minecraftGetInstance\.gameRenderer|this\.gameRenderer|gameRenderer|renderer)"
    $text = Replace-Pattern $text $relative "$rendererReceiver\.l\(\)" '${1}.getDepthFar()' 'GameRenderer.l() -> getDepthFar()'
    $text = Replace-Pattern $text $relative "$rendererReceiver\.n\(\)" '${1}.getMinecraft()' 'GameRenderer.n() -> getMinecraft()'
    $text = Replace-Pattern $text $relative "$rendererReceiver\.p\(\)" '${1}.getMainCamera()' 'GameRenderer.p() -> getMainCamera()'
    $text = Replace-Pattern $text $relative "$rendererReceiver\.q\(\)" '${1}.lightTexture()' 'GameRenderer.q() -> lightTexture()'
    $text = Replace-Pattern $text $relative "$rendererReceiver\.r\(\)" '${1}.overlayTexture()' 'GameRenderer.r() -> overlayTexture()'

    if ($text -ne $original) {
        Set-Content -Encoding UTF8 -Path $path -Value $text
    }
}

$rows | Sort-Object file, pattern | Export-Csv -NoTypeInformation -Encoding UTF8 $reportPath
$sum = ($rows | Measure-Object -Property count -Sum).Sum
if ($null -eq $sum) { $sum = 0 }
Write-Host "ui member replacements: $sum"
Write-Host "report: $reportPath"
