package com.obstacleavoid.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.assets.AtlasRegions;
import com.obstacleavoid.game.common.GameManager;

public class HighScoreScreen extends MenuScreenBase {

    private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);


    public HighScoreScreen(ObstacleAvoidGame game) {
        super(game);
    }


    // ======= private methods ======

    @Override
    protected Actor createUi() {
        Table table = new Table();
        TextureAtlas gameplayAtlas = getAssetManager().get(AssetDescriptors.GAMEPLAY);


        // TEXTURE REGIONS ==========
        TextureRegion backgroundRegion = gameplayAtlas.findRegion(AtlasRegions.BACKGROUND);
        Skin UI_SKIN = getAssetManager().get(AssetDescriptors.UI_SKIN);
        //==========

        // ==== LABELS =========


        // ==================

        // background
        table.setBackground(new TextureRegionDrawable(backgroundRegion));
        // high score text
        String HighScoreString = GameManager.INSTANCE.getHighScoreString();
        Label HighScoreText = new Label("HIGH SCORE ", UI_SKIN);
        Label HighScoreNumber = new Label(HighScoreString, UI_SKIN);
        // back button
        TextButton backButton = new TextButton("BACK", UI_SKIN);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });
        // setup table
        Table contentTable = new Table(UI_SKIN);
        contentTable.defaults().pad(20);
        contentTable.setBackground(AtlasRegions.PANEL);
        contentTable.add(HighScoreText).row();
        contentTable.add(HighScoreNumber).row();
        contentTable.add(backButton);
        contentTable.center();

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;


    }

    @Override
    protected void back() {
        log.debug("back()...");
        switchScreen(new MenuScreen(getGame()));
    }
}
