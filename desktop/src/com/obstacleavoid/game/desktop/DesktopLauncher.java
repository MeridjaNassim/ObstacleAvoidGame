package com.obstacleavoid.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.config.GameConfig;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=(int) GameConfig.width;
		config.height= (int) GameConfig.height;

        new LwjglApplication(new ObstacleAvoidGame(), config);
    }
}
