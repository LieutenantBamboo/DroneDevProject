import java.io.*;
import java.util.*;

public class Depot {

    int id;
    Point location;
    ArrayList<Point> customers;
    double fixedCost;
    double customerCost;

    public Depot(int id,int x,int y,double fixedCost){
	this.id = id;
	location = new Point(x,y);
	customers = new ArrayList<Point>();
	customers.add(location);
	this.fixedCost = fixedCost;
	customerCost = 0.0;
    }    

    int cross(Point O,Point A,Point B) {
	return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    public Point[] convex_hull(Point[] P){
	if (P.length > 1){
	    int n = P.length, k = 0;
	    Point[] H = new Point[2 * n];
	    Arrays.sort(P);
	    // Build lower hull
	    for (int i=0;i<n;++i){
		while (k >= 2 && cross(H[k - 2],H[k - 1],P[i]) <= 0) k--;
		H[k++] = P[i];
	    }
	    // Build upper hull
	    for (int i=n-2,t=k+1;i>=0;i--){
		while (k >= t && cross(H[k - 2],H[k - 1],P[i]) <= 0) k--;
		H[k++] = P[i];
	    }
	    if (k > 1) H = Arrays.copyOfRange(H,0,k-1); 
	    return H;
	}
	return P;
    }
    
    public ArrayList<Point> convexHull(){
	Point[] P = new Point[customers.size()];
	customers.toArray(P);
	if (P.length > 1){
	    int n = P.length, k = 0;
	    Point[] H = new Point[2 * n];
	    Arrays.sort(P);
	    // Build lower hull
	    for (int i=0;i<n;++i){
		while (k >= 2 && cross(H[k - 2],H[k - 1],P[i]) <= 0) k--;
		H[k++] = P[i];
	    }
	    // Build upper hull
	    for (int i=n-2,t=k+1;i>=0;i--){
		while (k >= t && cross(H[k - 2],H[k - 1],P[i]) <= 0) k--;
		H[k++] = P[i];
	    }
	    if (k > 1) H = Arrays.copyOfRange(H,0,k-1);
	    return new ArrayList<Point>(Arrays.asList(H));
	}
	return customers;
    }

    public void addCustomer(Point p){
	customers.add(p);
	customerCost = customerCost + Point.distanceEuclidean(location,p);
    }

    public String toString(){
	String s = "id: "+ id +" location: "+ location +" customers: ";
	for (Point p : customers) s = s +" "+ p;
	return s;
    }


    public static void main(String[] args) throws IOException {
	
	Scanner sc  = new Scanner(new File(args[0]));
	int n       = sc.nextInt();
	Depot depot = new Depot(0,0,0,100);
	
	int xMin = 99999;
	int xMax = 0;
	int yMin = 99999;
	int yMax = 0;
	double pointSize = 0.1;
	    
	for (int i=0;i<n;i++){
	    int x = sc.nextInt();
	    int y = sc.nextInt();
	    xMin  = Math.min(xMin,x);
	    xMax  = Math.max(xMax,x);
	    yMin  = Math.min(yMin,y);
	    yMax  = Math.max(yMax,y);
	    depot.addCustomer(new Point(x,y));
	}
	sc.close();
	
	// draw points
	StdDraw.clear(StdDraw.LIGHT_GRAY);
	StdDraw.setXscale(xMin, xMax);
	StdDraw.setYscale(yMin, yMax);
	StdDraw.show(0);	
	for (Point p : depot.customers) StdDraw.circle(p.x,p.y,pointSize);
	StdDraw.show(0);
	StdDraw.textLeft(xMin,yMin,args[0]);

	// get and display convex hull
	ArrayList<Point> hull = depot.convexHull();
	StdDraw.setPenColor(StdDraw.BLACK);
	Point v = hull.get(hull.size()-1);
	for (Point w : hull){
	    StdDraw.line(v.x,v.y,w.x,w.y);
	    v = w;
	}

	// print convex hull
	for (Point p : hull) System.out.print(p);
    }
}
