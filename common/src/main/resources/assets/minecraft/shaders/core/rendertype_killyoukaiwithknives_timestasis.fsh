#version 150

uniform sampler2D Sampler0;

in vec4 vertCoord;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, (vertCoord.xy / vertCoord.w) / 2 + vec2(0.5, 0.5));
    fragColor = vec4(1, 1, 1, color.a) - vec4(color.rgb, 0);
}