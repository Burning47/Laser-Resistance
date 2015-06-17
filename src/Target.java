import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The "Target" class, a specific type of GameObject, and used in the Game class to handle logic. When this is hit by a laser, will be removed from the game.
 * 
 * @author Illya Pilipenko, modified by Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class Target extends GameObject
{ 
  /**
   * A constructor for the class, sets a random x-coord; a predetermined y-coord; width, height, and icon/image. Also sets up the Rectangle.
   * 
   * @param width int for the width.
   * @param height int for the height.
   * @param icon BufferedImage for the image to be displayed representing this object.
   */
  public Target (int width, int height, BufferedImage icon)
  {
    super (175 + (int) (Math.random () * 206), 0, width, height, 0, icon);
  }
}