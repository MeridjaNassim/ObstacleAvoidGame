package com.obstacleavoid.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.obstacleavoid.game.ObstacleAvoidGame;
import com.obstacleavoid.game.config.DifficultyLevel;
import com.obstacleavoid.game.config.GameConfig;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String  HIGH_SCORE_KEY_EASY = "highscoreEasy";
    private static final String  HIGH_SCORE_KEY_MEDIUM = "highscoreMedium";
    private static final String  HIGH_SCORE_KEY_HARD = "highscoreHard";
    private static final String  DIFFICULTY_KEY = "difficulty";

    private Preferences PREFS ;
    private int highscoreEasy ;
    private int highscoreMedium;
    private int highscoreHard;
    private int lives = GameConfig.LIVE_START;
    private int score;
    private DifficultyLevel difficultyLevel ;

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    private GameManager(){
        PREFS = Gdx.app.getPreferences(ObstacleAvoidGame.class.getName());
        String difficultyName = PREFS.getString(DIFFICULTY_KEY,DifficultyLevel.MEDIUM.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
        highscoreEasy = PREFS.getInteger(HIGH_SCORE_KEY_EASY,0);
        highscoreMedium = PREFS.getInteger(HIGH_SCORE_KEY_MEDIUM,0);
        highscoreHard = PREFS.getInteger(HIGH_SCORE_KEY_HARD,0);
    }
    public String getHighScoreString (){
        switch (difficultyLevel){
            case EASY:
                return getDifficultyLevel().name() +":"+String.valueOf(highscoreEasy);

            case MEDIUM:
                return getDifficultyLevel().name() +":"+String.valueOf(highscoreMedium);

            case HARD:
                return getDifficultyLevel().name() +":"+ String.valueOf(highscoreHard);


        }
        return String.valueOf(highscoreEasy);
    }
    public void updateDifficulty(DifficultyLevel level){
        if(difficultyLevel ==level){
            return;
        }
        difficultyLevel = level;
        PREFS.putString(DIFFICULTY_KEY,difficultyLevel.name());
        PREFS.flush();

    }

    public void updateHighScore(){
        switch (getDifficultyLevel()){
            case EASY:
                if(score<highscoreEasy) return;
                else {
                    highscoreEasy = score;
                    PREFS.putInteger(HIGH_SCORE_KEY_EASY,highscoreEasy);
                    PREFS.flush();
                }
                break;

            case MEDIUM:
                if(score<highscoreMedium) return;
                else {
                    highscoreMedium = score;
                    PREFS.putInteger(HIGH_SCORE_KEY_MEDIUM,highscoreMedium);
                    PREFS.flush();
                }
                break;

            case HARD:
                if(score<highscoreHard) return;
                else {
                    highscoreHard = score;
                    PREFS.putInteger(HIGH_SCORE_KEY_HARD,highscoreHard);
                    PREFS.flush();
                }
                break;
        }

    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void decrementLives(){
        lives--;
    }
    public void incrementLives(){
        lives++;
    }
    public boolean isGameOver(){
        return lives <=0;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void reset(){
        GameManager.INSTANCE.setLives(GameConfig.LIVE_START);
        GameManager.INSTANCE.setScore(0);
    }
    public void updateScore(int amount){
        score +=amount;
    }
    public float getObstacleSpawnTime(){
        float interval;

        switch (getDifficultyLevel()){
            case EASY:
                interval =GameConfig.OBSTACLE_SPAWN_TIME_EASY;
                break;
            case MEDIUM:
                interval = GameConfig.OBSTACLE_SPAWN_TIME_MEDIUM;
                break;
            case HARD:
                interval = GameConfig.OBSTACLE_SPAWN_TIME_HARD;
                break;
            default:
                interval =GameConfig.OBSTACLE_SPAWN_TIME;
        }
        return interval;
    }
    public float getHeartSpawnTime(){
        float interval;

        switch (getDifficultyLevel()){
            case EASY:
                interval =GameConfig.HEART_SPAWN_TIME_EASY;
                break;
            case MEDIUM:
                interval = GameConfig.HEART_SPAWN_TIME_MEDIUM;
                break;
            case HARD:
                interval = GameConfig.HEART_SPAWN_TIME_HARD;
                break;
            default:
                interval =GameConfig.OBSTACLE_SPAWN_TIME;
        }
        return interval;
    }
}
