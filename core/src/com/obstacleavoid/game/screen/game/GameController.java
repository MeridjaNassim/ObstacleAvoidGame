package com.obstacleavoid.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.assets.AssetDescriptors;
import com.obstacleavoid.game.common.GameManager;
import com.obstacleavoid.game.config.DifficultyLevel;
import com.obstacleavoid.game.config.GameConfig;
import com.obstacleavoid.game.entity.Background;
import com.obstacleavoid.game.entity.GameObject;
import com.obstacleavoid.game.entity.Heart;
import com.obstacleavoid.game.entity.Obstacle;
import com.obstacleavoid.game.entity.Player;
import com.obstacleavoid.game.util.GdxUtils;

@Deprecated
public class GameController {

    // ============ CONSTANTS ==================
    private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;
    // =============== fields =============
    float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
    float startPlayerY = 1;

    private Background background;
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private Array<Heart> hearts = new Array<Heart>();
    private float obstacleTimer = 0;
    private float heartTimer= 0;
    private float scoreTimer;

    private boolean roundLost = false;
    private int lives = GameConfig.LIVE_START;
    private int score;
    private int displayScore = 0;
    private Sound hit;
    private  final GameMusicPlayer musicPlayer;

    private Pool<Obstacle> obstaclePool;
    private Pool<Heart> heartPool;
    DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
    //====================================

    public GameController(ObstacleAvoidGame game) {
        this.game =game;
        this.assetManager = game.getAssetManager();

        init();
        musicPlayer = new GameMusicPlayer(assetManager.get(AssetDescriptors.GAMEPLAY_TRACK));
    }


    // ============== public methods =====================


    public Background getBackground() {
        return background;
    }

    public void update(float delta) {
        if (isGameOver()) {
         if(musicPlayer.isPlaying()){
//             musicPlayer.stopPlayer();
         }
            return;
        }
        if (!isGameOver()) {
            playMusic();
        }
        updatePlayer();
        updateObstacles(delta);
        updateHearts(delta);
        updateScore(delta);
        updateDisplayScore(delta);

        if(playerCollidedHeart() && ! roundLost){
            lives++;
        }

        if (playerCollidedObstacle() && !roundLost) {
            log.debug("Collison Detected ");
            lives--;
            roundLost = true;
            if (!isGameOver()) {
                TryAgain();
            } else {

//                GameManager.INSTANCE.updateHighScore(score,getDifficultyLevel());
                return;
            }
        }
    }



    public Player getPlayer() {
        return player;
    }

    public Array<Heart> getHearts() {
        return hearts;
    }

    public Array<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getLives() {
        return lives;
    }

    public boolean isRoundLost() {
        return roundLost;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public DifficultyLevel getDifficultyLevel() {
        return GameManager.INSTANCE.getDifficultyLevel();
    }

    public boolean isGameOver() {
        if (lives <= 0) {
            return true;
        }
        return false;
    }

    // ==================== private methods ===========
    private void init() {
        hit = assetManager.get(AssetDescriptors.HIT_TRACK);

        obstaclePool = Pools.get(Obstacle.class, 40);
        heartPool = Pools.get(Heart.class,10);
        player = new Player();
        // calculating position;


        player.setPosition(startPlayerX, startPlayerY);

        // creating debugcameracontroller

        background = new Background();
        background.setPosition(0, 0);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEGHT);


    }


    private void TryAgain() {
        GdxUtils.clearScreen();
        resetGameStage();
        roundLost = false;
    }

    private void resetGameStage() {
        obstaclePool.freeAll(obstacles);
        obstacles.clear();
        obstaclePool.clear();
        player.setPosition(startPlayerX, startPlayerY);

    }

    private boolean playerCollidedObstacle() {

        for (Obstacle obstacle : obstacles) {
            if (obstacle.isNotHit() && obstacle.CollisionWithPlayer(player)) {
                hit.play();
                return true;
            }
        }
        return false;
    }
    private boolean playerCollidedHeart() {

        for (Heart heart : hearts) {
            if (heart.isNotHit() && heart.CollisionWithPlayer(player)) {
                hit.play();
                return true;
            }
        }
        return false;
    }


