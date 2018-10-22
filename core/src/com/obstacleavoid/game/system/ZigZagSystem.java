package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Mouvement;
import com.obstacleavoid.game.component.Obstacle;
import com.obstacleavoid.game.config.GameConfig;

public class ZigZagSystem extends IteratingSystem {

    private static final Family FAMILY =Family.all(
            Obstacle.class,
            Mouvement.class

    ).get();

    public ZigZagSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Mouvement mouvement = Mappers.MOUVEMENT.get(entity);
        mouvement.xSpeed = MathUtils.random(-GameConfig.OBSTACLE_MAX_X_SPEED,GameConfig.OBSTACLE_MAX_X_SPEED);
    }
}
