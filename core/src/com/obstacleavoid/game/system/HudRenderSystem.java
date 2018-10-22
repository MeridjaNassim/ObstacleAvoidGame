package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.config.GameConfig;

public class HudRenderSystem extends EntitySystem {

    private Viewport viewport;
    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    public HudRenderSystem(Viewport viewport, SpriteBatch batch, BitmapFont font) {
        this.viewport = viewport;
        this.batch = batch;
        this.font = font;
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw(){
        String livesText = "LIVES: " + GameManager.INSTANCE.getLives();
        layout.setText(font, livesText);
        font.draw(batch, livesText, 20, GameConfig.HUD_HEIGHT - layout.height);
        String scoreText = "SCORE : " + GameManager.INSTANCE.getScore();
        layout.setText(font, scoreText);
        font.draw(batch, scoreText, GameConfig.HUD_WIDTH - layout.width - 20, GameConfig.HUD_HEIGHT - layout.height);
        String Difficulty = GameManager.INSTANCE.getDifficultyLevel().name();
        layout.setText(font,Difficulty);
        font.draw(batch,Difficulty,GameConfig.HUD_WIDTH/2f-layout.width+15,GameConfig.HUD_HEIGHT-layout.height);
    }
}
