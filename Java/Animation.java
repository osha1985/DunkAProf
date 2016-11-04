import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

/**
 * This class manages the animation of game elements that are animated. 
 * 
 * 
 * @author Osher Cohen
 *
 */
public class Animation {

    /**
     * This is used to store the frames of the animation.
     */
    private Vector <Image> animationImages = new Vector<Image>();

    /**
     * This indicates how many frames should be skipped before switching
     * to the next animation frame.
     */
    private int frameSkip;

    /**
     * This is used to indicate which animation frame is currently being displayed.
     */
    private int currentFrame;

    /**
     * This indicates if the animation is playing
     */
    private boolean isPlaying = false;

    /**
     * This indicates if the animation will loop.
     */
    private boolean isLooping = false;

    /**
     * This stores the amount of animation frames.
     */
    private int frameQuantity;

	private int imageWidth;

	private int imageHeight;
    
    /**
     * Constructs the animation object which is used to store and control the animation.
     *
     * @param	fileName		The name of the sequence of frames in the animation
     * @param	frameQuantity	This is used to store the amount of frames in the animation
     * @param	frameSkip		This indicates how many game frames should be skipped before switching to the next animation frame.
     * 
     */
    public Animation(String fileName, int frameQuantity, int frameSkip ) {
    	
    	/**
    	 * Store the frames of the animation in a vector.
    	 */
        for(int i = 0; i < frameQuantity; i++) {
            try {
                animationImages.add(ImageIO.read(new File(fileName + i + ".png")));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.frameSkip = frameSkip;
        this.frameQuantity = frameQuantity;
        this.imageWidth = animationImages.get(0).getWidth(null);
        this.imageHeight = animationImages.get(0).getHeight(null);
    }

    /**
     * This is used to start the animation.
     */
    public void playAnimation() {
        isPlaying = true;
    }

    /**
     * This causes the animation to loop.
     */
    public void loopAnimation() {
        isLooping = true;
    }

    /**
     * This is used to return the image which represents the current frame of the animation.
     * @return	Image	An Image object that is a reference to the image that represents the current frame of the animation.
     */
    public Image getImage() {
    	
       /** 
        * If the animation is playing return the current animation frame. By dividing currentFrame by frameSkip,
        * you change the animation frame every frameSkip amount of game frames.
    	*/
        if(isPlaying) {
            return this.animationImages.get(currentFrame/frameSkip);
            
        /**
         * Otherwise do not return anything.
         */
        } else {
        	return null;
        }
        
    }

    /**
     * This is used to update the state of the animation.
     */
    public void update() {
    	/**
    	 * If the animation is playing ...
    	 */
        if(isPlaying) {
        	
        	/**
        	 * ... then increment the frame to get to the next frame.
        	 */
            currentFrame++;
            
            /**
             *  If the current frame's index is greater than the amount of frames, ...
             */
            if(currentFrame >= (frameQuantity * frameSkip) ) {
            	
            	/**
            	 * ... then reset the animation.
            	 */
                currentFrame = 0;
                
                /**
                 *  If the animation is not an animation that is supposed to loop, ...
                 */
                if(!isLooping) {
                	
                	/**
                	 *  ... then stop the animation from playing after it has been reset.
                	 */
                    isPlaying = false;
                    
                }
            }
        }
    }
    
    /**
     * This is used to indicate if the animation is playing or not.
     * 
     * @return True if the animation is playing. Otherwise it returns False.
     */
    public boolean isPlaying() {
    	return this.isPlaying;
    }
    
    public int getWidth() {
    	 return this.imageWidth;
     }
     
    public int getHeight() {
    	return this.imageHeight;
    }

}
