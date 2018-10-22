package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Dimension;
import com.obstacleavoid.game.component.Player;
import com.obstacleavoid.game.component.Position;
import com.obstacleavoid.game.config.GameConfig;

public class TouchHandlingSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(

            Player.class,
            Position.class,
            Dimension.class


    ).get();


    private final Viewport viewport;

    public TouchHandlingSystem(Viewport gameViewport) {
        super(FAMILY);
        this.viewport = gameViewport;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        Dimension dimension =Mappers.DIMENSION.get(entity);

        // ============ HANDLING TOUCH SCREEN INPUT ==========================
        if (Gdx.input.isTouched() && !GameManager.INSTANCE.isGameOver()) {
            Vector2 screenTouchPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouchPosition));
            worldTouch.x = MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - dimension.width / 2f);

            position.x = worldTouch.x;

        }
        //=======================================================================

    }
}
