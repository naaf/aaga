package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AlgoDominant {

	private int edgeThreshold;

	public AlgoDominant(int edgeThreshold) {
		this.edgeThreshold = 55;
	}

	public boolean isValide(List<Point> points, List<Point> ensDominant) {
		List<Point> ps = new ArrayList<>(points);
		for (Point p : ensDominant) {
			ps.removeAll(neighbor(p, ps));
			ps.remove(p);
		}

		return ps.isEmpty();
	}

	private List<Point> neighbor(Point p, List<Point> vertices) {
		List<Point> result = new ArrayList<Point>();

		for (Point point : vertices)
			if (point.distance(p) < edgeThreshold && !point.equals(p))
				result.add((Point) point.clone());

		return result;
	}

	Point getPPC(List<Point> points) {
		final Comparator<Point> comp = (p1, p2) -> Integer.compare(neighbor(p1, points).size(),
				neighbor(p2, points).size());
		return points.stream().max(comp).get();
	}

	List<Point> getPPI(List<Point> points) {
		return points.stream().filter(p -> neighbor(p, points).isEmpty()).collect(Collectors.toList());
	}

	public ArrayList<Point> glouton(List<Point> points) {

		System.out.println("edgeThreshold ==" + edgeThreshold);

		List<Point> ps = new ArrayList<>(points);
		List<Point> ensDominant = getPPI(points);
		ps.removeAll(ensDominant);
		Point p;
		do {
			p = getPPC(ps);
			ensDominant.add(p);
			ps.removeAll(neighbor(p, ps));
			ps.remove(p);
		} while (!isValide(points, ensDominant));
		return (ArrayList<Point>) ensDominant;
	}

	public ArrayList<Point> calcul(List<Point> ps, ArrayList<Point> ensNo) {
		ArrayList<Point> tmpEnsNo = new ArrayList<>();
		tmpEnsNo.addAll(ensNo);

		for (Point p : ensNo) {
			for (Point q : ensNo) {
				if (p.distance(q) < 2 * edgeThreshold && !p.equals(q)) {
					for (Point k : ps) {
						tmpEnsNo.remove(p);
						tmpEnsNo.remove(q);
						tmpEnsNo.add(k);

						if (!(k.equals(p) || k.equals(q)) && ensNo.size() > tmpEnsNo.size() && isValide(ps, tmpEnsNo)) {
							return tmpEnsNo;
						}
						
						tmpEnsNo.add(p);
						tmpEnsNo.add(q);
						tmpEnsNo.remove(k);

					}
				}
			}
		}
		return ensNo;
	}

	public ArrayList<Point> localSearching(List<Point> points) {
		ArrayList<Point> ensNo = new ArrayList<>();
		ArrayList<Point> newEnsNo = glouton(points);

		do {
			ensNo = newEnsNo;
			newEnsNo = calcul(points, ensNo);

			System.out.print("ensNo.size() " + (ensNo.size()));
			System.out.println(", newEnsNo.size() " + (newEnsNo.size()));

		} while (ensNo.size() > newEnsNo.size());

		return ensNo;
	}

}
