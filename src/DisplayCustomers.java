import java.io.*;
import java.util.*;

public class DisplayCustomers {

    public static void main(String[] args) throws IOException {

	if (args.length == 0 || args[0].equals("-h")){
	    System.out.println("\nDisplay Customers version 20161214 \n"+
			       "-p DOUBLE  point size (default 2.0) \n" +
			       "-h       Print this help message");
	    return;
	}

	double pointSize = 2.0;
	for (int i=0;i<args.length;i++){
	    if (args[i].equals("-p")) pointSize = Double.parseDouble(args[i+1]);
	}

	// read in the customers as points
	Scanner sc = new Scanner(new File(args[0]));
	int noCustomers = sc.nextInt();
	Point[] customer = new Point[noCustomers];	  
	int xMin = Integer.MAX_VALUE;
	int xMax = Integer.MIN_VALUE;
	int yMin = Integer.MAX_VALUE;
	int yMax = Integer.MIN_VALUE;
	  
	for (int i=0;i<noCustomers;i++){
	    int x = sc.nextInt();
	    int y = sc.nextInt();
	    xMin  = Math.min(xMin,x);
	    xMax  = Math.max(xMax,x);
	    yMin  = Math.min(yMin,y);
	    yMax  = Math.max(yMax,y);
	    customer[i] = new Point(x,y);
	}
	sc.close();

	// draw points
	StdDraw.clear(StdDraw.LIGHT_GRAY);
	StdDraw.setXscale(xMin, xMax);
	StdDraw.setYscale(yMin, yMax);
	StdDraw.show(0);
	for (Point p : customer) StdDraw.filledCircle(p.x,p.y,pointSize);
	StdDraw.show(0);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.textLeft(xMin,yMin,args[0]);
    }
}