package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

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

    public static ArrayList<Point> sortPoints(ArrayList<Point> points) {
	ArrayList<Point> tmpPoints = new ArrayList<Point>(points);
	ArrayList<Point> sorted = new ArrayList<>();
	Point p = tmpPoints.remove(0);
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

    public static ArrayList<Point> sortPoints2(ArrayList<Point> points) {
	ArrayList<Point> tmpPoints = new ArrayList<Point>(points);
	ArrayList<Point> sorted = new ArrayList<>();
	Random r = new Random();
	Point current = tmpPoints.remove(r.nextInt(points.size()));
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

    public static ArrayList<Point> sortPoints3(ArrayList<Point> points) {
	final int fenetre = 5;
	ArrayList<Point> tmpPoints = new ArrayList<Point>(points);
	ArrayList<Point> sorted = new ArrayList<>();
	Random r = new Random();
	Point current = tmpPoints.remove(r.nextInt(points.size()));
	sorted.add(current);
	Point pr = null;
	Point closest = null;
	while (!tmpPoints.isEmpty()) {
	    ArrayList<Point> tmpPs = new ArrayList<Point>(tmpPoints);
	    ArrayList<Point> tmpSorted = new ArrayList<Point>();
	    for (int i = 0; i < fenetre; i++) {
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
	ArrayList<Point> hitPointSorted = sortPoints2(hitPoints);
	return random(points, edgeThreshold, hitPointSorted);

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
		    if (dist[i][j] > dist[i][k] + dist[k][j]) {
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