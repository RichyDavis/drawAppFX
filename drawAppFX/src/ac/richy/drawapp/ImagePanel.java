package ac.richy.drawapp;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class ImagePanel
{
	private Group image;
	private Rectangle graphics;

	public ImagePanel(int width, int height)
	{
		setImageSize(width, height);
	}

	private void setImageSize(int width, int height)
	{
		image = new Group();
		graphics = new Rectangle(0,0,width,height);
		image.getChildren().add(graphics);
		clear(Color.WHITE);
	}

	public void setBackgroundColour(Paint colour)
	{
		graphics.setFill(colour);
	}

	public void clear(Paint colour)
	{
		setBackgroundColour(colour);
		image.getChildren().remove(graphics);
		image.getChildren().add(graphics);
	}

	public void setColour(Paint colour)
	{
		Node node = image.getChildren().get(image.getChildren().size()-1);
		if (node instanceof Shape) {
			if (((Shape) node).getFill() == null && ((Shape) node).getStroke() != null)
				((Shape) node).setStroke(colour);
			else
				((Shape) node).setFill(colour);
		}
	}

	public void setStroke(Paint colour)
	{
		Node node = image.getChildren().get(image.getChildren().size()-1);
		if (node instanceof Shape) {
			((Shape) node).setStroke(colour);
		}
	}

	public void drawLine(int x1, int y1, int x2, int y2)
	{
		Line line = new Line(x1, y1, x2, y2);
		image.getChildren().add(line);
	}

	public void drawRect(int x1, int y1, int x2, int y2) {
		Rectangle rectangle = new Rectangle(x1, y1, x2, y2);
		rectangle.setStroke(Color.BLACK);
		rectangle.setStrokeType(StrokeType.INSIDE);
		rectangle.setFill(null);
		image.getChildren().add(rectangle);
	}
	
	public void fillRect(int x1, int y1, int x2, int y2) {
		Rectangle rectangle = new Rectangle(x1, y1, x2, y2);
		image.getChildren().add(rectangle);
	}

	public void drawString(int x, int y, String s)
	{
		Text text = new Text(x,y,s);
		image.getChildren().add(text);
	}

	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		//Arc is the only one that will produce a different result to the original DrawApp
		Arc arc = new Arc(x, y, width, height, startAngle, arcAngle);
		arc.setStroke(Color.BLACK);
		arc.setStrokeType(StrokeType.INSIDE);
		arc.setFill(null);
		image.getChildren().add(arc);
	}

	public void drawOval(int x, int y, int width, int height)
	{
		Ellipse ellipse = new Ellipse((x+width/2),(y+height/2),(width/2)+1,(height/2)+1);
		ellipse.setStroke(Color.BLACK);
		ellipse.setStrokeType(StrokeType.INSIDE);
		ellipse.setFill(null);
		image.getChildren().add(ellipse);
	}

	public Group getImage()
	{
		return image;
	}
}
