package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DefaultTeam {
    public ArrayList<Point> calculAngularTSP(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {

//	 List<Point> hitPoints2 = Algo.sortPoints(0,hitPoints).subList(0, 5);
//	 
//	 ArrayList<Point> result = Algo.random(points, edgeThreshold, new
//	 ArrayList<>(hitPoints2));
//	 System.out.println(Evaluator.score(result));
	
	ArrayList<Point> result = Algo.glouton(points, edgeThreshold, hitPoints);
	System.out.println(Evaluator.score(result));

	return result;
    }
}
