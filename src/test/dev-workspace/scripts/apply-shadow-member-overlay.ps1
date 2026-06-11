$ErrorActionPreference = 'Stop'
. "$PSScriptRoot\env.ps1"

$overlay = Join-Path $script:WorkspaceRoot 'src-named-overlay\java\net\labymod\v1_21_11'
$reportPath = Join-Path $script:WorkspaceRoot 'reports\shadow-member-overlay-replacements.csv'

$classRows = Import-Csv (Join-Path $script:ProjectRoot 'deobf-output\mojang_class_map_1.21.11.csv')
$memberRows = Import-Csv (Join-Path $script:ProjectRoot 'deobf-output\mojang_member_map_1.21.11.csv')

$membersByOwner = @{}
foreach ($row in $memberRows) {
    if (-not $membersByOwner.ContainsKey($row.owner_named)) {
        $membersByOwner[$row.owner_named] = New-Object System.Collections.Generic.List[object]
    }
    $membersByOwner[$row.owner_named].Add($row)
}

function Get-SimpleName {
    param([string] $Type)
    $clean = ($Type -replace '@[^ ]+\s*', '') -replace '\s+', ' '
    $clean = $clean.Trim()
    if ($clean -match '<') {
        $clean = $clean.Substring(0, $clean.IndexOf('<'))
    }
    if ($clean -match '\.') {
        $clean = $clean.Substring($clean.LastIndexOf('.') + 1)
    }
    if ($clean -match '\$') {
        return $clean.Substring($clean.LastIndexOf('$') + 1)
    }
    return $clean
}

function Get-TargetClass {
    param([string] $Text)

    if ($Text -match '@Mixin\(\s*targets\s*=\s*\{\s*"([^"]+)"\s*\}') {
        return $matches[1].Replace('/', '.')
    }

    if ($Text -notmatch '@Mixin\(\{([^}]+)\.class\}\)') {
        return $null
    }

    $target = $matches[1].Trim()
    if ($target -match '^(net\.minecraft|com\.mojang)\.') {
        return $target
    }

    if ($target -match '\.') {
        $outer = $target.Substring(0, $target.IndexOf('.'))
        $inner = $target.Substring($target.IndexOf('.') + 1).Replace('.', '$')
        $outerImportPattern = "(?m)^import\s+([A-Za-z0-9_$.]+\.$([regex]::Escape($outer)));\s*$"
        $outerImports = [regex]::Matches($Text, $outerImportPattern)
        if ($outerImports.Count -gt 0) {
            foreach ($import in $outerImports) {
                $fqcn = $import.Groups[1].Value
                if ($fqcn -match '^(net\.minecraft|com\.mojang)\.') {
                    return "$fqcn`$$inner"
                }
            }

            return "$($outerImports[0].Groups[1].Value)`$$inner"
        }

        return $target
    }

    $importPattern = "(?m)^import\s+([A-Za-z0-9_$.]+\.$([regex]::Escape($target)));\s*$"
    $imports = [regex]::Matches($Text, $importPattern)
    if ($imports.Count -gt 0) {
        foreach ($import in $imports) {
            $fqcn = $import.Groups[1].Value
            if ($fqcn -match '^(net\.minecraft|com\.mojang)\.') {
                return $fqcn
            }
        }

        return $imports[0].Groups[1].Value
    }

    return $null
}

function Get-ParamCount {
    param([string] $Params)
    $trimmed = $Params.Trim()
    if (-not $trimmed) {
        return 0
    }
    return ($trimmed -split ',').Count
}

function Split-TopLevelParams {
    param([string] $Params)

    $items = New-Object System.Collections.Generic.List[string]
    $current = New-Object System.Text.StringBuilder
    $depth = 0

    foreach ($ch in $Params.ToCharArray()) {
        if ($ch -eq '<') {
            $depth++
        }
        elseif ($ch -eq '>' -and $depth -gt 0) {
            $depth--
        }

        if ($ch -eq ',' -and $depth -eq 0) {
            $items.Add($current.ToString().Trim())
            [void] $current.Clear()
            continue
        }

        [void] $current.Append($ch)
    }

    $tail = $current.ToString().Trim()
    if ($tail) {
        $items.Add($tail)
    }

    return @($items.ToArray())
}

function Get-DeclaredParamTypes {
    param([string] $Params)

    $trimmed = $Params.Trim()
    if (-not $trimmed) {
        return @()
    }

    $types = @()
    foreach ($param in (Split-TopLevelParams $trimmed)) {
        $clean = $param -replace '@[A-Za-z0-9_.$]+(?:\([^)]*\))?\s*', ''
        $clean = $clean -replace '\bfinal\s+', ''
        $clean = $clean.Trim()

        if ($clean -match '^(?<type>.+?)\s+[A-Za-z_$][A-Za-z0-9_$]*$') {
            $types += (Get-SimpleName $matches['type'])
        }
        elseif ($clean) {
            $types += (Get-SimpleName $clean)
        }
    }

    return @($types)
}

function Get-MappedParamTypes {
    param([string] $Params)

    $trimmed = $Params.Trim()
    if (-not $trimmed) {
        return @()
    }

    $types = @()
    foreach ($param in (Split-TopLevelParams $trimmed)) {
        $types += (Get-SimpleName $param)
    }

    return @($types)
}

