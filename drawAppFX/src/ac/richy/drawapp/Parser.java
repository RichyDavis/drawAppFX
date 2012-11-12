package ac.richy.drawapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class Parser
{
  private BufferedReader reader;
  private ImagePanel image;
  private MainWindow frame;
  private ArrayList<String> commands = new ArrayList<String>();
  private int index = 0;
  private boolean step = false;
  private boolean stepNotif = false;

  public Parser(Reader reader, ImagePanel image, MainWindow frame) {
    this.reader = new BufferedReader(reader);
    this.image = image;
    this.frame = frame;
    initButtons();
  }

  public void parse() throws ParseException {
	  try {
		  if (reader.ready()) {
			  String line = reader.readLine();
			  while (line != null) {
				  commands.add(line);
				  line = reader.readLine();
			  }
		  }
		  else throw new ParseException("Blocked or invalid input");
	  } catch (IOException e) {
		  frame.postMessage("Parse failed.\n");
		  return;
	  }
	  processCommands();
  }

  public void processCommands() {
	  try {
		  if (!commands.isEmpty()) {
			  while (step == false && index < commands.size()) {
				  parseLine(commands.get(index));
				  index++;
			  }
			  if (index >= commands.size())
				  frame.postMessage("Drawing Complete. " +
						  "No further drawing commands available.\n");
		  }
	  } catch (ParseException e) {
		  frame.postMessage("Parse Exception: " + e.getMessage() +"\n");
		  return;
	  }
  }

  private void parseLine(String line) throws ParseException {
	  if (line.length() < 2) return;
	  String command = line.substring(0, 2);
	  if (command.equals("ST")) { 
		  step = true; 
		  if (!stepNotif)
			  frame.postMessage("Input: Step through feature is enabled.\n");
		  stepNotif = true;
		  return; 
	  }
	  if (command.equals("SF")) { step = false; return; }
	  if (command.equals("DL")) { drawLine(line.substring(2,line.length())); return; }
	  if (command.equals("DR")) { drawRect(line.substring(2, line.length())); return; }
	  if (command.equals("FR")) { fillRect(line.substring(2, line.length())); return; }
	  if (command.equals("SC")) { setColour(line.substring(3, line.length())); return; }
	  if (command.equals("DS")) { drawString(line.substring(3, line.length())); return; }
	  if (command.equals("DA")) { drawArc(line.substring(2, line.length())); return; }
	  if (command.equals("DO")) { drawOval(line.substring(2, line.length())); return; }
	  throw new ParseException("Unknown drawing command \""+command+"\" of line \""+line+"\"");
  }

  private void drawLine(String args) throws ParseException {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    image.drawLine(x1,y1,x2,y2);
  }

  private void drawRect(String args) throws ParseException {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    image.drawRect(x1, y1, x2, y2);
  }

  private void fillRect(String args) throws ParseException {
    int x1 = 0;
    int y1 = 0;
    int x2 = 0;
    int y2 = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    x2 = getInteger(tokenizer);
    y2 = getInteger(tokenizer);
    image.fillRect(x1, y1, x2, y2);
  }

  private void drawArc(String args) throws ParseException {
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    int startAngle = 0;
    int arcAngle = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x = getInteger(tokenizer);
    y = getInteger(tokenizer);
    width = getInteger(tokenizer);
    height = getInteger(tokenizer);
    startAngle = getInteger(tokenizer);
    arcAngle = getInteger(tokenizer);
    image.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  private void drawOval(String args) throws ParseException {
    int x1 = 0;
    int y1 = 0;
    int width = 0;
    int height = 0;

    StringTokenizer tokenizer = new StringTokenizer(args);
    x1 = getInteger(tokenizer);
    y1 = getInteger(tokenizer);
    width = getInteger(tokenizer);
    height = getInteger(tokenizer);
    image.drawOval(x1, y1, width, height);
  }

  private void drawString(String args) throws ParseException {
    int x = 0;
    int y = 0 ;
    String s = "";
    StringTokenizer tokenizer = new StringTokenizer(args);
    x = getInteger(tokenizer);
    y = getInteger(tokenizer);
    int position = args.indexOf("@");
    if (position == -1) throw new ParseException("DrawString string is missing");
    s = args.substring(position+1,args.length());
    image.drawString(x,y,s);
  }

  private void setColour(String colourName) throws ParseException {
    if (colourName.equals("black")) { image.setColour(Color.BLACK); return;}
    if (colourName.equals("blue")) { image.setColour(Color.BLUE); return;}
    if (colourName.equals("cyan")) { image.setColour(Color.CYAN); return;}
    if (colourName.equals("darkgray")) { image.setColour(Color.DARKGRAY); return;}
    if (colourName.equals("gray")) { image.setColour(Color.GRAY); return;}
    if (colourName.equals("green")) { image.setColour(Color.GREEN); return;}
    if (colourName.equals("lightgray")) { image.setColour(Color.LIGHTGRAY); return;}
    if (colourName.equals("magenta")) { image.setColour(Color.MAGENTA); return;}
    if (colourName.equals("orange")) { image.setColour(Color.ORANGE); return;}
    if (colourName.equals("pink")) { image.setColour(Color.PINK); return;}
    if (colourName.equals("red")) { image.setColour(Color.RED); return;}
    if (colourName.equals("white")) { image.setColour(Color.WHITE); return;}
    if (colourName.equals("yellow")) { image.setColour(Color.YELLOW); return;}
    throw new ParseException("Invalid colour name \"" + colourName + "\"");
  }

  public void initButtons() {
	  frame.getQuitButton().setOnAction(new EventHandler<ActionEvent>() {
		  @Override public void handle(ActionEvent e) {
			  frame.getStage().close();
		  }
	  });

	  frame.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
		  @Override public void handle(ActionEvent e) {
			  try {
				  if (step) {
					  if (!(index < commands.size())) {
						  frame.postMessage(commands.size() + "\n");
						  parseLine(commands.get(0));
						  index++;
					  }
					  else {
						  frame.postMessage("Drawing Complete. " +
								  "No further drawing commands available.\n");
					  }
				  }
				  else {
					  if (!stepNotif)
						  frame.postMessage("Input: Step through has not been enabled.\n");
					  processCommands();
				  }
			  } catch (ParseException e1) {
				  frame.postMessage("Parse Exception: " + e1.getMessage() +"\n");
				  return;
			  }
		  }
	  });
  }

  private int getInteger(StringTokenizer tokenizer) throws ParseException {
    if (tokenizer.hasMoreTokens())
      return Integer.parseInt(tokenizer.nextToken());
    else
      throw new ParseException("Missing Integer value on input command");
  }
  
  public void resetIndex() {
	  index = 0;
  }
}
