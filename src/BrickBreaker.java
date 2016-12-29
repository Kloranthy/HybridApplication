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
		private double width;
		private double height;

		public Arena() {
			// default values
		}

		public double getWidth() {
			return width;
		}

		public void setWidth(double width) {
			this.width = width;
		}

		public double getHeight() {
			return height;
		}

		public void setHeight(double height) {
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
		private double radius;
		private double x;
		private double y;
		private double dx;
		private double dy;

		public Ball() {
			// use default values
		}

		public double getRadius() {
			return radius;
		}

		public void setRadius(double radius) {
			this.radius = radius;
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

		public double getDX() {
			return dx;
		}

		public void setDX(double dx) {
			this.dx = dx;
		}

		public double getDY() {
			return dy;
		}

		public void setDY(double dy) {
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
		private double width;
		private double height;
		private double x;
		private double y;
		private double dx;

		public Paddle() {
			//
		}

		public double getWidth() {
			return width;
		}

		public void setWidth(double width) {
			this.width = width;
		}

		public double getHeight() {
			return height;
		}

		public void setHeight(double height) {
			this.height = height;
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

		public double getDX() {
			return dx;
		}

		public void setDX(double dx) {
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
		private double width;
		private double height;
		private double x;
		private double y;
		private boolean isBroken;

		public Brick() {
			// default values?
		}

		public Brick(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getWidth() {
			return width;
		}

		public void setWidth(double width) {
			this.width = width;
		}

		public double getHeight() {
			return height;
		}

		public void setHeight(double height) {
			this.height = height;
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
		private double radius;
		private double x;
		private double y;
		private int status;

		public double getRadius() {
			return radius;
		}

		public void setRadius(double radius) {
			this.radius = radius;
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
