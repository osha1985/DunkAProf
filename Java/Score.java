import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This class keeps track of the score in the game as well as the a few 
 * attributes such as Font, Color and xy coordinates.
 */
public class Score {

    /**
     * This is used to store the current score. 
     */
    private int currentScore = 0;

    /**
     * This stores the x coordinate of the image of score.
     */
    private int xPosition;

    /**
     * This stores the y coordinate of image of the score
     */
    private int yPosition;

    /**
     * This stores the color of the image of the score. 
     */
    private Color color;

    /**
     * This store the font of the image of the score.
     */
    private Font font;

    /**
     * This is used to construct the score object.
     * 
     * @param color		-	The color of the score on the screen.
     * @param font		-	The font of the score on the screen
     * @param xPosition	-	The x coordinate of the score on the screen.
     * @param yPosition	-	The y coordinate of the score on the screen.
     */
    Score(Color color, Font font, int xPosition, int yPosition) {
        this.color = color;
        this.font = font;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * This is used to get the current score.
     * 
     * @return The current score.
     */
    public int getScore() {
        return currentScore;
    }

    /**
     * This is used to increase the score.
     */
    public void increaseScore(int i) {
        currentScore += i;
    }


    /**
     * This is used to draw the score to the screen.
     * 
     * @param graphics	-	A reference to the off-screen graphics buffer.
     */
    public void draw(Graphics graphics) {
        // Draw the score
        graphics.setColor(this.color);
        graphics.setFont(font);
        graphics.drawString("Score \t" + this.currentScore + "", this.xPosition, this.yPosition);
    }
}
