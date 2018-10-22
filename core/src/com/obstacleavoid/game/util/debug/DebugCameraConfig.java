package com.obstacleavoid.game.util.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraConfig {

    // ======= CONSTANTS ===========

    // =========Strings (names inside json file for configuration )============================

    private static final String MAX_ZOOM_IN = "maxZoomIn";

    private static final String MAX_ZOOM_OUT = "maxZoomOut";


    private static final String MOVE_SPEED = "moveSpeed";

    private static final String ZOOM_SPEED = "zoomSpeed";


    private static final String LEFT_KEY = "leftKey";
    private static final String RIGHT_KEY = "rightKey";
    private static final String UP_KEY = "upKey";
    private static final String DOWN_KEY = "downKey";

    private static final String RESET_KEY = "resetKey";
    private static final String LOG_KEY = "logKey";

    private static final String ZOOMIN_KEY = "zoomIn";
    private static final String ZOOMOUT_KEY = "zoomOut";
    //============================================================================================


    private static final Logger log = new Logger(DebugCameraController.class.getName(), Logger.DEBUG);
    private static final int DEFAULT_LEFT_KEY = Input.Keys.Q;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_UP_KEY = Input.Keys.Z;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;
    private static final int DEFAULT_RESET_KEY = Input.Keys.R;
    private static final int DEFAULT_ZOOM_KEY = Input.Keys.PAGE_UP;
    private static final int DEFAULT_DEZOOM_KEY = Input.Keys.PAGE_DOWN;
    private static final int DEFAULT_LOG_KEY = Input.Keys.ENTER;


    private static final float DEFAULT_MOVE_SPEED = 20.0f;
    private static final float DEFAULT_ZOOM_SPEED = 20f;
    private static final float DEFAULT_MAX_ZOOM_IN = 0.20f;
    private static final float DEFAULT_MAX_ZOOM_OUT = 30f;

    private static final String FILE_PATH = "debug/debugCameraConfig.json";


    // ==========================
    // === attributes :
    private float maxZoomIn;
    private float maxZoomOut;
    private float moveSpeed;
    private float zoomSpeed;

    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;

    private int zoomInKey;
    private int zoomOutKey;
    private int resetKey;
    private int logKey;

    private FileHandle fileHandle;

    // ===== constructor :


    public DebugCameraConfig() {
        init();
    }


    // ========== public API ==============


    //<editor-fold desc="GETTER METHODS">

    public float getMaxZoomIn() {
        return maxZoomIn;
    }

    public float getMaxZoomOut() {
        return maxZoomOut;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getZoomSpeed() {
        return zoomSpeed;
    }

    //</editor-fold>

    //<editor-fold desc="isKeyPressed METHODS">
    public boolean isLeftPressed() {
        return Gdx.input.isKeyPressed(leftKey);
    }

    public boolean isRightPressed() {
        return Gdx.input.isKeyPressed(rightKey);
    }

    public boolean isUpPressed() {
        return Gdx.input.isKeyPressed(upKey);
    }

    public boolean isDownPressed() {
        return Gdx.input.isKeyPressed(downKey);
    }

    public boolean isResetPressed() {
        return Gdx.input.isKeyPressed(resetKey);
    }

    public boolean isLogPressed() {
        return Gdx.input.isKeyPressed(logKey);
    }

    public boolean isZoomInPressed() {
        return Gdx.input.isKeyPressed(zoomInKey);
    }

    public boolean isZoomOutPressed() {
        return Gdx.input.isKeyPressed(zoomOutKey);
    }
    //</editor-fold>

    @Override
    public String toString() {
        String LS = System.getProperty("line.separator");

        return "debugCameraConfig {"+LS+
                "maxZoomIn== " +maxZoomIn+LS+
                "maxZoomOut==" + maxZoomOut+LS+
                "moveSpeed== "+ moveSpeed +LS+
                "leftKey==" +Input.Keys.toString(leftKey) +LS+
                "rightKey==" +Input.Keys.toString(rightKey) +LS+
                "upKey==" +Input.Keys.toString(upKey )+LS+
                "downKey==" +Input.Keys.toString(downKey) +LS+
                "logKey==" +Input.Keys.toString(logKey) +LS+
                "resetKey==" +Input.Keys.toString(resetKey )+LS+
                "zoomInKey==" +Input.Keys.toString(zoomInKey) +LS+
                "zoomOutKey==" +Input.Keys.toString(zoomOutKey) +LS+
                "zoomSpeed==" + zoomSpeed+ LS +"}";
    }


    // =========== private methods ===========


    private void init() {
        fileHandle = Gdx.files.internal(FILE_PATH);
        if (fileHandle.exists()) {
            load();
        } else {
            log.info("file does not exist at " + FILE_PATH);
            setupDefaults();
        }
    }


    private void setupDefaults() {
        moveSpeed = DEFAULT_MOVE_SPEED;
        zoomSpeed = DEFAULT_ZOOM_SPEED;
        maxZoomIn = DEFAULT_MAX_ZOOM_IN;
        maxZoomOut = DEFAULT_MAX_ZOOM_OUT;

        leftKey = DEFAULT_LEFT_KEY;
        rightKey = DEFAULT_RIGHT_KEY;
        upKey = DEFAULT_UP_KEY;
        downKey = DEFAULT_DOWN_KEY;

        logKey = DEFAULT_LOG_KEY;
        resetKey = DEFAULT_RESET_KEY;
    }


    private void load() {
        try {
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(fileHandle);
            maxZoomIn = root.getFloat(MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_IN);
            maxZoomOut = root.getFloat(MAX_ZOOM_OUT, DEFAULT_MAX_ZOOM_OUT);
            moveSpeed = root.getFloat(MOVE_SPEED, DEFAULT_MOVE_SPEED);
            zoomSpeed = root.getFloat(ZOOM_SPEED, DEFAULT_ZOOM_SPEED);

            leftKey = getInputValueKey(root, LEFT_KEY, DEFAULT_LEFT_KEY);
            rightKey = getInputValueKey(root, RIGHT_KEY, DEFAULT_RIGHT_KEY);
            upKey = getInputValueKey(root, UP_KEY, DEFAULT_UP_KEY);
            downKey = getInputValueKey(root, DOWN_KEY, DEFAULT_DOWN_KEY);
            resetKey = getInputValueKey(root, RESET_KEY, DEFAULT_RESET_KEY);
            logKey = getInputValueKey(root, LOG_KEY, DEFAULT_LOG_KEY);
            zoomInKey = getInputValueKey(root, ZOOMIN_KEY, DEFAULT_ZOOM_KEY);
            zoomOutKey = getInputValueKey(root, ZOOMOUT_KEY, DEFAULT_DEZOOM_KEY);

        } catch (Exception e) {
            log.error("Error Loading config...",e);
            log.info("Using Default config...");
            setupDefaults();
        }
    }

    private static int getInputValueKey(JsonValue root, String key, int defaultKey) {

        String keyString = root.getString(key, Input.Keys.toString(defaultKey));

        return Input.Keys.valueOf(keyString);
    }

}
