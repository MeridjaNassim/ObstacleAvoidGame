package com.obstacleavoid.game.system.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Bounds;
import com.obstacleavoid.game.component.Heart;
import com.obstacleavoid.game.component.Obstacle;
import com.obstacleavoid.game.component.Player;

public class CollisionSystem extends EntitySystem {

    private static final Logger log = new Logger(CollisionSystem.class.getName(), Logger.DEBUG);

    private static final Family PLAYER_FAMILY = Family.all(
            Player.class,
            Bounds.class
    ).get();



    private static final Family OBSTACLE_FAMILY = Family.all(
            Obstacle.class,
            Bounds.class
    ).get();

    private static final Family HEART_FAMILY = Family.all(

            Heart.class,
            Bounds.class
    ).get();

    private final CollisionListener listenerObstacle ;
    private final CollisionListener listenerHeart ;




    public CollisionSystem(CollisionListener listenerObstacle,CollisionListener listenerHeart){
        this.listenerObstacle = listenerObstacle;
        this.listenerHeart = listenerHeart;
    }

    @Override
    public void update(float deltaTime) {

        ImmutableArray<Entity> players = getEngine().getEntitiesFor(PLAYER_FAMILY); // will be  one player
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(OBSTACLE_FAMILY);
        ImmutableArray<Entity> hearts = getEngine().getEntitiesFor(HEART_FAMILY);
        for (Entity player: players) {

            for (Entity heart : hearts){
                Heart hrt = Mappers.HEART.get(heart);
                if(hrt.hit){
                    continue;
                }
                if(checkCollision(player,heart)){
                    hrt.hit = true;
                    getEngine().removeEntity(heart);
                    listenerHeart.hit();
                }
                }

            for (Entity obstacle : obstacles) {
                Obstacle obs = Mappers.OBSTACLE.get(obstacle);
                if(obs.hit){
                    continue;
                }
                if(checkCollision(player,obstacle)){
                    obs.hit = true;
                    log.debug("collision detected ");
                    listenerObstacle.hit();
                }
            }

        }

    }

    private boolean checkCollision(Entity player ,Entity obstacle){
        Bounds playerBounds = Mappers.BOUNDS.get(player);
        Bounds obstacleBounds = Mappers.BOUNDS.get(obstacle);
        return Intersector.overlaps(playerBounds.bounds,obstacleBounds.bounds);

    }
}
