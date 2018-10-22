package com.obstacleavoid.game.screen.game;

import com.badlogic.ashley.core.Family;
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
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.common.EntityFactory;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.component.Removalbe;
import com.obstacleavoid.game.config.GameConfig;
import com.obstacleavoid.game.screen.menu.MenuScreen;
import com.obstacleavoid.game.system.BoundSystem;
import com.obstacleavoid.game.system.CleanUpSystem;
import com.obstacleavoid.game.system.HudRenderSystem;
import com.obstacleavoid.game.system.MouvementSystem;
import com.obstacleavoid.game.system.TouchHandlingSystem;
import com.obstacleavoid.game.system.ZigZagSystem;
import com.obstacleavoid.game.system.spawningSystems.HeartSpawnSystem;
import com.obstacleavoid.game.system.spawningSystems.ObstacleSpawnSystem;
import com.obstacleavoid.game.system.PlayerSystem;
import com.obstacleavoid.game.system.RenderSystem;
import com.obstacleavoid.game.system.ScoreSystem;
import com.obstacleavoid.game.system.WorldWrapSystem;
import com.obstacleavoid.game.system.collision.CollisionListener;
import com.obstacleavoid.game.system.collision.CollisionSystem;
import com.obstacleavoid.game.util.EngineUtils;
import com.obstacleavoid.game.util.GdxUtils;
import com.obstacleavoid.game.system.debug.*;

public class GameScreen implements Screen {


    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);


    private ObstacleAvoidGame game;
    private AssetManager assetManager;

    private Viewport viewport;
    private Viewport hudViewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private EntityFactory factory;
    private OrthographicCamera camera;
    private Sound hit;
    private boolean restart;
    private boolean DEBUG;
    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
    }

    @Override
    public void show() {
        hit = assetManager.get(AssetDescriptors.HIT_TRACK);
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        batch = new SpriteBatch();
        font = assetManager.get(AssetDescriptors.FONT);
        renderer = new ShapeRenderer();
        engine = new PooledEngine();
        factory = new EntityFactory(engine,assetManager);
        EngineAddSystems();
        addEntities();
        log.debug("show");


    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        engine.update(delta);

        if (GameManager.INSTANCE.isGameOver()){
            GameManager.INSTANCE.reset();
            game.setScreen(new MenuScreen(game));
        }

        if(restart){
            restart =false;
            addEntities();
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        log.debug("hide");
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();

    }


    // ========= private Methods =======

    private void EngineAddSystems() {
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new TouchHandlingSystem(viewport));
        engine.addSystem(new MouvementSystem());
        engine.addSystem(new ZigZagSystem());
        engine.addSystem(new WorldWrapSystem(viewport));
        engine.addSystem(new BoundSystem());

        engine.addSystem(new ObstacleSpawnSystem(this.factory));
        engine.addSystem(new HeartSpawnSystem(this.factory));
        engine.addSystem(new CleanUpSystem());

        CollisionListener heartlistner = new CollisionListener() {
            @Override
            public void hit() {
                hit.play();
                GameManager.INSTANCE.incrementLives();
            }
        };
        CollisionListener obstacleListner = new CollisionListener() {
            @Override
            public void hit() {
                hit.play();
                GameManager.INSTANCE.decrementLives();
                if (GameManager.INSTANCE.isGameOver()) {
                    GameManager.INSTANCE.updateHighScore();
                } else {
                    restart = true;
                    EngineUtils.removeByFamily(engine, Family.all(Removalbe.class).get());
                }
            }
        };

        engine.addSystem(new CollisionSystem(obstacleListner,heartlistner));
        engine.addSystem(new ScoreSystem());
        engine.addSystem(new RenderSystem(viewport,batch));
        if (DEBUG) {
            engine.addSystem(new GridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(camera, GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y));
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
        }
        engine.addSystem(new HudRenderSystem(hudViewport, batch, font));
    }

    private void addEntities(){
        factory.addBackground();
        factory.addPlayer();

    }

}
