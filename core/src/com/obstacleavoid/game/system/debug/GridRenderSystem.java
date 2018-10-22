package com.obstacleavoid.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.game.util.ViewportUtils;

public class GridRenderSystem extends EntitySystem {
    // ======== LOGGER ========



    // ====== ATTRIBUTES ========

    private final Viewport viewport;
    private final ShapeRenderer renderer;

    // ========== CONSTRATOR ===============
    public GridRenderSystem(Viewport viewport, ShapeRenderer renderer) {
        this.viewport = viewport;
        this.renderer = renderer;
    }

    // ========== update called every frame by engine =========


    @Override
    public void update(float deltaTime) {
        viewport.apply();
        ViewportUtils.drawGrid(viewport,renderer);
    }
}
