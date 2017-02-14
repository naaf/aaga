package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	ArrayList<Integer> a = new ArrayList<>();
	for (int i = 0; i < 15; i++) {
	    a.add(i);
	}
	List<Integer> d = a.subList(0, 5);
	List<Integer> m = a.subList(5, 10);
	List<Integer> f = a.subList(10, a.size());
	ArrayList<Integer> b = new ArrayList<>();
	System.out.println(d);
	b.addAll(d);
	Collections.reverse(m);
	b.addAll(m);
	b.addAll(f);
	System.out.println(a);
	System.out.println(b);
	

    }

}