function Test-SameParamTypes {
    param(
        [string[]] $Declared,
        [string[]] $Mapped
    )

    if ($Declared.Count -ne $Mapped.Count) {
        return $false
    }

    for ($i = 0; $i -lt $Declared.Count; $i++) {
        if (($Declared[$i] -cne $Mapped[$i]) -and (-not $Mapped[$i].EndsWith($Declared[$i], [System.StringComparison]::Ordinal))) {
            return $false
        }
    }

    return $true
}

function Get-MappedMethodName {
    param(
        [object[]] $Candidates,
        [string] $ReturnType,
        [string] $Params
    )

    $returnSimple = Get-SimpleName $ReturnType
    $paramCount = Get-ParamCount $Params
    $paramTypes = @(Get-DeclaredParamTypes $Params)
    $filtered = @()
    $typedFiltered = @()

    foreach ($candidate in $Candidates) {
        if ($candidate.descriptor_hint -match '([^:\s]+)\s+([A-Za-z0-9_$]+)\(([^)]*)\)') {
            $mappedReturn = Get-SimpleName $matches[1]
            $mappedParams = $matches[3]
            if (($mappedReturn -eq $returnSimple) -and ((Get-ParamCount $mappedParams) -eq $paramCount)) {
                $filtered += $candidate
                $mappedParamTypes = @(Get-MappedParamTypes $mappedParams)
                if (Test-SameParamTypes $paramTypes $mappedParamTypes) {
                    $typedFiltered += $candidate
                }
            }
        }
    }

    if ($typedFiltered.Count -eq 1) {
        return $typedFiltered[0].named
    }

    if ($filtered.Count -eq 1) {
        return $filtered[0].named
    }

    if ($Candidates.Count -eq 1) {
        return $Candidates[0].named
    }

    return $null
}

$rows = New-Object System.Collections.Generic.List[object]

Get-ChildItem -Recurse -Filter *.java $overlay | ForEach-Object {
    $path = $_.FullName
    $relative = $path.Substring($overlay.Length + 1)
    $text = Get-Content $path -Raw
    $original = $text
    $targetClass = Get-TargetClass $text

    if (-not $targetClass -or -not $membersByOwner.ContainsKey($targetClass)) {
        return
    }

    $ownerMembers = @($membersByOwner[$targetClass].ToArray())
    $renames = [System.Collections.Generic.Dictionary[string,string]]::new([System.StringComparer]::Ordinal)

    $fieldMatches = [regex]::Matches($text, '(?ms)@Shadow(?:\([^)]*\))?\s*(?:@[A-Za-z0-9_.]+(?:\([^)]*\))?\s*)*(?<mods>(?:public|private|protected)?\s*(?:static\s+)?(?:final\s+)?)?(?<type>[A-Za-z0-9_.$<>, ?\[\]]+)\s+(?<name>[A-Za-z_$][A-Za-z0-9_$]*)\s*;')
    foreach ($match in $fieldMatches) {
        $oldName = $match.Groups['name'].Value
        $candidates = @($ownerMembers | Where-Object { $_.kind -eq 'field' -and $_.obfuscated -ceq $oldName })
        if ($candidates.Count -eq 1 -and $candidates[0].named -and $candidates[0].named -ne $oldName) {
            $newName = $candidates[0].named
            $renames[$oldName] = $newName
            $rows.Add([pscustomobject]@{ file = $relative; owner = $targetClass; kind = 'field'; obfuscated = $oldName; named = $newName })
        }
    }

    $methodMatches = [regex]::Matches($text, '(?ms)@Shadow(?:\([^)]*\))?\s*(?:@[A-Za-z0-9_.]+(?:\([^)]*\))?\s*)*(?<mods>(?:public|private|protected)?\s*(?:static\s+)?(?:abstract\s+)?)?(?<type>[A-Za-z0-9_.$<>, ?\[\]]+)\s+(?<name>(?:shadow\$)?[A-Za-z_$][A-Za-z0-9_$]*)\s*\((?<params>[^)]*)\)\s*(?:throws\s+[A-Za-z0-9_.$,\s]+)?(?:;|\{)')
    foreach ($match in $methodMatches) {
        $declaredName = $match.Groups['name'].Value
        $obfuscatedName = $declaredName -replace '^shadow\$', ''
        $candidates = @($ownerMembers | Where-Object { $_.kind -eq 'method' -and $_.obfuscated -ceq $obfuscatedName })
        if ($candidates.Count -eq 0) {
            continue
        }

        $mappedName = Get-MappedMethodName $candidates $match.Groups['type'].Value $match.Groups['params'].Value
        if ($mappedName -and $mappedName -ne $obfuscatedName) {
            $newDeclaredName = if ($declaredName.StartsWith('shadow$')) { 'shadow$' + $mappedName } else { $mappedName }
            $renames[$declaredName] = $newDeclaredName
            $rows.Add([pscustomobject]@{ file = $relative; owner = $targetClass; kind = 'method'; obfuscated = $declaredName; named = $newDeclaredName })
        }
    }

    foreach ($entry in $renames.GetEnumerator()) {
        $old = [regex]::Escape($entry.Key)
        $new = $entry.Value
        $text = [regex]::Replace($text, "(?<![A-Za-z0-9_$])$old(?![A-Za-z0-9_$])", { param($m) $new })
    }

    if ($text -ne $original) {
        Set-Content -Encoding UTF8 -Path $path -Value $text
    }
}

$rows | Sort-Object file, kind, obfuscated | Export-Csv -NoTypeInformation -Encoding UTF8 $reportPath
Write-Host "shadow member replacements: $($rows.Count)"
Write-Host "report: $reportPath"
