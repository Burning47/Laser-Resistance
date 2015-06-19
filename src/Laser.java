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
    int quadrant;
    
    int [] newPoints = new int [2];
    if (angle >= 270) // upleft
    {
      angle = 360 - angle;
      quadrant = 4;
    }
    else if (angle >= 180) // bottomleft
    {
      angle = angle - 180;
      quadrant = 3;
    }
    else if (angle >= 90) // bottomright
    {
      angle = 180 - angle;
      quadrant = 2;
    }
    else // upright
    {
      quadrant = 1;
    }
    newPoints = calculateLengthExtend (angle, length, quadrant);
    x2 = getX () + newPoints [0];
    y2 = getY () + newPoints [1];
    currPoint.setLocation (x2, y2);
  }
  
  private int [] calculateLengthExtend (int angle, int length, int quadrant)
  {
    int [] temp = new int [2];
    
    if (angle <= 45)
    {
      temp [0] = (int) (length * (Math.tan (Math.toRadians (angle))));
      temp [1] = length;
    }
    else
    {
      temp [0] = length;
      temp [1] = (int) (length / (Math.tan (Math.toRadians (angle))));
    }
    
    if (quadrant == 1 || quadrant == 4)
      temp [1] = temp [1] * (-1);
    if (quadrant == 3 || quadrant == 4)
      temp [0] = temp [0] * (-1);
    return temp;
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