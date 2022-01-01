package com.company.zombieGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum Music {

    MUSIC("musics/musicGame.wav"),
    RAIN("musics/rain.wav");

    private final String path;
    private Clip clip;

    Music(String path) {
        this.path = path;
    }

    public void start() {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(path));
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
    }

}
