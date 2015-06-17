import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * The "ConcaveMirror" class, similar to the CurvedMirror, but in a concave shape (the outside of a circle).
 * 
 * @author Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class ConcaveMirror extends CurvedMirror
{
  /**
   * A constructor for the class, sets the x-coord, y-coord, width, height, side which is reflective. Also sets up the Ellipse2D or circle, and calculates the centre of it, and the radius.
   * 
   * @param x int for the x-coord.
   * @param y int for the y-coord.
   * @param width int for the width.
   * @param height int for the height.
   * @param side boolean for which side is reflective, false for right, true for left.
   * @param icon BufferedImage, stores the image used for the mirror.
   */
  public ConcaveMirror (int x, int y, int width, int height, boolean side, BufferedImage icon)
  {
    super (x, y, width, height, side, icon);
  }
  
  /**
   * Checks if this point is found in the Ellipse2D or circle. Returns false if it is, and true if it is not. This is because this is a concave mirror, which means it needs to check 
   * if the inverse space of the Ellipse contains it.
   * 
   * @param point the Point2D to be checked.
   * @return false if the point is contained in the Ellipse, true if not.
   */
  public boolean contains (Point2D point)
  {
    return !(getCircle ().contains (point));
  }
  
  /**
   * Returns the x-coordinate of this mirror given the y-coordinate.
   * 
   * @param y double for the y-coordinate used to find the corresponding x-coordinate of this circle/mirror.
   * @param temp double, used to store a temporary value.
   * @return the double value of the x-coordinate corresponding to this y-coordinate
   */
  public double findXCoord (double y)
  {
    double temp = Math.sqrt (Math.pow ((double) getRadius (), 2) - Math.pow (y - getCentreY (), 2));
    if (getReflectSide () == true)
      temp += getCentreX ();
    else
      temp = temp * (-1) + getCentreX ();
    return temp;
  }
}