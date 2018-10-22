package com.obstacleavoid.game.screen.game;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.List;

public class GameMusicPlayer {

    private Music gameMusic;
    private List<Music> musics;
    private Music currentMusic;
    private static int index = 0;

    public GameMusicPlayer(Music gameMusic) {
        this.gameMusic = gameMusic;
        this.musics = new ArrayList<Music>();
        this.musics.add(gameMusic);
        this.currentMusic = gameMusic;
    }

    public GameMusicPlayer(List<Music> playlist) {
        this.musics = new ArrayList<Music>(playlist);
        this.currentMusic = musics.get(0);
    }

    public void playMusics() {
        if (!musics.isEmpty()) {
            if (!currentMusic.isPlaying()) {
                currentMusic.play();
                if (musics.size() > 1) {
                    index = (index + 1) % musics.size();
                    currentMusic = musics.get(index);
                }
            }
        }

    }

    public void stopPlayer() {
        currentMusic.stop();
    }

    public boolean isPlaying() {
        return currentMusic.isPlaying();
    }
}
