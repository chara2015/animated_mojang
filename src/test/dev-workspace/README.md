# LabyMod 4 / Minecraft 1.21.11 dev workspace

This workspace is for continuing deobfuscation and targeted development.

Important paths:

- `src/main/java`: raw JADX output from `LabyMod-4.jar`.
- `src-named-overlay/java`: generated readable overlay for `net.labymod.v1_21_11`, with Minecraft class names rewritten to Mojang names.
- `src-readable/java`: IDE-friendly merged source root; raw core/API plus the generated v1_21_11 overlay.
- `libs/minecraft-client-1.21.11-named.jar`: Minecraft client remapped from obfuscated to Mojang names.
- `classpath.txt`: libraries resolved from the real launch JSON and local `.minecraft/libraries`.
- `.vscode/settings.json`: Java source paths, resources, referenced libraries, and JDK 21.
- `reports/named-overlay-replacements.csv`: files touched by the generated class-name overlay.

Useful commands:

```powershell
.\scripts\rebuild-readable-workspace.ps1
.\scripts\verify.ps1
.\scripts\remap-v1_21_11-bytecode.ps1
.\scripts\decompile-v1_21_11-named.ps1
.\scripts\sync-named-bytecode-sources.ps1
.\scripts\rebuild-named-overlay.ps1
.\scripts\apply-short-class-overlay.ps1
.\scripts\apply-core-member-overlay.ps1
.\scripts\apply-ui-member-overlay.ps1
.\scripts\apply-shadow-member-overlay.ps1
.\scripts\repair-overlay-identifiers.ps1
.\scripts\build-readable-source.ps1
.\scripts\package-minecraft-core-sources.ps1
.\scripts\setup-idea-project.ps1
. .\scripts\env.ps1
Find-MojangClass gfj
Find-MojangMember gfj V
```

Current state:

- Minecraft classes are fully available in Mojang names through `libs/minecraft-client-1.21.11-named.jar`.
- LabyMod core and API sources are available from JADX output.
- The v1_21_11 overlay is readable enough for navigation and targeted edits.
- VS Code uses `src-readable/java` as the single source root to avoid duplicate raw/overlay classes.
- IntelliJ IDEA project files are generated in `.idea` and `labymod-deobf-dev.iml`.
- Minecraft member names in LabyMod source are not globally rewritten yet; use `Find-MojangMember` or `deobf-output/core_member_map.csv` when working on a specific class.
