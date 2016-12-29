import java.util.LinkedList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		String frameJSON = "{arena:{width:800,height:600}," +
			"ball:{radius:10}," +
			"paddle:{width:80,height:10,x:400,y:585,dx:10}," +
			"bricks:[" +
			"brick:{width:80,height:20,x:10,y:10,isBroken:0}," +
			"brick:{width:80,height:20,x:10,y:10,isBroken:0}" +
			"]," +
			"explosions:[" +
			"explosion:{radius:30,x:30,y:20,status:2}," +
			"explosion:{radius:30,x:30,y:20,status:2}" +
			"]," +
			"score:5," +
			"lives:2" +
			"}";
		int start;
		int end;
		// get arena json from frame json
		String arenaJSON;
		start = frameJSON.indexOf("arena:{");
		end = frameJSON.indexOf("}", start) + 1;
		arenaJSON = frameJSON.substring(start, end);
		System.out.println(arenaJSON);
		// get ball json from frame json
		String ballJSON;
		start = frameJSON.indexOf("ball:{", end);
		end = frameJSON.indexOf("}", start) + 1;
		ballJSON = frameJSON.substring(start, end);
		System.out.println(ballJSON);
		// get paddle json from frame json
		String paddleJSON;
		start = frameJSON.indexOf("paddle:{", end);
		end = frameJSON.indexOf("}", start) + 1;
		paddleJSON = frameJSON.substring(start, end);
		System.out.println(paddleJSON);
		// get brick array json from frame json
		String bricksJSON;
		start = frameJSON.indexOf("bricks:[", end);
		end = frameJSON.indexOf("]", start) + 1;
		bricksJSON = frameJSON.substring(start, end);
		System.out.println(bricksJSON);
		List<String> brickJSONList = new LinkedList<String>();
		String brickJSON;
		while (frameJSON.indexOf("brick:{") > 0) {
			start = frameJSON.indexOf("brick:{");
			end = frameJSON.indexOf("}", start) + 1;
			brickJSON = frameJSON.substring(start, end);
			System.out.println(brickJSON);
			brickJSONList.add(brickJSON);
		}
		// get brick array json from frame json
		String explosionsJSON;
		start = frameJSON.indexOf("explosions:[", end);
		end = frameJSON.indexOf("]", start) + 1;
		explosionsJSON = frameJSON.substring(start, end);
		System.out.println(explosionsJSON);
		// get score json from frame json
		String scoreJSON;
		start = frameJSON.indexOf("score:", end);
		end = frameJSON.indexOf(",", start);
		scoreJSON = frameJSON.substring(start, end);
		System.out.println(scoreJSON);
		// get lives json from frame json
		String livesJSON;
		start = frameJSON.indexOf("lives:", end);
		end = frameJSON.indexOf("}", start);
		livesJSON = frameJSON.substring(start, end);
		System.out.println(livesJSON);
	}
}