    private void updateScore(float delta) {
        scoreTimer += delta;
        if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
            score += MathUtils.random(1, 4);
            scoreTimer = 0;
        }

    }

    private void updateDisplayScore(float delta) {
        if (displayScore < score) {
            displayScore = Math.min(score, displayScore + (int) (1 / delta * delta));
        }
    }

    private void updatePlayer() {

        float xSpeed = 0;
//        float ySpeed=0;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED
            ;
        }
//        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
//            ySpeed = GameConfig.MAX_PLAYER_Y_SPEED;}
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
//            ySpeed= -GameConfig.MAX_PLAYER_Y_SPEED;
//        }
//        ySpeed+= ySpeed;
        player.setX(player.getX() + xSpeed);
//        player.setY(player.getY()+ySpeed);
        restrictWorldBounds(player);
    }

    private void updateObstacles(float delta) {
        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
            restrictWorldBounds(obstacle);
        }
        disposePassedObstacle();
        createNewObstacle(delta);
    }
    private void updateHearts(float delta) {
        for (Heart heart : hearts) {
            heart.update(delta);
            restrictWorldBounds(heart);
        }
        disposePassedHeart();
        createNewHeart(delta);
    }

    private void disposePassedObstacle() {

        if (obstacles.size > 0) {
            Obstacle first = obstacles.first();
            float minObstacleY = -GameConfig.OBSTACLE_SIZE;
            if (first.getY() < minObstacleY) {
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }

        }


//        if (obstacles.get(index).getY() < player.getY() - GameConfig.LIMIT) {
//            obstacles.removeIndex(index);
//        }
    }
    private void disposePassedHeart() {

        if (hearts.size > 0) {
            Heart first = hearts.first();
            float minObstacleY = -GameConfig.OBSTACLE_SIZE;
            if (first.getY() < minObstacleY) {
                hearts.removeValue(first, true);
                heartPool.free(first);
            }

        }


//        if (obstacles.get(index).getY() < player.getY() - GameConfig.LIMIT) {
//            obstacles.removeIndex(index);
//        }
    }


    private void createNewObstacle(float delta) {

        obstacleTimer += delta;
        if (obstacleTimer >=difficultyLevel.getObstacleSpawnTime() ) {
            float min = 0;
            float max = GameConfig.WORLD_WIDTH;
            float ObstacleX = MathUtils.random(min, max);
            float ObstacleY = GameConfig.WORLD_HEGHT - GameConfig.OBSTACLE_SIZE;

            Obstacle newobstacle = obstaclePool.obtain();
            newobstacle.setYspeed(difficultyLevel.getObstacleSpeed());
            newobstacle.setPosition(ObstacleX, ObstacleY);

            obstacles.add(newobstacle);
            obstacleTimer = 0f;
        }

    }
    private void createNewHeart(float delta) {

        heartTimer += delta;
        if (heartTimer >=difficultyLevel.getHeartSpawnTime() ) {
            float min = 0;
            float max = GameConfig.WORLD_WIDTH;
            float HeartX = MathUtils.random(min, max);
            float HeartY = GameConfig.WORLD_HEGHT - GameConfig.OBSTACLE_SIZE;

            Heart newheart = heartPool.obtain();
            newheart.setYspeed(difficultyLevel.getObstacleSpeed()); // heart speed is same as obstacle
            newheart.setPosition(HeartX, HeartY);

            hearts.add(newheart);
            heartTimer = 0f;
        }

    }


    private void restrictWorldBounds(GameObject object) {
        float objectX = MathUtils.clamp(object.getX(), object.getWidth() / 2f, GameConfig.WORLD_WIDTH - object.getWidth() / 2f);
        object.setPosition(objectX, object.getY());

    }

    private void playMusic(){
        if(!musicPlayer.isPlaying()){
            musicPlayer.playMusics();
        }

    }
}
