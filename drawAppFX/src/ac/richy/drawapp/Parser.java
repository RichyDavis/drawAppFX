package ac.richy.drawapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
		} catch (FileNotFoundException e) {
			frame.postMessage("FileNotFound Exception: " + e.getMessage() +"\n");
		}
	}

	private void parseLine(String line) throws ParseException, FileNotFoundException {
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
		if (command.equals("SC")) { setColour(line.substring(2, line.length())); return; }
		if (command.equals("SS")) { setStroke(line.substring(2, line.length())); return; }
		if (command.equals("SG")) { setGradient(line.substring(2, line.length())); return; }
		if (command.equals("SR")) { setRadialGradient(line.substring(2, line.length())); return; }
		if (command.equals("DS")) { drawString(line.substring(3, line.length())); return; }
		if (command.equals("DA")) { drawArc(line.substring(2, line.length())); return; }
		if (command.equals("DO")) { drawOval(line.substring(2, line.length())); return; }
		if (command.equals("DI")) { drawImage(line.substring(3, line.length())); return; }
		if (command.equals("BS")) { setBackgroundSize(line.substring(2, line.length())); return; }
		if (command.equals("PS")) { postString(line.substring(3, line.length())); return; }
		if (command.equals("PC")) { postClear(); return; }
		throw new ParseException("Unknown drawing command \""+command+"\" of line \""+line+"\"");
	}

	private void setBackgroundSize(String args) throws ParseException {
		int x = 0;
		int y = 0;
		StringTokenizer tokenizer = new StringTokenizer(args);
		x = getInteger(tokenizer);
		y = getInteger(tokenizer);
		frame.setWidth(x);
		frame.setHeight(y);
		image.setBackgroundSize(x,y);
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
		if (position == -1) throw new ParseException("DrawString string is missing/invalid");
		s = args.substring(position+1,args.length());
		image.drawString(x,y,s);
	}

	private void drawImage(String args) throws ParseException, FileNotFoundException {
		int x = 0;
		int y = 0 ;
		int width = 0;
		int height = 0;
		String filename = "";
		StringTokenizer tokenizer = new StringTokenizer(args);
		x = getInteger(tokenizer);
		y = getInteger(tokenizer);
		width = getInteger(tokenizer);
		height = getInteger(tokenizer);
		int position = args.indexOf("@");
		if (position == -1) 
			throw new ParseException("DrawImage filename is missing/invalid");
		filename = args.substring(position+1,args.length());

		File file = new File(filename);
		if (file.exists()) image.drawImage(x,y,width,height,filename);
		else throw new FileNotFoundException("DrawImage file \"" + filename +
				"\" not found in drawAppFX directory");
	}

	private void setColour(String args) throws ParseException {
		int position = args.indexOf("@");
		if (position == -1)
			throw new ParseException("setColour colour argument missing/invalid");
		args = args.substring(position+1,args.length());
		image.setColour(getColour(args));
	}

	private void setStroke(String args) throws ParseException {
		int position = args.indexOf("@");
		if (position == -1)
			throw new ParseException("setStroke colour argument missing/invalid");
		args = args.substring(position+1,args.length());
		image.setStroke(getColour(args));
	}

	private void setGradient(String args) throws ParseException {
		int x = 0;
		int y = 0;
		StringTokenizer tokenizer = new StringTokenizer(args);
		x = getInteger(tokenizer);
		y = getInteger(tokenizer);
		int position = args.indexOf("@");
		if (position == -1)
			throw new ParseException("setGradient colour arguments missing/invalid");
		args = args.substring(position+1,args.length());
		String[] colours = new String[2];
		if (args.split(",").length == 2) {
			colours = args.split(",");
			image.setGradient(getColour(colours[0]), getColour(colours[1]),x,y);
		}
		else
			throw new ParseException("Invalid number of linear gradient colour arguments");
	}

	private void setRadialGradient(String args) throws ParseException {
		int position = args.indexOf("@");
		if (position == -1)
			throw new ParseException("setRadialGradient colour arguments missing/invalid");
		args = args.substring(position+1,args.length());
		String[] colours = new String[2];
		if (args.split(",").length == 2) {
			colours = args.split(",");
			image.setRadialGradient(getColour(colours[0]), getColour(colours[1]));
		}
		else
			throw new ParseException("Invalid number of radial gradient colour arguments");
	}

	private Color getColour(String colourName) throws ParseException {
		if (colourName.equals("black")) { return Color.BLACK;}
		if (colourName.equals("blue")) { return Color.BLUE;}
		if (colourName.equals("cyan")) { return Color.CYAN;}
		if (colourName.equals("darkgray")) { return Color.DARKGRAY;}
		if (colourName.equals("gray")) { return Color.GRAY;}
		if (colourName.equals("green")) { return Color.GREEN;}
		if (colourName.equals("lightgray")) { return Color.LIGHTGRAY;}
		if (colourName.equals("magenta")) { return Color.MAGENTA;}
		if (colourName.equals("orange")) { return Color.ORANGE;}
		if (colourName.equals("pink")) { return Color.PINK;}
		if (colourName.equals("red")) { return Color.RED;}
		if (colourName.equals("white")) { return Color.WHITE;}
		if (colourName.equals("yellow")) { return Color.YELLOW;}
		throw new ParseException("Invalid colour name \"" + colourName + "\"");
	}

	private void postString(String args) throws ParseException {
		int position = args.indexOf("@");
		String s = "";
		if (args != null && !args.isEmpty() && position != -1) {
			s = args.substring(position+1,args.length()).concat("\n");
			frame.postMessage(s);
		}
		else throw new ParseException("postString string is missing/invalid");
	}

	private void postClear() {
		frame.clearMessageView();
	}

	public void initButtons() {
		frame.getQuitButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				frame.getStage().close();
			}
		});
		
		frame.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				image.saveImage();
			}
		});

		frame.getNextButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent actionevent) {
				try {
					if (step) {
						if (index < commands.size()) {
							parseLine(commands.get(index));
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
				} catch (ParseException e) {
					frame.postMessage("Parse Exception: " + e.getMessage() +"\n");
					return;
				} catch (FileNotFoundException e) {
					frame.postMessage("FileNotFound Exception: " + e.getMessage() +"\n");
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
