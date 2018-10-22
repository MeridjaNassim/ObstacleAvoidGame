package com.obstacleavoid.game.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public final class EngineUtils {


    private EngineUtils(Engine engine) {

    }

    public static void removeByFamily(Engine engine, Family family) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        for (Entity entity : entities
                ) {
            engine.removeEntity(entity);
        }
    }


}
