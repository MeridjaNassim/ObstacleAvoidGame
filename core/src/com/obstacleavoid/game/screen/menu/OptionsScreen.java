package com.obstacleavoid.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
import com.obstacleavoid.game.config.DifficultyLevel;

public class OptionsScreen extends MenuScreenBase {


    private static final Logger log = new Logger(OptionsScreen.class.getName(), Logger.DEBUG);

    private ButtonGroup<CheckBox> checkBoxButtonGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen(ObstacleAvoidGame game) {
        super(game);
    }


    //============private methods ======


    @Override
    protected Actor createUi() {
        Table table = new Table();
        table.defaults().pad(15);
        TextureAtlas gameplayAtlas = getAssetManager().get(AssetDescriptors.GAMEPLAY);

        Skin UI_SKIN = getAssetManager().get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(AtlasRegions.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        // LABELS

        final Label difficulty = new Label("DIFFICULTY LEVEL", UI_SKIN);
        easy = checkBox(DifficultyLevel.EASY.name(), UI_SKIN);
        medium = checkBox(DifficultyLevel.MEDIUM.name(), UI_SKIN);
        hard = checkBox(DifficultyLevel.HARD.name(), UI_SKIN);


        checkBoxButtonGroup = new ButtonGroup<CheckBox>(easy, medium, hard);
        DifficultyLevel level = GameManager.INSTANCE.getDifficultyLevel();

        checkBoxButtonGroup.setChecked(level.name());

        TextButton backButton = new TextButton("BACK", UI_SKIN);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });
        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };
        easy.addListener(listener);
        medium.addListener(listener);
        hard.addListener(listener);

        // setup table :

        Table contentTable = new Table(UI_SKIN);
        contentTable.defaults().pad(10);
        contentTable.setBackground(AtlasRegions.PANEL);
        contentTable.add(difficulty).row();
        contentTable.add(easy).row();

        contentTable.add(medium).row();

        contentTable.add(hard).row();

        contentTable.add(backButton).row();
        contentTable.pack();
        table.add(contentTable);
        table.center();
        table.setFillParent(true
        );
        table.pack();

        return table;
    }

    private void difficultyChanged() {
        log.debug("difficulty Changed");
        CheckBox checked = checkBoxButtonGroup.getChecked();
        if (checked == easy) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
        } else if (checked == medium) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
        } else if (checked == hard) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
        }
    }

    @Override
    protected void back() {
        switchScreen(new MenuScreen(getGame()));
    }

    private CheckBox checkBox(String text, Skin skin) {
        CheckBox checkBox = new CheckBox(text, skin);
        checkBox.left().pad(8);
        checkBox.getLabelCell().pad(8);
        return checkBox;
    }
}
