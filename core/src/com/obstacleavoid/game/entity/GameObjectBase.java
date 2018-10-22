package com.obstacleavoid.game.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase implements GameObject {


    private float x , y ; // object coordinates

    private float width,height;


    private float halfWidth = width/2f;
    private float halfHeight = height/2f;


    private Circle bounds ; // Cirle that delimits the object hitbox

    public GameObjectBase(float boundsRadius) {
        bounds = new Circle(x,y,boundsRadius);
    }


    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }


    public void setSize(float width, float height){
        this.width= width;
        this.height = height;
        updateBounds();
    }
    public Circle getBounds() {
        return bounds;
    }


    public void setBounds(Circle bounds) {
        this.bounds = bounds;
    }

    public  void drawDebug(ShapeRenderer renderer) {


        renderer.circle(x,y,bounds.radius,30);
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }



    @Override
    public void update() {

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    public void updateBounds(){
        bounds.setPosition(x,y);
    }

    @Override
    public float getHeight() {
        return height;
    }
}
