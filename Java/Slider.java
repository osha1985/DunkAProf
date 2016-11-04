import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is object used as a starting point for our projectiles in the game.
 * This object slides back and forth on an x axis just below the dunk tanks. 
 */
public class Slider {

    /**
     * This is the image which represents the slider on the screen.
     */
    private BufferedImage image;

    /**
     *	This is the current x coordinate of the slider on the screen.
     */
    private int xPosition;

    /**
     * This is the current y coordinate of the slider on the screen.
     */
    private int yPosition;

    /**
     * This is the width of the JFrame.
     */
    private int panelWidth;

    /**
     * This indicates the direction that the slider is moving in.
     */
    private Direction direction = Direction.RIGHT;

    /**
     * Represents the moving status for this object.  It is set to true
     * if this slider is moving, otherwise false.
     */
    boolean moving = true;

    /**
     * 
     * @param fileName Name of the image file that will be used to represent this slider.
     * @param xPosition Starting x position of this slider
     * @param yPosition Starting y position of this slider
     * @param panelWidth Width of the container to determine how far this slider will move in any direction.
     */
    public Slider(String fileName, int xPosition, int yPosition, int panelWidth) {
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.panelWidth = panelWidth;
    }

    /**
     * Draws the slider on the screen
     * @param graphics
     */
    public void draw(Graphics graphics) {

        // Draw the slider
        graphics.drawImage(image, xPosition, yPosition, null); 

    }  // end of draw()

    /**
     * Represents the direction in which the slider is moving.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the moving direction of the slider.
     * @param direction Represents the direction in which the slider is moving
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets current x position of this slider; used for starting position of projectile
     */
    public int getxPosition() {
        return this.xPosition;
    }

    /**
     * Gets current y position of this slider; used for starting position of projectile
     */
    public int getyPosition() {
        return this.yPosition;
    }

    /**
     * Move this slider either left or right based on the current direction and distance to 
     * the end of the game panel.
     * @param distance Integer used to decrement x position and determine whether to move right
     * or not.
     */
    public void move(int distance) {
        if(moving) {
            switch(this.direction) {
            case RIGHT:
                xPosition += 5;
                if(xPosition >  panelWidth - image.getWidth()) {
                    this.direction = Direction.LEFT;
                }
                break;
            case LEFT:
                xPosition -= distance;
                if(xPosition < 0) {
                    this.direction = Direction.RIGHT;
                }
			case FORWARD:
				break;
			default:
				break;
            }
        }

    }

    /**
     * Used to stop this slider by setting the moving variable to false.
     */
    public void stopMovement() {
        moving = false;
    }

    /**
     * Used to start this slider by setting the moving variable to true.
     */
    public void startMovement() {
        moving = true;
    }

    /**
     * Used to determine if this slider is moving.
     * @return True if this object is currently moving.
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Gets the width of the image representing this slider
     * @return Slider's width
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Gets the height of the image representing this slider
     * @return Slider's height
     */
    public int getHeight() {
        return image.getHeight();
    }

}
