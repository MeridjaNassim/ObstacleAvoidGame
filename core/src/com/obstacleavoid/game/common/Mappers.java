package com.obstacleavoid.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.obstacleavoid.game.component.Bounds;
import com.obstacleavoid.game.component.Dimension;
import com.obstacleavoid.game.component.Drawable;
import com.obstacleavoid.game.component.Heart;
import com.obstacleavoid.game.component.Mouvement;
import com.obstacleavoid.game.component.Obstacle;
import com.obstacleavoid.game.component.Position;

public final class Mappers {

    public static final ComponentMapper<Bounds> BOUNDS = ComponentMapper.getFor(Bounds.class);

    public static final ComponentMapper<Mouvement> MOUVEMENT = ComponentMapper.getFor(Mouvement.class);

    public static final ComponentMapper<Position> POSITION = ComponentMapper.getFor(Position.class);

    public static final ComponentMapper<Obstacle> OBSTACLE = ComponentMapper.getFor(Obstacle.class);

    public static final ComponentMapper<Heart> HEART = ComponentMapper.getFor(Heart.class);
    public static final ComponentMapper<Drawable> DRAWABLE = ComponentMapper.getFor(Drawable.class);

    public static final ComponentMapper<Dimension> DIMENSION = ComponentMapper.getFor(Dimension.class);

    private Mappers(){}
}
