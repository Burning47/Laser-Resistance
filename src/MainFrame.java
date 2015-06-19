import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Creates a 1024 x 720 pixel window with a menu in the menu bar.
 * <p>
 * @author Kenneth Tran
 * @version V1.0.0, 10 June, 2014
 */
public class MainFrame implements ActionListener{
  /**
   * sl reference for SpringLayout, points to the SpringLayout class. 
   */
  private SpringLayout sl;
  /**
   * hsp reference for HighScoresPanel, points to the HighScoresPanel class. 
   */
  private HighScoresPanel hsp;
  /**
   * highscores array of HighScoreEntry, used to store entries of high score entries. 
   */
  private HighScoreEntry [] highScores;
  /**
   * les reference for Lesson, points to the Lesson class. 
   */
  private Lesson les;
  /**
   * lp reference for LevelSelect, points to the LevelSelect class. 
   */
  private LevelSelect lp;
  /**
   * mainMenu reference for JPanel, points to the JPanel class. 
   */
  private JPanel mainMenu;
  /**
   * gp reference for Game, points to the Game class. 
   */
  private Game gp;
  /**
   * mf reference for JFrame, points to the JFrame class. 
   */
  JFrame mf;
  
  
  /**
   * Constructs the JFrame Window with menus and menu items. Creates 6 buttons and an image 
   * contained in a Label on the JPanel. Upon clicking any of the buttons, or clicking the appropriate key bindings, 
   * the user will be brought to the screen of the button that they clicked. Also opens the high score file and reads in the data into an array.
   * <p>
   * @param quitItem reference for JMenuItem, stores the menu item for 'quit'.
   * @param fileMenu reference for JMenu, stores the menu for 'file'.
   * @param frameMenus reference for JMenuBar, stores the menu bar which holds the menus.   * @param image reference for JLabel, stores the label and ImageIcon of image that contains the title of the game.
   * @param instructionsButton reference for JButton, stores the button that will lead to the instructions and rules screen that the user may read 
   * to learn how the game and program works overall. 
   * @param lessonButton reference for JButton, stores the button that will lead to the lesson screen where the user learns about lasers and optics.
   * @param practiceButton reference for JButton, stores the button that will allow the user to practice the game before playing the game.
   * @param playButton reference for JButton, stores the button that will allow the user to play the game (scores will be saved in this mode).
   * @param highScoresButton reference for JButton, stores the button that will lead to the high scores screen.
   * @param quitButton reference for JButton, stores the button that will exit the program. 
   * @param backgroung reference for JLabel, used to store the image read in from a file to be used as the background of the main menu.
   */
  public MainFrame ()
  {
    mf = new JFrame ("Laser Resistance");
    
    mainMenu = new JPanel();
    sl = new SpringLayout();
    mainMenu.setLayout(sl);
    
    
    JLabel image = new JLabel(new ImageIcon("res/Menus/LaserResistanceTitle.png"));
    sl.putConstraint (SpringLayout.NORTH, image, 10, SpringLayout.NORTH , mf);
    sl.putConstraint (SpringLayout.WEST, image, 10, SpringLayout.WEST, mf);
    mainMenu.add(image);
    
    JButton playButton = new JButton (new ImageIcon ("res/Menus/button3.png"));
    playButton.setBorderPainted(false);
    playButton.setContentAreaFilled(false);
    playButton.setActionCommand("Play");    
    playButton.addActionListener(this);
    playButton.setFocusable (false);
    sl.putConstraint (SpringLayout.NORTH, playButton, 200, SpringLayout.NORTH , mf);
    sl.putConstraint (SpringLayout.WEST, playButton, 10, SpringLayout.WEST, mf);
    mainMenu.add (playButton);
    
    JButton practiceButton = new JButton (new ImageIcon ("res/Menus/button5.png"));
    practiceButton.setBorderPainted(false);
    practiceButton.setContentAreaFilled(false);
    practiceButton.setActionCommand("Practice");
    practiceButton.addActionListener(this);
    practiceButton.setFocusable (false);
    sl.putConstraint (SpringLayout.NORTH, practiceButton, 10, SpringLayout.SOUTH , playButton);
    sl.putConstraint (SpringLayout.WEST, practiceButton, 10, SpringLayout.WEST, mf);
    mainMenu.add (practiceButton);
    
    JButton lessonButton = new JButton (new ImageIcon ("res/Menus/button2.png"));
    lessonButton.setBorderPainted(false);
    lessonButton.setContentAreaFilled(false);
    lessonButton.setActionCommand ("Lesson");
    lessonButton.addActionListener(this);
    lessonButton.setFocusable (false);
    sl.putConstraint (SpringLayout.NORTH, lessonButton, 10, SpringLayout.SOUTH , practiceButton);
    sl.putConstraint (SpringLayout.WEST, lessonButton, 10, SpringLayout.WEST, mf);
    mainMenu.add (lessonButton);
    
    
    JButton highScoresButton = new JButton (new ImageIcon ("res/Menus/button4.png"));
    highScoresButton.setBorderPainted(false);
    highScoresButton.setContentAreaFilled(false);
    highScoresButton.setActionCommand ("High Scores");
    highScoresButton.addActionListener(this);  
    highScoresButton.setFocusable (false);
    sl.putConstraint (SpringLayout.NORTH, highScoresButton, 10, SpringLayout.SOUTH , lessonButton);
    sl.putConstraint (SpringLayout.WEST, highScoresButton, 10, SpringLayout.WEST, mf);
    mainMenu.add (highScoresButton);
    
    JButton quitButton = new JButton (new ImageIcon ("res/Menus/button6.png"));
    quitButton.setBorderPainted(false);
    quitButton.setContentAreaFilled(false);
    quitButton.setActionCommand("Quit");
    quitButton.addActionListener(this);
    quitButton.setFocusable (false);
    sl.putConstraint (SpringLayout.NORTH, quitButton, 10, SpringLayout.SOUTH , highScoresButton);
    sl.putConstraint (SpringLayout.WEST, quitButton, 10, SpringLayout.WEST, mf);
    mainMenu.add (quitButton);   
    
    JLabel background  = new JLabel (new ImageIcon ("res/Menus/laserbackground.jpg"));
    sl.putConstraint (SpringLayout.NORTH, background, 0, SpringLayout.NORTH , mf);
    sl.putConstraint (SpringLayout.WEST, background, 0, SpringLayout.WEST, mf);
    mainMenu.add (background);
    
    mf.add(mainMenu);
    
    
    JMenuItem helpItem = new JMenuItem ("Help");
    JMenuItem aboutItem = new JMenuItem ("About");
    
    JMenu helpMenu = new JMenu ("Help");
    helpMenu.add (helpItem);
    helpMenu.add (aboutItem);
    JMenuBar bar = new JMenuBar ();
    bar.add (helpMenu);
    mf.setJMenuBar (bar);   
    helpItem.addActionListener (this);
    aboutItem.addActionListener (this);
    
    mainMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK), "Play");
    mainMenu.getActionMap().put("Play", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        showLevelSelect (true);
      }
    });
    
    mainMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, Event.CTRL_MASK), "Practice");
    mainMenu.getActionMap().put("Practice", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        showLevelSelect (false);
      }
    });
    
    mainMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_3, Event.CTRL_MASK), "Lesson");
    mainMenu.getActionMap().put("Lesson", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        showLesson();
      }
    });
    
    mainMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, Event.CTRL_MASK), "High Scores");
    mainMenu.getActionMap().put("High Scores", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        showHighScores();
      }
    });
    
    mainMenu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_5, Event.CTRL_MASK), "Quit");
    mainMenu.getActionMap().put("Quit", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    
    mf.setSize (1024, 720);
    mf.setResizable(false);
    mf.setLocationRelativeTo (null);
    mf.setVisible (true);
    mf.repaint();
    mf.invalidate();
    mf.validate();
    mf.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
    
    open ();
  }
  
  /**
   * The purpose of this method is to open the high scores file. It checks the header
   * to determine if the high scores file is valid. It saves the high scores into an array of HighScoreEntry's.
   * <p>
   * @param in BufferedReader, refers to BufferedReader class used to read in from a file.
   * @param ioe IOException, reference to the thrown and caught IOException object.
   * @param x int, used in for loop.
   * @param count, used for counting the entries read.
   * 
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations
   * @exception NumberFormatException Used to catch if something goes wrong when converting an integer to a String
   */
  private void open ()
  {
    int count = 0;
    highScores = new HighScoreEntry [10];
    try
    {
      BufferedReader in = new BufferedReader (new FileReader ("res/leaderboards.kdat"));
      String temp = in.readLine ();
      if (temp != null && temp.equals ("laserinos"))
      {
        count = Integer.parseInt (in.readLine ());
        for (int x = 0 ; x < count ; x++)
        {
          highScores [x] = new HighScoreEntry (in.readLine (), Integer.parseInt (in.readLine ()), Integer.parseInt (in.readLine ()));
        }
      }
      else{
        write (highScores);
      }
    }
    catch (IOException ioe)
    {
      write (highScores);
    }
    catch (NumberFormatException nfe)
    {
    }
  }
  
  /**
   * This method writes to and updates the high score file.
   * <p>
   * @param out PrintWriter, refers to PrintWriter class used to read in from a file.
   * @param ioe IOException, reference to the thrown and caught IOException object.
   * @param x int, used in for loop.
   * 
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations
   */
  public void write (HighScoreEntry [] toWrite)
  {
    int count = 0;
    try
    {
      PrintWriter out = new PrintWriter (new FileWriter ("res/leaderboards.kdat"));
      for (int x = 0; x < toWrite.length; x++)
      {
        if (highScores [x] == null)
          break;
        else
          count ++;
      }
      out.println ("laserinos");
      out.println (count);
      for (int x = 0; x < count; x++)
      {
        out.println (toWrite [x].getName ());
        out.println (toWrite [x].getScore ());
        out.println (toWrite [x].getLevel ());
      }
      out.close ();
    }
    catch (IOException ioe)
    {
    }
  }
  
  /**
   * This method is used to hide the menu panel and show the game panel. 
   */
  public void showGame (int level, boolean isLive)
  {
    mf.remove(lp);
    mf.repaint();
    mf.invalidate();
    mf.validate();
    gp = new Game (level, isLive, highScores, this);
    mf.add (gp);
    gp.repaint ();
    gp.revalidate();
  }
  
  /**
   * This method is used to hide the menu panel and show the lesson panel. 
   */
  public void showLesson (){
    mf.remove(mainMenu);
    les = new Lesson(this);
    mf.add (les);
    les.repaint();
    les.revalidate();
  }
  
  /**
   * This method is used to hide the menu panel and show the level select panel. 
   * @param isLive boolean to determine if the selected level is in practice mode (false), or an actual game (true)
   */
  public void showLevelSelect (boolean isLive)
  {
    mf.remove(mainMenu);
    lp = new LevelSelect (isLive, this);
    mf.add (lp);
    lp.repaint ();
    lp.revalidate ();
  }
  
  /**
   * This method is used to hide the menu panel and show the high scores panel. 
   */
  public void showHighScores ()
  {
    mf.remove(mainMenu);
    hsp = new HighScoresPanel (highScores, this);
    mf.add (hsp);
    hsp.repaint ();
    hsp.revalidate ();
  }
  
  /**
   * The purpose of this static method is to be used by the classes of other panels to 
   * return to the menu. 
   */
  public void showMenu (char panel)
  {
    if (panel == 'h')
      mf.remove (hsp);
    else if (panel == 'l')
      mf.remove (lp);
    else if (panel == 't')
      mf.remove (les);
    else if (panel == 'g')
      mf.remove (gp);
    mf.add (mainMenu);
    mainMenu.repaint ();
    mainMenu.invalidate();
    mainMenu.validate();
  }
  
  /**
   * Displays a dialog which displays information about the game, the version, and the authors.
   * 
   * @param d Reference for JDialog to display the dialog window
   * @param game JLabel which displays the logo of the game
   * @param company JLabel which displays the logo of the company
   * @param title JLabel which displays the title of the game
   * @param team JLabel which displays the company name
   * @param programmers JLabel which displays the names of the programmers
   * @param version JLabel which displays the current version of the game
   * @param help JLabel which displays contact information for help
   */
  public void aboutDialog ()
  {
    JLabel game = new JLabel (new ImageIcon ("res/Menus/gamelogo.png"));
    JLabel company = new JLabel (new ImageIcon ("res/Menus/logo.png"));
    JLabel title = new JLabel (new ImageIcon ("res/Menus/LaserResistanceTitle.png"));
    JLabel team = new JLabel ("By Rapture Softworks, 2014");
    JLabel programmers = new JLabel ("Programmers: Illya Pilipenko, Kenneth Tran, Erik Trinh");
    JLabel version = new JLabel ("V 1.0.0");
    JLabel help = new JLabel ("Contact: support@raptureworks.com");
    final JDialog d = new JDialog (mf, "About", true);
    SpringLayout layout = new SpringLayout ();
    d.setSize (600, 500);
    d.setResizable (false);
    d.setLayout (layout);
    
    
    
    layout.putConstraint (SpringLayout.WEST, version, 5, SpringLayout.WEST, d);
    layout.putConstraint (SpringLayout.NORTH, version, 5, SpringLayout.NORTH, d);
    d.add (version);
    
    layout.putConstraint (SpringLayout.WEST, game, 200, SpringLayout.WEST, d);
    layout.putConstraint (SpringLayout.NORTH, game, 20, SpringLayout.NORTH, d);
    d.add (game);
    
    layout.putConstraint (SpringLayout.WEST, title, 115, SpringLayout.WEST, d);
    layout.putConstraint (SpringLayout.NORTH, title, 20, SpringLayout.SOUTH, game);
    d.add (title);
    
    layout.putConstraint (SpringLayout.NORTH, company, 300, SpringLayout.NORTH, d);
    layout.putConstraint (SpringLayout.WEST, company, 50, SpringLayout.WEST, d);
    d.add (company);
    
    layout.putConstraint (SpringLayout.SOUTH, programmers, 0, SpringLayout.SOUTH, company);
    layout.putConstraint (SpringLayout.WEST, programmers, 35, SpringLayout.EAST, company);
    d.add (programmers);
    
    layout.putConstraint (SpringLayout.SOUTH, team, -10, SpringLayout.NORTH, programmers);
    layout.putConstraint (SpringLayout.WEST, team, -20, SpringLayout.WEST, programmers);
    d.add (team);
    
    layout.putConstraint (SpringLayout.NORTH, help, 10, SpringLayout.SOUTH, programmers);
    layout.putConstraint (SpringLayout.WEST, help, 20, SpringLayout.WEST, programmers);
    d.add (help);
    
    d.setLocationRelativeTo (mf);
    d.setVisible (true);
  }
  
  /**
   * This method processes the user input based on what menu item is clicked
   * and processes a response. 
   * <p>
   * Pressing the "Lesson" button will lead to the lesson panel, "Practice" will lead to a level selection screen for a practice mode, "Play" will 
   * lead to a level selection screen for a game, "High Scores" will display a high scores panel, "Quit" will close the window, "About" will display a JPanel about 
   * the authors, and "Help" will bring up a .chm help file.
   * <p>
   * @param ae points to the ActionEvent class.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand().equals("Lesson"))
    {
      showLesson();
    }
    else if (ae.getActionCommand().equals("Practice"))
    {
      showLevelSelect (false);
    }
    else if (ae.getActionCommand().equals("Play"))
    {
      showLevelSelect (true);
    }
    else if (ae.getActionCommand().equals("High Scores"))
    {
      showHighScores();
    }
    else if (ae.getActionCommand().equals("About"))
    {
      aboutDialog ();
    }
    else if (ae.getActionCommand().equals("Help"))
    {
      String progpath = new String ("hh.exe res/help.chm");
      try  
      {
        Runtime.getRuntime ( ).exec (progpath);
      }
      catch (IOException ioe)
      {
        JOptionPane.showMessageDialog (mf, "Couldn't find Help File");
      }
    }
    else
    {
      System.exit(0);
    }
  } 
}
