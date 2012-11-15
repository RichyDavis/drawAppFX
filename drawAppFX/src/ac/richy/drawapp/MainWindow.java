package ac.richy.drawapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	public static final double DEFAULT_WIDTH = 500d;
	public static final double DEFAULT_HEIGHT = 300d;
	private double width;
	private double height;

	private Stage stage;
	private ImagePanel imagePanel;
	private TextArea messageView;
	private Button quitButton;
	private Button nextButton;
	private Button saveButton;
	private HBox hboxButtons;
	private VBox vbox;

	public MainWindow(Stage stage)
	{
		this(stage, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public MainWindow(Stage stage, double width, double height)
	{
		this.stage = stage;
		this.width = width;
		this.height = height;
		buildGUI(stage);
	}

	private void buildGUI(Stage stage) {
		stage.setTitle("Draw App");
		AnchorPane anchorpane = new AnchorPane();
		Scene scene = new Scene(anchorpane, width, height, Color.WHITESMOKE);
		imagePanel = new ImagePanel(this,width,height);

		quitButton = new Button("Close");
		quitButton.setPrefSize(100, 20);
		saveButton = new Button("Save");
		saveButton.setPrefSize(100, 20);
		nextButton = new Button("Next");
		nextButton.setPrefSize(100, 20);
		hboxButtons = new HBox();
		hboxButtons.setPadding(new Insets(5d, 6d, 5d, 6d));
		hboxButtons.setSpacing(10d);
		hboxButtons.setAlignment(Pos.CENTER);
		hboxButtons.getChildren().addAll(quitButton,saveButton,nextButton);

		messageView = new TextArea();
		messageView.setPrefHeight(140d);
		messageView.setEditable(false);

		vbox = new VBox();
		vbox.setPadding(new Insets(10d));
		vbox.setSpacing(8d);
		vbox.setStyle("-fx-background-color: #C6C6C6;");
		vbox.getChildren().addAll(messageView, hboxButtons);

		AnchorPane.setBottomAnchor(vbox, 0d);
		AnchorPane.setLeftAnchor(vbox, 0d);
		AnchorPane.setRightAnchor(vbox, 0d);
		anchorpane.getChildren().addAll(imagePanel.getPane(),vbox);

		stage.setScene(scene);
		stage.show();
		setWidth(width);
		setHeight(height);
		stage.show();
		initButtons();
	}
	
	private void initButtons() {
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stage.close();
			}
		});
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				imagePanel.saveImage();
			}
		});
	}

	public void setWidth(double width) {
		width += (stage.getScene().getX()*2);
		stage.setWidth(width);
		this.width = width;
	}

	public void setHeight(double height) {
		height += (stage.getScene().getY() + stage.getScene().getX() +
				quitButton.getPrefHeight() + hboxButtons.getPadding().getTop() +
				hboxButtons.getPadding().getBottom() +
				messageView.getPrefHeight() + vbox.getPadding().getTop() +
				vbox.getPadding().getBottom() + vbox.getSpacing() + 1);
		stage.setHeight(height);
		this.height = height;
	}

	public ImagePanel getImagePanel()
	{
		return imagePanel;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
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