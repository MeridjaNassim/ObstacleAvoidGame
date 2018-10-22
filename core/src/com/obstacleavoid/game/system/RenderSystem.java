package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Dimension;
import com.obstacleavoid.game.component.Drawable;
import com.obstacleavoid.game.component.Position;

import java.util.Map;

public class RenderSystem extends EntitySystem {


    private static final Family FAMILY = Family.all(

            Drawable.class,
            Dimension.class,
            Position.class

    ).get();

    private final Viewport viewport;
    private final SpriteBatch batch;

    private Array<Entity> renderQueue = new Array<Entity>();

    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> entities =getEngine().getEntitiesFor(FAMILY);
        renderQueue.addAll(entities.toArray());
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        draw();
        batch.end();
        renderQueue.clear();
    }

    private void draw(){
        for (Entity entity: renderQueue
             ) {
            Position position = Mappers.POSITION.get(entity);
            Dimension dimension = Mappers.DIMENSION.get(entity);
            Drawable  drawable = Mappers.DRAWABLE.get(entity);

            batch.draw(drawable.textureRegion,position.x,position.y,dimension.width,dimension.height);

        }
    }
}
