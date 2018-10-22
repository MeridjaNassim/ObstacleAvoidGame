package com.obstacleavoid.game.entity;

public class Background implements GameObject {

    private float x;
    private float y;

    private float width ;
    private  float height;

    public Background(){

    }



    @Override
    public void setPosition(float x, float y) {
        this.x=x;
        this.y =y;

    }

    public void setSize(float width, float height){
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void update() {

    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }
}
