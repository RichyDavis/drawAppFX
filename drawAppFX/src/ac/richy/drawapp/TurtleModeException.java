package ac.richy.drawapp;

@SuppressWarnings("serial")
public class TurtleModeException extends Exception
{
  public TurtleModeException()
  {
    super("Exception during turtle mode command");
  }

  public TurtleModeException(String message)
  {
    super(message);
  }
}