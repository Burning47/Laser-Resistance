import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The "Barrier" class, just defines a specific type of GameObject and coordinates for the rectangle. Used in game to see if hit. If this is hit by a laser 
 * it does not react nor does it reflect the laser.
 * 
 * @author Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class Barrier extends GameObject
{
  /**
   * A constructor for the class, sets a predetermined x-coord, y-coord, width, and height. Also sets up the Rectangle.
   * <p>
   * @param icon BufferedImage, stores the image used for the barrier.
   */
  public Barrier (BufferedImage icon)
  {
    super (175, 460, 250, 30, 0, icon);
  }
}