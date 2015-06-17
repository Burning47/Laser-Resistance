import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;

/**
 * The "Game" class, sets up a panel for the game, sets up the game space and sets and draws the game components based on the level. This class handles 
 * the game logic, including things like the score, rules, setting the boundaries, and the controls. It also handles the pause menu, and logic when the game is over, 
 * adding high score entries when appropriate.
 * 
 * @author Erik Trinh
 * @version V1.0.0, 10 June, 2014
 */
public class Game extends JPanel implements ActionListener
{
  /**
   * Private Player variable for the player object.
   */
  private Player p;
  
  /**
   * Private ArrayList of Targets currently on the board.
   */
  private ArrayList <Target> targets = new ArrayList <Target> ();
  
  /**
   * Private ArrayList of lasers created by pressing the fire button.
   */
  private ArrayList <Laser> lasers = new ArrayList <Laser> ();
  
  /**
   * Private JButton which stores the fire button.
   */
  private JButton fireButton;
  /**
   * Private JButton which stores the button for the pause menu.
   */
  private JButton pauseButton;
  
  /**
   * Private JLabel which displays the angle the player is currently facing.
   */
  private JLabel angleDisplay;
  
  /**
   * Private JLabel which displays the current score.
   */
  private JLabel scoreDisplay;
  
  /**
   * Private JLabel which displays the current level.
   */
  private JLabel levelDisplay;
  
  /**
   * Private JLabel which displays the current number of lives.
   */
  private JLabel livesDisplay;
  
  /**
   * Private Mirror variable for the mirror on the left side of the board.
   */
  private Mirror leftMirror;
  
  /**
   * Private Mirror variable for the mirror on the right side of the board.
   */
  private Mirror rightMirror;
  
  /**
   * Private Barrier variable for the barrier directly in front of the player starting area.
   */
  private Barrier barrier;
  
  /**
   * Private Rectangle depicting the area of the end zone, where if a target reaches, the game is over.
   */
  private Rectangle endZone;
  
  /**
   * Private Rectangle depicting the area of which the player can move within.
   */
  private Rectangle playerZone;
  
  /**
   * Private Rectangle depicting the area of the whole game board.
   */
  private Rectangle gameSpace;
  
  /**
   * Private int for the user's current score.
   */
  private int score;
  
  /**
   * Private int for the user's score from the previous level, to determine when the user passes the current one.
   */
  private int previousScore;
  
  /**
   * Private int for the amount of lives the user has.
   */
  private int lives;
  
  /**
   * Private int for the multiplier on the score constant when the user hits consecutive shots.
   */
  private int multiplier;
  
  /**
   * Private int for the current level the game is set to, 1 for easy (straight mirrors), 2 for medium (concave mirrors), 3 for hard (convex mirrors).
   */
  private int level;
  
  /**
   * Private Timer for the timer used to animate the targets and laser, isntance level since the object cannot be referenced inside a nested method.
   */
  private Timer timer;
  
  /**
   * Private int for the amount of times the timer has played the action.
   */
  private int count;
  
  /**
   * Private Color for the laser that will be drawn (changes its alpha value so once it fires, the laser fades away).
   */
  private Color laserColor;
  
  /**
   * Private SpringLayout, reference for the SpringLayout which this panel will be using.
   */
  private SpringLayout layout;
  
  /**
   * Private boolean to determine if the game is actually a live game (true), or just a practice (false).
   */
  private boolean isLive;
  
  /**
   * Private JDialog to be used for the pause menu and game over screen.
   */
  private JDialog d;
  
  /**
   * Private array of HighScoreEntry objects for the current entries of high scores (up to 10).
   */
  private HighScoreEntry [] highScores;
  
  /**
   * Private MainFrame which this panel is attached to.
   */
  private MainFrame parent;
  
  /**
   * Private String for the name of the player to be displayed on the high scores.
   */
  private String playerName;
  
