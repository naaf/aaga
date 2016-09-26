package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AlgoDominant {

	private int edgeThreshold;
	private final static Logger LOGGER = Logger.getLogger(AlgoDominant.class.getName());

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

	public Point getMax(List<Point> points) {
		Point res = points.get(0);
		int ntmp = neighbor(res, points).size();
		int nb;
		for (int i = 1; i < points.size(); i++) {
			nb = neighbor(points.get(i), points).size();
			if (ntmp < nb) {
				ntmp = nb;
				res = points.get(i);
			}
		}

		return res;
	}
	Point getPPC(List<Point> points) {
		final Comparator<Point> comp = (p1, p2) -> Integer.compare(
				neighbor(p1, points).size(), neighbor(p2, points).size());
		return points.stream().max(comp).get();
	}

	List<Point> getPPI(List<Point> points) {
		return points.stream().filter(p -> neighbor(p, points).isEmpty())
				.collect(Collectors.toList());
	}

	public ArrayList<Point> glouton(List<Point> points) {

		System.out.println("edgeThreshold ==" +edgeThreshold);

		List<Point> ps = new ArrayList<>(points);
		List<Point> ensDominant = getPPI(points);
		ps.removeAll(ensDominant);
		ensDominant.forEach(p -> System.out.println(p +" ppi ==>" + neighbor(p, points)));
		Point p;
		do {
			p = getMax(ps);
			ensDominant.add(p);
			ps.removeAll(neighbor(p, ps));
			ps.remove(p);
		} while (! isValide(points, ensDominant));
		return (ArrayList) ensDominant;
	}

	public ArrayList<Point> calcul(List<Point> ps, ArrayList<Point> result) {
		ArrayList<Point> tmp = new ArrayList<>(ps);
		tmp.removeAll(result);
		ArrayList<Point> tmpResult = new ArrayList<>(result);
		ArrayList<Point> resultA = result;
		for (Point p : result) {
			for (Point q : result) {
				if (p.distance(q) < 3 * edgeThreshold) {
					for (Point k : tmp) {
						tmpResult = new ArrayList<>(result);
						tmpResult.remove(p);
						tmpResult.remove(q);
						tmpResult.add(k);
//						if(isValide(ps, tmpResult)){
							if(resultA.size() > tmpResult.size()){
								resultA = tmpResult;
								System.out.println("passe voa " + resultA.size());
							}
//						}


					}
				}
			}
		}
		return resultA;
	}

	public ArrayList<Point> localSearching(List<Point> points) {
		ArrayList<Point> resultOld = null;
		ArrayList<Point> result = glouton(points);
		ArrayList<Point> resultTMP = glouton(points);
		List<Point> ps = new ArrayList<>(points);
		do {
			do {
				resultOld = new ArrayList<Point>();
				resultOld.addAll(result);
				result = calcul(ps, resultOld);
				System.out.println("result.size() " + (result.size()));
				System.out.println("resultOld.size() " + (resultOld.size()));

			} while (result.size() < resultOld.size());
			resultTMP.addAll(resultOld);
			ps = new ArrayList<>(points);
			ps.removeAll(resultTMP);
			for(Point p : resultTMP)
				ps.removeAll(neighbor(p, ps));
			result = glouton(ps);
		} while (!ps.isEmpty());
		return resultOld;
	}

}
