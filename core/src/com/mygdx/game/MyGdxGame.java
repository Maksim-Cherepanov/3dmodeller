package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.graphics.Color.*;


public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public Environment environment;
	public boolean loading;
	private TextureRegion region;
	protected Stage stage;
	private Viewport viewport;
	private TextButton textbutton;
	protected BitmapFont font;
	protected StringBuilder stringBuilder;
	protected Label label;
	protected TextButton button;

	@Override
	public void create () {
		stage = new Stage();
		font = new BitmapFont();
		stringBuilder = new StringBuilder();
		viewport = new FitViewport(1200,840);
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		textbutton = new TextButton("ok", skin);
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.add(button);
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		table.add(new Image(skin.newDrawable("white", Color.WHITE))).size(64);
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(7f, 7f, 7f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		camController = new CameraInputController(cam);
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(camController);
		Gdx.input.setInputProcessor(new InputAdapter(){
			public boolean touchDown(int x,int y,int pointer,int button){
				// код при нажатии
				System.out.println("true");
				return true; // возвращает true, сообщая, что событие было обработано
			}

			public boolean touchUp(int x,int y,int pointer,int button){
				// код при отпускании
				return true; // возвращает true, сообщая, что событие было обработано
			}
		});
		Gdx.input.setInputProcessor(multiplexer);
		assets = new AssetManager();
		assets.load("DiamondSword.g3dj", Model.class);
		loading = true;
	}

	private void doneLoading() {
		/*Model ship = assets.get("DiamondSword.g3dj", Model.class);
		ModelInstance shipInstance = new ModelInstance(ship);
		shipInstance.transform.setToTranslation(0, 0, 0);
		instances.add(shipInstance);
		ModelInstance shipInstance2 = new ModelInstance(ship);
		shipInstance.transform.setToTranslation(20, 0, -20);
		instances.add(shipInstance2);*/
		/*Model ship = assets.get("DiamondSword.g3dj", Model.class);
		for (float x = -5f; x <= 5f; x += 2f) {
			for (float z = -5f; z <= 5f; z += 2f) {
				ModelInstance shipInstance = new ModelInstance(ship);
				shipInstance.transform.setToTranslation(x, 0, z);
				instances.add(shipInstance);
			}
		}*/
		ModelBuilder modelBuilder = new ModelBuilder();
		Model model = modelBuilder.createBox(5, 5, 5,
				new Material(ColorAttribute.createDiffuse(GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		ModelInstance Boxmodel = new ModelInstance(model);
		Boxmodel.transform.setToTranslation(0, 0, 0);
		instances.add(Boxmodel);
	}
	private int visibleCount;
	int s=0;
	int j=0;
	int c=0;
	@Override
	public void render () {
		if (loading && assets.update())
			doneLoading();
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();
		/*if(Gdx.input.justTouched()){
			ModelBuilder modelBuilder = new ModelBuilder();
			Model model2 = modelBuilder.createBox(5, 5, 5,
					new Material(ColorAttribute.createDiffuse(GREEN)),
					VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
			ModelInstance Boxmodel = new ModelInstance(model2);
			Boxmodel.transform.setToTranslation(s,j,c);
			instances.add(Boxmodel);

			j+=5;

		} */

		/*stringBuilder.setLength(0);
		stringBuilder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
		stringBuilder.append(" Visible: ").append(visibleCount);
		//label.setText(stringBuilder);*/
		stage.draw();
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

	}

	@Override
	public void pause() {
	}


	@Override
	public void resume() {
	}

}
