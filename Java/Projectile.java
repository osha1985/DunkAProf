import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Object used to hit the targets near the faculty.  This class contains an image for the 
 * projectile, x and y position coordinates, the speed/velocity of projection, whether or not
 * its been fired, the direction in which it is moving, and methods that those attributes.
 */
public class Projectile {
	
	/**
	 * Stores the image for this projectile
	 */
    private Image image;
    
	/**
	 * Stores the current x position for this projectile
	 */
    private int xPosition;
    
	/**
	 * Stores the current y position for this projectile
	 */
    private int yPosition;
    
	/**
	 * Determines this projectiles speed; used to add or subtract from the current
	 * x and or y positions depending on direction.
	 */
    private int speed  = 20;
    
	/**
	 * States whether this projectile has been fired or not.
	 */
    boolean fired = false;
    
	/**
	 * Holds the direction position for this projectile and initiates it Direction.FORWARD
	 */
    Direction direction = Direction.FORWARD;
    
    /**
     * Constructor that sets the projectile image and starting x and y coordinates.
     * @param fileName File image used to represent a projectile.
     * @param xPosition Starting x position of the projectile.
     * @param yPosition Starting y position of the projectile.
     */
    public Projectile(String fileName, int xPosition, int yPosition) {
        try {
            image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(20, 20, Image.SCALE_DEFAULT); // Read in the file and scale it
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.xPosition = xPosition; // Initialize the x Position of the projectile
        this.yPosition = yPosition; // Initialize the y Position of the projectile
    }

    public void fire() {
        fired = true;
        
    } 
    
    /**
     * Draws the projectile to the off-screen image.
     */
    public void draw(Graphics graphics) {

        // If the projectile is in the state of being fired, draw the projectile.
        if(fired) {
            graphics.drawImage(image, (int)this.xPosition, (int)this.yPosition, null);
        }

    }
    /**
     * This controls the movement of the projectile.
     * 
     * @param xPosition	-	The x coordinate of the projectile when it is not fired.
     * @param yPosition	-	The y coordinate of the projectile when it is not fired.
     */
    public void move(int xPosition, int yPosition) {
        if (fired) {
        	switch(direction) {
			case FORWARD:
				this.yPosition -= speed;
				break;
			case LEFT:
				this.xPosition -= speed;
				this.yPosition -= speed;
				break;
			case RIGHT:
				this.xPosition += speed;
				this.yPosition -= speed;
				break;
			default:
				break;
        	
        	}
            //Reset the projectile to its original location if it goes beyond the bounds of the JPanel
            if(this.yPosition < 0 || this.yPosition > 600 ) {
                fired = false;
                this.xPosition = xPosition;
                this.yPosition = yPosition;
            }
        } else {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        }

    }
    
    /**
     * This gets the x coordinate of the projectile. 
     * 
     * @return	The x coordinate of the projectile.
     */
    public double getXPosition() {
        return xPosition;
    }

    /**
     * This gets the y coordinate of the projectile.
     * 
     * @return The y coordinate of the projectile.
     */
    public double getYPosition() {
        return yPosition;
    }

    /**
     * This returns the bounding box surrounding the projectile.
     * 
     * @return The bounding box surrounding the projectile.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle((int)this.xPosition, (int)this.yPosition, this.image.getWidth(null), this.image.getHeight(null));
    }

    /**
     * This gets the width of the projectile.
     * 
     * @return The width of the projectile
     */
    public int getWidth() {
        return image.getWidth(null);
    }

    /**
     * This gets the height of the projectile
     * 
     * @return The height of the projectile.
     */
    public int getHeight() {
        return image.getHeight(null);
    }
    
    /**
     * Sets the direction of the projectile
     * @param direction Direction of the projectile
     */
    public void setDirection(Direction direction) {
    	this.direction = direction;
    }
}
