package ac.richy.drawapp;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class ImagePanel
{
	private MainWindow frame;
	Pane pane;
	private Canvas canvas;
	private Canvas backgroundcanvas;
	private GraphicsContext view;
	private GraphicsContext backgroundview;
	private boolean turtlemode = false;
	private int turtleangle = 0;
	private int turtleX = 0;
	private int turtleY = 0;

	public ImagePanel(MainWindow frame, int width, int height)
	{
		this.frame = frame;
		pane = new Pane();
		canvas = new Canvas(width,height);
		view = canvas.getGraphicsContext2D();
		backgroundcanvas = new Canvas(width,height);
		backgroundview = backgroundcanvas.getGraphicsContext2D();
		pane.getChildren().addAll(backgroundcanvas,canvas);
		setBackgroundColour(Color.WHITE);
	}

	public void setBackgroundSize(int width, int height)
	{
		canvas.setWidth(width);
		canvas.setHeight(height);
		backgroundcanvas.setWidth(width);
		backgroundcanvas.setHeight(height);
		setBackgroundColour(backgroundview.getFill());
	}

	public void setBackgroundColour(Paint colour)
	{
		backgroundview.setFill(colour);
		backgroundview.fillRect(0, 0, backgroundcanvas.getWidth(),
				backgroundcanvas.getHeight());
	}

	public void clear(Paint colour)
	{
		Paint prev_colour = view.getFill();
		setColour(colour);
		view.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		setColour(prev_colour);
	}
	
	public void setColour(Paint colour)
	{
		view.setFill(colour);
		view.setStroke(colour);
	}
	
	public void setGradient(Color colour1, Color colour2, int x, int y) {
		
		Stop stops[] = {new Stop(0d,colour1), new Stop(1d,colour2)};
		view.setFill(new LinearGradient(0,0,x,y,true,CycleMethod.NO_CYCLE,stops));
	}
	
	public void setRadialGradient(Color colour1, Color colour2) {
		
		Stop stops[] = {new Stop(0d,colour1), new Stop(1d,colour2)};
		view.setFill(new RadialGradient(0,0,0.5,0.5,0.5,true,CycleMethod.NO_CYCLE,stops));
	}

	public void setLineWidth(int width)
	{
		view.setLineWidth(width);
	}

	public void drawLine(int x1, int y1, int x2, int y2)
	{
		view.strokeLine(x1, y1, x2, y2);
	}

	public void drawRect(int x1, int y1, int x2, int y2) {
		view.strokeRect(x1, y1, x2, y2);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2) {
		view.fillRect(x1, y1, x2, y2);
	}

	public void drawString(int x, int y, String s)
	{
		view.strokeText(s, x, y);
	}

	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		view.strokeArc(x, y, width, height, startAngle, arcAngle, ArcType.OPEN);
	}

	public void drawOval(int x, int y, int width, int height)
	{
		view.strokeOval(x, y, width, height);
	}
	
	public void fillOval(int x, int y, int width, int height)
	{
		view.fillOval(x, y, width, height);
	}
	
	public void drawImage(int x, int y, int width, int height, String filename) {
		Image image = new Image(filename);
		view.drawImage(image, x, y, width, height);
	}

	public void turtleModeOn(int x, int y) {
		turtlemode = true;
		if (!turtlemode)
			frame.postMessage("Turtle mode is enabled.\n");
		try {
			turtleSetPosition(x,y);
		} catch (TurtleModeException e) {
			frame.postMessage("Error activating turtle mode: cursor not repositioned\n");
		}
	}
	
	public void turtleModeOff() {
		if (turtlemode)
			frame.postMessage("Turtle mode is disabled.\n");
		turtlemode = false;
	}

	public void turtleForward(int distance) throws TurtleModeException {
		if (turtlemode) {
			int prevX = turtleX;
			int prevY = turtleY;
			switch (turtleangle) {
			case (0) : turtleX += distance; break;
			case (90) : turtleY += distance; break;
			case (180) : turtleX -= distance; break;
			case (270) : turtleY -= distance; break;
			}
			if (turtleangle > 0 && turtleangle < 90) {
				turtleX += distance*Math.sin(turtleangle);
				turtleY -= distance*Math.cos(turtleangle);
			} 
			else if (turtleangle > 90 && turtleangle < 180) {
				turtleX -= distance*Math.cos(turtleangle);
				turtleY -= distance*Math.sin(turtleangle);
			}
			else if (turtleangle > 180 && turtleangle < 270) {

				turtleX -= distance*Math.sin(turtleangle);
				turtleY += distance*Math.cos(turtleangle);
			}
			else if (turtleangle > 270) {
				turtleX += distance*Math.cos(turtleangle);
				turtleY += distance*Math.sin(turtleangle);
			}
			drawLine(prevX, prevY, turtleX, turtleY);
			postCursorPosition();
		}
		else throw new TurtleModeException("Turtle Mode is disabled");
	}

	public void turtleTurn(int angle) throws TurtleModeException {
		if (turtlemode) {
			turtleangle = (turtleangle + angle) % 360;
			if (turtleangle < 0)
				turtleangle = 360 + turtleangle;
			postCursorPosition();
		}
		else throw new TurtleModeException("Turtle Mode is disabled");
	}

	public void turtleSetAngle(int angle) throws TurtleModeException {
		if (turtlemode) {
			turtleangle = angle % 360;
			if (turtleangle < 0)
				turtleangle = 360 + turtleangle;
			postCursorPosition();
		}
		else throw new TurtleModeException("Turtle Mode is disabled");
	}
	
	public void turtleSetPosition(int x, int y) throws TurtleModeException {
		if (turtlemode) {
			turtleX = x;
			turtleY = y;
			postCursorPosition();
		}
		else throw new TurtleModeException("Turtle Mode is disabled");
	}

	public void postCursorPosition() {
		frame.postMessage("Cursor is at " + turtleX + ", "
				+ turtleY + " with angle " + turtleangle + "\n");
	}
	
	public Pane getPane() {
		return pane;
	}

	public void saveImage() {
		try {
			SimpleDateFormat dformat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			File output = new File("./javaFXimage_" + dformat.format(new Date()) + ".png");
			RenderedImage image = SwingFXUtils.fromFXImage(canvas.snapshot(null,null),null);
			ImageIO.write(image,"png",output);
		} catch (IOException e) {
			frame.postMessage("Save image failed - IOException: failure writing image to file\n");
		}
	} 
}
