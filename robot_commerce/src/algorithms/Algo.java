package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algo {

    public static Point getClosest(Point a, ArrayList<Point> points) {
	double min = Double.MAX_VALUE;
	Point minPoint = null;
	for (Point p : points) {
	    if (min > a.distance(p)) {
		minPoint = p;
		min = a.distance(p);
	    }
	}
	return minPoint;
    }

    static Point getClosest(Point pr, Point current, ArrayList<Point> points) {
	double min = Double.MAX_VALUE;
	double score = 0;
	Point minPoint = null;
	for (Point p : points) {
	    score = current.distance(p) + Evaluator.angle(pr, current, p);
	    if (min > score) {
		minPoint = p;
		min = score;
	    }
	}
	return minPoint;
    }

    public static ArrayList<Point> sortPoints(int firstPoint, ArrayList<Point> points) {
	ArrayList<Point> tmpPoints = new ArrayList<Point>(points);
	ArrayList<Point> sorted = new ArrayList<>();
	Point p = tmpPoints.remove(firstPoint);
	Point closest;
	sorted.add(p);
	while (!tmpPoints.isEmpty()) {
	    closest = getClosest(p, tmpPoints);
	    sorted.add(closest);
	    p = closest;
	    tmpPoints.remove(p);
	}
	return sorted;
    }

    public static ArrayList<Point> sortPoints2(int firstPoint, ArrayList<Point> points) {
	ArrayList<Point> tmpPoints = new ArrayList<Point>(points);
	ArrayList<Point> sorted = new ArrayList<>();
	Point current = tmpPoints.remove(firstPoint);
	sorted.add(current);
	Point pr = null;
	Point closest = null;
	while (!tmpPoints.isEmpty()) {
	    closest = getClosest(pr, current, tmpPoints);
	    sorted.add(closest);
	    tmpPoints.remove(closest);
	    pr = current;
	    current = closest;
	}
	return sorted;
    }

    public static ArrayList<Point> sortPoints3(int firstPoint, ArrayList<Point> points) {
	final int chemin = 5;
	ArrayList<Point> tmpPoints = new ArrayList<Point>(points);
	ArrayList<Point> sorted = new ArrayList<>();
	Point current = tmpPoints.remove(firstPoint);
	sorted.add(current);
	Point pr = null;
	Point closest = null;
	while (!tmpPoints.isEmpty()) {
	    ArrayList<Point> tmpPs = new ArrayList<Point>(tmpPoints);
	    ArrayList<Point> tmpSorted = new ArrayList<Point>();
	    for (int i = 0; i < chemin; i++) {
		closest = getClosest(pr, current, tmpPoints);
		sorted.add(closest);
		tmpPoints.remove(closest);
		pr = current;
		current = closest;
	    }
	}
	return sorted;
    }

    public static ArrayList<Point> glouton(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
	ArrayList<Point> hitPointSorted = null, sol = null, solOpt = null;
	double minScore = Double.MAX_VALUE;

	for (int i = 0; i < hitPoints.size(); i++) {
	    hitPointSorted = sortPoints2(i, hitPoints);
	    sol = random(points, edgeThreshold, hitPointSorted);
	    if (Evaluator.score(sol) < minScore) {
		minScore = Evaluator.score(sol);
		solOpt = new ArrayList<>(sol);
	    }
	    System.out.println("i" +i + " score : " + Evaluator.score(solOpt));
	}

	
	System.out.println(Evaluator.score(solOpt));
	for (int i = 0; i < 5; i++) {
	    solOpt = optDecroissement(solOpt, points, edgeThreshold, hitPointSorted);
	    System.out.println(Evaluator.score(solOpt));
	}
	return solOpt;

    }

    public static ArrayList<Point> optDecroissement(ArrayList<Point> sol, ArrayList<Point> points, int edgeThreshold,
	    ArrayList<Point> hitPoints) {

	for (int i = 0; i < sol.size() - 5; i++) {
	    for (int j = i + 4; j < sol.size() - 1; j++) {
		if (sol.get(i).distance(sol.get(i + 1))
			+ sol.get(j).distance(sol.get(j + 1)) > sol.get(i).distance(sol.get(j))
				+ sol.get(j + 1).distance(sol.get(i + 1))) {

		    ArrayList<Point> solOpt = new ArrayList<>();
		    List<Point> d = sol.subList(0, i + 1);
		    List<Point> m = sol.subList(i + 1, j + 1);
		    List<Point> f = sol.subList(j + 1, sol.size());

		    solOpt.addAll(d);
		    Collections.reverse(m);
		    solOpt.addAll(m);
		    solOpt.addAll(f);
		    if (Evaluator.isValid(points, solOpt, hitPoints, edgeThreshold)) {
			System.out.println("TROUVER");
			return solOpt;
		    }
		    System.out.println("NON VALIDE");
		}
	    }
	}

	return sol;

    }

    public static ArrayList<Point> random(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
	int[][] paths = new int[points.size()][points.size()];
	for (int i = 0; i < paths.length; i++)
	    for (int j = 0; j < paths.length; j++)
		paths[i][j] = i;
	double[][] dist = new double[points.size()][points.size()];

	for (int i = 0; i < paths.length; i++) {
	    for (int j = 0; j < paths.length; j++) {
		if (i == j) {
		    dist[i][i] = 0;
		    continue;
		}
		if (points.get(i).distance(points.get(j)) <= edgeThreshold)
		    dist[i][j] = points.get(i).distance(points.get(j));
		else
		    dist[i][j] = Double.POSITIVE_INFINITY;
		paths[i][j] = j;
	    }
	}
	for (int k = 0; k < paths.length; k++) {
	    for (int i = 0; i < paths.length; i++) {
		for (int j = 0; j < paths.length; j++) {
		    if (dist[i][j] > dist[i][k] + dist[k][j] ) {
			if(dist[i][j] != Double.POSITIVE_INFINITY && dist[i][j] <= dist[i][k] + dist[k][j] + Evaluator.angle(points.get(i), points.get(k), points.get(j))){
			    continue;
			}
			dist[i][j] = dist[i][k] + dist[k][j];
			paths[i][j] = paths[i][k];
		    }
		}
	    }
	}
	ArrayList<Point> result = new ArrayList<Point>();
	int s, t;
	Point p, q;
	for (int index = 0; index < hitPoints.size(); index++) {
	    s = points.indexOf(hitPoints.get(index));
	    t = points.indexOf(hitPoints.get((index + 1) % hitPoints.size()));
	    while (paths[s][t] != t && paths[s][t] != s) {
		if (points.get(s).distance(points.get(paths[s][t])) > edgeThreshold) {
		    System.err.println("FATAL ERROR. VERY PANICKED.");
		}

		p = points.get(s);
		q = points.get(paths[s][t]);

		result.add(p);

		s = paths[s][t];
	    }
	    if (points.get(s).distance(points.get(paths[s][t])) > edgeThreshold || paths[s][t] != t) {
		System.err.println("FATAL ERROR. VERY PANICKED.");
	    }

	    p = points.get(s);
	    q = points.get(t);

	    result.add(p);
	    result.add(q);
	}
	return result;
    }

}