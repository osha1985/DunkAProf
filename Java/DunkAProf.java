import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * 	This is the main entry point for the project DunkAProf.  The goal of the game is to dunk
 * 	the professors as many times as possible in the alloted time.
 *	A slider, which houses projectiles to be thrown, moves back and forth. When you hit the spacebar button, the slider will fire a projectile.
 *	When the projectile hits a person, he will scream, the score counter will increment, and the person will get dunked.
 *	As this is happening, the game clock is ticking down. When the clock reaches 0, the game ends. P pauses the game and spacebar fires the projectile.
 *	TODO: Implement a method to change the speed of the slider as a difficulty setting.
 *	TODO: Implement targets that, when hit, dunk the person.
 *	@author     Osher Cohen
 *	@version	1.0
 */

//This is used to tell eclipse not to bitch about not having a serial id.
@SuppressWarnings("serial")

/**
 * 
 * This class extend JFrame and implements Windows Listener.  
 * This JFrame class houses the Game Panel (GamePanel.java) which contains the main game elements (timers, slider, dunk tanks, images, game logic, etc)
 * This WindowListener is used to capture and process game events from the JFrame and calls the appropriate methods in the game object (Stop, Pause and Resume).
 *
 */
public class DunkAProf extends JFrame implements WindowListener {


    /**
     * This is a reference to the JPanel where all the action takes place.
     * If you're not sure why a JPanel is used, please refer to the Java documentation (http://docs.oracle.com/javase/7/docs/api/)
     * on JFrame and JPanel.  In short, JFrame (in this context) is used as a simple container that will have the sole responsibility of rendering the JPanel
     * and minor window functions. This allows for flexibility and better organization of game components and game logic.
     */
    private GamePanel gamePanel;

    /**
     * Constructs the JFrame
     */
    public DunkAProf() {
        super("DunkAProf");
        makeGUI();
        addWindowListener( this );
        
        /**
         * This is used to ensure that this JFrame conforms to the size of its subcomponents...
         * ie the JPanel (game panel)
         */
        this.pack();
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }// end of DunkAProf() constructor

    /**
     * This is used to set up the JFrame's GUI by initiating the game panel and adding 
     * it to this JFrame window.
     */
    private void makeGUI() {
        // The default BorderLayout is used
        Container container = getContentPane();

        gamePanel = new GamePanel();

        // Add the gamePanel to the window container
        container.add(gamePanel, "Center");

    }  // end of makeGUI()

    
    /**
     * Resume game when this JFrame is activated.
     * http://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
     */
    @Override
    public void windowActivated(WindowEvent arg0) {
    	gamePanel.resumeGame();

    }

    /**
     * Not used in this context but must be implemented.
     */
    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    /**
     * Stops the game when this JFrame is closing.
     * http://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
     */
    @Override
    public void windowClosing(WindowEvent arg0) {

        // If the window is closing, stop the game.
        gamePanel.stopGame();
        
    }

    /**
     * Paused the game when this JFrame is deactivated.
     * http://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
     */
    @Override
    public void windowDeactivated(WindowEvent arg0) {
    	
    	// If game is not active window, pause the game
    	gamePanel.pauseGame();

    }

    /**
     * Resume game when this JFrame is changing from a minimized state to a normal state.
     * http://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
     */
    @Override
    public void windowDeiconified(WindowEvent e) {
    	gamePanel.resumeGame();

    }

    /**
     * Paused the game when this JFrame is changing from a normal state to a minimized state.
     * http://docs.oracle.com/javase/7/docs/api/java/awt/event/WindowListener.html
     */
    @Override
    public void windowIconified(WindowEvent e) {
    	
    	// If window is minimized, pause game
    	gamePanel.pauseGame();

    }

    /**
     * Not used in this context but must be implemented.
     */
    @Override
    public void windowOpened(WindowEvent e) {


    }

    /**
     * Main entry point for this game.  It calls the constructor for this class
     * which sets the appropriate variables and calls the necessary functions
     * for the game to run.
     */
    public static void main(String args[]) {
        new DunkAProf();
    }

}
