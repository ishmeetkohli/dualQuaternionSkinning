#version 150

const int MAX_JOINTS = 50; //max joints allowed in a skeleton
const int MAX_WEIGHTS = 3; //max number of joints that can affect a vertex

in vec3 in_position;
in vec2 in_textureCoords;
in vec3 in_normal;
in ivec3 in_jointIndices;
in vec3 in_weights;

out vec2 pass_textureCoords;
out vec3 pass_normal;

uniform mat4 jointTransforms[MAX_JOINTS];
uniform mat4 projectionViewMatrix;

void main(void) {
	mat4 blendDQ = mat4(0);

	for (int i = 0; i < MAX_WEIGHTS; i++) {
		mat4 jointDQ = jointTransforms[in_jointIndices[i]];
		blendDQ += jointDQ * in_weights[i];
	}

	blendDQ /= length(blendDQ[0]);

	vec3 posePosition = in_position.xyz + 2.0*cross(blendDQ[0].yzw, cross(blendDQ[0].yzw, in_position.xyz) + blendDQ[0].x*in_position.xyz);
	vec3 trans = 2.0*(blendDQ[0].x*blendDQ[1].yzw - blendDQ[1].x*blendDQ[0].yzw + cross(blendDQ[0].yzw, blendDQ[1].yzw));
	posePosition += trans;

	vec3 worldNormal = in_normal + 2.0*cross(blendDQ[0].yzw, cross(blendDQ[0].yzw, in_normal) + blendDQ[0].x*in_normal);

	gl_Position = projectionViewMatrix * vec4(posePosition, 1.0);
	pass_normal = worldNormal;
	pass_textureCoords = in_textureCoords;
}
