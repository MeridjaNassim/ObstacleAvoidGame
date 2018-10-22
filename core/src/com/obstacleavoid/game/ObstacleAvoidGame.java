package com.obstacleavoid.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.screen.loading.LoadingScreen;

public class ObstacleAvoidGame extends Game {


    private AssetManager assetManager;
    private SpriteBatch batch ;


    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);



        setScreen(new LoadingScreen(this));


    }



    @Override
    public void dispose() {

        assetManager.dispose();
        batch.dispose();
    }
}
