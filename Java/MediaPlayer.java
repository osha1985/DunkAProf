import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineListener;

/**
 * This object contains the attributes for playing a sound file for this game.  It is used
 * by the Professor, Dean and Trustee objects to play screams and splash sounds.
 */
public class MediaPlayer {
	
	/**
	 * Entry point/container for the sampled-audio system resources.
	 */
    private Mixer mixer;
    
    /**
     * Stores a dataline to the sound file by calling the mixer's getline method.
     */
    private Clip clip;
    
    /**
     * Stores the list of audio mixers/players on the current system.
     */
    private Mixer.Info [] mixerInfo = AudioSystem.getMixerInfo();
    
    /**
     * Represents a specific line of audio to passed to a Clip object.
     */
    private DataLine.Info dataInfo;
    
    /**
     * Stores an audio input stream from the provided sound file
     */
    private AudioInputStream audioStream;
    
    /**
     * Stores the name of the sound file to be played.
     */
    private String fileName;
    
    /**
     * This is used to check if the audio file is playing.
     */
    private boolean isPlaying = false;
    
    /**
     * This is used to listen to the playback status of the audio file.
     */
    private Listener listener;
    /**
     * Stores a flag indicating whether the sound loops forever or not
     */
    private boolean loopContinuously = false;

    /**
     * Constructor which takes a sound file as a parameter to be played.
     * @param fileName			Name of sound file to be played
     * @param loopContinuously	Indicates if the sound file should loop continuously
     */
    public  MediaPlayer (String fileName, boolean loopContinuously) {
        this.fileName = fileName;
        this.loopContinuously = loopContinuously;
        listener = new Listener();
    }


    /**
     * Plays the audio file specified in the constructor.
     *
     */
    public void play() {
        try {
        	
            /**
             * The AudioSystem class acts as the entry point to the sampled-audio system resources.
             */
            this.mixer = AudioSystem.getMixer(mixerInfo[0]);

            /**
             * Obtains an audio input stream from the provided File.
             */
            this.audioStream = AudioSystem.getAudioInputStream(new File(this.fileName));

            /**
             * Construct the data line's info object from the specified information, which includes a single audio format.
             */
            this.dataInfo = new DataLine.Info(Clip.class, audioStream.getFormat());

            this.clip = (Clip) mixer.getLine(dataInfo);
            
            //Opens the clip with the format and audio data present in the provided audio input stream.
            this.clip.open(audioStream);
            
           
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        /**
         * This used to add a LineListener to the clip which will listen for 
         * when the sound file starts and stops. 
         */
        this.clip.addLineListener(listener);
        
        /**
         * If the sound is supposed to loop continuously, then loop it continuously.
         */
        if (this.loopContinuously) {
        	this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        
        /**
         * 	Otherwise, play it once.
         */
        } else {
        	
        	/**
        	 * If the sound isn't playing, then you can play it.
        	 */
        	if(!this.isPlaying) {
        		this.clip.start();
            }
        }
    }
    private class Listener implements LineListener {

		@Override
		public void update(LineEvent event) {
			// TODO Auto-generated method stub
			if(event.getType() == Type.STOP) {
				MediaPlayer.this.isPlaying = false;
			} else if (event.getType() == Type.START) {
				MediaPlayer.this.isPlaying = true;
			}
			
		}
    	
    }
}
