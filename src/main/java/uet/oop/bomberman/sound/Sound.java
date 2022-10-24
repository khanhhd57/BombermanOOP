package uet.oop.bomberman.sound;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import org.apache.log4j.chainsaw.Main;

public class Sound {
    public static void play(String sound){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }

    public static void stop(String sound){
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip c = AudioSystem.getClip();
                    AudioInputStream inputStr = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                    c.open(inputStr);
                    c.stop();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

    }
}
