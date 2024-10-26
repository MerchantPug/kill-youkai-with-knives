#version 150

in vec3 Position;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertCoord;

void main() {
    vec4 coord = ProjMat * ModelViewMat * vec4(Position, 1.0);
    gl_Position = coord;
    vertCoord = coord;
}
