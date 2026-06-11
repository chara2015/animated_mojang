# LabyMod main menu lighting reference

This folder collects the LabyMod files relevant to the dynamic main-menu cave background,
schematic rendering, colored legacy light, point lights, particles, and render pipeline glue.

## Main entry points

- `src-readable/java/net/labymod/core/client/gui/background/DynamicBackgroundController.java`
  - Main menu dynamic background controller.
  - Contains camera positions such as `POS_TITLE_SCREEN`.
  - Calls `configureLighting(...)` and binds `SchematicUniformBlock`.

- `src-readable/java/net/labymod/core/client/render/schematic/SchematicRenderer.java`
  - Schematic render orchestration and layer rendering.

- `src-readable/java/net/labymod/core/client/render/schematic/block/BlockRenderer.java`
  - Emits block vertices.
  - Applies `legacyLightEngine.getRedStrengthAt(...)`, `getGreenStrengthAt(...)`,
    `getBlueStrengthAt(...)`.
  - Writes the `HasLighting` flag used by the shader path.

## Lighting

- `src-readable/java/net/labymod/core/client/render/schematic/lighting/`
  - `LightSource.java`
  - `PointLightSource.java`
  - `LightSourceRegistry.java`

- `src-readable/java/net/labymod/core/client/render/schematic/lighting/legacy/`
  - `LegacyLightEngine.java`
  - `DefaultLegacyLightEngine.java`
  - `ColoredLegacyLightEngine.java`
  - `WorldLightAccessor.java`

## Light-emitting materials

- `src-readable/java/net/labymod/core/client/render/schematic/block/material/material/TorchMaterial.java`
- `src-readable/java/net/labymod/core/client/render/schematic/block/material/material/LiquidMaterial.java`
- `src-readable/java/net/labymod/core/client/render/schematic/block/material/material/GlowstoneMaterial.java`
- `src-readable/java/net/labymod/core/client/render/schematic/block/material/material/FurnaceMaterial.java`

## Shader / pipeline glue

- `src-readable/java/net/labymod/api/laby3d/shaders/block/SchematicUniformBlock.java`
- `src-readable/java/net/labymod/api/laby3d/pipeline/RenderStates.java`
- `src-readable/java/net/labymod/api/laby3d/vertex/VertexDescriptions.java`
- `minecraft-shaders-referenced/assets/minecraft/shaders/`
  - Minecraft shader files available in the extracted workspace and referenced by the MC render pipeline.

## Assets

- `startup-animation-package/assets/assets/labymod/data/normal_cave.schem`
- `startup-animation-package/assets/assets/labymod/data/winter_cave.schem`
- `startup-animation-package/assets/assets/labymod/textures/splash/cave/`
  - `blocks.png`
  - `lava_flow.png`
  - `particles.png`
  - `entities.png`
  - `snow.png`
  - `hearts.png`

## Current port assets

- `assets-used-by-our-port/`
  - The assets currently copied into / used by the Animated_Mojang port.
