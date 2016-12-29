import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.LinkedList;
import java.util.List;

public class RenderModule {

	public static void main(String[] args) {
		String frameJSON = "{arena:{width:800,height:600}," +
			"ball:{radius:10,x:300,y:300,dx:6,dy:6}," +
			"paddle:{width:80,height:10,x:400,y:585,dx:10}," +
			"bricks:[" +
			"brick:{width:80,height:20,x:680,y:40,isBroken:0}," +
			"brick:{width:80,height:20,x:10,y:10,isBroken:0}" +
			"]," +
			"explosions:[" +
			"explosion:{radius:30,x:660,y:50,status:1}," +
			"explosion:{radius:30,x:300,y:200,status:2}" +
			"]," +
			"score:5," +
			"lives:2" +
			"}";
		RenderModule renderModule = new RenderModule();
		renderModule.drawFrame(frameJSON);
	}

	public Canvas test() {
		String frameJSON = "{arena:{width:800,height:600}," +
			"ball:{radius:10,x:300,y:300,dx:6,dy:6}," +
			"paddle:{width:80,height:10,x:400,y:585,dx:10}," +
			"bricks:[" +
			"brick:{width:80,height:20,x:680,y:40,isBroken:0}," +
			"brick:{width:80,height:20,x:10,y:10,isBroken:0}" +
			"]," +
			"explosions:[" +
			"explosion:{radius:30,x:660,y:50,status:1}," +
			"explosion:{radius:30,x:300,y:200,status:2}" +
			"]," +
			"score:5," +
			"lives:2" +
			"}";
		return drawFrame(frameJSON);
	}

	public Canvas drawFrame(String frameJSON) {
		// declare variables
		int start;
		int end;
		String arenaJSON;
		String ballJSON;
		String paddleJSON;
		String bricksJSON;
		List<String> brickJSONList = new LinkedList<String>();
		String explosionsJSON;
		List<String> explosionJSONList = new LinkedList<String>();
		String scoreJSON;
		String livesJSON;
		// parse frame into parts
		// get arena json from frame json
		start = frameJSON.indexOf("arena:{");
		end = frameJSON.indexOf("}", start) + 1;
		arenaJSON = frameJSON.substring(start, end);
		System.out.println(arenaJSON);
		// get ball json from frame json
		start = frameJSON.indexOf("ball:{", end);
		end = frameJSON.indexOf("}", start) + 1;
		ballJSON = frameJSON.substring(start, end);
		System.out.println(ballJSON);
		// get paddle json from frame json
		start = frameJSON.indexOf("paddle:{", end);
		end = frameJSON.indexOf("}", start) + 1;
		paddleJSON = frameJSON.substring(start, end);
		System.out.println(paddleJSON);
		// get brick array json from frame json
		start = frameJSON.indexOf("bricks:[", end);
		end = frameJSON.indexOf("]", start) + 1;
		bricksJSON = frameJSON.substring(start, end);
		System.out.println(bricksJSON);
		// get explosions array json from frame json
		start = frameJSON.indexOf("explosions:[", end);
		end = frameJSON.indexOf("]", start) + 1;
		explosionsJSON = frameJSON.substring(start, end);
		System.out.println(explosionsJSON);
		// get score json from frame json
		start = frameJSON.indexOf("score:", end);
		end = frameJSON.indexOf(",", start);
		scoreJSON = frameJSON.substring(start, end);
		System.out.println(scoreJSON);
		// get lives json from frame json
		start = frameJSON.indexOf("lives:", end);
		end = frameJSON.indexOf("}", start);
		livesJSON = frameJSON.substring(start, end);
		System.out.println(livesJSON);
		// get bricks from bricks array
		if (bricksJSON.contains("brick:{")) {
			start = 0;
			end = 0;
			String brickJSON;
			while (bricksJSON.indexOf("brick:{", end) > 0) {
				start = bricksJSON.indexOf("brick:{", end);
				end = bricksJSON.indexOf("}", start) + 1;
				brickJSON = bricksJSON.substring(start, end);
				System.out.println(brickJSON);
				brickJSONList.add(brickJSON);
			}
		}
		else {
			System.out.println("no bricks");
		}
		// get explosions from explosions array
		if (explosionsJSON.contains("explosion:{")) {
			start = 0;
			end = 0;
			String explosionJSON;
			while (explosionsJSON.indexOf("explosion:{", end) > 0) {
				start = explosionsJSON.indexOf("explosion:{", end);
				end = explosionsJSON.indexOf("}", start) + 1;
				explosionJSON = explosionsJSON.substring(start, end);
				System.out.println(explosionJSON);
				explosionJSONList.add(explosionJSON);
			}
		}
		else {
			System.out.println("no explosions");
		}
		// parse parts? todo convert this to batch
		// draw parts on sub canvases todo convert this to batch
		Canvas arenaCanvas = drawArena(arenaJSON);
		SubCanvas ballCanvas = drawBall(ballJSON);
		SubCanvas paddleCanvas = drawPaddle(paddleJSON);
		List<SubCanvas> brickCanvasList = new LinkedList<SubCanvas>();
		for (String brickJSON : brickJSONList) {
			SubCanvas brickCanvas = drawBrick(brickJSON);
		}
		// draw sub canvases on frame canvas
		Canvas frameCanvas = arenaCanvas;
		GraphicsContext graphicsContext = frameCanvas.getGraphicsContext2D();
		graphicsContext.drawImage(
			ballCanvas.getCanvas().snapshot(null, null),
			ballCanvas.getX(),
			ballCanvas.getY(),
			ballCanvas.getCanvas().getWidth(),
			ballCanvas.getCanvas().getWidth()
		);
		// do something with frame canvas to make it available to ui
		return frameCanvas;
	}

