import java.io.*;
import java.util.*;

public class Translate {

    public static void main(String[] args) throws IOException {

	// read in the customers as points
	Scanner sc = new Scanner(new File(args[0]));
	int noCustomers = sc.nextInt();
	Point[] customer = new Point[noCustomers];	  
	int xMin = Integer.MAX_VALUE;
	int xMax = Integer.MIN_VALUE;
	int yMin = Integer.MAX_VALUE;
	int yMax = Integer.MIN_VALUE;
	double pointSize = 2.0;
	  
	int k = 0;
	for (int i=0;i<noCustomers;i++){
	    int x = (int)(sc.nextDouble());
	    int y = (int)(sc.nextDouble());
	    xMin  = Math.min(xMin,x);
	    xMax  = Math.max(xMax,x);
	    yMin  = Math.min(yMin,y);
	    yMax  = Math.max(yMax,y);
	    boolean found = false;
	    for (int j=0;j<k && !found;j++) found = customer[j].x == x && customer[j].y == y;
	    if (!found) customer[k++] = new Point(x,y);
	}
	sc.close();

	//System.out.println("range: ("+ xMin +","+ xMax +") ("+ yMin +","+ yMax +")");
	/*
	for (Point p : customer){
	    int x = p.x - xMin;
	    int y = p.y - yMin;
	    if (x < 1000 && y < 1000) System.out.println(x +" "+ y);
	}
	*/
	    
	// draw points
	/*
	StdDraw.clear(StdDraw.LIGHT_GRAY);
	StdDraw.setXscale(xMin, xMax);
	StdDraw.setYscale(yMin, yMax);
	StdDraw.show(0);
	for (int i=0;i<k;i++) StdDraw.filledCircle(customer[i].x,customer[i].y,pointSize);
	StdDraw.show(0);
	*/

	System.out.println(k);
	//for (int i=0;i<k;i++) System.out.println((customer[i].x - xMin) +" "+ (customer[i].y - yMin));
	for (int i=0;i<k;i++) System.out.println(customer[i].x +" "+ customer[i].y);
    }
}