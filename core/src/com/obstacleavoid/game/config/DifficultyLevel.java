package com.obstacleavoid.game.config;

public enum DifficultyLevel {

    EASY(GameConfig.EASY_OBS_SPEED,GameConfig.OBSTACLE_SPAWN_TIME_EASY,GameConfig.HEART_SPAWN_TIME_EASY),
    MEDIUM(GameConfig.MEDIUM_OBS_SPEED,GameConfig.OBSTACLE_SPAWN_TIME_MEDIUM,GameConfig.HEART_SPAWN_TIME_MEDIUM),
    HARD(GameConfig.HARD_OBS_SPEED,GameConfig.OBSTACLE_SPAWN_TIME_HARD,GameConfig.HEART_SPAWN_TIME_HARD),
    VERYHARD(GameConfig.VERY_HARD_OBS_SPEED,GameConfig.OBSTACLE_SPAWN_TIME_HARD,GameConfig.HEART_SPAWN_TIME_HARD);

    private final float obstacleSpawnTime;
    private final float heartSpawnTime;
    private final float obstacleSpeed;
    DifficultyLevel(float obstacleSpeed,float ObstacleSpawnTime,float HeartSpawnTime) {
        this.obstacleSpeed = obstacleSpeed;
        this.obstacleSpawnTime = ObstacleSpawnTime;
        this.heartSpawnTime = HeartSpawnTime;
        }

    public float getObstacleSpeed() {
        return obstacleSpeed;
    }

    public float getObstacleSpawnTime(){
        return obstacleSpawnTime;
    }

    public float getHeartSpawnTime() {
        return heartSpawnTime;
    }
}
