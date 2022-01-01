package com.company.zombieGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum Sound {

    MARLOC("sounds/best1.wav"),
    GUN("sounds/gun.wav"),
    NOPE("sounds/nope.wav"),
    BURP("sounds/burp.wav"),
    ATTACK("sounds/hit.wav"),
    LIGHTNING("sounds/lightning.wav"),
    ENDING("sounds/end.wav"),
    LASER("sounds/laser.wav");

    private final String path;

    Sound(String path) {
        this.path = path;
    }

    public void start() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResourceAsStream(path));
            clip.open(inputStream);
            clip.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
