/**
 * The driver class for the splash screen and main menu.
 * <p>
 * @author Kenneth Tran
 * @version V1.0.0, 10 June, 2014
 */
public class MainProgram
{
  /** 
   * The main method. Static method which an array of strings can be sent to the program to help with program initialization. 
   * 
   * @param args String array, contains the supplied command-line arguments
   */
  public static void main (String[]args){
    new SplashScreen();
    new MainFrame();
  }
}