	private Canvas drawArena(String arenaJSON) {
		int start;
		int end;
		double width;
		double height;
		start = arenaJSON.indexOf("width:") + 6;
		end = arenaJSON.indexOf(",");
		width = Double.parseDouble(
			arenaJSON.substring(start, end)
		);
		start = arenaJSON.indexOf("height:") + 7;
		end = arenaJSON.indexOf("}");
		height = Double.parseDouble(
			arenaJSON.substring(start, end)
		);
		Canvas canvas = new Canvas();
		canvas.setWidth(width);
		canvas.setHeight(height);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillRect(0, 0, width, height);
		return canvas;
	}

	/**
	 * overkill for a simple ball but when drawing entities
	 * this will make sense.
	 * @param ballJSON
	 * @return
	 */
	private SubCanvas drawBall(String ballJSON) {
		int start;
		int end;
		double radius;
		double x;
		double y;
		double width;
		double height;
		start = ballJSON.indexOf("radius:") + 7;
		end = ballJSON.indexOf(",", start);
		radius = Double.parseDouble(
			ballJSON.substring(start, end)
		);
		start = ballJSON.indexOf("x:", end) + 2;
		end = ballJSON.indexOf(",", start);
		x = Double.parseDouble(
			ballJSON.substring(start, end)
		);
		start = ballJSON.indexOf("y:", end) + 2;
		end = ballJSON.indexOf(",", start);
		y = Double.parseDouble(
			ballJSON.substring(start, end)
		);
		width = radius * 2;
		height = radius * 2;
		Canvas canvas = new Canvas();
		canvas.setWidth(width);
		canvas.setHeight(height);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillOval(0, 0, width, height);
		SubCanvas subCanvas = new SubCanvas();
		subCanvas.setCanvas(canvas);
		subCanvas.setX(x);
		subCanvas.setY(y);
		return subCanvas;
	}

