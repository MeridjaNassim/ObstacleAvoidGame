package com.obstacleavoid.game.system.spawningSystems;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.obstacleavoid.game.common.EntityFactory;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.config.DifficultyLevel;
import com.obstacleavoid.game.config.GameConfig;

public class ObstacleSpawnSystem extends SpawnBaseSystem {

    private final EntityFactory factory;

    public ObstacleSpawnSystem(EntityFactory factory) {

        super(GameManager.INSTANCE.getObstacleSpawnTime());

        this.factory = factory;
    }

    @Override
    protected void spawn(float x, float y) {
        factory.addObstacle(x,y);
    }


    // ======= private method =======


}
