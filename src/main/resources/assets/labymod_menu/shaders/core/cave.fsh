#version 330

#moj_import <minecraft:dynamictransforms.glsl>

#define MAX_LIGHTS 48

layout(std140) uniform Schematic {
    float LightSourceEnabled;
    vec3 LightSourcePosition[MAX_LIGHTS];
    vec3 LightSourceColor[MAX_LIGHTS];
    float LightSourceConstant[MAX_LIGHTS];
    float LightSourceLinear[MAX_LIGHTS];
    float LightSourceQuadratic[MAX_LIGHTS];
};

uniform sampler2D Sampler0;

in vec4 vertexColor;
in vec2 texCoord0;
in vec3 worldPosition;

out vec4 fragColor;

vec3 calcLight(vec3 lightPos, vec3 lightColor, float constant, float linear, float quadratic) {
    float distanceToLight = length(lightPos);
    float attenuation = 1.0 / (
        constant + linear * distanceToLight + quadratic * distanceToLight * distanceToLight
    );
    return lightColor * attenuation;
}

void main() {
    vec4 textureColor = texture(Sampler0, texCoord0) * ColorModulator;
#ifdef ALPHA_CUTOUT
    if ((textureColor * vertexColor).a < ALPHA_CUTOUT) {
        discard;
    }
#endif

    vec3 totalDiffuse = vec3(1.0);
    if (LightSourceEnabled > 0.5) {
        totalDiffuse = vec3(0.0);
        for (int index = 0; index < MAX_LIGHTS; index++) {
            float quadratic = LightSourceQuadratic[index];
            if (quadratic == 0.0) {
                continue;
            }
            totalDiffuse += calcLight(
                LightSourcePosition[index] - worldPosition,
                LightSourceColor[index],
                LightSourceConstant[index],
                LightSourceLinear[index],
                quadratic
            );
        }
    }

    vec4 finalColor = vec4(totalDiffuse, 1.0) * vertexColor * textureColor;
    if (finalColor.a < 0.1) {
        discard;
    }
    fragColor = finalColor;
}
