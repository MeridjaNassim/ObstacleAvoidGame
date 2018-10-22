package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Dimension;
import com.obstacleavoid.game.component.Position;
import com.obstacleavoid.game.component.WorldWrap;

public class WorldWrapSystem extends IteratingSystem {
    private static final Logger log = new Logger(WorldWrapSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(
            WorldWrap.class,
            Position.class,
            Dimension.class
    ).get();

    private final Viewport viewport;

    public WorldWrapSystem(Viewport viewport) {
        super(FAMILY);
        this.viewport = viewport;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        Dimension dimension = Mappers.DIMENSION.get(entity);
        position.x = MathUtils.clamp(position.x,0,viewport.getWorldWidth()-dimension.width);
        position.y = MathUtils.clamp(position.y,-1,viewport.getWorldHeight()-dimension.height);
    }
}
