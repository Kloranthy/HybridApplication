import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
		BufferedImage frame = renderModule.drawFrame(frameJSON);
		File out = new File("frame.png");
		try {
			ImageIO.write(frame, "png", out);
		}
		catch (IOException io) {
			io.printStackTrace();
		}
	}

	public BufferedImage test() {
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

	public BufferedImage drawFrame(String frameJSON) {
		// declare variables
		BufferedImage frame;
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
		// draw parts on sprites todo convert this to batch
		BufferedImage arenaImage = drawArena(arenaJSON);
		Sprite ballSprite = drawBall(ballJSON);
		Sprite paddleSprite = drawPaddle(paddleJSON);
		List<Sprite> brickSpriteList = new LinkedList<Sprite>();
		for (String brickJSON : brickJSONList) {
			Sprite brickSprite = drawBrick(brickJSON);
			brickSpriteList.add(brickSprite);
		}
		List<Sprite> explosionSpriteList = new LinkedList<Sprite>();
		for (String explosionJSON : explosionJSONList) {
			Sprite explosionSprite = drawExplosion(explosionJSON);
			explosionSpriteList.add(explosionSprite);
		}
		// draw sub canvases on buffered image
		frame = new BufferedImage(
			arenaImage.getWidth(),
			arenaImage.getHeight(),
			BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D graphics2D = frame.createGraphics();
		graphics2D.drawImage(
			ballSprite.getBufferedImage(),
			ballSprite.getX(),
			ballSprite.getY(),
			null
		);
		graphics2D.drawImage(
			paddleSprite.getBufferedImage(),
			paddleSprite.getX(),
			paddleSprite.getY(),
			null
		);
		for (Sprite brickSprite : brickSpriteList) {
			graphics2D.drawImage(
				brickSprite.getBufferedImage(),
				brickSprite.getX(),
				brickSprite.getY(),
				null
			);
		}
		for (Sprite explosionSprite : explosionSpriteList) {
			graphics2D.drawImage(
				explosionSprite.getBufferedImage(),
				explosionSprite.getX(),
				explosionSprite.getY(),
				null
			);
		}
		// do something with frame canvas to make it available to ui
		return frame;
	}

	private BufferedImage drawArena(String arenaJSON) {
		int start;
		int end;
		int width;
		int height;
		start = arenaJSON.indexOf("width:") + 6;
		end = arenaJSON.indexOf(",");
		width = Integer.parseInt(
			arenaJSON.substring(start, end)
		);
		start = arenaJSON.indexOf("height:") + 7;
		end = arenaJSON.indexOf("}");
		height = Integer.parseInt(
			arenaJSON.substring(start, end)
		);
		BufferedImage arenaImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics2D = arenaImage.createGraphics();
		graphics2D.setPaint(Color.BLACK);
		graphics2D.fillRect(0, 0, width, height);
		return arenaImage;
	}

	/**
	 * overkill for a simple ball but when drawing entities
	 * this will make sense.
	 * @param ballJSON
	 * @return
	 */
	private Sprite drawBall(String ballJSON) {
		int start;
		int end;
		int radius;
		int x;
		int y;
		int width;
		int height;
		start = ballJSON.indexOf("radius:") + 7;
		end = ballJSON.indexOf(",", start);
		radius = Integer.parseInt(
			ballJSON.substring(start, end)
		);
		start = ballJSON.indexOf("x:", end) + 2;
		end = ballJSON.indexOf(",", start);
		x = Integer.parseInt(
			ballJSON.substring(start, end)
		);
		start = ballJSON.indexOf("y:", end) + 2;
		end = ballJSON.indexOf(",", start);
		y = Integer.parseInt(
			ballJSON.substring(start, end)
		);
		width = radius * 2;
		height = radius * 2;
		BufferedImage bufferedImage = new BufferedImage(
			width,
			height,
			BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setPaint(Color.GREEN);
		graphics2D.fillOval(0, 0, width, height);
		Sprite sprite = new Sprite();
		sprite.setBufferedImage(bufferedImage);
		sprite.setX(x);
		sprite.setY(y);
		return sprite;
	}

	/**
	 * overkill for a simple paddle but when drawing entities
	 * this will make sense.
	 * @param paddleJSON
	 * @return
	 */
	private Sprite drawPaddle(String paddleJSON) {
		int start;
		int end;
		int width;
		int height;
		int x;
		int y;
		start = paddleJSON.indexOf("width:") + 6;
		end = paddleJSON.indexOf(",", start);
		width = Integer.parseInt(
			paddleJSON.substring(start, end)
		);
		start = paddleJSON.indexOf("height:", end) + 7;
		end = paddleJSON.indexOf(",", start);
		height = Integer.parseInt(
			paddleJSON.substring(start, end)
		);
		start = paddleJSON.indexOf("x:", end) + 2;
		end = paddleJSON.indexOf(",", start);
		x = Integer.parseInt(
			paddleJSON.substring(start, end)
		);
		start = paddleJSON.indexOf("y:", end) + 2;
		end = paddleJSON.indexOf(",", start);
		y = Integer.parseInt(
			paddleJSON.substring(start, end)
		);
		BufferedImage bufferedImage = new BufferedImage(
			width,
			height,
			BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setPaint(Color.GREEN);
		graphics2D.fillRect(x, y, width, height);
		Sprite sprite = new Sprite();
		sprite.setBufferedImage(bufferedImage);
		sprite.setX(x);
		sprite.setY(y);
		return sprite;
	}

	/**
	 * overkill for a simple brick but when drawing entities
	 * this will make sense.
	 * @param brickJSON
	 * @return
	 */
	private Sprite drawBrick(String brickJSON) {
		int start;
		int end;
		int width;
		int height;
		int x;
		int y;
		start = brickJSON.indexOf("width:") + 6;
		end = brickJSON.indexOf(",", start);
		width = Integer.parseInt(
			brickJSON.substring(start, end)
		);
		start = brickJSON.indexOf("height:", end) + 7;
		end = brickJSON.indexOf(",", start);
		height = Integer.parseInt(
			brickJSON.substring(start, end)
		);
		start = brickJSON.indexOf("x:", end) + 2;
		end = brickJSON.indexOf(",", start);
		x = Integer.parseInt(
			brickJSON.substring(start, end)
		);
		start = brickJSON.indexOf("y:", end) + 2;
		end = brickJSON.indexOf(",", start);
		y = Integer.parseInt(
			brickJSON.substring(start, end)
		);
		BufferedImage bufferedImage = new BufferedImage(
			width,
			height,
			BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setPaint(Color.GREEN);
		graphics2D.fillRect(x, y, width, height);
		Sprite sprite = new Sprite();
		sprite.setBufferedImage(bufferedImage);
		sprite.setX(x);
		sprite.setY(y);
		return sprite;
	}

	private Sprite drawExplosion(String explosionJSON) {
		int start;
		int end;
		int radius;
		int width;
		int height;
		int x;
		int y;
		int status;
		start = explosionJSON.indexOf("radius:") + 7;
		end = explosionJSON.indexOf(",", start);
		radius = Integer.parseInt(
			explosionJSON.substring(start, end)
		);
		start = explosionJSON.indexOf("x:", end) + 2;
		end = explosionJSON.indexOf(",", start);
		x = Integer.parseInt(
			explosionJSON.substring(start, end)
		);
		start = explosionJSON.indexOf("y:", end) + 2;
		end = explosionJSON.indexOf(",", start);
		y = Integer.parseInt(
			explosionJSON.substring(start, end)
		);
		start = explosionJSON.indexOf("status:", end) + 7;
		end = explosionJSON.indexOf("}", start);
		status = Integer.parseInt(
			explosionJSON.substring(start, end)
		);
		width = radius * 2;
		height = radius * 2;
		BufferedImage bufferedImage = new BufferedImage(
			width,
			height,
			BufferedImage.TYPE_3BYTE_BGR
		);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setPaint(Color.GREEN);
		graphics2D.fillOval(0, 0, width, height);
		Sprite sprite = new Sprite();
		sprite.setBufferedImage(bufferedImage);
		sprite.setX(x);
		sprite.setY(y);
		return sprite;
	}

	private class Sprite {
		private BufferedImage bufferedImage;
		private int x;
		private int y;

		public Sprite() {}

		public BufferedImage getBufferedImage() {
			return bufferedImage;
		}

		public void setBufferedImage(BufferedImage bufferedImage) {
			this.bufferedImage = bufferedImage;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
}
