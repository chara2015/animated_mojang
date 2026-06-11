# LabyMod Startup Animation Notes

这个文件夹用于集中查看 LabyMod 启动动画相关素材和实现代码。

## 来源

素材来自：

```text
F:\.idea\antiobf\obf\labymod-neo\assets\common.jar
```

实现代码来自：

```text
F:\.idea\antiobf\dev-workspace\src-readable\java
```

## 文件夹结构

```text
startup-animation-package
├─ assets
│  └─ assets/labymod
│     ├─ animations/cave_entities.animation.json
│     ├─ data/cave_entities.bbmodel
│     ├─ data/normal_cave.schem
│     ├─ data/winter_cave.schem
│     ├─ models/cave_entities.geo.json
│     └─ textures/splash
│        ├─ labymod.png
│        ├─ minecraft_sprite.png
│        ├─ cave/*.png
│        ├─ minecraft/minecraft_0.png ... minecraft_8.png
│        └─ mojangstudios/mojangstudios_0.png ... mojangstudios_11.png
└─ code
   └─ net/labymod/...
```

## 动画素材

核心 opening 动画：

```text
assets/assets/labymod/animations/cave_entities.animation.json
assets/assets/labymod/data/cave_entities.bbmodel
assets/assets/labymod/models/cave_entities.geo.json
assets/assets/labymod/textures/splash/cave/entities.png
```

洞穴背景：

```text
assets/assets/labymod/data/normal_cave.schem
assets/assets/labymod/data/winter_cave.schem
assets/assets/labymod/textures/splash/cave/blocks.png
assets/assets/labymod/textures/splash/cave/lava_flow.png
assets/assets/labymod/textures/splash/cave/particles.png
```

特殊天气/节日效果：

```text
assets/assets/labymod/textures/splash/cave/snow.png
assets/assets/labymod/textures/splash/cave/hearts.png
```

Loading logo 相关：

```text
assets/assets/labymod/textures/splash/labymod.png
assets/assets/labymod/textures/splash/mojangstudios/*.png
assets/assets/labymod/textures/splash/minecraft/*.png
assets/assets/labymod/textures/splash/minecraft_sprite.png
```

## 实现入口

Minecraft 创建 LoadingOverlay 时被替换：

```text
code/net/labymod/v1_21_11/mixins/client/MixinMinecraft.java
```

`CustomLoadingOverlay` 在资源加载完成后触发洞穴 opening：

```text
code/net/labymod/v1_21_11/client/overlay/CustomLoadingOverlay.java
```

关键调用：

```java
this.caveRenderer.playOpening();
```

## Opening 动画实现

主要逻辑在：

```text
code/net/labymod/core/client/gui/background/DynamicBackgroundController.java
```

关键方法：

```java
public void playOpening()
```

它会：

```java
camera.teleport(POS_OPENER_START);
camera.moveTo(4000L, OPENER_CURVE, POS_OPENER_TRANSFER, POS_TITLE_SCREEN);
this.entityAnimationController.playNext(animation);
```

也就是摄像机从远处飞入主菜单洞穴位置，同时播放实体模型里的 `opening` 动画。

关键位置常量：

```java
POS_OPENER_START
POS_OPENER_TRANSFER
POS_TITLE_SCREEN
POS_PLAYER_ENTITY
```

## 主菜单衔接

主菜单创建和兜底播放 opening：

```text
code/net/labymod/core/client/gui/screen/activity/activities/menu/MainMenuActivity.java
```

主菜单每帧渲染、UI 入场缩放/旋转/透明度：

```text
code/net/labymod/core/client/gui/screen/widget/widgets/title/MainMenuWidget.java
```

关键方法：

```java
renderWidget(...)
transformUI(...)
```

`transformUI` 会根据摄像机和 `POS_TITLE_SCREEN` 的距离，把 UI 从较大缩放和旋转状态过渡到正常 2D 菜单。

## Logo/加载画面

Boot logo 控制器：

```text
code/net/labymod/core/client/gui/background/BootLogoController.java
```

Logo 抽象层：

```text
code/net/labymod/core/client/gui/background/bootlogo/AbstractBootLogoRenderer.java
```

新版 Mojang Studios/LabyMod logo 渲染：

```text
code/net/labymod/core/client/gui/background/bootlogo/MojangStudiosBootLogoRenderer.java
```

旧版 logo 渲染：

```text
code/net/labymod/core/client/gui/background/bootlogo/LegacyMojangBootLogoRenderer.java
```

资源常量定义：

```text
code/net/labymod/api/Constants.java
code/net/labymod/api/Textures.java
```

## 修改建议

如果要改“加载完之后飞入主菜单”的动画：

1. 改摄像机路径：`DynamicBackgroundController.java` 里的 `POS_OPENER_START`、`POS_OPENER_TRANSFER`、`POS_TITLE_SCREEN`、`OPENER_CURVE`、`4000L`。
2. 改实体动作：`cave_entities.animation.json` 里的 `opening` 动画。
3. 改实体模型/骨骼：`cave_entities.bbmodel` 或 `cave_entities.geo.json`。
4. 改洞穴场景：`normal_cave.schem` / `winter_cave.schem`。
5. 改贴图：`textures/splash/cave/*.png`。

如果只想改 Mojang/LabyMod loading 贴图，看 `textures/splash/labymod.png` 和 `textures/splash/mojangstudios/*.png`。
