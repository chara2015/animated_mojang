package animated_mojang.client.legacy;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.client.gui.GuiGraphics;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Function;

final class LegacyRenderBridge {
	private static final Method DIRECT_LOCATION = findDirectMethod("location", String.class, String.class);
	private static final Method DIRECT_BLIT = findDirectMethod("blit", GuiGraphics.class, Object.class,
			int.class, int.class, float.class, float.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class);
	private static final MappingResolver MAPPINGS = FabricLoader.getInstance().getMappingResolver();
	private static final Class<?> LOCATION_CLASS = findClass(
			"net.minecraft.class_2960", "net.minecraft.resources.Identifier", "net.minecraft.resources.ResourceLocation");
	private static final Object GUI_TEXTURED_PIPELINE = findGuiTexturedPipeline();
	private static final Method GUI_TEXTURED_RENDER_TYPE = findRenderTypeFactory();
	private static final Method REFLECTIVE_BLIT = findReflectiveBlit();

	private LegacyRenderBridge() {
	}

	static Object location(String namespace, String path) {
		if (DIRECT_LOCATION != null) {
			return invoke(DIRECT_LOCATION, null, namespace, path);
		}
		try {
			for (Method method : LOCATION_CLASS.getMethods()) {
				if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == LOCATION_CLASS
						&& hasParameters(method, String.class, String.class)) {
					return method.invoke(null, namespace, path);
				}
			}
			Constructor<?> constructor = LOCATION_CLASS.getDeclaredConstructor(String.class, String.class);
			constructor.setAccessible(true);
			return constructor.newInstance(namespace, path);
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Unable to create Minecraft resource location", exception);
		}
	}

	static void blit(GuiGraphics graphics, Object texture, int x, int y, float u, float v, int width, int height,
			int sourceWidth, int sourceHeight, int textureWidth, int textureHeight, int color) {
		if (DIRECT_BLIT != null) {
			invoke(DIRECT_BLIT, null, graphics, texture, x, y, u, v, width, height,
					sourceWidth, sourceHeight, textureWidth, textureHeight, color);
			return;
		}
		if (REFLECTIVE_BLIT == null) {
			throw new IllegalStateException("No supported GuiGraphics.blit overload was found");
		}
		Class<?>[] parameters = REFLECTIVE_BLIT.getParameterTypes();
		try {
			if (parameters.length == 13 && GUI_TEXTURED_PIPELINE != null
					&& parameters[0].isInstance(GUI_TEXTURED_PIPELINE)) {
				REFLECTIVE_BLIT.invoke(graphics, GUI_TEXTURED_PIPELINE, texture, x, y, u, v, width, height,
						sourceWidth, sourceHeight, textureWidth, textureHeight, color);
			} else if (parameters.length == 13) {
				Function<Object, Object> renderType = location -> invoke(GUI_TEXTURED_RENDER_TYPE, null, location);
				REFLECTIVE_BLIT.invoke(graphics, renderType, texture, x, y, u, v, width, height,
						sourceWidth, sourceHeight, textureWidth, textureHeight, color);
			} else {
				REFLECTIVE_BLIT.invoke(graphics, texture, x, y, width, height, u, v, sourceWidth, sourceHeight,
						textureWidth, textureHeight);
			}
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException("Unable to invoke Minecraft GUI texture renderer", exception);
		}
	}

	private static Method findReflectiveBlit() {
		for (Method method : GuiGraphics.class.getMethods()) {
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 13 && GUI_TEXTURED_PIPELINE != null
					&& parameters[0].isInstance(GUI_TEXTURED_PIPELINE) && parameters[1] == LOCATION_CLASS
					&& numericTextureParameters(parameters, 2)) {
				return method;
			}
			if (parameters.length == 13 && Function.class.isAssignableFrom(parameters[0])
					&& parameters[1] == LOCATION_CLASS && GUI_TEXTURED_RENDER_TYPE != null
					&& numericTextureParameters(parameters, 2)) {
				return method;
			}
			if (parameters.length == 11 && parameters[0] == LOCATION_CLASS
					&& numericTextureParameters(parameters, 1)) {
				return method;
			}
		}
		return null;
	}

	private static Class<?> findClass(String... names) {
		for (String name : names) {
			try {
				String runtimeName = name.startsWith("net.minecraft.class_")
						? MAPPINGS.mapClassName("intermediary", name) : name;
				return Class.forName(runtimeName);
			} catch (ClassNotFoundException ignored) {
			}
		}
		throw new IllegalStateException("No supported Minecraft resource location class was found");
	}

	private static Method findDirectMethod(String name, Class<?>... parameters) {
		try {
			return Class.forName("animated_mojang.client.legacy.IdentifierRenderBridge").getMethod(name, parameters);
		} catch (ReflectiveOperationException ignored) {
			return null;
		}
	}

	private static Object findGuiTexturedPipeline() {
		Class<?> pipelineType = null;
		for (Method method : GuiGraphics.class.getMethods()) {
			Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length == 13 && parameters[1] == LOCATION_CLASS && numericTextureParameters(parameters, 2)
					&& !Function.class.isAssignableFrom(parameters[0])) {
				pipelineType = parameters[0];
				break;
			}
		}
		if (pipelineType == null) {
			return null;
		}
		try {
			Class<?> pipelines = findClass("net.minecraft.class_10799", "net.minecraft.client.renderer.RenderPipelines");
			String mappedField = MAPPINGS.mapFieldName("intermediary", "net.minecraft.class_10799", "field_56883",
					"Lcom/mojang/blaze3d/pipeline/RenderPipeline;");
			for (String fieldName : new String[] { mappedField, "GUI_TEXTURED" }) {
				try {
					Field field = pipelines.getField(fieldName);
					if (Modifier.isStatic(field.getModifiers()) && field.getType() == pipelineType) {
						return field.get(null);
					}
				} catch (ReflectiveOperationException ignored) {
				}
			}
		} catch (IllegalStateException ignored) {
		}
		return null;
	}

	private static Method findRenderTypeFactory() {
		try {
			Class<?> renderType = findClass("net.minecraft.class_1921", "net.minecraft.client.renderer.RenderType");
			for (Method method : renderType.getMethods()) {
				if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == renderType
						&& hasParameters(method, LOCATION_CLASS)) {
					return method;
				}
			}
		} catch (IllegalStateException ignored) {
		}
		return null;
	}

	private static boolean hasParameters(Method method, Class<?>... expected) {
		Class<?>[] actual = method.getParameterTypes();
		if (actual.length != expected.length) {
			return false;
		}
		for (int index = 0; index < actual.length; index++) {
			if (actual[index] != expected[index]) {
				return false;
			}
		}
		return true;
	}

	private static boolean numericTextureParameters(Class<?>[] parameters, int offset) {
		for (int index = offset; index < parameters.length; index++) {
			if (parameters[index] != int.class && parameters[index] != float.class) {
				return false;
			}
		}
		return true;
	}

	private static Object invoke(Method method, Object target, Object... arguments) {
		try {
			return method.invoke(target, arguments);
		} catch (ReflectiveOperationException exception) {
			throw new IllegalStateException(exception);
		}
	}
}
