package com.obstacleavoid.game.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.config.GameConfig;
import com.obstacleavoid.game.screen.menu.MenuScreen;
import com.obstacleavoid.game.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter {


    // ======= CONSTANTS ========

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;

    private static final float PROGRESS_BAR_HEIGHT = 60f;

    private static final Logger log = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);

    // =========== ATTRIBUTES =======

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean finishedLoading = false;

    // ================================
    //===== GAME ATTRIBUTES ============

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;


    // CONSTRUCTOR
    public LoadingScreen(ObstacleAvoidGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }
    // ===========


    @Override
    public void show() {


        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        //=========== LOADING ASSETS ====================


        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.GAMEPLAY);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.HIT_TRACK);
        assetManager.load(AssetDescriptors.GAMEPLAY_TRACK);
        // blocks until all assets are loaded ....

        assetManager.finishLoading();



    }

    @Override
    public void render(float delta) {
        if (!finishedLoading) {
            update(delta);


            GdxUtils.clearScreen();

            viewport.apply();
            renderer.setProjectionMatrix(camera.combined);
            renderer.setColor(Color.YELLOW);
            renderer.begin(ShapeRenderer.ShapeType.Filled);

            draw();


            renderer.end();



        } else {
            switchSceen();
        }


    }

    // ======== private methods =====


    private void draw() {
        float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

        renderer.rect(progressBarX, progressBarY, PROGRESS_BAR_WIDTH * progress, PROGRESS_BAR_HEIGHT);

    }

    private void update(float delta) {

        // progress is between 0 and 1
        progress = assetManager.getProgress();
        // update returns true if all assets are loaded
        if (assetManager.update()) {
            waitTime -= delta;
            if (waitTime <= 0) {
                finishedLoading = true;
            }
        }


    }

    private void switchSceen() {
        game.setScreen(new MenuScreen(game));
    }

    // ============================
    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
    }


    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        renderer.dispose();

    }




    public boolean isFinishedLoading() {
        return finishedLoading;
    }
}
