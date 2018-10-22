package com.obstacleavoid.game.entity;

public interface GameObject {

    void setPosition(float x, float y);
    void update();
    float getX();
    float getY();
    float getWidth();
    float getHeight();
}
