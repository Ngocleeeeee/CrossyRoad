package Game;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundUrl[] = new URL[30];
	
	public Sound() {
		soundUrl[0]=getClass().getResource("/Sound/Jump.wav");
		soundUrl[1]=getClass().getResource("/Sound/Death.wav");
		soundUrl[2]=getClass().getResource("/Sound/Pause.wav");
		soundUrl[3]=getClass().getResource("/Sound/Theme.wav");
		soundUrl[4]=getClass().getResource("/Sound/Start.wav");
		soundUrl[5]=getClass().getResource("/Sound/Power.wav");
		soundUrl[6]=getClass().getResource("/Sound/PowerDown.wav");
		soundUrl[7]=getClass().getResource("/Sound/ThemePlay.wav");
	}
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
			clip=AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
}
