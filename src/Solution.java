import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthewconnorday on 22/02/17.
 */

// Solution Wrapper Class

public class Solution {

    private Point[] points;
    private double cost;
    private boolean legal;

    public Solution(Point[] points, double cost) {
        this.points = points;
        this.cost = cost;
        this.legal = true;
    }

    public Solution() {
        this(null, 0.0);
    }

    public Point[] getPoints() {
        return points;
    }

    public double getCost() {
        return cost;
    }

    public boolean getLegal() {
        return legal;
    }

    public int compareTo(Solution s) {
        if(!this.legal){return 1;}
        if(!s.legal){return -1;}
        if (this.cost < s.cost) {
            return -1;
        } else {
            return 1;
        }
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setLegal(boolean legal) {
        this.legal = legal;
    }

    @Override

    public String toString() {
        return "Solution{" +
                "points=" + Arrays.toString(points) +
                ", cost=" + cost +
                '}';
    }
}
