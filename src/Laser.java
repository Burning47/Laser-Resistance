import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * The "Laser" class, a type of GameObject in the shape of two points (a line). Has the ability to set its end point based on the direction the laser is headed to, and the x-component to be added. Used in the Game class
 * and is constantly "extended" until the laser hits another object.
 * 
 * @author Illya Pilipenko, modified by Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class Laser extends GameObject
{
  /**
   * Private Point for the current end point of this laser.
   */
  private Point currPoint;
  /**
   * Private Point for the starting point of this laser.
   */
  private Point startPoint;
  /**
   * Private int for the end x-coord.
   */
  private int x2;
  /**
   * Private int for the end y-coord.
   */
  private int y2;
  
  /**
   * A constructor for the class, sets the x-coord, y-coord, and angle, and sets up the starting point and current end point (which initially is the same as the starting point).
   * 
   * @param x int for the x-coord.
   * @param y int for the y-coord.
   * @param angle int for the angle.
   */
  public Laser (int x, int y, int angle)
  {
    super (x, y, angle);
    x2 = x;
    y2 = y;
    startPoint = new Point (x, y);
    currPoint = new Point (x2, y2);
  }
  
  /** Extends laser to (getX () + length, and the corresponding y-coordinate based on the angle), setting currPoint to these coordinates, based on the laser's angle and starting point.
    * If the angle is too narrow, which creates the potential to skip y's (making it possible to not detect a collision with another object), then it will extend on the y-axis instead and calculate the correspoint x-coordinate.
    * 
    * @param length int for the length of one component to extend by, calculating the other component based off of this.
    * @param angle int for the current angle of this laser.
    */
  public void extend (int length)
  {
    int angle = getAngle ();
    if (angle >= 270) // quadrant 2
    {
      if (angle >= 350)
      {
        angle = 360 - angle;
        x2 = (int) (length * (Math.tan (Math.toRadians (angle)))) * (-1) + getX ();
        y2 = length * (-1) + getY ();
      }
      else
      {
        angle -= 270;
        y2 = ((int) (length * (Math.tan (Math.toRadians (angle))))) * (-1) + getY ();
        x2 = length * (-1) + getX ();
      }
    }
    else if (angle >= 180) // quadrant 3
    {
      if (angle <= 190)
      {
        angle -= 180;
        x2 = (int) (length * (Math.tan (Math.toRadians (angle)))) * (-1) + getX ();
        y2 = length + getY ();
      }
      else
      {
      angle = 270 - angle;
      y2 = (int) (length * (Math.tan (Math.toRadians (angle)))) + getY ();
      x2 = length * (-1) + getX ();
      }
    }
    else if (angle >= 90) // quadrant 4
    {
      if (angle >= 170)
      {
        angle = 180 - angle;
        x2 = (int) (length * (Math.tan (Math.toRadians (angle)))) + getX ();
        y2 = length + getY ();
      }
      else
      {
        angle -= 90;
        y2 = (int) (length * (Math.tan (Math.toRadians (angle)))) + getY ();
        x2 = length + getX ();
      }
    }
    else // quadrant 1
    {
      if (angle <= 10)
      {
        x2 = (int) (length * (Math.tan (Math.toRadians (angle)))) + getX ();
        y2 = length * (-1) + getY ();
      }
      else
      {
        angle = 90 - angle;
        y2 = ((int) (length * (Math.tan (Math.toRadians (angle))))) * (-1) + getY ();
        x2 = length + getX ();
      }
    }
    currPoint.setLocation (x2, y2);
  }
  
  /**
   * Returns currPoint, or the current end point of the laser.
   * 
   * @return the Point currPoint, or the current end point of the laser.
   */
  public Point getCurrPoint ()
  {
    return currPoint;
  }
  
  /**
   * Returns startPoint, the starting point of the laser.
   * 
   * @return the Point startPoint, othe starting point of the laser.
   */
  public Point getStartPoint ()
  {
    return startPoint;
  }
  
  /**
   * Returns the x-coord for the end point.
   * 
   * @return integer of the value of the x-coord of the end point.
   */
  public int getX2 ()
  {
    return x2;
  }
  
  /**
   * Returns the y-coord for the end point.
   * 
   * @return integer of the value of the y-coord of the end point
   */
  public int getY2 ()
  {
    return y2;
  }
}