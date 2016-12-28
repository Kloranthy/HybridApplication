import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class HybridApplication extends Application {
	private Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// create scene
		scene = new Scene(
			new Browser(),
			800,
			600,
			Color.web("#000000")
		);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hybrid Application");
		primaryStage.show();
	}

	class Browser extends Region {
		private final WebView webView = new WebView();
		private final WebEngine webEngine = webView.getEngine();

		public Browser() {
			// todo apply stylesheet
			// get index.html
			String index = HybridApplication
				.class
				.getResource("index.html")
				.toExternalForm();
			// process page loading
			webEngine.getLoadWorker()
				.stateProperty()
				.addListener(
					new ChangeListener<Worker.State>() {
						@Override
						public void changed(
							ObservableValue<? extends Worker.State> observable,
							Worker.State oldState,
							Worker.State newState
						) {
							if (newState == Worker.State.SUCCEEDED) {
								JSObject window = (JSObject) webEngine
									.executeScript("window");
								window.setMember(
									"app",
									new JavaApp()
								);
							}
						}
					}
				);
			// load page
			webEngine.load(index);
			// add to JavaFX app
			getChildren().add(webView);
		}

		public class JavaApp {
			public void hello() {
				System.out.println("hello world");
			}
			// >>this is where the getFrame() method will go<<
		}
	}
}
