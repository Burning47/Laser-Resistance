import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The "GameObject" class, is the basis for all objects to be used in the actual logic for the game. Uses the (x, y) coordinate system, and a Rectangle with a width and height, since 
 * most subclasses of GameObject will be in a rectangular shape. Also stores the icon for it for when it is displayed and drawn on the panel.
 * 
 * @author Illya Pilipenko, modified by Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class GameObject
{
  /**
   * Private int for the width.
   */
  private int width;
  /**
   * Private int for the height.
   */
  private int height;
  /**
   * Private Rectangle for the collision object of this object.
   */
  private Rectangle rectangle;
  /**
   * Private int for the x-coord.
   */
  private int x;
  /**
   * Private int for the y-coord.
   */
  private int y;
  /**
   * Private int for the angle.
   */
  private int angle;
  /**
   * Private BufferedImage for the icon to represent this object when drawn.
   */
  private BufferedImage icon;
  
  /**
   * A constructor for the class, sets the x-coord, y-coord, width, height, angle, and icon/image. Also sets up the Rectangle.
   * 
   * @param x int for the x-coord.
   * @param y int for the y-coord.
   * @param width int for the width.
   * @param height int for the height.
   * @param angle int for the angle.
   * @param icon BufferedImage for the image to be displayed representing this object.
   */
  public GameObject (int x, int y, int width, int height, int angle, BufferedImage icon)
  {
    this.x = x;
    this.y = y;
    setAngle (angle);
    this.width = width;
    this.height = height;
    this.icon = icon;
    rectangle = new Rectangle (getX (), getY (), getWidth (), getHeight ());
  }
  
  /**
   * A constructor for the class, sets the x-coord, y-coord, and angle.
   * 
   * @param x int for the x-coord.
   * @param y int for the y-coord.
   * @param angle int for the angle.
   */
  public GameObject (int x, int y, int angle)
  {
    this.x = x;
    this.y = y;
    setAngle (angle);
  }
  
  /**
   * A default constructor.
   */
  public GameObject ()
  {
  }
  
  /**
   * Sets the angle for this object in degrees, with the value always being from 0 - 359, with 0 being up/forward/north, and going in a clockwise direction.
   * 
   * @param newAngle int for the new angle value of this object.
   */
  public void setAngle(int newAngle)
  {
    angle = newAngle % 360;
    if (angle < 0)
      angle += 360;
  }
  
  /**
   * Returns the value of the angle.
   * 
   * @return integer of the value of the angle.
   */
  public int getAngle ()
  {
    return angle;
  }
  
  /**
   * Returns the x-coord.
   * 
   * @return integer of the value of the x-coord.
   */
  public int getX()
  {
    return x;
  }
  
  /**
   * Returns the y-coord.
   * 
   * @return integer of the value of the y-coord.
   */
  public int getY()
  {
    return y;
  }
  
  /**
   * Returns the width.
   * 
   * @return integer of the width.
   */
  public int getWidth ()
  {
    return width;
  }
  
  /**
   * Returns the height.
   * 
   * @return integer of the height.
   */
  public int getHeight ()
  {
    return height;
  }
  
  /**
   * Returns the Rectangle of this object.
   * 
   * @return the Rectangle of this object.
   */
  public Rectangle getRect ()
  {
    return rectangle;
  }
  
  /**
   * Returns the image of this object.
   * 
   * @return the BufferedImage of this object.
   */
  public BufferedImage getImage()
  {
    return icon;
  }
  
  /**
   * Moves the object's x and y coordinates by newX and newY, respectively. Sets the rectangle at the updated coordinates as well.
   * 
   * @param newX int for how much to move along the x-axis.
   * @param newY int for how much to move along the y-axis.
   */
  public void move (int newX, int newY)
  {
    x += newX;
    y += newY;
    rectangle.setLocation (x, y);
  }
}