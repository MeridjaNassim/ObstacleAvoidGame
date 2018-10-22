package com.obstacleavoid.game.system;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.MathUtils;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.config.GameConfig;

public class ScoreSystem extends IntervalSystem {

    public ScoreSystem() {
        super(GameConfig.SCORE_MAX_TIME);
    }

    @Override
    protected void updateInterval() {
        GameManager.INSTANCE.updateScore(MathUtils.random(1,5));
    }
}
