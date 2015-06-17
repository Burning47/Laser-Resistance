import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

import java.util.ArrayList;
/**
 * This class creates the lesson panel where the user may scroll between 3 slides by clicking buttons.
 * Each of the slides contain information regarding optics. The user may return to the main menu from any
 * slide by clicking the main menu button. 
 * <p> 
 * @author Kenneth Tran
 * @version V1.0.0, 10 June, 2014
 */
public class Lesson extends JPanel implements ActionListener
{
  /**
   * sl reference for SpringLayout, points to the SpringLayout class.
   */
  private SpringLayout sl = new SpringLayout();
  /**
   * slides reference for ArrayList, used to store 3 slides in an array list of ImageIcon objects. 
   */
  private ArrayList<ImageIcon> slides;
  /**
   * slideNum int, stores the value of the slide currently in view.
   */
  private int slideNum;
  /**
   * slide JLabel, stores the JLabel that will be set to show the slides of information.
   */
  private JLabel slide; 
  /**
   * JButton, stores the image for the button that shows a foreward arrow used to navigate to the next slide.
   */
  private JButton nextSlide;
  /**
   * JButton, stores the image for the button that shows a backward arrow used to navigate to the previous slide.
   */
  private JButton prevSlide;
  /**
   * Private MainFrame, which this panel is attached to.
   */
  private MainFrame parent;
  
  /**
   * This constructs the lesson panel, setting up the layout, buttons, labels, and key bindings.
   * <p>
   * @param frame MainFrame, the frame which this class is added to.
   * @param title JLabel, stores the image used for the title. 
   * @param returnMenu JButton, stores the image for the button that indicates it is to be used to return to the main menu.
   */
  public Lesson (MainFrame frame)
  {
    parent = frame;
    sl = new SpringLayout();
    this.setLayout(sl);
    
    slides = new ArrayList<ImageIcon> ();
    for (int x = 1; x < 4; x++){
      slides.add(new ImageIcon ("res/Menus/Lesson/lp" + x +"transf.png"));
    }
    
    slideNum = 0;
    slide = new JLabel (slides.get(slideNum));
    sl.putConstraint (SpringLayout.NORTH, slide, -20, SpringLayout.NORTH , this);
    sl.putConstraint (SpringLayout.WEST, slide, 0, SpringLayout.WEST, this);
    this.add(slide);
    
    JLabel title = new JLabel(new ImageIcon("res/Menus/LessonTitle.png"));
    sl.putConstraint (SpringLayout.NORTH, title, 10, SpringLayout.NORTH , this);
    sl.putConstraint (SpringLayout.WEST, title, 10, SpringLayout.WEST, this);
    this.add(title);
    
    JButton returnMenu = new JButton (new ImageIcon ("res/Menus/mainmenu.png"));
    returnMenu.setBorderPainted(false);
    returnMenu.setContentAreaFilled(false);
    returnMenu.setActionCommand("Menu");
    returnMenu.addActionListener(this);
    returnMenu.setFocusable (false);
    sl.putConstraint (SpringLayout.SOUTH, returnMenu, 0, SpringLayout.SOUTH , this);
    sl.putConstraint (SpringLayout.WEST, returnMenu, 300, SpringLayout.WEST, this);
    add (returnMenu);
    
    nextSlide = new JButton (new ImageIcon ("res/Menus/nextshrink.png"));
    nextSlide.setBorderPainted(false);
    nextSlide.setContentAreaFilled(false);
    nextSlide.setActionCommand("Next");
    nextSlide.addActionListener(this);
    nextSlide.setFocusable (false);
    sl.putConstraint (SpringLayout.SOUTH, nextSlide, 0, SpringLayout.SOUTH , this);
    sl.putConstraint (SpringLayout.WEST, nextSlide, 700, SpringLayout.WEST, this);
    add (nextSlide);
    
    prevSlide = new JButton (new ImageIcon ("res/Menus/backshrink.png"));
    prevSlide.setBorderPainted(false);
    prevSlide.setContentAreaFilled(false);
    prevSlide.setActionCommand("Back");
    prevSlide.addActionListener(this);
    prevSlide.setEnabled (false);
    prevSlide.setFocusable (false);
    sl.putConstraint (SpringLayout.SOUTH, prevSlide, 0, SpringLayout.SOUTH , this);
    sl.putConstraint (SpringLayout.WEST, prevSlide, 124, SpringLayout.WEST, this);
    add (prevSlide);
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK), "Back");
    getActionMap().put("Back", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        if (slideNum > 0)
        {
          if (slideNum == 2)
            nextSlide.setEnabled (true);
          else
            prevSlide.setEnabled (false);
          slideNum--;
        }
        slide.setIcon (slides.get(slideNum));
        repaint();
        revalidate();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, Event.CTRL_MASK), "Next");
    getActionMap().put("Next", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        if (slideNum < 2)
        {
          if (slideNum == 0)
            prevSlide.setEnabled (true);
          else
            nextSlide.setEnabled (false);
          slideNum++;
        }
        slide.setIcon (slides.get(slideNum));
        repaint();
        revalidate();
      }
    });
    
    getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_4, Event.CTRL_MASK), "Menu");
    getActionMap().put("Menu", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        parent.showMenu('t');
      }
    });
  }
  
  /**
   * The purpose of this method is to determine what action is performed based on the button that was pressed.
   * <p>
   * @param ae This is a reference variable for ActionEvent.
   */
  public void actionPerformed(ActionEvent ae)
  {
    if (ae.getActionCommand().equals("Menu"))
    {
      parent.showMenu('t');
    }
    else if (ae.getActionCommand().equals("Next"))
    {
      if (slideNum < 2)
      {
        if (slideNum == 0)
          prevSlide.setEnabled (true);
        else
          nextSlide.setEnabled (false);
        slideNum++;
      }
    }
    else
    {
      if (slideNum > 0)
      {
        if (slideNum == 2)
          nextSlide.setEnabled (true);
        else
          prevSlide.setEnabled (false);
        slideNum--;
      }
    }
    slide.setIcon (slides.get(slideNum));
    this.repaint();
    this.revalidate();
  }
  
  /**
   * Draws a background image.
   * 
   * @param g Reference, points to the Graphics class.
   * @exception IOException Used as a general class of exceptions for failed or interrupted input operations.
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