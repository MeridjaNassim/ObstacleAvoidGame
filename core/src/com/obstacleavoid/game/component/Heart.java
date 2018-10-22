package com.obstacleavoid.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class Heart implements Component , Pool.Poolable {
    public boolean hit;

    @Override
    public void reset() {
        hit=false;
    }
}
