package com.obstacleavoid.screen.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.common.EntityFactory;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.screen.menu.MenuScreen;
import com.obstacleavoid.system.BoundsSystem;
import com.obstacleavoid.system.CleanUpSystem;
import com.obstacleavoid.system.Collision.CollisionListener;
import com.obstacleavoid.system.Collision.CollisionSystem;
import com.obstacleavoid.system.HeartSpawnSystem;
import com.obstacleavoid.system.HudRenderSystem;
import com.obstacleavoid.system.MovementSystem;
import com.obstacleavoid.system.ObstacleSpawnSystem;
import com.obstacleavoid.system.PlayerSystem;
import com.obstacleavoid.system.RenderSystem;
import com.obstacleavoid.system.ScoreSystem;
import com.obstacleavoid.system.WorldWrapSystem;
import com.obstacleavoid.system.debug.DebugCameraSystem;
import com.obstacleavoid.system.debug.DebugRenderSystem;
import com.obstacleavoid.system.debug.GridRenderSystem;
import com.obstacleavoid.util.GdxUtils;


public class GameScreen implements Screen {

private static final Logger log=new Logger(GameScreen.class.getName(),Logger.DEBUG);
private static final boolean DEBUG=false;
    private final ObstacleAvoidGame game;
    private  final AssetManager assetManager;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private PooledEngine engine;
    private OrthographicCamera camera;
    private EntityFactory factory;
    private Sound hit;
    private boolean reset;
    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager=game.getAssetManager();
    }

    @Override
    public void show() {
//        log.debug("show()");
        camera=new OrthographicCamera();
        viewport=new FitViewport(GameConfig.WORLD_WIDTH,GameConfig.WORLD_HEIGHT,camera);
        hudViewport=new FitViewport(GameConfig.HUD_WIDTH,GameConfig.HUD_HEIGHT);
        renderer=new ShapeRenderer();
//        batch=new SpriteBatch();
        engine=new PooledEngine();
        factory=new EntityFactory(engine,assetManager);

        BitmapFont font=assetManager.get(AssetDescriptors.FONT);
        hit=assetManager.get(AssetDescriptors.HIT_SOUND);

        CollisionListener listener=new CollisionListener() {
            @Override
            public void hitObstacle() {

                GameManager.INSTANCE.decrementLives();
                hit.play();

                if(GameManager.INSTANCE.isGameOver()){
                    GameManager.INSTANCE.updateHighScore();
                }else {
                    engine.removeAllEntities();

                     reset = true;
                }
            }
        };






        engine.addSystem(new PlayerSystem());
        engine.addSystem(new MovementSystem());

        engine.addSystem(new WorldWrapSystem(viewport));

        engine.addSystem(new BoundsSystem());
        engine.addSystem(new ObstacleSpawnSystem(factory));
        engine.addSystem(new HeartSpawnSystem(factory));
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem(listener));
        engine.addSystem(new ScoreSystem());

        engine.addSystem(new RenderSystem(viewport,game.getBatch()));


        if(DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(camera,
                    GameConfig.WORLD_CENTER_X,
                    GameConfig.WORLD_CENTER_Y));
        }

        engine.addSystem(new HudRenderSystem(hudViewport,game.getBatch(),font));
       addEntities();

    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        engine.update(delta);
//        log.debug("entities size= "+engine.getEntities().size());
        if(GameManager.INSTANCE.isGameOver()){
            GameManager.INSTANCE.reset();
            game.setScreen(new MenuScreen(game));
        }

        if(reset){
            reset=false;
            addEntities();
        }

    }

    private void addEntities(){
        factory.addBackground();
        factory.addPlayer();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
        hudViewport.update(width,height,true);



    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
//        batch.dispose();

    }
}
