package com.obstacleavoid.game.system.debug;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Bounds;
import com.obstacleavoid.game.component.Player;
import com.obstacleavoid.game.component.Position;

public class debugPlayerSystem extends EntitySystem {

    private static final Logger log = new Logger(debugPlayerSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            Player.class,
            Bounds.class,
            Position.class

    ).get();

    public debugPlayerSystem() {
    }

    @Override
    public void update(float deltaTime) {
        Engine engine =getEngine();
        ImmutableArray<Entity> playeres = engine.getEntitiesFor(FAMILY);
        for (Entity player:
             playeres) {
            Bounds bounds = Mappers.BOUNDS.get(player);
            Position position = Mappers.POSITION.get(player);
            log.debug("Player Position x== "+position.x +" y == " + position.y);
            log.debug("Player bounds x == "+ bounds.bounds.x +" Player bounds y == "+ bounds.bounds.y);
        }
    }
}
