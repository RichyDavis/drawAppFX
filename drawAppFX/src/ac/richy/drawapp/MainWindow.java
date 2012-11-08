package ac.richy.drawapp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener
{
  public static final int DEFAULT_WIDTH = 500;
  public static final int DEFAULT_HEIGHT = 300;

  private int width;
  private int height;

  private ImagePanel imagePanel;
  private JTextArea messageView;
  private JButton quitButton;

  public MainWindow()
  {
    this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  public MainWindow(int width, int height)
  {
    super("Draw App");
    this.width = width;
    this.height = height;
    buildGUI();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  private void buildGUI()
  {
    JPanel backPanel = new JPanel();
    backPanel.setLayout(new BorderLayout());
    imagePanel = new ImagePanel(width, height);
    backPanel.add(imagePanel,BorderLayout.CENTER);

    messageView = new JTextArea();
    messageView.setRows(6);
    messageView.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(messageView);

    JPanel lowerPanel = new JPanel();
    lowerPanel.setLayout(new BorderLayout());
    lowerPanel.add(scrollPane,BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    quitButton = new JButton("Close Window");
    buttonPanel.add(quitButton);
    quitButton.addActionListener(this);
    lowerPanel.add(buttonPanel,BorderLayout.SOUTH);

    backPanel.add(lowerPanel,BorderLayout.SOUTH);
    this.add(backPanel);
  }

  public ImagePanel getImagePanel()
  {
    return imagePanel;
  }

  public void postMessage(final String s)
  {
     SwingUtilities.invokeLater(
        new Runnable()
        {
          public void run()
          {
            messageView.append(s);
            messageView.repaint();
          }
        });
  }

  public void actionPerformed(ActionEvent actionEvent)
  {
    setVisible(false);
    dispose();
    System.exit(0);
  }
}