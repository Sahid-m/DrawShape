import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound {

	private Clip clip;

	public void playSoundOnce(String soundFile) {
	    try {
	        File soundPath = new File(soundFile);
	        
	        if(soundPath.exists()) {
	        	AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
	        	Clip clip = AudioSystem.getClip();
	        	clip.open(audioInput);
	        	clip.start();
	        	System.out.println("Played Sound");
	        }else {
	        	System.out.println("File Not Found");
	        }
	    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}




	public void playSoundForTime(char action, String soundFile) {
		switch (action) {
		case 'p':
			playSound(soundFile);
			break;
		case 's':
			stopSound();
			break;
		default:
			System.out.println("Invalid action.");
		}
	}

	private void stopSound() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
	}

	private void playSound(final String soundFile) {
		new Thread(new Runnable() {
			public void run() {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(soundFile));
					clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the sound indefinitely
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
