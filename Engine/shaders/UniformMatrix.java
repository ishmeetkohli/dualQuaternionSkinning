package shaders;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

public class UniformMatrix extends Uniform{
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public UniformMatrix(String name) {
		super(name);
	}
	
	public void loadMatrix(Matrix4f matrix){
//		matrix.transpose(matrix);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(super.getLocation(), false, matrixBuffer);
	}
	
	public FloatBuffer getMatrix(int programID){
		FloatBuffer params = BufferUtils.createFloatBuffer(16);
		GL20.glGetUniform(programID, super.getLocation(), params);
		return params;
	}
}
