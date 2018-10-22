package com.obstacleavoid.game.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.obstacleavoid.game.config.GameConfig;

public class Obstacle extends GameObjectBase implements Pool.Poolable {


    // SPEED CONSTANT FOR MOVING obstacle


    // circle configs

    private boolean hit = false;
    private float obstacleXtimer=0;
    private static final float obstacleXchangeTime = 0.01f;
    private static final float OBSTACLE_MAX_X_SPEED = 0.1f;
    public Obstacle() {
        super(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE,GameConfig.OBSTACLE_SIZE);

    }

    private float ySpeed = GameConfig.MEDIUM_OBS_SPEED;


    // method called in render() used to update obstacle position ingame depending on keys pressed and with specified speed
    public void update(float delta) {

        updateX(delta);
        super.setY(super.getY() - ySpeed);

        updateBounds();
    }

    private void updateX(float delta){
        obstacleXtimer += delta;
        if (obstacleXtimer >= obstacleXchangeTime) {
            float random = MathUtils.random(-OBSTACLE_MAX_X_SPEED,OBSTACLE_MAX_X_SPEED);

            super.setX(super.getX()-random);
            obstacleXtimer =0;
        }
    }

    // Getters :


    public void setYspeed(float yspeed) {
        this.ySpeed = yspeed;
    }



    public boolean CollisionWithPlayer(Player player) {
        Circle playerBounds = player.getBounds();
        boolean overlaps = Intersector.overlaps(playerBounds, this.getBounds());
        hit = overlaps;

        return overlaps;

    }

    public boolean isNotHit() {
        return !hit;
    }


    public float getySpeed() {
        return ySpeed;
    }

    @Override
    public void reset() {
        obstacleXtimer =0;
        hit = false;
    }
}
