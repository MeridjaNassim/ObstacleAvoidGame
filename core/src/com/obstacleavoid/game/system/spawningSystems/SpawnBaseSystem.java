package com.obstacleavoid.game.system.spawningSystems;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.obstacleavoid.game.common.EntityFactory;
import com.obstacleavoid.game.config.GameConfig;

public abstract class SpawnBaseSystem extends IntervalSystem {



    public SpawnBaseSystem(float interval) {
        super(interval);

    }

    @Override
    protected void updateInterval() {
        float min = 0;
        float max = GameConfig.WORLD_WIDTH-GameConfig.OBSTACLE_SIZE;
        float EntityX = MathUtils.random(min, max);
        float EntityY = GameConfig.WORLD_HEGHT;
        spawn(EntityX,EntityY);

    }
    protected abstract void spawn(float x, float y);
}