	/**
	 * overkill for a simple paddle but when drawing entities
	 * this will make sense.
	 * @param paddleJSON
	 * @return
	 */
	private SubCanvas drawPaddle(String paddleJSON) {
		int start;
		int end;
		double width;
		double height;
		double x;
		double y;
		start = paddleJSON.indexOf("width:") + 6;
		end = paddleJSON.indexOf(",", start);
		width = Double.parseDouble(
			paddleJSON.substring(start, end)
		);
		start = paddleJSON.indexOf("height:", end) + 7;
		end = paddleJSON.indexOf(",", start);
		height = Double.parseDouble(
			paddleJSON.substring(start, end)
		);
		start = paddleJSON.indexOf("x:", end) + 2;
		end = paddleJSON.indexOf(",", start);
		x = Double.parseDouble(
			paddleJSON.substring(start, end)
		);
		start = paddleJSON.indexOf("y:", end) + 2;
		end = paddleJSON.indexOf(",", start);
		y = Double.parseDouble(
			paddleJSON.substring(start, end)
		);
		Canvas canvas = new Canvas();
		canvas.setWidth(width);
		canvas.setHeight(height);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0, 0, width, height);
		SubCanvas subCanvas = new SubCanvas();
		subCanvas.setCanvas(canvas);
		subCanvas.setX(x);
		subCanvas.setY(y);
		return subCanvas;
	}

	/**
	 * overkill for a simple brick but when drawing entities
	 * this will make sense.
	 * @param brickJSON
	 * @return
	 */
	private SubCanvas drawBrick(String brickJSON) {
		int start;
		int end;
		double width;
		double height;
		double x;
		double y;
		start = brickJSON.indexOf("width:") + 6;
		end = brickJSON.indexOf(",", start);
		width = Double.parseDouble(
			brickJSON.substring(start, end)
		);
		start = brickJSON.indexOf("height:", end) + 7;
		end = brickJSON.indexOf(",", start);
		height = Double.parseDouble(
			brickJSON.substring(start, end)
		);
		start = brickJSON.indexOf("x:", end) + 2;
		end = brickJSON.indexOf(",", start);
		x = Double.parseDouble(
			brickJSON.substring(start, end)
		);
		start = brickJSON.indexOf("y:", end) + 2;
		end = brickJSON.indexOf(",", start);
		y = Double.parseDouble(
			brickJSON.substring(start, end)
		);
		Canvas canvas = new Canvas();
		canvas.setWidth(width);
		canvas.setHeight(height);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.GREEN);
		graphicsContext.fillRect(0, 0, width, height);
		SubCanvas subCanvas = new SubCanvas();
		subCanvas.setCanvas(canvas);
		subCanvas.setX(x);
		subCanvas.setY(y);
		return subCanvas;
	}

	private SubCanvas drawExplosion(String explosionJSON) {
		int start;
		int end;
		double radius;
		double width;
		double height;
		double x;
		double y;
		int status;
		start = explosionJSON.indexOf("radius:") + 7;
		end = explosionJSON.indexOf(",", start);
		radius = Double.parseDouble(
			explosionJSON.substring(start, end)
		);
		start = explosionJSON.indexOf("x:", end) + 2;
		end = explosionJSON.indexOf(",", start);
		x = Double.parseDouble(
			explosionJSON.substring(start, end)
		);
		start = explosionJSON.indexOf("y:", end) + 2;
		end = explosionJSON.indexOf(",", start);
		y = Double.parseDouble(
			explosionJSON.substring(start, end)
		);
		start = explosionJSON.indexOf("status:", end) + 7;
		end = explosionJSON.indexOf("}", start);
		status = Integer.parseInt(
			explosionJSON.substring(start, end)
		);
		width = radius * 2;
		height = radius * 2;
		Canvas canvas = new Canvas();
		canvas.setWidth(width);
		canvas.setHeight(height);
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setFill(Color.YELLOWGREEN);
		graphicsContext.fillOval(0, 0, width, height);
		SubCanvas subCanvas = new SubCanvas();
		subCanvas.setCanvas(canvas);
		subCanvas.setX(x);
		subCanvas.setY(y);
		return subCanvas;
	}

	private class SubCanvas {
		private Canvas canvas;
		private double x;
		private double y;

		public SubCanvas() {}

		public Canvas getCanvas() {
			return canvas;
		}

		public void setCanvas(Canvas canvas) {
			this.canvas = canvas;
		}

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}
	}
}
