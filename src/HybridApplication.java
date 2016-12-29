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
	private Browser browser;

	private boolean running;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		// create the browser
		browser = new Browser();
		// create scene
		scene = new Scene(
			browser,
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
			webEngine.setJavaScriptEnabled(true);
			// get html
			String html = HybridApplication
				.class
				.getResource("main-menu.html")
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
									"java",
									new JavaApp()
								);
							}
						}
					}
				);
			// load page
			webEngine.load(html);
			// add to JavaFX app
			getChildren().add(webView);
		}

		public class JavaApp {
			public void launchBrickBreaker() {
				// get html
				String html = HybridApplication
					.class
					.getResource("brick-breaker.html")
					.toExternalForm();
				// load page
				webEngine.load(html);
			}

			public void exit() {
				// todo add platform.exit()
			}

			public String getStateJSON() {
				return "test";
			}

			public void print(String out) {
				System.out.println(out);
			}
			// >>this is where the getFrame() method will go<<
		}
	}
}
