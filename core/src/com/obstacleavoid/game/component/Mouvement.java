package com.obstacleavoid.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class Mouvement implements Component ,Pool.Poolable{

    public float xSpeed ;
    public float ySpeed;

    @Override
    public void reset() {
        xSpeed =0;
        ySpeed =0;
    }
}
