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

mat4 DQToMatrix(in vec4 Qn, in vec4 Qd)
{
	mat4 M;
	float x = Qn.x, y = Qn.y, z = Qn.z, w = Qn.w;
//	float w = Qn.x, x = Qn.y, y = Qn.z, z = Qn.w;
	float t0 = Qd.x, t1 = Qd.y, t2 = Qd.z, t3 = Qd.w;

//	Rotation
	M[0][0] = w*w + x*x - y*y - z*z;
	M[0][1] = 2*x*y - 2*w*z;
	M[0][2] = 2*x*z + 2*w*y;

	M[1][0] = 2*x*y + 2*w*z;
	M[1][1] = w*w + y*y - x*x - z*z;
	M[1][2] = 2*y*z - 2*w*x;

	M[2][0] = 2*x*z - 2*w*y;
	M[2][1] = 2*y*z + 2*w*x;
	M[2][2] = w*w + z*z - x*x - y*y;


//	float xx = x * x, xy = x * y, xz = x * z, xw = x * w;
//	float yy = y * y, yz = y * z, yw = y * w, zz = z * z, zw = z * w;
//
//	M[0][0]  = 1 - 2 * ( yy + zz );
//	M[0][1]  =     2 * ( xy - zw );
//	M[0][2] =     2 * ( xz + yw );
//
//	M[1][0]  =     2 * ( xy + zw );
//	M[1][1]  = 1 - 2 * ( xx + zz );
//	M[1][2]  =     2 * ( yz - xw );
//
//	M[2][0]  =     2 * ( xz - yw );
//	M[2][1]  =     2 * ( yz + xw );
//	M[2][2] = 1 - 2 * ( xx + yy );


//	Translation
	M[0][3] = -2*t0*x + 2*w*t1 - 2*t2*z + 2*y*t3;
	M[1][3] = -2*t0*y + 2*t1*z - 2*x*t3 + 2*w*t2;
	M[2][3] = -2*t0*z + 2*x*t2 + 2*w*t3 - 2*t1*y;

//	M /= len2;

//	Dummy Row
	M[3][0] = 0;
	M[3][1] = 0;
	M[3][2] = 0;
	M[3][3] = 1;

	return M;
}

void main(void) {
	mat4 blendDQ = mat4(0);

	for (int i = 0; i < MAX_WEIGHTS; i++) {
		mat4 jointDQ = jointTransforms[in_jointIndices[i]];
		blendDQ += jointDQ * in_weights[i];
	}

	blendDQ = blendDQ/length(blendDQ[0]);

	mat4 matrix = DQToMatrix(blendDQ[0], blendDQ[1]);
	vec4 posePosition = matrix * vec4(in_position, 1.0);
	vec4 worldNormal = matrix * vec4(in_normal, 0.0);

	gl_Position = projectionViewMatrix * posePosition;
	pass_normal = worldNormal.xyz;
	pass_textureCoords = in_textureCoords;
}
