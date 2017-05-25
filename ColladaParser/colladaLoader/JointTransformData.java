package colladaLoader;

import org.lwjgl.util.vector.Matrix4f;

public class JointTransformData {

	public final String jointNameId;
	public final Matrix4f jointLocalTransform;
	
	protected JointTransformData(String jointNameId, Matrix4f jointLocalTransform){
		this.jointNameId = jointNameId;
		this.jointLocalTransform = jointLocalTransform;
	}
}
