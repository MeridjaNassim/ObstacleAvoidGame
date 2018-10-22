package com.obstacleavoid.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.game.common.Mappers;
import com.obstacleavoid.game.component.Mouvement;
import com.obstacleavoid.game.component.Position;

public class MouvementSystem extends IteratingSystem {

    private static final Logger log = new Logger(MouvementSystem.class.getName(), Logger.DEBUG);

    private static final Family FAMILY = Family.all(

            Position.class,
            Mouvement.class


    ).get();

    public MouvementSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position position = Mappers.POSITION.get(entity);
        Mouvement mouvement = Mappers.MOUVEMENT.get(entity);

        position.x += mouvement.xSpeed;
        position.y +=mouvement.ySpeed;


    }



}
