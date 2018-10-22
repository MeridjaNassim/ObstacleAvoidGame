package com.obstacleavoid.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.assets.AtlasRegions;
import com.obstacleavoid.game.screen.game.GameScreen;

public class MenuScreen extends MenuScreenBase {

    private static final Logger log = new Logger(MenuScreen.class.getName(), Logger.DEBUG);

    public MenuScreen(ObstacleAvoidGame game) {
        super(game);
    }

    // ========= private Methods ====


    @Override
    protected Actor createUi() {

        Table table = new Table();

        TextureAtlas gameplayAtlas = getAssetManager().get(AssetDescriptors.GAMEPLAY);


        TextureRegion background = gameplayAtlas.findRegion(AtlasRegions.BACKGROUND);


        // play button ====

        Skin UI_SKIN = getAssetManager().get(AssetDescriptors.UI_SKIN);
        TextButton playButton = new TextButton("PLAY", UI_SKIN);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });
        // ===================

        // ==== HIGH SCORE BUTTON ===

        TextButton highScoreButton = new TextButton("HIGH SCORE", UI_SKIN);
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                displayHighScore();
            }
        });
        //===========

        // ==== OPTIONS  BUTTON ===

        TextButton optionsButton = new TextButton("OPTIONS", UI_SKIN);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                displayOptions();
            }
        });
        //===========

        // ==== QUIT  BUTTON ===
        TextButton quitButton = new TextButton("QUIT", UI_SKIN);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Quit();
            }
        });
        //===========

        // setup Table
        Table buttonTable = new Table(UI_SKIN);
        buttonTable.defaults().pad(20);
        buttonTable.setBackground(AtlasRegions.PANEL);
        buttonTable.add(playButton).row();
        buttonTable.add(highScoreButton).row();
        buttonTable.add(optionsButton).row();
        buttonTable.add(quitButton).row();
        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.pack();
        //////////////////


        table.setBackground(new TextureRegionDrawable(background));

        return table;
    }


    private void play() {
        log.debug("play()");
        switchScreen(new GameScreen(getGame()));
    }

    private void displayHighScore() {
        log.debug("high score display ... ");
        switchScreen(new HighScoreScreen(getGame()));
    }

    private void displayOptions() {
        log.debug("options display ...");
        switchScreen(new OptionsScreen(getGame()));
    }


    @Override
    protected void back() {
        Quit();
    }

    private void Quit() {
        Gdx.app.exit();
    }
}
