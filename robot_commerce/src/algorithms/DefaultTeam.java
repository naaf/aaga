package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {
  public ArrayList<Point> calculAngularTSP(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    //REMOVE >>>>>
    ArrayList<Point> result = Algo.glouton(points, edgeThreshold, hitPoints);
    System.out.println(Evaluator.score(result));
//    System.out.println(Evaluator.(result));
    //<<<<< REMOVE

    return result;
  }
}
