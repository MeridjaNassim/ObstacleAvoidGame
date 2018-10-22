package com.obstacleavoid.game.system.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.util.debug.DebugCameraController;

public class DebugCameraSystem extends EntitySystem {

    // ======== LOGGER ===========



    // ==========  CONSTANTS  ===========
    private static final DebugCameraController DEBUG_CAMERA_CONTROLLER = new DebugCameraController();


    // ======= ATTRIBUTES ===========
    private final OrthographicCamera camera;

    // ======= CONSTRUCTORS ==========


    public DebugCameraSystem(OrthographicCamera camera,float startX , float startY) {
        this.camera = camera;
        DEBUG_CAMERA_CONTROLLER.setStartposition(startX,startY);
    }

    @Override
    public void update(float deltaTime) {

        DEBUG_CAMERA_CONTROLLER.handleDebugInput(deltaTime);
        DEBUG_CAMERA_CONTROLLER.applyTo(camera);
    }
}
