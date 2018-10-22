package com.obstacleavoid.game.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.screen.menu.MenuScreen;

@Deprecated
public class GameScreenOld implements Screen {


    private static final Logger log = new Logger(GameScreenOld.class.getName(), Logger.DEBUG);

    private GameController gameController;
    private GameRendrer gameRendrer;

    private ObstacleAvoidGame game;
    private AssetManager assetManager;



    public GameScreenOld(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {


        log.debug("show");
        gameController = new GameController(game);
        gameRendrer = new GameRendrer(game.getBatch(),gameController, assetManager);

    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRendrer.render(delta);

        if(gameController.isGameOver()){

            game.setScreen(new MenuScreen(game));
        }

    }

    @Override
    public void resize(int width, int height) {
        gameRendrer.resize(width, height);
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
        gameRendrer.dispose();
    }


}
