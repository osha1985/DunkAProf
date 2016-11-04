import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;


/**
 * This class is used to display and manage the player's time limit. 
 * This class uses the Watch class (Implemented below), which uses an ActionListener, to simulate
 * the 'ticking'or count down effect.
 * 
 * @author Osher Cohen
 *
 */
public class Clock  {

    /**
     * This is used to store the game's time limit
     */
    private int timeLimit;

    /**
     * This is used to store the font used for the clock.
     */
    private Font font;

    /**
     * This is used to schedule a decrement of the time every second
     */
    private Timer timer;

    /**
     * This is used to store the x coordinate of the clock.
     */
    private int xPosition;

    /**
     * This is used to store the y coordinate of the clock.
     */
    private int yPosition;
    
    /**
     * This is used to store the time left in milliseconds.
     */
    private int timeInMilliseconds;

    /**
     * Color object used to set color of the clock's text
     */
    private final Color color;
    
    /**
     * This constructs a clock object which represents
     *
     * @param fileName	The file name of the font file that the string for the time limit will use.
     * @param xPosition	The x coordinate of the position where the time limit will be placed.
     * @param yPosition	The y coordinate of the position where the time limit will be placed.
     * @param timeLimit	The time limit the user has in the game.
     * 
     */
    public Clock(String fileName, int xPosition, int yPosition, int timeLimit) {
        try {
        	
        	/**
        	 * Create a font object from the font file
        	 */
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName));
            
            /**
             * Make the font size 36 point.
             */
            font = font.deriveFont(36f);
        } catch (FontFormatException e) {
        	
            e.printStackTrace();
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        this.xPosition = xPosition;
        this.yPosition = yPosition;
	this.color = new Color(255, 247, 153);
        /**
         * The time limit is decremented as soon as the game starts, so the time limit has to be incremented to counteract this.
         */
        this.timeLimit = timeLimit + 1;
        
        /**
         * One second is a thousand milliseconds.
         */
        this.timeInMilliseconds = this.timeLimit * 1000;
        
        /**
         * Create an instance of the ActionListener Watch that will be used to decrement the timeLimit.
         */
        Watch watch = new Watch();
        
        /**
         * Create a Timer that will tell the ActionListener Watch to perform its action (decrement the timer) every millisecond.
         */
        timer = new Timer(1,watch);
    }

    /**
     * This method draws the clock to the JPanel
     *
     * @param  graphics	A reference to the JPanel buffer
     */
    public void draw(Graphics graphics) {
    	
    	/**
    	 * Set the font which is to be used to draw the time limit to the screen.
    	 */
        graphics.setFont(font);
        graphics.setColor(this.color);
        /**
         * Draw the time limit to the JPanel's off-screen buffer.
         */
        graphics.drawString("Time \t\t" + TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) + "", this.xPosition, this.yPosition);
    }

    /**
     * This method returns the game's time limit.
     *
     * @return  timeLimit	The game's time limit.
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * This method stops the clock.
     */
    public void stopClock() {
        timer.stop();
    }

    /**
     * This method allows you to resume the clock from where it left of when you stopped it.
     */
    public void resumeClock() {
        timer.start();
    }
    
    /**
     * This is used to check if the player's time limit has run out
     * @return True if the player has run out of time. Otherwise it returns false.
     */
    public boolean outOfTime() {
        return TimeUnit.MILLISECONDS.toSeconds(timeInMilliseconds) <= 0;
    }
    
    /**
     * Watch is an ActionListener that decrements the time whenever the timer tells it to (every millisecond).
     * @author Osher Cohen
     *
     */
    private class Watch implements ActionListener {

		@Override
		/**
		 * Actions that the ActionListen will perform are placed here. More specifically, this will decrement the time in milliseconds.
		 * If the time in milliseconds is less than zero, then this stops the clock.
		 */
		public void actionPerformed(ActionEvent e) {
			Clock.this.timeInMilliseconds-= 1;
			if(Clock.this.timeInMilliseconds <= 0) {
				Clock.this.stopClock();
			}
		}
		
    }
   
}