  /**
   * Private BufferedImage for the image of the background of only the game screen.
   */
  private BufferedImage gameBackground;
  
  /**
   * Private BufferedImage for the image of the background of the non0playing space.
   */
  private BufferedImage userBackground;
  
  /**
   * Private BufferedImage for the image of the zone where the player can move.
   */
  private BufferedImage playerZoneImage;
  
  /**
   * Private BufferedImage for the image of the end zone.
   */
  private BufferedImage endZoneImage;
  
  /**
   * Constructor for the class, sets up the panel and the position of all the components. The components are then added to the panel. Initialize the variables, displays the GUI, sets up key bindings, and sets up the timer 
   * to be used for animation.
   * <p>
   * Pressing 'w' will move the player up, 's' will move the player down, 'a' will move the player left, 'd' will move the player right.
   * Pressing the right arrow and left arrow will change the angle of the player in a clockwise direction and a counter-clockwise direction respectively.
   * Pressing the space bar will have the same function as the on screen button "Fire", which calls the fire method and shoots the laser.
   * Pressing the "Pause" button or escape will bring up a dialog for the pause menu.
   * 
   * @param level int for the level of this game which it is starting at (1 for the first level, 2 for the second, 3 for the third).
   * @param isLive boolean to determine if it is practice mode (false), or an actual game (true).
   * @param highScores Array of HighScoreEntries which will this class will modify based on the user's score.
   * @param frame MainFrame which this class is added to.
   * 
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations.
   */
  public Game (int level, boolean isLive, HighScoreEntry [] highScores, MainFrame frame)
  {
    super ();
    layout = new SpringLayout ();
    setLayout (layout);
    
    score = 0;
    previousScore = 0;
    this.level = level;
    multiplier = 1;
    lives = 5;
    this.isLive = isLive;
    this.highScores = highScores;
    
    parent = frame;
    
    endZone = new Rectangle (0, 385, 600, 75);
    playerZone = new Rectangle (175, 491, 250, 109);
    gameSpace = new Rectangle (0, 0, 600, 600);
    
    targets = new ArrayList <Target> ();
    targets.add (new Target (35, 35, assignImage ()));
    
    try
    {
      p = new Player ((int) (playerZone.getX () + playerZone.getWidth () / 2), (int) (playerZone.getY () + playerZone.getHeight () / 2), ImageIO.read (new File("res/Game/player.png")));
      barrier = new Barrier (ImageIO.read (new File("res/Game/barrier.png")));
      gameBackground = ImageIO.read (new File("res/Game/background.png"));
      userBackground = ImageIO.read (new File("res/Game/userbackground.png"));
      playerZoneImage = ImageIO.read (new File("res/Game/playerspace.png"));
      endZoneImage = ImageIO.read (new File("res/Game/endspace.png"));
    }
    catch(IOException ioe)
    {
    }
    
    addMirrors ();
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "fire");
    getActionMap().put("fire", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        fire ();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
    getActionMap().put("up", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        playerMove (0, -1);
        repaint ();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
    getActionMap().put("down", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        playerMove (0, 1);
        repaint ();
      }
    });
    
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");
    getActionMap().put("left", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        playerMove (-1, 0);
        repaint ();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
    getActionMap().put("right", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        playerMove (1, 0);
        repaint ();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "clockwise");
    getActionMap().put("clockwise", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        p.setAngle (p.getAngle () + 1);
        repaint ();
        angleDisplay.setText ("Current Angle: " + Integer.toString (p.getAngle ()));
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "counterclockwise");
    getActionMap().put("counterclockwise", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        p.setAngle (p.getAngle () - 1);
        repaint ();
        angleDisplay.setText ("Current Angle: " + Integer.toString (p.getAngle ()));
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "pause");
    getActionMap().put("pause", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        pauseDialog (timer.isRunning ());
      }
    });
    
    createAndShowGUI ();
    
    timer = new Timer(24, new ActionListener() {
      public void actionPerformed(ActionEvent evt) 
      {
        count ++;
        laserColor = new Color (255, 0, 0, 210 - 3 * count);
        for (Target target : targets)
        {
          target.move (0,  1);
        }
        if (count >= 70)
        {
          timer.stop ();
          fireButton.setEnabled (true);
          getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "fire");
          checkEnd ();
          targets.add (new Target (35, 35, assignImage ()));
          checkAdvance ();
        }
        repaint ();
      }
    });
  }
  
  /**
   * Returns a random BufferedImage of a target which will be assigned to a Target object to be drawn.
   * 
   * @param n int for the number of the target image to be returned.
   * @param imageName String for the file name for the image to be returned.
   * @param image BufferedImage which will be returned.
   * @return the image with the name specified by imageName for a target.
   * 
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations.
   */
  public BufferedImage assignImage ()
  {
    int n = (int) (1 + Math.random () * 9);
    try
    {
      return ImageIO.read(new File("res/Game/monster" + n + ".png"));
    }
    catch(IOException ioe)
    {
    }
    return null;
  }
  
  /**
   * Sets the mirrors on the game depending on what level it is.
   * 
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations.
   */
  public void addMirrors ()
  {
    try
    {
      if (level == 1)
      {
        leftMirror = new Mirror (0, 0, 30, 600, ImageIO.read (new File("res/Game/mirror.png")));
        rightMirror = new Mirror (570, 0, 30, 600, ImageIO.read (new File("res/Game/mirror.png")));
      }
      else if (level == 2)
      {
        leftMirror = new ConcaveMirror (0, -400, 1400, 1400, false, ImageIO.read (new File("res/Game/concave.png")));
        rightMirror = new ConcaveMirror (-800, -400, 1400, 1400, true, ImageIO.read (new File("res/Game/concave.png")));
      }
      else
      {
        leftMirror = new CurvedMirror (-1331, -400, 1400, 1400, false, ImageIO.read (new File("res/Game/convex.png")));
        rightMirror = new CurvedMirror (531, -400, 1400, 1400, true, ImageIO.read (new File("res/Game/convex.png")));
      }
    }
    catch (IOException ioe)
    {
    }
  }
  
  /**
   * Initializes and adds the buttons and labels to this panel. The SpringLayout constraints for the components are also set here. Also adds ActionListeners to the buttons.
   */
  private void createAndShowGUI ()
  {
    fireButton = new JButton (new ImageIcon ("res/Menus/fire.png"));
    fireButton.setActionCommand("Fire");
    fireButton.setBorderPainted(false);
    fireButton.setContentAreaFilled(false);
    
    pauseButton = new JButton (new ImageIcon ("res/Menus/pause.png"));
    pauseButton.setActionCommand("Menu");
    pauseButton.setBorderPainted(false);
    pauseButton.setContentAreaFilled(false);
    
    fireButton.setFocusable (false);
    pauseButton.setFocusable (false);
    
    angleDisplay = new JLabel ("Current Angle: " + Integer.toString (p.getAngle ()));
    if (isLive == true)
    {
      scoreDisplay = new JLabel ("Score: " + Integer.toString (score));
      livesDisplay = new JLabel ("Lives: " + Integer.toString (lives));
      levelDisplay = new JLabel ("Level: " + Integer.toString (level));
    }
    else
    {
      scoreDisplay = new JLabel ("Score: N/A");
      livesDisplay = new JLabel ("Lives: N/A");
      levelDisplay = new JLabel ("Practice Mode");
    }
    
    layout.putConstraint(SpringLayout.WEST, levelDisplay,
                         620,
                         SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, levelDisplay,
                         20,
                         SpringLayout.NORTH, this);
    add (levelDisplay);
    
    layout.putConstraint(SpringLayout.WEST, livesDisplay,
                         0,
                         SpringLayout.WEST, levelDisplay);
    layout.putConstraint(SpringLayout.NORTH, livesDisplay,
                         25,
                         SpringLayout.SOUTH, levelDisplay);
    add (livesDisplay);
    
    
    layout.putConstraint(SpringLayout.WEST, scoreDisplay,
                         0,
                         SpringLayout.WEST, levelDisplay);
    layout.putConstraint(SpringLayout.NORTH, scoreDisplay,
                         25,
                         SpringLayout.SOUTH, livesDisplay);
    add (scoreDisplay);
    
    layout.putConstraint(SpringLayout.WEST, angleDisplay,
                         0,
                         SpringLayout.WEST, levelDisplay);
    layout.putConstraint(SpringLayout.NORTH, angleDisplay,
                         25,
                         SpringLayout.SOUTH, scoreDisplay);
    add (angleDisplay);
    
    layout.putConstraint(SpringLayout.WEST, fireButton,
                         721,
                         SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, fireButton,
                         25,
                         SpringLayout.SOUTH, angleDisplay);
    add (fireButton);
    
    layout.putConstraint(SpringLayout.WEST, pauseButton,
                         0,
                         SpringLayout.WEST, fireButton);
    layout.putConstraint(SpringLayout.SOUTH, pauseButton,
                         -20,
                         SpringLayout.SOUTH, this);
    add (pauseButton);
    
    fireButton.addActionListener (this);
    pauseButton.addActionListener (this);
  }
  
  /**
   * Moves the player x length along the x-axis, and the y length along the y-axis, if the new coordinates for the player are still in the player zone.
   * 
   * @param x int for the distance to be moved along the x-axis.
   * @param y int for the distance to be moved along the y-axis.
   */
  public void playerMove (int x, int y)
  {
    if (playerZone.contains (p.getX () + x, p.getY () + y, p.getWidth (), p.getHeight ()))
      p.move (x, y);
  }
  
  /**
   * Disables the fire button and the keybinding for space. It also fires and processes the laser, then sets the timer in order to animate the targets moving
   * forward and the laser fading away. Once the animation is complete, the timer is stopped, fire button and keybinding for space re-enabled, checks if a target 
   * has reached the end, checks if the user is eligible to move on to the next level, and then spawns a new target.
   */
  public void fire ()
  {
    fireButton.setEnabled (false);
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
    
    lasers = new ArrayList <Laser> ();
    lasers.add (new Laser (p.getCentreX (), p.getCentreY (), p.getAngle ()));
    processLaser ();
    
    count = 0;
    timer.start ();
  }
  
  /**
   * Continuously extends the laser (incrementing the variable x), until it hits something, or is not in the game board anymore. Based on what it hits, a specific function will be performed. If 
   * it is a target, the target is removed, and the score and multiplier updated. If it is a mirror, it will create another laser, pretty much simulating a reflection.
   * 
   * @param check GameObject to check if the laser has hit anything yet, and then used to determine what action to take place depending on what type of GameObject it is
   * @param x the x-component length to extend the laser by
   */
  public void processLaser ()
  {
    GameObject check;
    int x;
    for (int y = 0 ; y < lasers.size () ; y ++)
    {
      x = 1;
      check = null;
      while (check == null && gameSpace.contains (lasers.get (y).getCurrPoint ()))
      {
        lasers.get (y).extend (x);
        check = checkHit (lasers.get (y).getCurrPoint ());
        x++;
      }
      if (check instanceof Target)
      {
        targets.remove (check);
        if (isLive == true)
        {
          score = score + 1 * multiplier;
          scoreDisplay.setText ("Score: " + Integer.toString (score));
          if (multiplier < 10)
            multiplier ++;
        }
      }
      else if (check instanceof Mirror)
      {
        if (lasers.size () < 3)
          lasers.add (((Mirror)check).reflect (lasers.get (y)));
        else
          multiplier = 1;
      }
      else
      {
        multiplier = 1;
      }
    }
  }
  
  /**
   * Returns the GameObject which containts this points, pretty much checking if this point has hit any GameObject.
   * 
   * @param point Point to be checked if it hits anything, or is contained by any of the shapes of the GameObjects.
   * @return the GameObject which this point hits, or which contains this point.
   */
  public GameObject checkHit (Point point)
  {
    if (barrier.getRect ().contains (point))
    {
      return barrier;
    }
    for (Target target : targets)
    {
      if (target.getRect ().contains (point))
      {
        return target;
      }
    }
    if (leftMirror.contains (point))
    {
      return leftMirror;
    }
    if (rightMirror.contains (point))
    {
      return rightMirror;
    }
    return null;
  }
  
  /**
   * Stops the timer and shows a dialog representative of a pause menu. The dialog shows a resume button to go back to the game (same action as pressing the escape key), and an exit button, which takes the 
   * user back to the main menu after checking if their current score can be added to the high scores list.
   * 
   * @param timerState boolean for if the timer was running (true), or not (false).
   * @param resume AbstractAction for what would happen if they exited the pause dialog back to the game.
   * @param resumeButton JButton for the "Resume" button.
   * @param exitButton JButton for the "Exit" button.
   * @param e ActionEvent, points to the ActionEvent class.
   */
  public void pauseDialog (boolean timerState)
  {
    final boolean wasRunning = timerState;
    timer.stop ();
    d = new JDialog (parent.mf, "Menu", true);
    d.setSize (150, 200);
    d.setResizable (false);
    d.setLayout (new FlowLayout ());
    
    d.add (new JLabel ("Paused"));
    
    AbstractAction resume = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        d.dispose ();
        if (wasRunning == true)
          timer.start ();
      }
    };
    
    d.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "resume");
    d.getRootPane().getActionMap().put("resume", resume);
    
    JButton resumeButton = new JButton (new ImageIcon ("res/Menus/resume.png"));
    resumeButton.setBorderPainted(false);
    resumeButton.setContentAreaFilled(false);
    resumeButton.setFocusable (false);
    resumeButton.addActionListener (resume);
    d.add (resumeButton);
    
    JButton exitButton = new JButton (new ImageIcon ("res/Menus/exit.png"));
    exitButton.setBorderPainted(false);
    exitButton.setContentAreaFilled(false);
    exitButton.addActionListener (new ActionListener() {
      public void actionPerformed (ActionEvent e){
        checkScore ();
        parent.showMenu('g');
        d.dispose ();
      }
    });
    exitButton.setFocusable (false);
    d.add (exitButton);
    
    d.addWindowListener (new WindowAdapter() 
                           {
      public void windowClosing(WindowEvent e)
      {
        if (wasRunning == true)
          timer.start ();
      }
    });
    
    d.setLocationRelativeTo (this);
    d.setVisible (true);
  }
  
  
  /**
   * Displays a dialog when the game is over (0 lives left), has a button to go back to main menu. It will check if the user
   * can add their score to the list. In addtion, it can also display an advance dialog when the user has enough points to move on.
   * 
   * @param isOver boolean to determine if it is a game over dialog (true) or an advance dialog (false).
   * @param display JLabel to display a title on the dialog.
   * @param statement JLabel to display a statement with the user's score and level they ended on.
   * @param exitButton JButton for the "Exit" button.
   */
  public void generalDialog (boolean isOver)
  {
    final boolean over = isOver;
    d = new JDialog (parent.mf, true);
    d.setResizable (false);
    d.setLayout (new FlowLayout ());
    
    JLabel display;
    JButton exitButton;
    
    if (over == true)
    {
      d.setSize (275, 135);
      display = new JLabel ("Game Over");
      d.add (display);
      
      d.add (new JLabel ("You finished with a score of " + score + " on level " + level + "!"));
                    
      exitButton = new JButton (new ImageIcon ("res/Menus/exit.png"));
      
      d.addWindowListener (new WindowAdapter() 
                             {
        public void windowClosing(WindowEvent e)
        {
          System.out.println("jdialog window closing event received");
          parent.showMenu('g');
          checkScore ();
        }
      });
    }
    else
    {
      d.setSize (275, 115);
      display = new JLabel ("You have advanced to the next level!");
      d.add (display);
      exitButton = new JButton (new ImageIcon ("res/Menus/continue.png"));
    }
    
    exitButton.setBorderPainted(false);
    exitButton.setContentAreaFilled(false);
    
    exitButton.addActionListener (new ActionListener() {
      public void actionPerformed (ActionEvent e){
        if (over == true)
        {
        checkScore ();
        parent.showMenu('g');
        }
        d.dispose ();
      }
    });
    exitButton.setFocusable (false);
    d.add (exitButton);
    
    d.setLocationRelativeTo (this);
    d.setVisible (true);
  }
  
  /**
   * Checks if there is an empty spot for a high score entry, or if the current score is greater than or equal to the current lowest score on the list. If it is true, then 
   * a new entry will replace the old one, with another JDialog to request a name. It then sorts the high score list and saves it into a file.
   * 
   * @param name String, used to store the name of the user. 
   */
  public void checkScore ()
  {
    if (isLive == true)
    {
      if (score != 0 && (highScores [9] == null || (score >= highScores [9].getScore () && level >= highScores [9].getLevel ())))
      {
        String name = askName ();
        if (name != null)
          highScores [9] = new HighScoreEntry (name, score, level);
        else
          return;
        sortScores ();
        parent.write (highScores);
      }
    }
  }
  
  /**
   * Sorts the current list of high scores with an insertion sort.
   */
  private void sortScores ()
  {
    for (int x = 1 ; x < highScores.length ; x++)
    {
      HighScoreEntry entry = highScores [x];
      int y;
      for (y = x - 1 ; y >= 0 && entry != null && (highScores [y] == null || (entry.getScore () >= highScores [y].getScore () && entry.getLevel () >= highScores [y].getLevel ())); y--)
        highScores [y + 1] = highScores [y];
      highScores [y + 1] = entry;
    }
  }
  
  /**
   * Uses a JDialog to get a name for the high score entry.
   * 
   * @param d2 JDialog to ask the name.
   * @param requestLabel JLabel to ask for input.
   * @param textField JTextField for the user to input in.
   * @param okButton JButton to send the text in the textField.
   * 
   * @return Name in a String format for the high scores list.
   */
  public String askName ()
  {
    final JDialog d2 = new JDialog (parent.mf, true);
    d2.setSize (390, 110);
    d2.setResizable (false);
    d2.setLayout (new FlowLayout ());
    
    JLabel requestLabel = new JLabel ("Please enter your name for a high score (max 16 characters):");
    d2.add (requestLabel);
    final JTextField textField = new JTextField ("Player", 16);
    d2.add (textField);
    
    playerName = textField.getText ();
    
    JButton okButton = new JButton (new ImageIcon ("res/Menus/ok.png"));
    okButton.setBorderPainted(false);
    okButton.setContentAreaFilled(false);
    
    okButton.addActionListener (new ActionListener() {
      public void actionPerformed (ActionEvent e){
        if (textField.getText ().length () > 16)
          textField.setText ("Player");
        else
        {
          playerName = textField.getText ();
          d2.dispose ();
        }
      }
    });
    okButton.setFocusable (false);
    d2.add (okButton);
    
    d2.setLocationRelativeTo (this);
    d2.setVisible (true);
    
    return playerName;
  }
  
  /**
   * Checks if any of the targets are at the end.
   */
  public void checkEnd ()
  {
    for (int x = 0; x < targets.size () ; x ++)
    {
      if (endZone.contains (targets.get (x).getRect ()))
      {
        if (isLive == true)
        {
          lives --;
          livesDisplay.setText ("Lives: " + Integer.toString (lives));
        }
        targets.remove (targets.get (x));
        x --;
      }
    }
    if (isLive == true && lives <= 0)
    {
      generalDialog (true);
    }
  }
  
  /**
   * Checks if the user is able to move onto the next level based on their score at the beginning of this level, and their current score. If they are, then 
   * the player object is reset, amount of targets reset, lives reset, and the new mirrors set.
   */
  public void checkAdvance ()
  {
    if (isLive == true && score >= previousScore + 20 && level < 3)
    {
      previousScore = score;
      targets = new ArrayList <Target> ();
      targets.add (new Target (40, 40, assignImage ()));
      p.setAngle (0);
      angleDisplay.setText ("Current Angle: " + Integer.toString (p.getAngle ()));
      p.move (((int) (playerZone.getX () + playerZone.getWidth () / 2)) - (p.getX () + p.getWidth ()/ 2), ((int) (playerZone.getY () + playerZone.getHeight () / 2)) - (p.getY () + p.getHeight () / 2) );
      level ++;
      levelDisplay.setText ("Level: " + Integer.toString (level));
      addMirrors ();
      
      repaint ();
      generalDialog (false);
    }
  }
  
  /** 
   * Compares the title of the action the user performed with a String and determines a response to their action. Clicking the "fire" buttons will call the fire () method.
   * 
   * @param e Reference, points to the ActionEvent class.
   */
  public void actionPerformed (ActionEvent e)
  {
    if (e.getActionCommand ().equals ("Fire"))
    {
      fire ();
    }
    else
    {
      pauseDialog (timer.isRunning ());
    }
  }
  
  /**
   * Draws all the graphics relating to the game portion of this panel, such as all the targets, player, mirrors, background, lasers, barriers, end zones, and player zones.
   * 
   * @param g Reference, points to the Graphics class
   * @param g2 Reference, points to the Graphics2D class to acquire more control over the graphics being drawn
   * @param transform Reference, points to the AffineTransform class, used for the transformations needed for drawing the rotated player
   * @param old Reference, points to the AffineTransform class, used to store the transformations applied to the Graphics2D before drawing the player
   */
  public void paintComponent (Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    
    // game background
    g.drawImage (gameBackground, 0, 0, 600, 600, this);
    
    // barrier
    g.drawImage (barrier.getImage (), barrier.getX (), barrier.getY (), barrier.getWidth (), barrier.getHeight (), this);
    
    // end zone
    g.drawImage (endZoneImage, (int) endZone.getX (), (int) endZone.getY (), (int) endZone.getWidth (), (int) endZone.getHeight(), this);
    
    // mirrors
    g.drawImage (leftMirror.getImage (), leftMirror.getX (), leftMirror.getY (), leftMirror.getWidth (), leftMirror.getHeight (), this);
    g.drawImage (rightMirror.getImage (), rightMirror.getX (), rightMirror.getY (), rightMirror.getWidth (), rightMirror.getHeight (), this);
    
    // user control background
    g.drawImage (userBackground, 0, 0, 1024, 720, this);
    
    // player zone
    g.drawImage (playerZoneImage, (int) playerZone.getX (), (int) playerZone.getY (), (int) playerZone.getWidth (), (int) playerZone.getHeight(), this);
    
    // lasers
    g.setColor (laserColor);
    for (Laser laser : lasers)
    {
      g.drawLine (laser.getX (), laser.getY (), laser.getX2 (), laser.getY2 ());
    }
    
    // player
    AffineTransform transform = new AffineTransform();
    int theta = p.getAngle ();
    transform.rotate(Math.toRadians(theta), p.getX() + p.getWidth ()/2, p.getY() + p.getHeight ()/2);
    AffineTransform old = g2.getTransform();
    g2.transform(transform);
    g2.drawImage (p.getImage (), p.getX (), p.getY (), p.getWidth (), p.getHeight (), this);
    g2.setTransform(old);
    
    // targets
    g.setColor (Color.BLACK);
    for (Target target : targets)
    {
      g.drawImage(target.getImage(), target.getX (),target.getY (), target.getWidth (), target.getHeight (), this);
    }
  }
}