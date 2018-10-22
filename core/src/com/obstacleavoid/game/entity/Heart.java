package com.obstacleavoid.game.entity;



public class Heart extends Obstacle {


    @Override
    public void update(float delta) {
        super.setY(super.getY() - getySpeed());

        updateBounds();
    }
}
