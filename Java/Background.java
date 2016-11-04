import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This class is used to manage the background image.  
 * This is used and set in the GamePanel class.
 * 
 * @author Osher Cohen
 *
 */

public class Background {
	 
    /**
     * This is used to store the background image.
     */
    private Image image;
    
    /**
     * Constructs the object which takes care of the background image.
     * @param fileName		The filename of the file that stores the background image.
     * @param width			The width of the JPanel.
     * @param height		The height of the JPanel.
     */
    public Background(String fileName, int width, int height) {
        try {
        	
        	/**
        	 * The background is resized to fill the JPanel
        	 */
            image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(width, height, Image.SCALE_DEFAULT); // Read in the file and scale it
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * This is used to draw the background image onto the JPanel.
     * @param graphics	The Graphics object that represents JPanel's off-screen buffer.
     */
    
    public void draw(Graphics graphics) { 
        graphics.drawImage(image, 0, 0, null);

    }  // end of draw()
}
