package ac.richy.drawapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainWindow
{
	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 300;
	private int width;
	private int height;

	private Stage stage;
	private ImagePanel imagePanel;
	private TextArea messageView;
	private Button quitButton;
	private Button nextButton;

	public MainWindow(Stage stage)
	{
		this(stage, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public MainWindow(Stage stage, int width, int height)
	{
		this.stage = stage;
		this.width = width;
		this.height = height;
		buildGUI(stage);
	}

	public void buildGUI(Stage stage) {
		stage.setTitle("Draw App");
		AnchorPane anchorpane = new AnchorPane();
		Scene scene = new Scene(anchorpane, width, height, Color.WHITESMOKE);
		imagePanel = new ImagePanel(width,height);

		quitButton = new Button("Close");
		quitButton.setPrefSize(100, 20);
		nextButton = new Button("Next");
		nextButton.setPrefSize(100, 20);
		HBox hboxButtons = new HBox();
		hboxButtons.setPadding(new Insets(5d, 6d, 5d, 6d));
		hboxButtons.setSpacing(10d);
		hboxButtons.setAlignment(Pos.CENTER);
		hboxButtons.getChildren().addAll(quitButton,nextButton);

		messageView = new TextArea();
		messageView.setPrefHeight(140d);
		messageView.setEditable(false);

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10d));
		vbox.setSpacing(8d);
		vbox.setStyle("-fx-background-color: #C6C6C6;");
		vbox.getChildren().addAll(messageView, hboxButtons);

		AnchorPane.setBottomAnchor(vbox, 0d);
		AnchorPane.setLeftAnchor(vbox, 0d);
		AnchorPane.setRightAnchor(vbox, 0d);
		anchorpane.getChildren().addAll(imagePanel.getImage(),vbox);

		stage.setScene(scene);
		/* Annoyingly, stage width and height include size of any decorations, so 15-30
		 * has been added. Note that decoration sizes are not guaranteed to be the same
		 *  for all users.
		 */
		stage.setWidth(width + 15);
		stage.setHeight(height+ 30 + quitButton.getPrefHeight() +
				hboxButtons.getPadding().getTop() +
				hboxButtons.getPadding().getBottom() +
				messageView.getPrefHeight() + vbox.getPadding().getTop() +
				vbox.getPadding().getBottom() + vbox.getSpacing());
		stage.show();
	}

	public ImagePanel getImagePanel()
	{
		return imagePanel;
	}
	
	public Button getQuitButton()
	{
		return quitButton;
	}
	
	public Button getNextButton()
	{
		return nextButton;
	}

	public Stage getStage()
	{
		return stage;
	}

	public void postMessage(final String s)
	{
		messageView.appendText(s);
	}
	
	public void clearMessageView()
	{
		messageView.clear();
	}
}