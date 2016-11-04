import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/**
 * This class is used to pause the game by writing the appropriate words below.
 * This class contains Font and Fontmetric used to display the text in a desired manner.
 * This class also contains the panel width and height pertaining to the game window size. 
 */
public class Paused {
	
	/**
	 * Stores the font characteristics used for display 
	 */
	private Font font;
	
	/**
	 * Stores additional font characteristics used for display; used for placing the 
	 * desired text centrally in the game window.
	 */
	private FontMetrics metrics;
	
	/**
	 * Width of the game panel.
	 */
	private int panelWidth;
	
	/**
	 * Height of the game panel.
	 */
	private int panelHeight;
	
/**
 * Constructor used to set class attributes
 * @param font Font that will be used
 * @param metrics Font metrics that will be used
 * @param panelWidth Game panel width
 * @param panelHeight Game panel height
 */
	public Paused(Font font, FontMetrics metrics, int panelWidth, int panelHeight) {
		this.font = font;
		this.metrics = metrics;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		
	}
	/**
	 * Draw method used to draw the Pause screen title and information on the screen while
	 * the game is paused.
	 * @param graphics Standard graphics object
	 */
	public void draw(Graphics graphics) {
		graphics.setColor(Color.BLACK);
		graphics.setFont(font);
		graphics.drawString( "Paused" , (this.panelWidth - metrics.stringWidth("Paused"))/2 , (this.panelHeight - metrics.getHeight())/2 );
		graphics.drawString( "Press ESC to continue" , (this.panelWidth - metrics.stringWidth("Press ESC to continue"))/2 , (this.panelHeight - metrics.getHeight()) / 2 + 50);
	}
}
