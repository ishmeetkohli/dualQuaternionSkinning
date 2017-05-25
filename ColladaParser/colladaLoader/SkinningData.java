package colladaLoader;

import java.util.List;

public class SkinningData {
	
	public final List<String> jointOrder;
	public final List<VertexSkinData> verticesSkinData;
	
	protected SkinningData(List<String> jointOrder, List<VertexSkinData> verticesSkinData){
		this.jointOrder = jointOrder;
		this.verticesSkinData = verticesSkinData;
	}


}
