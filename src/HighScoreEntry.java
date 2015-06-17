/**
 * This is a data class used to store data of a single high score entry.
 * <p>
 * @author Kenneth Tran
 * @version V1.0.0, 10 June, 2014
 */
public class HighScoreEntry{
  /**
   * name String, stores the name of a single high score entry.
   */
  private String name;
  /**
   * score int, stores the score of a single high score entry.
   */
  private int score;
  /**
   * level int, stores the level of a single high score entry.
   */
  private int level;
  
  /**
   * Accessor method for the name.
   * <p>
   * @return name String, the name stored in the high score entry.
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * Accessor method for the score.
   * <p>
   * @return score int, the score stored in the high score entry.
   */
  public int getScore()
  {
    return score;
  }
  
  /**
   * Accessor method for the level.
   * <p>
   * @return score int, the score stored in the high score entry.
   */
  public int getLevel()
  {
    return level;
  }
  
  /**
   * Constructor used to create a high score entry with a name and score. 
   * <p>
   * @param name String, stores the name of the high score entry created. 
   * @param score int, stores the score of the high score entry created.
   * @param level int, stores the level of the high score entry created.
   */
  public HighScoreEntry (String name, int score, int level)
  {
    this.name = name;
    this.score = score;
    this.level = level;
  }
}