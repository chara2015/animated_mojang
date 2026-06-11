package animated_mojang.client.internal;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.MipmapStrategy;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathTexture extends ReloadableTexture {
	private final String path;

	public ClasspathTexture(Identifier id) {
		super(id);
		this.path = "assets/" + id.getNamespace() + "/" + id.getPath();
	}

	@Override
	public TextureContents loadContents(ResourceManager resourceManager) throws IOException {
		InputStream stream = ClasspathTexture.class.getClassLoader().getResourceAsStream(path);
		if (stream == null) {
			throw new IOException("Missing classpath texture " + path);
		}

		try (stream) {
			return new TextureContents(NativeImage.read(stream),
					new TextureMetadataSection(false, false, MipmapStrategy.AUTO, TextureMetadataSection.DEFAULT_ALPHA_CUTOFF_BIAS));
		}
	}
}
