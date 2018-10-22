package com.obstacleavoid.game.util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraController {

    private static final Logger log = new Logger(DebugCameraController.class.getName(),Logger.DEBUG);

    // ===== attributes =====
    private Vector2 position = new Vector2();
    private Vector2 startposition = new Vector2();
    private float zoom = 1;
    private DebugCameraConfig config;
    // ======= constructors ====

    public DebugCameraController() {
        config = new DebugCameraConfig();
        log.info("cameraConfig == " + config);
    }

    // ====== public methods =====


    public void setStartposition(float x, float y) {
        startposition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(OrthographicCamera camera) {
        camera.position.set(position, 0);
        camera.zoom = zoom;
        camera.update();

    }


    public void handleDebugInput(float delta) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }
        float moveSpeed = config.getMoveSpeed() * delta;
        float zoomSpeed = config.getZoomSpeed()* delta;
        // move controls
        if (config.isLeftPressed()) {
            moveLeft(moveSpeed);
        }
        if (config.isRightPressed()) {
            moveRight(moveSpeed);
        }
        if (config.isUpPressed()) {
            moveUp(moveSpeed);
        }
        if (config.isDownPressed()) {
            moveDown(moveSpeed);
        }
        if (config.isZoomOutPressed()) {
            zoom(zoomSpeed);
        }
        if (config.isZoomInPressed()) {
            zoom(-zoomSpeed);
        }

        if (config.isResetPressed()) {
            reset();
        }
        if(config.isLogPressed()){
            log.debug("Camera Position : "+position +" zoom is : " + zoom);
        }

    }

    // === private methods



    private void zoom(float zoomAmount ){
        setZoom(zoom+zoomAmount);
    }

    private void setZoom(float value){
        zoom = MathUtils.clamp(value,config.getMaxZoomIn(),config.getMaxZoomOut());
    }
    private void reset(){
        position.set(startposition);
        setZoom(1.0f);
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveLeft(float speed) {
        moveCamera(-speed, 0);
    }

    private void moveRight(float speed) {
        moveCamera(speed, 0);
    }

    private void moveUp(float speed) {
        moveCamera(0, speed);
    }

    private void moveDown(float speed) {
        moveCamera(0, -speed);
    }
}
