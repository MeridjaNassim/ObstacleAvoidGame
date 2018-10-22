package com.obstacleavoid.game.common;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.assets.AtlasRegions;
import com.obstacleavoid.game.component.Background;
import com.obstacleavoid.game.component.Bounds;
import com.obstacleavoid.game.component.CleanUp;
import com.obstacleavoid.game.component.Dimension;
import com.obstacleavoid.game.component.Drawable;
import com.obstacleavoid.game.component.Heart;
import com.obstacleavoid.game.component.Mouvement;
import com.obstacleavoid.game.component.Obstacle;
import com.obstacleavoid.game.component.Player;
import com.obstacleavoid.game.component.Position;
import com.obstacleavoid.game.component.Removalbe;
import com.obstacleavoid.game.component.WorldWrap;
import com.obstacleavoid.game.config.GameConfig;

public class EntityFactory {

    private PooledEngine engine;
    private final AssetManager assetManager;
    private final TextureAtlas gameplayAtlas ;
    public EntityFactory(PooledEngine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
    }

    public void addPlayer() {
        float x = (GameConfig.WORLD_WIDTH-GameConfig.PLAYER_SIZE)/2f;
        float y =1- GameConfig.PLAYER_SIZE/2f;

        Bounds bounds = engine.createComponent(Bounds.class);
        bounds.bounds.set(x, y, GameConfig.PLAYER_BOUNDS_RADIUS
        );
        Mouvement playerMouvement = engine.createComponent(Mouvement.class);
        playerMouvement.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;

        Position positionPlayer = engine.createComponent(Position.class);
        positionPlayer.x = x;
        positionPlayer.y= y;
        Player player = engine.createComponent(Player.class);
        WorldWrap worldWrap = engine.createComponent(WorldWrap.class);

        Drawable drawable = engine.createComponent(Drawable.class);
        drawable.textureRegion = gameplayAtlas.findRegion(AtlasRegions.PLAYER);

        Dimension dimension = engine.createComponent(Dimension.class);
        dimension.width = GameConfig.PLAYER_SIZE;
        dimension.height = GameConfig.PLAYER_SIZE;
        Entity playerEntity = engine.createEntity();
        playerEntity.add(positionPlayer);
        playerEntity.add(bounds);
        playerEntity.add(playerMouvement);
        playerEntity.add(player);
        playerEntity.add(worldWrap);
        playerEntity.add(drawable);
        playerEntity.add(dimension);
        playerEntity.add(new Removalbe());
        engine.addEntity(playerEntity);

    }
    public void addObstacle(float x, float y){
        float randomScaler = MathUtils.random(1.0f,3.5f);

        Bounds bounds = engine.createComponent(Bounds.class);
        bounds.bounds.set(x,y,GameConfig.OBSTACLE_BOUNDS_RADIUS*randomScaler);

        Mouvement mouvement = engine.createComponent(Mouvement.class);
        mouvement.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getObstacleSpeed();
        Position position = engine.createComponent(Position.class);

        position.x = x;
        position.y= y;
        WorldWrap wrap = engine.createComponent(WorldWrap.class);
        CleanUp cleanUp = engine.createComponent(CleanUp.class);

        Obstacle obstacle = engine.createComponent(Obstacle.class);


        Drawable drawable = engine.createComponent(Drawable.class);
        drawable.textureRegion = gameplayAtlas.findRegion(AtlasRegions.OBSTACLE);

        Dimension dimension = engine.createComponent(Dimension.class);

        dimension.width = GameConfig.OBSTACLE_SIZE*randomScaler;
        dimension.height = GameConfig.OBSTACLE_SIZE*randomScaler;


        Entity obstacleEntity = engine.createEntity();
        obstacleEntity.add(bounds);
        obstacleEntity.add(wrap);
        obstacleEntity.add(mouvement);
        obstacleEntity.add(position);
        obstacleEntity.add(cleanUp);
        obstacleEntity.add(obstacle);
        obstacleEntity.add(dimension);
        obstacleEntity.add(drawable);
        obstacleEntity.add(new Removalbe());
        engine.addEntity(obstacleEntity);
    }

    public void addBackground(){
        Position position = engine.createComponent(Position.class);

        position.x = 0;
        position.y= 0;

        Dimension dimension = engine.createComponent(Dimension.class);
        dimension.width = GameConfig.WORLD_WIDTH;
        dimension.height = GameConfig.WORLD_HEGHT;


        Drawable drawable = engine.createComponent(Drawable.class);
        drawable.textureRegion = gameplayAtlas.findRegion(AtlasRegions.BACKGROUND);

        Entity background = engine.createEntity();
        background.add(new Background());
        background.add(drawable);
        background.add(position);
        background.add(dimension);
        engine.addEntity(background);



    }

    public void addHeart(float x, float y){
        Bounds bounds = engine.createComponent(Bounds.class);
        bounds.bounds.set(x,y,GameConfig.OBSTACLE_BOUNDS_RADIUS);

        Mouvement mouvement = engine.createComponent(Mouvement.class);
        mouvement.ySpeed = -GameManager.INSTANCE.getDifficultyLevel().getObstacleSpeed();
        Position position = engine.createComponent(Position.class);

        position.x = x;
        position.y= y;
        WorldWrap wrap = engine.createComponent(WorldWrap.class);
        CleanUp cleanUp = engine.createComponent(CleanUp.class);

        Heart heart = engine.createComponent(Heart.class);

        Drawable drawable = engine.createComponent(Drawable.class);
        drawable.textureRegion = gameplayAtlas.findRegion(AtlasRegions.HEART);

        Dimension dimension = engine.createComponent(Dimension.class);
        dimension.width = GameConfig.OBSTACLE_SIZE;
        dimension.height = GameConfig.OBSTACLE_SIZE;


        Entity obstacleEntity = engine.createEntity();
        obstacleEntity.add(bounds);
        obstacleEntity.add(wrap);
        obstacleEntity.add(mouvement);
        obstacleEntity.add(position);
        obstacleEntity.add(cleanUp);
        obstacleEntity.add(heart);
        obstacleEntity.add(dimension);
        obstacleEntity.add(drawable);
        obstacleEntity.add(new Removalbe());
        engine.addEntity(obstacleEntity);
    }
}
