package renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import animatedModel.AnimatedModel;
import scene.ICamera;
import utils.OpenGlUtils;

public class AnimatedModelRenderer {

	private AnimatedModelShader shader;

	/**
	 * Initializes the shader program used for rendering animated models.
	 */
	public AnimatedModelRenderer() {
		this.shader = new AnimatedModelShader();
	}

	/**
	 * Renders an animated entity. The main thing to note here is that all the
	 * joint transforms are loaded up to the shader to a uniform array. Also 5
	 * attributes of the VAO are enabled before rendering, to include joint
	 * indices and weights.
	 * 
	 * @param entity
	 *            - the animated entity to be rendered.
	 * @param camera
	 *            - the camera used to render the entity.
	 * @param lightDir
	 *            - the direction of the light in the scene.
	 */
	
	boolean shown = false;
	public void render(AnimatedModel entity, ICamera camera, Vector3f lightDir) {
		prepare(camera, lightDir);
		entity.getTexture().bindToUnit(0);
		entity.getModel().bind(0, 1, 2, 3, 4);
//		shader.jointTransforms.loadMatrixArray(entity.getJointTransforms());
		shader.jointTransforms.loadMatrixArray(getDualQuatList(entity.getJointTransforms()));
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		entity.getModel().unbind(0, 1, 2, 3, 4);
		finish();
	}

	/**
	 * Deletes the shader program when the game closes.
	 */
	public void cleanUp() {
		shader.cleanUp();
	}

	/**
	 * Starts the shader program and loads up the projection view matrix, as
	 * well as the light direction. Enables and disables a few settings which
	 * should be pretty self-explanatory.
	 * 
	 * @param camera
	 *            - the camera being used.
	 * @param lightDir
	 *            - the direction of the light in the scene.
	 */
	private void prepare(ICamera camera, Vector3f lightDir) {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getProjectionViewMatrix());
		shader.lightDirection.loadVec3(lightDir);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	/**
	 * Stops the shader program after rendering the entity.
	 */
	private void finish() {
		shader.stop();
	}
	
	private Matrix4f[] getDualQuatList(Matrix4f[] jointTransforms){
		Matrix4f[] dqArray = new Matrix4f[jointTransforms.length];
		int i = 0;
		for(Matrix4f matrix : jointTransforms) {
			dqArray[i] = convertToDualQuats(matrix);
			i++;
		}
		return dqArray;
	}
	
	
	private Quaternion createFromAxisAngle(int xx, int yy, int zz, double a)
	{
		float factor = (float)Math.sin(a /2.0);
	    float x = xx * factor;
	    float y = yy * factor;
	    float z = zz * factor;
	    float w = (float) Math.cos( a / 2.0 );

	    Quaternion finalQuat = new Quaternion(x, y, z, w);
	    finalQuat.normalise(finalQuat);
	    return finalQuat;
	}
	
	private Matrix4f convertToDualQuats(Matrix4f matrix) {
		Quaternion rotationQuat = new Quaternion();
		Quaternion.setFromMatrix(matrix, rotationQuat);
		
		Quaternion translationQuat = new Quaternion();
		translationQuat.x = matrix.m03;
		translationQuat.y = matrix.m13;
		translationQuat.z = matrix.m23;
		translationQuat.w = 0;
		
		Quaternion realPart = rotationQuat;
		Quaternion dualPart = new Quaternion();
		Quaternion.mul(translationQuat, rotationQuat, dualPart);
		
		Matrix4f dualQuaternion = new Matrix4f();
		dualQuaternion.m00 = realPart.x;
		dualQuaternion.m01 = realPart.y;
		dualQuaternion.m02 = realPart.z;
		dualQuaternion.m03 = realPart.w;
		
		dualQuaternion.m10 = dualPart.x;
		dualQuaternion.m11 = dualPart.y;
		dualQuaternion.m12 = dualPart.z;
		dualQuaternion.m13 = dualPart.w;
		
		return dualQuaternion;
	}

}
