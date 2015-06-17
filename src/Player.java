import java.awt.image.BufferedImage;

/**
 * The "Player" class, a type of GameObject which the player will be able to control by moving it up, down, left, right, or controlling which angle the player is facing. The player will be able to create or "fire" lasers, 
 * which is handled in the Game class.
 * 
 * @author Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class Player extends GameObject
{
  /**
   * A constructor for the class, sets the x-coord, y-coord, and a predetermined width and height. Also sets up the Rectangle. Angle is set to 0 (up/forward/north).
   * 
   * @param x int for the x-coord of the centre of this object.
   * @param y int for the y-coord of the centre of this object.
   * @param icon BufferedImage, stores the image used for the mirror.
   */
  public Player (int x, int y, BufferedImage icon)
  {
    super (x - 15, y - 15, 30, 30, 0, icon);
  }
  
  /**
   * Returns the x-coord of the centre of this object.
   * 
   * @return integer of the value of the x-coord of the centre of this object.
   */
  public int getCentreX ()
  {
    return (2 * getX () + getWidth ()) / 2;
  }
  
  /**
   * Returns the y-coord of the centre of this object.
   * 
   * @return integer of the value of the y-coord of the centre of this object.
   */
  public int getCentreY ()
  {
    return (2 * getY () + getHeight ()) / 2;
  }
}