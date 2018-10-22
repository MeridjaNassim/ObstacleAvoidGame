package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.CleanUp;
import com.obstacleavoid.game.component.Position;
import com.obstacleavoid.game.config.GameConfig;

public class CleanUpSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(

            Position.class,
            CleanUp.class

    ).get();


    public CleanUpSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        if(position.y< -GameConfig.OBSTACLE_SIZE){
            getEngine().removeEntity(entity);
            }
    }
}
