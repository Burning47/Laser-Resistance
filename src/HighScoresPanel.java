import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

/**
 * This class creates the JPanel that displays the top ten scores in a table.
 * The user may view the scores, print the scores, or return to the main menu.
 *
 * @author Kenneth Tran modified by Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class HighScoresPanel extends JPanel implements ActionListener
{
  /**
   * Private SpringLayout, reference for the SpringLayout which this panel will be using.
   */
  private SpringLayout sl;
  
  /**
   * Private array of HighScoreEntry's for the high scores to be displayed.
   */
  private HighScoreEntry highScores [];
  
  /**
   * Private String for the name of the player to be displayed on the high scores.
   */
  private MainFrame parent;
  
  /**
   * Array of JLabels displaying the ranks.
   */
  private JLabel [] ranks;
  
  /**
   * Array of JLabels displaying the names.
   */
  private JLabel [] names;
  
  /**
   * Array of JLabels displaying the scores.
   */
  private JLabel [] scores;
  
  /**
   * Array of JLabels displaying the levels.
   */
  private JLabel [] levels;
  
  /**
   * The purpose of this constructor is to create the JPanel for the high scores, initializes variables, sets up the buttons, labels, and keybindings.
   * 
   * @param highScores Array of HighScoreEntry's to display.
   * @param frame MainFrame which this panel is attached to.
   * @param title JLabel, label that stores the image for the title.
   * @param returnMenu JButton, button used to return to the main menu.
   * @param printButton JButton, button used to print the high scores.  
   */
  public HighScoresPanel(HighScoreEntry highScores [], MainFrame frame)
  {
    sl = new SpringLayout();
    setLayout(sl);
    
    ranks = new JLabel [11];
    names = new JLabel [11];
    scores = new JLabel [11];
    levels = new JLabel [11];
    
    displayEntry(0, "Name", "Score", "Level");
    
    this.highScores = highScores;
    parent = frame;
    
    JLabel title = new JLabel(new ImageIcon("res/Menus/HighScoresTitle.png"));
    sl.putConstraint (SpringLayout.NORTH, title, 10, SpringLayout.NORTH , this);
    sl.putConstraint (SpringLayout.WEST, title, 10, SpringLayout.WEST, this);
    add(title);
    
    display();
    JButton returnMenu = new JButton (new ImageIcon ("res/Menus/mainmenu.png"));
    returnMenu.setActionCommand("Menu");
    returnMenu.setBorderPainted(false);
    returnMenu.setContentAreaFilled(false);
    sl.putConstraint (SpringLayout.WEST, returnMenu, 20, SpringLayout.WEST, this);
    sl.putConstraint (SpringLayout.SOUTH, returnMenu, -20, SpringLayout.SOUTH , this);
    
    returnMenu.addActionListener (this);
    returnMenu.setFocusable (false);
    add (returnMenu);
    
    JButton printButton = new JButton (new ImageIcon ("res/Menus/print.png"));
    printButton.setActionCommand("Print");
    printButton.setBorderPainted(false);
    printButton.setContentAreaFilled(false);
    sl.putConstraint (SpringLayout.EAST, printButton, -20, SpringLayout.EAST, this);
    sl.putConstraint (SpringLayout.SOUTH, printButton, -20, SpringLayout.SOUTH , this);
    
    printButton.addActionListener (this);
    printButton.setFocusable (false);
    add (printButton);
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK), "Print");
    getActionMap().put("Print", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        print ();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, Event.CTRL_MASK), "Menu");
    getActionMap().put("Menu", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        parent.showMenu('h');
      }
    });
  }
  
  /**
   * The purpose of this method is to display the high scores by creating labels for each entry and displaying it.
   * 
   * @param name String for the name to display
   * @param score String for the score to display
   * @param level String for the level to display
   *
   * @exception NumberFormatException Used to catch if something goes wrong when converting an integer to a String
   */
  public void display()
  {
    String name;
    String score;
    String level;
    
    try
    {
      for (int x = 0; x < 10; x++)
      {
        if (highScores [x] != null)
        {
          name = highScores [x].getName ();
          score = Integer.toString (highScores [x].getScore ());
          level = Integer.toString (highScores [x].getLevel ());
        }
        else
        {
          name = "---";
          score = "---";
          level = "---";
        }
        displayEntry (x+1, name, score, level);
      }
    }
    catch (NumberFormatException nfe)
    {
    }
  }
  
  /**
   * Brings up a dialog to print the high scores.
   * 
   * @param p Printer object to handle printing.
   * @exception NumberFormatException Used to catch if something goes wrong when converting an integer to a String.
   */
  private void print ()
  {
    Printer p = new Printer ();
    p.println ("", "Laser Resistance High Scores", "");
    p.println ("Name", "Score", "Level");
    try
    {
      for (int x = 0; x < highScores.length; x++)
      {
        p.print ((x+1) + ".  ");
        if (highScores [x] != null)
          p.println (highScores [x].getName (), Integer.toString (highScores [x].getScore ()), Integer.toString (highScores [x].getLevel ()));
        else
          p.println ("---", "---", "---");
      }
    }
    catch (NumberFormatException nfe)
    {
    }
    p.printUsingDialog ();
  }
  
  /**
   * Displays one row of high scores by using JLabels and setting the constraints before adding them.
   * 
   * @param entryNo int for the rank on the high scores of an entry to be used to create a JLabel.
   * @param name String, stores the name on the high scores of an entry to be used to create a JLabel.
   * @param score String, stores the score on the high scores of an entry to be used to create a JLabel.
   * @param level String, stores the level on the high scores of an entry to be used to create a JLabel.
   * @param yCoord int, stores the y coordinate for displaying the entries. 
   */
  public void displayEntry (int entryNo, String name, String score, String level)
  {
    int yCoord = 100 + entryNo * 40;
    
    ranks [entryNo] = new JLabel (entryNo + ".");
    if (entryNo == 0)
      ranks [entryNo].setText ("Rank");
    sl.putConstraint (SpringLayout.WEST, ranks [entryNo], 150, SpringLayout.WEST, this);
    sl.putConstraint (SpringLayout.NORTH, ranks [entryNo], yCoord, SpringLayout.NORTH , this);
    add (ranks [entryNo]);
    
    names [entryNo] = new JLabel (name);
    sl.putConstraint (SpringLayout.WEST, names [entryNo], 300, SpringLayout.WEST, this);
    sl.putConstraint (SpringLayout.NORTH, names [entryNo], yCoord, SpringLayout.NORTH , this);
    add (names [entryNo]);
    
    scores [entryNo] = new JLabel (score);
    sl.putConstraint (SpringLayout.EAST, scores [entryNo], -300, SpringLayout.EAST, this);
    sl.putConstraint (SpringLayout.NORTH, scores [entryNo], yCoord, SpringLayout.NORTH , this);
    add (scores [entryNo]);
    
    levels [entryNo] = new JLabel (level);
    sl.putConstraint (SpringLayout.EAST, levels [entryNo], -150, SpringLayout.EAST, this);
    sl.putConstraint (SpringLayout.NORTH, levels [entryNo], yCoord, SpringLayout.NORTH , this);
    add (levels [entryNo]);
  }
  
  /** 
   * Compares the title of the action the user performed with a String and determines a response to their action. Clicking the "Print" button 
   * will bring up the print dialog, the other button to go back to the main menu.
   * 
   * @param e Reference, points to the ActionEvent class.
   */
  public void actionPerformed (ActionEvent e)
  {
    if (e.getActionCommand ().equals ("Print"))
    {
      print ();
    }
    else
    {
      parent.showMenu('h');
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