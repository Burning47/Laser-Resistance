import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * The "LevelSelect" class, sets up a panel for the user to select the level of the game they will play, or the type of level they will practice on.
 * 
 * @author Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class LevelSelect extends JPanel implements ActionListener
{
  /**
   * Private boolean to determine if the game is actually a live game (true), or just a practice (false).
   */
  private final boolean isLive;
  
  /**
   * Private JButton to display the button to go to level 1.
   */
  private JButton level1;
  
  /**
   * Private JButton to display the button to go to level 2.
   */
  private JButton level2;
  
  /**
   * Private JButton to display the button to go to level 3.
   */
  private JButton level3;
  
  /**
   * Private JButton to display the "Main Menu" button to go to the main menu.
   */
  private JButton back;
  
  /**
   * Private SpringLayout, reference for the SpringLayout which this panel will be using.
   */
  private SpringLayout layout;
  
  /**
   * Private String for the name of the player to be displayed on the high scores.
   */
  private MainFrame parent;
  
  /**
   * Constructor for the class, sets up the panel, sets up the key bindings, and adds the buttons.
   *
   * @param isLive boolean to determine if the selected level will be practice (false), or an actual game (true).
   * @param frame MainFrame which this class is added to.
   */
  public LevelSelect (boolean live, MainFrame frame)
  {
    layout = new SpringLayout ();
    setLayout (layout);
    isLive = live;
    
    parent = frame;
    
    createAndShowGUI ();
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK), "1");
    getActionMap().put("1", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        parent.showGame (1, isLive);
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, Event.CTRL_MASK), "2");
    getActionMap().put("2", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        parent.showGame (2, isLive);
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, Event.CTRL_MASK), "3");
    getActionMap().put("3", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        parent.showGame (3, isLive);
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, Event.CTRL_MASK), "4");
    getActionMap().put("4", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        parent.showMenu('l');
      }
    });
  }
  
  /**
   * Instantiates all the buttons, sets constraints on them, adds actionListeners to them, and adds them to the panel.
   */
  private void createAndShowGUI ()
  {
    level1 = new JButton (new ImageIcon ("res/Menus/level1.png"));
    level1.setActionCommand("1");
    level1.setBorderPainted(false);
    level1.setContentAreaFilled(false);
    
    level2 = new JButton (new ImageIcon ("res/Menus/level2.png"));
    level2.setActionCommand("2");
    level2.setBorderPainted(false);
    level2.setContentAreaFilled(false);
    
    level3 = new JButton (new ImageIcon ("res/Menus/level3.png"));
    level3.setActionCommand("3");
    level3.setBorderPainted(false);
    level3.setContentAreaFilled(false);
    
    back = new JButton (new ImageIcon ("res/Menus/mainmenu.png"));
    back.setActionCommand("Menu");
    back.setBorderPainted(false);
    back.setContentAreaFilled(false);
    
    JLabel title = new JLabel(new ImageIcon("res/Menus/LevelsTitle.png"));
    layout.putConstraint (SpringLayout.NORTH, title, 10, SpringLayout.NORTH , this);
    layout.putConstraint (SpringLayout.WEST, title, 10, SpringLayout.WEST, this);
    this.add(title);
    
    layout.putConstraint(SpringLayout.WEST, level1, 100, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, level1, 210, SpringLayout.NORTH, this);
    level1.setFocusable (false);
    add (level1);
    
    layout.putConstraint(SpringLayout.WEST, level2, 432, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, level2, 0, SpringLayout.NORTH, level1);
    level2.setFocusable (false);
    add (level2);
    
    layout.putConstraint(SpringLayout.EAST, level3, -100, SpringLayout.EAST, this);
    layout.putConstraint(SpringLayout.NORTH, level3, 0, SpringLayout.NORTH, level1);
    level3.setFocusable (false);
    add (level3);
    
    layout.putConstraint(SpringLayout.WEST, back, 20, SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.SOUTH, back, -20, SpringLayout.SOUTH, this);
    back.setFocusable (false);
    add (back);
    
    level1.addActionListener (this);
    level2.addActionListener (this);
    level3.addActionListener (this);
    back.addActionListener (this);
  }
  
  /** 
   * Compares the title of the action the user performed with a String and determines a response to their action. Clicking the level 1 btuton
   * will go and display level 1, clicking level 2 will display level 2, clicking level 3 will display level 3. The other button to go back to the main menu.
   * 
   * @param e Reference, points to the ActionEvent class
   */
  public void actionPerformed (ActionEvent e)
  {
    if (e.getActionCommand ().equals ("1"))
    {
      parent.showGame (1, isLive);
    }
    else if (e.getActionCommand ().equals ("2"))
    {
      parent.showGame (2, isLive);
    }
    else if (e.getActionCommand ().equals ("3"))
    {
      parent.showGame (3, isLive);
    }
    else
    {
      parent.showMenu('l');
    }
  }
  
  /**
   * Draws a background image.
   * 
   * @param g Reference, points to the Graphics class
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations
   */
  public void paintComponent (Graphics g)
  {
    try
    {
      g.drawImage (ImageIO.read(new File("res/Menus/otherbackgrounds.png")), 0, 0, 1024, 720, this);
    }
    catch(IOException ioe)
    {
    }
  }
}