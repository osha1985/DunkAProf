import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class contains graphics (draw logic and images), actions (dunk logic, 
 * score splash rendering, playing scream sound), etc for an object
 * the trustee and its appearance in the game.
 * 
 * @author Osher Cohen
 *
 */
public class Person {

    /**
     * This is used to store the trustee's image
     */
	private Image image;

    /**
     * This is used to store and run the splash animation.
     */
    private Animation splashAnimation;

    /**
     * Integer used to hold score increment value
     */
     private int scoreModifier;

    /**
     * These are used to store the initial x and y coordinates of the trustee.
     */
    private int startX, startY;
   
    /**
     * These are used to store the current x and y coordinates of the trustee.
     */
    private int personXPosition, personYPosition;

    /**
     * This is used to indicate if the trustee is dunked or not.
     */
    private boolean dunked = false;
    
    /**
     * This is used to play the person's scream sound file if he has one.
     */
    private MediaPlayer scream = null;
   
    /**
     * The image that represents the target on the screen.
     */
    private Image targetImage;
    
    /**
     * The trustee's image when he is being undunked.
     */
    private Image undunkImage;
    
    /**
     * contains location of splash image for scoring
     */
    private Image scoreSplash;

    /**
     * This is used to indicate if the trustee is in the state of being undunked.
     */
	private boolean undunking = false;
	
	/**
	 * This stores the x coordinate of the target that is above the person's head.
	 */
	private int targetXPosition;

	/**
	 * This stores the y coordinate of the target that is above the person's head.
	 */
	private int targetYPosition;
	
	/**
	 * The x coordinate for the splash animation of this person.
	 */
	private int splashAnimationXPosition;
	
	/**
	 * The y coordinate for the splash animation of this person.
	 */
	private int splashAnimationYPosition;
	
	/**
	 * The splash that is heard when the Person hits the water.
	 */
	private MediaPlayer splashSound = null;
	
