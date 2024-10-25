#version 150

uniform sampler2D Sampler0;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord);
    fragColor = vec4(1, 1, 1, color.a) - vec4(color.rgb, 0);
}