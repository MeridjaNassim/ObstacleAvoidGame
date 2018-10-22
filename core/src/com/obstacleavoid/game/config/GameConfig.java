package com.obstacleavoid.game.config;

public final class GameConfig {

    public final static float width = 480f;
    public final static float height = 800f;
    public final static float LIMIT = 1.0f;
    public final static float WORLD_WIDTH = 6.0f;
    public final static float WORLD_HEGHT = 10.0f;

    public final static float WORLD_CENTER_X = WORLD_WIDTH/2f;
    public final static float WORLD_CENTER_Y = WORLD_HEGHT/2f;

    public static final float OBSTACLE_SPAWN_TIME = 0.25f; // spawn of obstacle time
    public static final float SCORE_MAX_TIME = 1.25f; // intervall of adding score

    public static final float HUD_WIDTH = 480f;
    public static final float HUD_HEIGHT = 800f;
    public static final int LIVE_START = 5; // starting lives

    public final static float MAX_PLAYER_X_SPEED =0.25f;


    // PLAYER
    public static final float PLAYER_BOUNDS_RADIUS = 0.4f;
    public final static float PLAYER_SIZE =PLAYER_BOUNDS_RADIUS *2;
    public static final float PLAYER_START_X = WORLD_CENTER_X;
    public static final float PLAYER_START_Y = PLAYER_SIZE;


    // Obstacle =======

    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f;
    public static final float OBSTACLE_SIZE = OBSTACLE_BOUNDS_RADIUS * 2;
    public static final float OBSTACLE_MAX_X_SPEED = 0.1f;

    // ======== DIFFICULTIES =======

    public static final float EASY_OBS_SPEED = 0.15f;
    public static final float MEDIUM_OBS_SPEED = 0.20f;
    public static final float HARD_OBS_SPEED = 0.30f;
    public static final float VERY_HARD_OBS_SPEED = 0.25f;


    // ============OBSTACLES SPAWN TIMES =========

    public static final float OBSTACLE_SPAWN_TIME_EASY = 0.4f;

    public static final float OBSTACLE_SPAWN_TIME_MEDIUM = 0.3f;

    public static final float OBSTACLE_SPAWN_TIME_HARD = 0.2f;


    //============================================

    // ============HEART SPAWN TIMES =========

    public static final float HEART_SPAWN_TIME_EASY = 30f;

    public static final float HEART_SPAWN_TIME_MEDIUM = 20f;

    public static final float HEART_SPAWN_TIME_HARD = 10f;


    //============================================

    private GameConfig(){

    }


}
