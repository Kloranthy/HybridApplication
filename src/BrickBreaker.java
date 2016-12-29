import java.util.LinkedList;
import java.util.List;

public class BrickBreaker {
	private Arena arena;
	// todo add support for multiple balls
	private Ball ball;
	private Paddle paddle;
	private List<Brick> bricks;
	private List<Explosion> explosions;
	private int score;
	private int lives;

	public BrickBreaker() {
		// create arena
		arena = new Arena();
		// create ball
		ball = new Ball();
		// create paddle
		paddle = new Paddle();
		// create list of bricks
		bricks = new LinkedList<Brick>();
		// create list of explosions
		explosions = new LinkedList<Explosion>();
	}

	public void initialize() {
		arena.setWidth(600);
		arena.setHeight(480);
		ball.setRadius(10);
		ball.setX(300);
		ball.setY(400);
		ball.setDX(4);
		ball.setDY(-4);
		paddle.setWidth(80);
		paddle.setHeight(10);
		paddle.setX(300);
		paddle.setY(460);
		paddle.setDX(10);
		// clear out any existing bricks
		bricks.clear();
		// create bricks
		// clear out any existing explosions
		explosions.clear();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void update() {
		//update all things
		//check for collisions
		//if ball hit a break brick it
		// add an explosion at ball's location
		// reverse ball's direction based on side it hit?
		//remove broken bricks and expired explosions
		//get json and pass to render module
	}

	public String toJSON() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		stringBuilder.append("arena:" + arena.toJSON() + ",");
		stringBuilder.append("ball:" + ball.toJSON() + ",");
		stringBuilder.append("paddle:" + ball.toJSON() + ",");
		stringBuilder.append("bricks:[");
		// loop to add each brick into json
		Brick[] brickArray = new Brick[bricks.size()];
		bricks.toArray(brickArray);
		for (int i = 0; i < brickArray.length; i++) {
			stringBuilder.append("brick:" + brickArray[i].toJSON());
			if (i != brickArray.length - 1) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append("],");
		stringBuilder.append("explosions:[");
		// loop to add each explosion into json
		Explosion[] explosionArray = new Explosion[explosions.size()];
		explosions.toArray(explosionArray);
		for (int i = 0; i < explosionArray.length; i++) {
			stringBuilder.append("explosion:" + explosionArray[i]);
			if (i != explosionArray.length - 1) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append("],");
		stringBuilder.append("score:" + score + ",");
		stringBuilder.append("lives:" + lives);
		stringBuilder.append("}");
		return stringBuilder.toString();
	}

	class Arena {
		private int width;
		private int height;

		public Arena() {}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public String toJSON() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("{");
			stringBuilder.append("width:" + width + ",");
			stringBuilder.append("height:" + height);
			stringBuilder.append("}");
			return stringBuilder.toString();
		}
	}

	class Ball {
		private int radius;
		private int x;
		private int y;
		private int dx;
		private int dy;

		public Ball() {}

		public int getRadius() {
			return radius;
		}

		public void setRadius(int radius) {
			this.radius = radius;
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

		public int getDX() {
			return dx;
		}

		public void setDX(int dx) {
			this.dx = dx;
		}

		public int getDY() {
			return dy;
		}

		public void setDY(int dy) {
			this.dy = dy;
		}

		public void update() {
			x += dx;
			y += dy;
		}

		public String toJSON() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("{");
			stringBuilder.append("radius:" + radius + ",");
			stringBuilder.append("x:" + x + ",");
			stringBuilder.append("y:" + y + ",");
			stringBuilder.append("dx:" + dx + ",");
			stringBuilder.append("dy:" + dy);
			stringBuilder.append("}");
			return stringBuilder.toString();
		}
	}

	class Paddle {
		private int width;
		private int height;
		private int x;
		private int y;
		private int dx;

		public Paddle() {}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
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

		public int getDX() {
			return dx;
		}

		public void setDX(int dx) {
			this.dx = dx;
		}

		public void update() {
			x += dx;
		}

		public String toJSON() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("{");
			stringBuilder.append("width:" + width + ",");
			stringBuilder.append("height:" + height + ",");
			stringBuilder.append("x:" + x + ",");
			stringBuilder.append("y:" + y + ",");
			stringBuilder.append("dx:" + dx);
			stringBuilder.append("}");
			return stringBuilder.toString();
		}
	}

	class Brick {
		private int width;
		private int height;
		private int x;
		private int y;
		private boolean isBroken;

		public Brick() {}

		public Brick(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
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

		public boolean isBroken() {
			return isBroken;
		}

		public void setBroken(boolean broken) {
			isBroken = broken;
		}

		public String toJSON() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("{");
			stringBuilder.append("width:" + width + ",");
			stringBuilder.append("height:" + height + ",");
			stringBuilder.append("x:" + x + ",");
			stringBuilder.append("y:" + y + ",");
			stringBuilder.append("isBroken:");
			if (isBroken) {
				stringBuilder.append("1");
			}
			else {
				stringBuilder.append("0");
			}
			stringBuilder.append("}");
			return stringBuilder.toString();
		}
	}

	class Explosion {
		public static final int FULL = 1;
		public static final int FADING = 2;
		public static final int FADED = 3;
		public static final int GONE = 4;
		private int radius;
		private int x;
		private int y;
		private int status;

		public Explosion() {}

		public int getRadius() {
			return radius;
		}

		public void setRadius(int radius) {
			this.radius = radius;
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

		public int getStatus() {
			return status;
		}

		public void update() {
			status++;
		}

		public String toJSON() {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("{");
			stringBuilder.append("radius:" + radius + ",");
			stringBuilder.append("x:" + x + ",");
			stringBuilder.append("y:" + y + ",");
			stringBuilder.append("status:" + status);
			stringBuilder.append("}");
			return stringBuilder.toString();
		}
	}
}
