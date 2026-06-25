#version 330

#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec3 Normal;

out vec4 vertexColor;
out vec2 texCoord0;
out vec3 worldPosition;

void main() {
    vec3 pos = Position + ModelOffset;
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);

    float shading = Normal.x != 0.0 ? 0.6 : Normal.y != 0.0 ? 1.0 : 0.8;
    vertexColor = Color * vec4(shading, shading, shading, 1.0);
    texCoord0 = UV0;
    worldPosition = Position;
}
