package com.obstacleavoid.game.system.spawningSystems;

import com.obstacleavoid.game.common.EntityFactory;
import com.obstacleavoid.game.common.GameManager;

public class HeartSpawnSystem extends SpawnBaseSystem {

    private final EntityFactory factory;

    public HeartSpawnSystem(EntityFactory factory) {

        super(GameManager.INSTANCE.getHeartSpawnTime());

        this.factory = factory;
    }

    @Override
    protected void spawn(float x, float y) {
        factory.addHeart(x,y);
        }


    // ======= private method =======


}
