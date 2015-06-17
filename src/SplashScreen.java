import java.awt.*;
import javax.swing.*;

/**
 * Creates a 600 x 600 pixel window with 2 with a short animating gif as
 * the splash screen. After 3 seconds, the window will dispose itself.
 * <p>
 * @author Kenneth Tran
 * @version V1.0.0, 10 June, 2014
 */
public class SplashScreen extends JFrame{
  
  /**
   * Constructor for the SplashScreen class. Constructs the JFrame
   * window and displays the animated gif.
   */
  public SplashScreen(){
    
    super ("Laser Resistance");
    setSize (600,600);
    
    setResizable(false);
    setLocationRelativeTo (null);
    setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);
    setUndecorated(true);
    
    //setBackground (new Color (0,255,0,0));
    
    super.setVisible (true);
    
    animate();
    
    setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
  }
  /**
   * Creates a JPanel on the JFrame and adds a new JLabel to the JPanel.
   * The JLabel is an image which calls to a gif file for the splash screen. 
   * <p>
   * @param p reference for JPanel, used to create the JPanel that the splash screen will be displayed on. 
   * @param image reference for JLabel, used to store the gif image that is displayed.
   * @param e reference for Exception, used to catch an exception while sleeping the program. 
   */
  public void animate(){
    JPanel p = new JPanel();
    add (p);
    JLabel image = new JLabel(new ImageIcon("res/Menus/rsfixed.gif"));
    p.add(image);
    
    repaint();
    validate();
    
    try{
      Thread.sleep (3000);
    }
    catch(Exception e){
    }
    dispose();
  }
}