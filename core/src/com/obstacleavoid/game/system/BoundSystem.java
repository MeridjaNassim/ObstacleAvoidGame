package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Bounds;
import com.obstacleavoid.game.component.Dimension;
import com.obstacleavoid.game.component.Position;
import com.obstacleavoid.game.config.GameConfig;

import java.util.Map;

public class BoundSystem extends IteratingSystem {
    private static final Logger log = new Logger(BoundSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            Position.class,
            Bounds.class,
            Dimension.class

    ).get();

    public BoundSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Bounds bc = Mappers.BOUNDS.get(entity);
        Position pos = Mappers.POSITION.get(entity);
        Dimension dim = Mappers.DIMENSION.get(entity);

        bc.bounds.x =pos.x+dim.width/2f;

        bc.bounds.y =pos.y+dim.height/2f;


    }



}
