package com.hugomoncada;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect {
    
    Clip clip;

    public void playFile(String soundFileName){
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.setFramePosition(0);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


}
