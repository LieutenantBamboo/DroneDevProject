
class Point implements Comparable<Point> {
    int x, y;

    public Point(){x = y = -999;}
    
    public Point(int x,int y){
	this.x = x;
	this.y = y;
    }

    public int compareTo(Point p){
	if (x == p.x) return y - p.y;
	return x - p.x;
    }

    public static int distanceManhattan(Point a,Point b){
	return Math.abs(a.x-b.x) + Math.abs(a.y-b.y);
    }

    public static double distanceEuclidean(Point a,Point b){
	long dx = a.x - b.x;
	long dy = a.y - b.y;
	return Math.sqrt(dx*dx + dy*dy);
    }

    public String toString(){
	return "("+ x +","+ y +")";
    }
}

