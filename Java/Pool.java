import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class is the representation of the object in which the faculty will be dropped into
 * A pool is considered a dunk tank.  It contains an image for the the tank as well as
 * x and y positions coordinates.  This class also contains functions for getting the top
 * and bottom of the tanks that is used to determine faculty position with respect to this object
 * and displaying/playing animations and sounds.
 */

public class Pool {
    private Image image;
    int xPosition;
    int yPosition;
    
    /**
     * Constructs a new object which represents a pool on the JPanel.
     *
     * @param	fileName	-	The filename of the pool's image
     * @param	xPosition	-	The pool's initial x coordinate on the JPanel
     * @param	yPosition	-	The pool's initial y coordinate on the JPanel
     */
    public Pool(String fileName, int xPosition, int yPosition) {
        try {
        	image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(160, 210, Image.SCALE_DEFAULT); // Read in the file and scale it
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.xPosition = xPosition;
        this.yPosition = yPosition;

    }
    
    /**
     * This method draws the image of a pool to the JPanel.
     *
     * @param	graphics	A graphics object which is a reference to the JPanel
     */
    public void draw(Graphics graphics) {
        graphics.drawImage(image, xPosition, yPosition, null);

    }
    /**
     * This gets the bottom line of the pool; used to determine faculty position with respect to this object
     * and displaying/playing animations and sounds.
     * 
     * @return The bottom line of the pool
     */
    public Line2D getMiddleLine() {
    	return new Line2D.Float( this.xPosition , ((this.yPosition + this.image.getHeight(null)) / 2) , 
				(this.xPosition + this.image.getWidth(null)) , ((this.yPosition + this.image.getHeight(null)) / 2) );
    }
    
    /**
     * This gets the bottom line of the pool; used to determine faculty position with respect to this object
     * and displaying/playing animations and sounds.
     * 
     * @return The bottom y coordinate of the pool
     */
    public Line2D getBottomLine() {
    	return new Line2D.Float( this.xPosition , (this.yPosition + this.image.getHeight(null)) - 10, 
    							(this.xPosition + this.image.getWidth(null)) , (this.yPosition + this.image.getHeight(null)) - 10 );
    }

}
