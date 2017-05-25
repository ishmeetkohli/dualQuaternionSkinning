package main;

import org.lwjgl.opengl.Display;

import renderEngine.RenderEngine;
import scene.Scene;

public class AnimationApp {

	public static void main(String[] args) {
		RenderEngine engine = RenderEngine.init();
		Scene scene = SceneLoader.loadScene(GeneralSettings.RES_FOLDER);

		while (!Display.isCloseRequested()) {
			scene.getCamera().move();
			scene.getCamera().zoom();
			scene.getAnimatedModel().update();
			engine.renderScene(scene);
			engine.update();
		}
		engine.close();
	}

}
