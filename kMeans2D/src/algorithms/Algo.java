package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Algo {
	public static ArrayList<ArrayList<Point>> kMeans(ArrayList<Point> points) {
		int y_min, y_max, x_min, x_max;

		x_min = points.stream().mapToInt(e -> e.x).min().getAsInt();
		y_max = points.stream().mapToInt(e -> e.y).min().getAsInt();

		x_max = points.stream().mapToInt(e -> e.x).max().getAsInt();
		y_min = points.stream().mapToInt(e -> e.y).max().getAsInt();

		Point mi = new Point(x_min / 2, y_min / 2);

		ArrayList<ArrayList<Point>> ps = new ArrayList<>();

		ArrayList<Point> lp = new ArrayList<>();
		lp.add(new Point(x_min, y_min));
		ps.add(lp);

		lp = new ArrayList<>();
		lp.add(new Point(x_max, y_min));
		ps.add(lp);

		lp = new ArrayList<>();
		lp.add(new Point(x_min, y_max));
		ps.add(lp);

		lp = new ArrayList<>();
		lp.add(new Point(x_max, y_max));
		ps.add(lp);

		lp = new ArrayList<>();
		lp.add(mi);
		ps.add(lp);

		return ps;

	}

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

	public static ArrayList<ArrayList<Point>> kMeans2(ArrayList<Point> points, int k) {
		ArrayList<ArrayList<Point>> ps = new ArrayList<>();
		Random r = new Random();
		int index;
		Point p = null, ci = null;
		ArrayList<Point> lp = null;
		ArrayList<Point> barycentres = new ArrayList<>();
		
		for (int i = 0; i < k; i++) {
			lp = new ArrayList<>();
			index = r.nextInt(points.size());
			p = points.get(index);
			lp.add(p);
			barycentres.add(new Point(p));
			points.remove(index);
			ps.add(lp);
		}

		while (!points.isEmpty()) {
			for (int i = 0; i < k; i++) {
				lp = ps.get(i);
				ci = barycentres.get(i);
				p = getClosest(ci, points);
				lp.add(p);
				points.remove(p);
				ci.x = (int) (((lp.size() + ci.x) * p.x)/(double) (lp.size()+1));
				ci.y = (int) (((lp.size() + ci.y) * p.y)/(double) (lp.size()+1));
			}
			
		}

		return ps;

	}

}
