import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;


// This is used to tell eclipse not to bitch about not having a serial id.
@SuppressWarnings("serial")

/**
 * This is the main location for the game logic and houses all the game 
 * components/objects (professors, tanks, game background, etc) 
 * This class is where the game graphics for the game is drawn. It 
 * is also where the game logic is updated.
 * 
 *  This class extends JPanel and implements Runnable, KeyListenter, and ActionListener.
 *  A JPanel is used to as the main container to house the game graphics and associated methods and events.
 *  
 *  The Runnable interface is used so that this class can be executed by a thread.  We do this to ensure that 
 *  the running of this class will not be interrupted by any other calls from within this class...ie having 
 *  animation occur once a professor is hit. (http://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html)
 *  
 *  The KeyListener is used to capture keystroke events such as space bar or arrows and call the appropriate
 *  methods to simulate game logic. (http://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyListener.html)
 *  
 *  The Image object and the Graphics object reference the same off-screen game graphics buffer. 
 *  The reason I use both is that the Image object has functionality that the Graphics object lacks 
 *  and vice versa. For example, you can only draw an Image object to a graphics object which is 
 *  needed to draw the off-screen graphics to the JPanel. You also can't draw to an Image object. 
 *  This is why the Graphics object is necessary. A graphics object can't be created. It can only be retrieved by calling getGraphics()
 *  on an Image object.
 *  
 *  @author Osher Cohen
 *  
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    /**
    *	This is the used to store the width of the JPanel.
    */
    private final int panelWidth = 800;

    /**
     *	This is used to store the height of the JPanel.
     */
    private final int panelHeight = 600;

    /**
     * This is the thread that performs the animation.
     */
    private Thread animator;

    /**
     *	This is used to stop the game animation
     */
    private boolean running = false;

    /**
 	 * This is used to pause the game.
     */
    private boolean isPaused = false;

    /**
     *	This is used to indicate that the game is over.
     */
    private boolean gameOver = false;


    /**
     * This is used to store the font used for text in the game.
     */
    private Font font;


    /**
     * This encapsulates information about the rendering of the font used for text in the game. 
     */
    private FontMetrics metrics;

    /**
     * This class is used to manage the background image.
     */
    Background background;

    /**
     * This class contains graphics (draw logic and images), actions (dunk logic), etc for an 
     * object that will represent the Professor for the game.
     */
    private Person professor;

    /**
     * This class contains graphics (draw logic and images), actions (dunk logic), etc for an 
     * object that will represent the Dean for the game.
     */
    private Person dean;

    /**
     * This is the class that controls the game logic which governs the motion of the trustee 
     * and its appearance in the game.
     */
    private Person trustee;

    /**
     * This is the object used as a starting point for our projectiles in the game. 
     * This object slides back and forth on an x axis just below the dunk tanks.
     */
    private Slider slider;

    /**
     * This array of Pool objects are the dunk tanks used to dunk the faculty members.
     * A pool is considered a dunk tank.
     */
    private Pool [] pool = new Pool [3];

    /**
     * Object used to hit the targets near the faculty.
     */
    private Projectile projectile;

    /**
     * Represents the Game clock/time limit for this game.
     */
    private Clock clock;

    /**
     * This class keeps track of the score in the game as well as the a few attributes such as Font, Color and xy coordinates.
     */
    private Score score;

    /**
     *	This is used to store a reference to the JFrame
     */
    DunkAProf window;
    
    /**
     * This is used to store the image and location of the paused graphic.
     */
    Paused paused;
    
    /**
     *  Stores the Graphics object.
     */
    private Graphics screenGraphics = null;
    
    /**
     * Stores the Image object reference to off-screen buffer of the game image.
     */
    private Image screenImage = null;
    
    /**
     * Stores the reference to the background music
     */
    private MediaPlayer backgroundMusic = null;
    
    /**
     * The JPanel's constructor. It initializes all the in-game elements.
     * 
     */
    public GamePanel() {

        /**
         *  Create the background image.
         */
        background = new Background("images/background.png",panelWidth, panelHeight);

        /**
         *  Set the size of the JPanel.
         */
        setPreferredSize( new Dimension(panelWidth, panelHeight) );

        setFocusable(true);

        /**
         *  The JPanel now has focus, so it can receive key events.
         */
        requestFocus();


        /**
         *  Create the game components
         */
        professor = new Person("images/professor.png",null,"images/professor.png","images/score+1.png", "images/Splash/splash",80, 60, 70, 118, 85, 0, 1, 19, 100);
        dean = new Person("images/dean.png",null,"images/dean.png","images/score+1.png","images/Splash/splash", 372, 61, 65, 126, 381, 0, 1, 307, 100); 
        trustee = new Person("images/trustee.png","sounds/trusteeScream.wav","images/skeleton.png","images/score+3.png","images/AcidSplash/AcidAnimation" ,620, 60, 141, 130, 665, 0, 2, 585, 100);
        pool[0] = new Pool("images/tankWater.png", 35, 196);
        pool[1] = new Pool("images/tankWater.png", 321, 196);
        pool[2] = new Pool("images/tankAcid.png", 607, 196);
        slider = new Slider("images/slider.png", 0, 517, panelWidth);
        projectile = new Projectile("images/projectile.png", 0, 500);
        clock = new Clock("fonts/AGENCYR.TTF", 148, 34, 60);
        score = new Score(new Color(255, 247, 153), font, 20, 580);
        
       /**
        *  Load the font that will be used in the game.
        */
        try {
        	font = ( Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AGENCYR.TTF")) ).deriveFont(36f);
            metrics = this.getFontMetrics(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /**
         * Loads and starts the background music
         */
        backgroundMusic = new MediaPlayer("sounds/backgroundInGame.wav", true);
        backgroundMusic.play();
        		
        paused = new Paused(font, metrics, panelWidth, panelHeight);
        
        /**
         * The JPanel is now able to receive key events
         */
        addKeyListener(this);
    }  	 											// end of GamePanel()
    
    /**
     * This Notify the JPanel component that it has been added to the JFrame component and starts the game thread.
     */
    public void addNotify() {
    	
    	/**
    	 * Notify the JPanel component that it has been added to the JFrame component.
    	 */
        super.addNotify();
        
        /**
         * Start the thread.
         */
        startGame();
    }

    /**
     * This starts the JFrame thread if it isn't running.
     */
    private void startGame() { 					    // Initialize and start the thread
        if (animator == null || !running) {
        	
            animator = new Thread(this);
            animator.start();
        }
    }												// end of startGame()
    
    /**
     * This is called by the user to stop execution of JPanel.
     * Currently, this is only used to stop the game when the game is over.
     */
    public void stopGame() {						
        running = false;
    }

    /**
     *  This repeatedly updates the in-game logic, renders the game to an off-screen buffer, paints the off screen buffer to the screen,
     *  and tells the thread to sleep a little.
     */
    public void run() {							    // Repeatedly update, render, sleep
        running = true;
        while(running) {
            gameUpdate();							// The game state is updated.
            gameRender();							// Render the game state to a buffer.
            paintScreen();							// Paint with the buffer.
            try {
                Thread.sleep(20);					// Sleep a bit
            } catch(InterruptedException ex) {}
        }
    }												// end of run()

    /**
    * Draws the game logic to the off-screen image
    */
    private void gameRender() {

        // Draw the current frame to an image buffer
        if (screenImage == null) {
            screenImage = createImage(panelWidth, panelHeight);
            if (screenImage == null) {
                System.out.println("The ScreenImage is null");
                return;
            } else {
                screenGraphics = screenImage.getGraphics();
            }
        }
        if(!isPaused && !gameOver) {
            // Clear the background
            background.draw(screenGraphics);

            // Draw the professor, dean, trustee, slider, pools, and projectile to the image buffer
            professor.draw(screenGraphics);
            dean.draw(screenGraphics);
            trustee.draw(screenGraphics);
            slider.draw(screenGraphics);
            pool[0].draw(screenGraphics);
            pool[1].draw(screenGraphics);
            pool[2].draw(screenGraphics);
            projectile.draw(screenGraphics);
            clock.draw(screenGraphics);
            score.draw(screenGraphics);

        } else if (this.isPaused){ 						
        	
        	// Draw the game paused message to the screen
        	paused.draw(screenGraphics);
        	
        } else {
        	clock.draw(screenGraphics);
        	this.gameOverMessage(screenGraphics);
        	
        }

        // Used for finding out the location of objects on screen during development
        //System.out.println("" + this.getMousePosition().getX() + " " + this.getMousePosition().getY() + "");
    }

    /**
    * Updates the game logic.
    */
    private void gameUpdate() {
        if (!gameOver && !isPaused) {
            slider.move(5);

            // Align the center of the platform with the center of the projectile.
            projectile.move((slider.getxPosition() + (slider.getWidth()/2)) - (projectile.getWidth()/2),
                            (slider.getyPosition() + (slider.getHeight()/2)) - (projectile.getHeight()/2));

            // For each person, check if the projectile or pool has collided with that person. If it has, move them (dunk or undunk them).
            professor.move(pool[0], projectile,score, 15);
            dean.move(pool[1], projectile,score, 15);
            trustee.move(pool[2], projectile,score, 15);
        } else if (gameOver){
        	running = false;
        	// If the player has run out of time, then the game is over.
        }
        if (clock.outOfTime()) {
        	gameOver = true;
        }
        
        
    }
    
    /**
    *    Prints the game over message to the screen
    */
    private void gameOverMessage (Graphics graphics) {			// Place the game over message in the center
        String message = "Game Over. Your Score: " + score.getScore() + " Press enter to continue";
        int x = (panelWidth - metrics.stringWidth(message))/2;	// This is the code to calculate x and y values of the game over message.
        int y = (panelHeight - metrics.getHeight())/2;
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(message, x, y);
    } 															// end of gameOverMessage()

    /**
     *  Use active rendering to put the buffered image on-screen.
     */
    private void paintScreen() {
        Graphics graphics;
        try {
            graphics = this.getGraphics();
            if ( graphics != null && screenImage != null ) {
                graphics.drawImage(screenImage, 0, 0, null);
            }
            graphics.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
        }
    } // end of paintScreen()


    /**
     * Once the gameover screen has been reached, this governs the next action(s) to occur
     */

    /**
     * When a the game is over and the 'Enter' key is pressed
     * this application will close.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
        	System.exit(0);
        }
    }

    /**
     * Interprets user input and performs the corresponding method calls
     */

    /**
     * When 'Spacebar' key pressed, a projectile is fired.
     * When 'Esc' is pressed the game will pause or resume based on previous state.
     * When 'UpArrow' is pressed, the projectile will move forward (up).
     * When 'LeftArrow' is pressed, the projectile will move left.
     * When 'RightArrow' is pressed, teh projectile will move right.
     */
    @Override
    public void keyReleased(KeyEvent event) {
    	switch(event.getKeyCode()) {
    	case KeyEvent.VK_SPACE:
    		projectile.fire();
    		break;
    	case KeyEvent.VK_ESCAPE:
    		if(isPaused) {
                this.resumeGame();
            } else {
                this.pauseGame();
            } break;
    	case  KeyEvent.VK_LEFT:
    		projectile.setDirection(Direction.LEFT);
    		break;
    	case KeyEvent.VK_RIGHT:
    		projectile.setDirection(Direction.RIGHT);
    		break;
    	case KeyEvent.VK_UP:
    		projectile.setDirection(Direction.FORWARD);
    		break;
    	default:
    		break;
    	}
    }

    /**
     * Not used in this context but must be implemented.
     */
    @Override
    public void keyTyped(KeyEvent event) {

    }

    /**
     * This is used to pause the game.
     */
    public void pauseGame() {
        isPaused = true;
        
        /**
         *  If the game is paused, then the clock should be stopped
         */
        clock.stopClock();


    }

    /**
     * This resumes the game when it is paused.
     */
    public void resumeGame() {
        isPaused = false;
        
        /**
         * If the game is not paused, the game should be resumed.
         */
        clock.resumeClock();
    }


} 														// end of GamePanel class

