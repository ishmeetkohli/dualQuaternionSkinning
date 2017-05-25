package main;

import animatedModel.AnimatedModel;
import animation.Animation;
import loaders.AnimatedModelLoader;
import loaders.AnimationLoader;
import scene.ICamera;
import scene.Scene;
import utils.MyFile;

public class SceneLoader {

	public static Scene loadScene(MyFile resFolder) {
		ICamera camera = new Camera();
		AnimatedModel entity = AnimatedModelLoader.loadEntity(new MyFile(resFolder, GeneralSettings.MODEL_FILE), new MyFile(resFolder, GeneralSettings.DIFFUSE_FILE));
		Animation animation = AnimationLoader.loadAnimation(new MyFile(resFolder, GeneralSettings.ANIM_FILE));
		entity.doAnimation(animation);
		Scene scene = new Scene(entity, camera);
		scene.setLightDirection(GeneralSettings.LIGHT_DIR);
		return scene;
	}

}
