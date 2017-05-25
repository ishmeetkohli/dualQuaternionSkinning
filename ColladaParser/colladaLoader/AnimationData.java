package colladaLoader;

public class AnimationData {
	
	public final float lengthSeconds;
	public final KeyFrameData[] keyFrames;
	
	public AnimationData(float lengthSeconds, KeyFrameData[] keyFrames){
		this.lengthSeconds = lengthSeconds;
		this.keyFrames = keyFrames;
	}

}
