import java.io.*;
import java.util.*;

public class Warehouses {

    public static void main(String[] args) throws IOException {

	if (args.length == 0 || args[0].equals("-h")){
	    System.out.println("\nDrone Delivery Depot Placement Problem (DDDPP) version 20161214 \n"+
                               "-d INT     depot fixed cost (default is 500) \n"+
			       "-p DOUBLE  point size (default 2.0) \n"+
			       "-l         do not draw lines \n"+
			       "-h         >java Warehouses depotLocations customerLocations");
	    return;
	}

	int fixedCost = 2500;
	double pointSize = 2.0;
	boolean drawLines = true;
	double maxDistance = 150.01; // max distance a drone can travel
	boolean legal = true; // if all distances are less than maxDistance
	for (int i=0;i<args.length;i++){
	    if (args[i].equals("-d")) fixedCost = Integer.parseInt(args[i+1]);
	    if (args[i].equals("-p")) pointSize = Double.parseDouble(args[i+1]);
	    if (args[i].equals("-l")) drawLines = false;
	}

	// read in the depots with their locations
	Scanner sc    = new Scanner(new File(args[0]));
	int noDepots  = sc.nextInt(); // number of depots
	Depot[] depot = new Depot[noDepots];
	for (int i=0;i<noDepots;i++) depot[i] = new Depot(i,sc.nextInt(),sc.nextInt(),fixedCost);
	sc.close();

	// read in customers as points
	sc = new Scanner(new File(args[1]));
	int noCustomers  = sc.nextInt();
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

	for (Point p : customer){	    
	    Depot closestDepot = null;
	    double minDistance = Double.MAX_VALUE;
	    for (Depot d : depot){
		double distance = Point.distanceEuclidean(p,d.location);
		if (distance < minDistance){
		    minDistance = distance;
		    closestDepot = d;
		}
	    }
	    closestDepot.addCustomer(p);
	}

	double totalCost = 0;
	for (Depot d : depot){
	    d.fixedCost = fixedCost;
	    totalCost = totalCost + d.fixedCost + d.customerCost;
	    ArrayList<Point> hull = d.convexHull();
	    StdDraw.setPenColor(StdDraw.BLACK);
	    if (drawLines) for (Point p : d.customers){
		    StdDraw.setPenColor(StdDraw.BLACK);
		    double distance = Point.distanceEuclidean(p,d.location);
		    if (distance < maxDistance)
			StdDraw.line(d.location.x,d.location.y,p.x,p.y);
		    else {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(d.location.x,d.location.y,p.x,p.y);
			legal = false;
		    }
		}
	    StdDraw.setPenColor(StdDraw.BLACK);
	    if (hull.size() > 1){
		Point v = hull.get(hull.size()-1);
		for (Point w : hull){
		    StdDraw.line(v.x,v.y,w.x,w.y);
		    v = w;
		}
	    }
	    StdDraw.setPenColor(StdDraw.RED);
	    StdDraw.filledSquare(d.location.x,d.location.y,3*pointSize);
	    //try{Thread.currentThread().sleep(2000);} catch (InterruptedException e){}
	}
	StdDraw.setPenColor(StdDraw.BLACK);
	if (legal)
	    StdDraw.textLeft(xMin,yMin,args[0] +"   cost: "+ String.format("%.2f",totalCost) +"  legal");
	else
	    StdDraw.textLeft(xMin,yMin,args[0] +"   cost: "+ String.format("%.2f",totalCost) +"  illegal");
    }
}
