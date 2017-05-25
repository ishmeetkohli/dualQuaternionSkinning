package main;

import org.lwjgl.util.vector.Vector3f;

import utils.MyFile;


public class GeneralSettings {
	
	public static final MyFile RES_FOLDER = new MyFile("res");
//	public static final String MODEL_FILE = "cylinder.dae";
//	public static final String ANIM_FILE = "cylinder.dae";
//	public static final String DIFFUSE_FILE = "texture_green.png";
	
	
//	public static final String MODEL_FILE = "cylinder_moving.dae";
//	public static final String ANIM_FILE = "cylinder_moving.dae";
//	public static final String DIFFUSE_FILE = "thisTexture.png";

	public static final String MODEL_FILE = "model.dae";
	public static final String ANIM_FILE = "model.dae";
	public static final String DIFFUSE_FILE = "diffuse.png";

//	public static final String MODEL_FILE = "test8.dae";
//	public static final String ANIM_FILE = "test8.dae";
//	public static final String DIFFUSE_FILE = "texture_green.png";
	
	
	
	public static final int MAX_WEIGHTS = 3;
	
	public static final Vector3f LIGHT_DIR = new Vector3f(0.2f, -0.3f, -0.8f);
	
}
