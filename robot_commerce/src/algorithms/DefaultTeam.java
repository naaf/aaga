package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DefaultTeam {
  public ArrayList<Point> calculAngularTSP(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
    //REMOVE >>>>>
//      List<Point> hitPoints2 = Algo.sortPoints2(hitPoints).subList(0, 10);
      List<Point> hitPoints2 = hitPoints.subList(2, 4);
    ArrayList<Point> result = Algo.glouton(points, edgeThreshold, new ArrayList<>(hitPoints2));
    System.out.println(Evaluator.score(result));
//    System.out.println(Evaluator.(result));
    //<<<<< REMOVE

    return result;
  }
}
