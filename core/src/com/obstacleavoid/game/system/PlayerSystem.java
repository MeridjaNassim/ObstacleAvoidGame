package com.obstacleavoid.game.system;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Mouvement;
import com.obstacleavoid.game.component.Player;
import com.obstacleavoid.game.component.Position;
import com.obstacleavoid.game.config.GameConfig;

public class PlayerSystem extends IteratingSystem {


    private static final Logger log = new Logger(PlayerSystem.class.getName(), Logger.DEBUG);
    private static final Family FAMILY = Family.all(
            Player.class,
            Mouvement.class
    ).get();


    public PlayerSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Mouvement mouvement = Mappers.MOUVEMENT.get(entity);
        mouvement.xSpeed =0;


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            mouvement.xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            mouvement.xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

    }
}
