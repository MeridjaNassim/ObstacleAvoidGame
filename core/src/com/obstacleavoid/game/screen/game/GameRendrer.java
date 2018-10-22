package com.obstacleavoid.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.assets.AtlasRegions;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.config.GameConfig;
import com.obstacleavoid.game.entity.Background;
import com.obstacleavoid.game.entity.Heart;
import com.obstacleavoid.game.entity.Obstacle;
import com.obstacleavoid.game.entity.Player;
import com.obstacleavoid.game.util.GdxUtils;
import com.obstacleavoid.game.util.ViewportUtils;
import com.obstacleavoid.game.util.debug.DebugCameraController;
@Deprecated
public class GameRendrer implements Disposable {

    // ======== GAMEPLAY ELEMENTS ==============

    private TextureRegion player;
    private TextureRegion defaultObstacle;
    private TextureRegion background;
    private TextureRegion heart;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
//    List<Texture> obstacleTextures;


    // ============== Game Screen private objects =========
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private boolean setDebugCamera =false;
    private boolean renderDebug = true;
    private DebugCameraController debugCameraController;
    // ===================================

    // ======= HUD private objects =======

    private OrthographicCamera HUD_camera;
    private Viewport HUD_viewport;
    private final GlyphLayout HUD_glyphLayout = new GlyphLayout();
    private BitmapFont HUD_font;

    //==================================

    private final GameController gameController;

    public GameRendrer(SpriteBatch batch,GameController controller, AssetManager assetManager) {

        this.gameController = controller;
        this.assetManager = assetManager;
        this.batch = batch;
        init();
    }


    // ======= public methods ========

    public void render(float delta) {


        if (setDebugCamera) {
            debugCameraController.handleDebugInput(delta);
            debugCameraController.applyTo(camera);
        }

        // update world

        // ============ HANDLING TOUCH SCREEN INPUT ==========================
        if (Gdx.input.isTouched() && !gameController.isGameOver()) {
            Vector2 screenTouchPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouchPosition));
            Player player = gameController.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x, player.getWidth() / 2f, GameConfig.WORLD_WIDTH - player.getWidth() / 2f);
            player.setX(worldTouch.x);
        }
        //=======================================================================


        GdxUtils.clearScreen();

        // rendering gameplay

        renderGamePlay();


        // render ui/HUD

        renderUi();

        // rendering debug
        if (renderDebug) {
            renderDebug();
        }


    }


    public void resize(int width, int height) {
        viewport.update(width, height, true);
        HUD_viewport.update(width, height, true);
        ViewportUtils.debugPixel(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();


    }


    // ========== private Methods ==========


    private void init() {
        // init game textures :
        initTextures();


        // init game Screen
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEGHT, camera);
        renderer = new ShapeRenderer();
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartposition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        // init HUD =====

        initHUD();
    }

    private void initTextures() {


        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);


        defaultObstacle = gamePlayAtlas.findRegion(AtlasRegions.OBSTACLE);
        player = gamePlayAtlas.findRegion(AtlasRegions.PLAYER);
        background = gamePlayAtlas.findRegion(AtlasRegions.BACKGROUND);
        heart = gamePlayAtlas.findRegion(AtlasRegions.HEART);
    }

    private void initHUD() {
        HUD_camera = new OrthographicCamera();
        HUD_viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, HUD_camera);
        HUD_font = assetManager.get(AssetDescriptors.FONT);

    }

    private void renderGamePlay() {

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();


    }

    private void drawGamePlay() {
        // draw background :
        Background background1 = gameController.getBackground();

        batch.draw(background, background1.getX(), background1.getY(), background1.getWidth(), background1.getHeight());


        // draw player
        Player player = gameController.getPlayer();

        batch.draw(this.player, player.getX() - player.getWidth()+0.1f , player.getY() - player.getHeight()+0.4f, player.getWidth()*1.5f , player.getHeight()*1.25f );

        // drawing obstacles :

        for (Obstacle obstacle : gameController.getObstacles()) {

            batch.draw(defaultObstacle, obstacle.getX() - obstacle.getWidth() / 2f, obstacle.getY() - obstacle.getWidth() / 2f, obstacle.getWidth(), obstacle.getWidth());
        }
        for (Heart heart : gameController.getHearts()) {

            if (heart.isNotHit()) {
                batch.draw(this.heart, heart.getX() - heart.getWidth() / 2f, heart.getY() - heart.getWidth() / 2f, heart.getWidth(), heart.getWidth());
            }
        }

    }

    private void renderDebug() {

        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer, 1);

    }

    private void renderUi() {

        HUD_viewport.apply();
        batch.setProjectionMatrix(HUD_camera.combined);
        batch.begin();

        String livesText = "LIVES: " + gameController.getLives();
        HUD_glyphLayout.setText(HUD_font, livesText);
        HUD_font.draw(batch, livesText, 20, GameConfig.HUD_HEIGHT - HUD_glyphLayout.height);

        String scoreText = "SCORE : " + gameController.getDisplayScore();
        HUD_glyphLayout.setText(HUD_font, scoreText);
        HUD_font.draw(batch, scoreText, GameConfig.HUD_WIDTH - HUD_glyphLayout.width - 20, GameConfig.HUD_HEIGHT - HUD_glyphLayout.height);
        String Difficulty = GameManager.INSTANCE.getDifficultyLevel().name();
        HUD_glyphLayout.setText(HUD_font,Difficulty);
        HUD_font.draw(batch,Difficulty,GameConfig.HUD_WIDTH/2f-HUD_glyphLayout.width+15,GameConfig.HUD_HEIGHT-HUD_glyphLayout.height);

        batch.end();

    }

    private void drawDebug() {
        Player player = gameController.getPlayer();
        player.drawDebug(renderer);

        Array<Obstacle> obstacles = gameController.getObstacles();
        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(renderer);
        }
    }


}
