import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The "Mirror" class, a type of GameObject which has a rectangular shape. If the Rectangle attached to this object detects the end point of a laser hitting it, will create a new Laser object
 * with the correct angle and starting coordinates.
 * 
 * @author Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class Mirror extends GameObject
{
  /**
   * A constructor for the class, sets the x-coord, y-coord,width, and height. Also sets up the Rectangle.
   * 
   * @param x int for the x-coord.
   * @param y int for the y-coord.
   * @param width int for the width.
   * @param height int for the height.
   * @param icon BufferedImage, stores the image used for the mirror.
   */
  public Mirror (int x, int y, int width, int height, BufferedImage icon)
  {
    super (x, y, width, height, 0, icon);
  }
  
  /**
   * Checks if this point is found in the Rectangle. Returns true if it is, and false if it is not.
   * 
   * @param point the Point2D to be checked.
   * @return true if the point is contained in the Rectangle, false if not.
   */
  public boolean contains (Point2D point)
  {
    return getRect ().contains (point);
  }
  
  /**
   * Returns a new reflected laser with a starting point of the old laser's ending point, and a new angle calculated on the slope of the tangent the old laser hit the mirror at, and the old laser's angle.
   * 
   * @param laser Laser which hit the mirror, which this function will create a reflected laser of.
   * @return a new reflected laser with newly calculated angle and starting coordinates..
   */
  public Laser reflect (Laser laser)
  {
    return (new Laser (laser.getX2 (), laser.getY2 (), 360 - laser.getAngle ()));
  }
}