package ac.richy.drawapp;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main extends Application
{

	@Override
	public void start(Stage stage) throws Exception {
		final MainWindow main = new MainWindow(stage);

		ImagePanel imagePanel = main.getImagePanel();
		Reader reader = new InputStreamReader(System.in);
		Parser parser = new Parser(reader,imagePanel,main);
		parser.parse();
	}

	public static void main(String[] args) {launch(args);}
}