    /**
     * The constructor for the object representing the person.
     * 
     * @param fileName					The file which stores the image of the person.
     * @param screamFile				The file which stores the person's scream if he has one.
     * @param undunkImage				The file which stores the image of the person when he is being undunked.
     * @param scoreImage				The file which stores the image of the person score splash image.
     * @param personXPosition			The x coordinate of the person.
     * @param personYPosition			The y coordinate of the person.
     * @param personWidth				The scaled width of the image of the person.
     * @param personHeight				The scaled height of the image of the person
     * @param targetXPosition			The x coordinate of the target above the person's head.
     * @param targetYPosition			The y coordinate of the target above the person't head.
     * @param scoreModifier				The amount the score should be increased when the person's target is hit.
     * @param splashAnimationXPosition	The x coordinate of the splash animation
     * @param splashAnimationYPosition	The y coordinate of the splash animation
     */
    public Person(String fileName, String screamFile, String undunkImage, String scoreImage, String splashAnimationImage, int personXPosition, int personYPosition, int personWidth, int personHeight, int targetXPosition, int targetYPosition, int scoreModifier, int splashAnimationXPosition, int splashAnimationYPosition) {
        this.splashAnimation = new Animation(splashAnimationImage, 10, 4);
        try {
        	this.image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(personWidth, personHeight, Image.SCALE_DEFAULT);
        	this.undunkImage = ( ImageIO.read(new File(undunkImage) ) ).getScaledInstance(personWidth, personHeight, Image.SCALE_DEFAULT);
			this.targetImage = ( ImageIO.read(new File("images/Target.png")) ).getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			this.scoreSplash = ( ImageIO.read(new File(scoreImage)).getScaledInstance(80, 120, Image.SCALE_DEFAULT) );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.personXPosition = personXPosition;
        this.personYPosition = personYPosition;
        this.startX = personXPosition;
        this.startY = personYPosition;
        this.targetXPosition = targetXPosition;
        this.targetYPosition = targetYPosition;
        this.scoreModifier = scoreModifier;
        this.splashAnimationXPosition = splashAnimationXPosition;
        this.splashAnimationYPosition = splashAnimationYPosition;
        this.splashSound = new MediaPlayer("sounds/dunkInWater.wav", false);
        if(screamFile != null) {
        	this.scream = new MediaPlayer(screamFile, false);
        }
    }

    /**
     * This method draws the trustee's image, as well as, any animations associated with him, to the JPanel.
     *
     * @param  graphics	-	A reference to the JPanel buffer
     */
    public void draw(Graphics graphics) {
		
    	/**
    	 *  Draw the target.
    	 */
    	graphics.drawImage(targetImage, this.targetXPosition, this.targetYPosition, null);

        /**
         *  Draw the person. If he is not in the state of being undunked, then draw his normal image. 
         *  Otherwise, draw his undunked image.
         */
    	if(!undunking) {
    		graphics.drawImage(image, this.personXPosition, this.personYPosition, null);
    	} else {
    		graphics.drawImage(undunkImage, this.personXPosition, this.personYPosition, null);
    	}
		
		/**
		 *  Draw the splash animation if it is playing.
		 */
    	if(splashAnimation.isPlaying()) {
    		graphics.drawImage(splashAnimation.getImage(), this.splashAnimationXPosition, this.splashAnimationYPosition, splashAnimation.getWidth(), splashAnimation.getHeight(), null);
    	}
    	
    	if(this.isDunked()) {
    		graphics.drawImage(this.scoreSplash, this.startX, this.startY, null);
    	}
        

    }  // end of draw()

    /**
     * This is used to get the x coordinate of the trustee.
     * 
     * @return The x coordinate of the trustee.
     */
    public int getXPosition() {
        return this.personXPosition;
    }

    /**
     * This is used to get the y coordinate of the trustee.
     * 
     * @return The y coordinate of the trustee.
     */
    public int getYPosition() {
        return this.personYPosition;
    }

    /**
     * This is used to return the Rectangle representing the bounding box that surrounds the image.
     * 
     * @return The Rectangle object which represents the bounding box surrounding the 
     */
    public Rectangle getBoundingBox() {
    	return new Rectangle(this.personXPosition, this.personYPosition, this.image.getWidth(null), this.image.getHeight(null));
    }

    /**
     * This is used to return the Rectangle representing the bounding box surrounding the target.
     * 
     * @return	The Rectangle object representing the bounding box which surrounds the target.
     */
    public Rectangle getTargetBoundingBox() {
    	return new Rectangle(this.targetXPosition, this.targetYPosition , this.targetImage.getWidth(null) , this.targetImage.getHeight(null) );
    }
    
    /**
     * This is used to dunk the person.
     * When the person is dunked, if he has a scream assigned to him, then have him scream.
     */
    public void dunk() {
    	if(this.scream != null) {
    		this.scream.play();
    	}
        dunked = true;

    }

    /**
     * This is used to see if the trustee is dunked or not.
     * 
     * @return	True if the trustee is dunked, false otherwise
     */
    public boolean isDunked() {
        return dunked;
    }

    /**
     * This is used to control the movement of the trustee.
     * 
     * @param pool			-	An object that represents the pool that the trustee will be dunked into. 
     * @param projectile	-	An object that represents the projectile.
     * @param score			-	An object that represents the player's score.
     * @param distance		- 	The distance the person will move each frame. 
     */
    public void move(Pool pool, Projectile projectile, Score score, int distance) {
       
    	/**
         * Update the animations (change the animation frame, stop the animation, etc.)
         */
        splashAnimation.update();
        
        /**
         * Check if the person is in the dunked state.
         */
        if(this.isDunked()) {
        	
        	/**
        	 * If the person is in the dunked state, then check if he has passed the middle of the pool.
        	 */
        	
            if(this.getBoundingBox().intersectsLine(pool.getMiddleLine())) {
            	
            	/**
            	 *  If the person is in the dunked state and has passed the middle of the pool,
            	 *  then play the splash animation, play the splash sound, and make him sink into the pool.
            	 */
                this.splashAnimation.playAnimation();
                this.splashSound.play();
                
                this.personYPosition+=distance;
                
                /**
                 * Check if the person is in the dunked state and has passed the bottom of the pool.
                 */
            } else if (this.getBoundingBox().intersectsLine(pool.getBottomLine())) {
            	
            	/**
            	 * If the person is in the dunked state and has passed the bottom of the pool, then he should be undunked.
            	 * The splashSoundPlayed flag should be reset for the next time the person is dunked.
            	 */
                this.undunk();
                
                /**
            	 * If the trustee is in the dunked state but has not intersected any part of the pool,
            	 * then just have him sink down.
            	 */   
            } else {
            	
                this.personYPosition+=distance;
            }
            
            /**
        	 * Check if the person is not in the dunked state.
        	 */
        } else {
        	
        	/**
        	 * If person is not in the dunked state, check if he is in the state of being undunked.
        	 */
        	if(undunking) {
        		
        		/**
        		 *  If the person is not in the dunked state and is in the state of being undunked, then if he is in his original position, get him out 
        		 *  of the undunked state.
        		 */
        		if(this.personYPosition == this.startY) {
        			this.undunking = false;
        		
        		/**
        		 * If the person is not in the dunked state and is in the state of being undunked, then if he is not in his original position, continue undunking him.
        		 */
        		} else {
        			this.personYPosition-=5;
        		}
        	}
        	
        	/**
        	 * If the target above the person's head has collided with the projectile,
        	 * then increase the score and dunk the person.
        	 */
        	else if(targetCollided(projectile)) {
                score.increaseScore(scoreModifier);
                this.dunk();
            }
        }
    }

    /**
     *	This is used to get the trustee out of the pool.
     */
    public void undunk() {
        dunked = false;
        undunking = true;

    }

    /**
     * This is used to test if the projectile collided with the trustee.
     * 
     * @param projectile  - A reference to the object which represents the projectile
     * @return	True if the projectile collided with the trustee, otherwise it returns false.
     */
    public boolean targetCollided(Projectile projectile) {
        return projectile.getBoundingBox().intersects(this.getTargetBoundingBox());
    }
    
    /**
     * This returns a reference to the score splash image. 
     * @return A reference to the score splash image.
     */
    public Image getScoreSplash(){
        return scoreSplash;
    }
    
    /**
     * This returns the initial x coordinate of the person.
     * @return
     */
    public int getStartX() {
        return startX;
    }

    /**
     * This returns the initial y coordinate of the person.
     * @return
     */
    public int getStartY() {
        return startY;
    }
}
