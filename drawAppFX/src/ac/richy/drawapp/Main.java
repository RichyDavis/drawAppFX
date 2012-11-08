package ac.richy.drawapp;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class Main
{
  public static void main(String[] args)
  {
    final MainWindow main = new MainWindow();

    SwingUtilities.invokeLater(
    new Runnable()
    {
      public void run()
      {
        ImagePanel imagePanel = main.getImagePanel();
        Reader reader = new InputStreamReader(System.in);
        Parser parser = new Parser(reader,imagePanel,main);
        parser.parse();
        imagePanel.repaint();
      }
    }
  );

  }
}
