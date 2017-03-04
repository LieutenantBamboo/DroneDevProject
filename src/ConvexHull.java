import java.io.*;
import java.util.*;

public class ConvexHull {

    public static long cross(Point O,Point A,Point B) {
	return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    public static Point[] convex_hull(Point[] P){
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

    public static void main(String[] args) throws IOException {
	
	Scanner sc = new Scanner(new File(args[0]));
	int n = sc.nextInt();
	Point[] p = new Point[n];
	
	int xMin = 99999;
	int xMax = 0;
	int yMin = 99999;
	int yMax = 0;
	double pointSize = 0.1;
	    
	for (int i=0;i<n;i++){
	    int x = sc.nextInt();
	    int y = sc.nextInt();
	    p[i]  = new Point(x,y);
	    xMin  = Math.min(xMin,x);
	    xMax  = Math.max(xMax,x);
	    yMin  = Math.min(yMin,y);
	    yMax  = Math.max(yMax,y);
	}
	sc.close();
	
	// draw points
	StdDraw.clear(StdDraw.LIGHT_GRAY);
	StdDraw.setXscale(xMin, xMax);
	StdDraw.setYscale(yMin, yMax);
	StdDraw.show(0);	
	for (int i=0;i<n;i++)
	    StdDraw.circle(p[i].x,p[i].y,pointSize);
	StdDraw.show(0);
	StdDraw.textLeft(xMin,yMin,args[0]);

	// get and display convex hull
	Point[] hull = convex_hull(p).clone();
	int m = hull.length;
	StdDraw.setPenColor(StdDraw.BLACK);
	for (int i=0;i<m-1;i++)
	    StdDraw.line(hull[i].x,hull[i].y,hull[i+1].x,hull[i+1].y);
	StdDraw.line(hull[0].x,hull[0].y,hull[m-1].x,hull[m-1].y);

	// print convex hull
	for (int i=0;i<m;i++)
	    if (hull[i] != null)
		System.out.print(hull[i]);
    }
}
