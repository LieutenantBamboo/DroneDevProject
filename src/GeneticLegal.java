import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by matthewconnorday on 01/03/17.
 */
public class GeneticLegal {

    int numOfPoints = 317;
    int numOfWarehouses = 12;
    double mixingRatio = 0.1;
    int seedSize;
    int xMax = 0;
    int xMin = 0;
    int yMax = 0;
    int yMin = 0;
    double costDifference = 10E3;
    double bestCost = 10E5;
    Point[] customerPoints = new Point[numOfPoints];
    List<Solution> solutions;
    Solution badSol = new Solution(new Point[numOfWarehouses], 100000);

    /**
     * Default constructor
     */
    public GeneticLegal() {
        // Default seed size
        seedSize = 1000;
        convexHull();
        solutions = new ArrayList<>();
    }

    /**
     * Constructor with specified seedSize
     *
     * @param seedSize
     */
    public GeneticLegal(int seedSize) {
        this.seedSize = seedSize;
        convexHull();
        solutions = new ArrayList<>();
    }

    /**
     * 'main' method, used to run without need to run the file itself
     */
    public void call() {
        int iteration = 1;
        generate();
        sort();
        // While iteration difference is non-negligible

        while ((solutions.get(0).getCost() > 57100) || (solutions.get(0).getCost() == -1)) {
            breed();
            sort();
            /* For inidivdual solution tracing
            for(Solution solution : solutions){
                System.out.println(solution.toString());
            }
            */
            System.out.print("Iteration " + iteration + ": ");
            System.out.println(solutions.get(0).toString());
            iteration++;
        }
        System.out.println(solutions.get(0).getCost());

    }

    public boolean isLegal(Solution s){
        if((cost(s.getPoints())[1] == 0) || (outOfConvex(s))){return false;}
        else {return true;}
    }

    public boolean isLegal(Point[] points){
        if(((cost(points)[1]) == 0) || (outOfConvex(points))){return false;}
        else {return true;}
    }

    public void sort() {
        for (int x = 0; x < seedSize; x++) {
            if(!isLegal(solutions.get(x))){
                solutions.get(x).setLegal(false);
            }
        }
        solutions.sort(Comparator.comparing(Solution::getCost));
    }

    public static void main(String[] args) {
        GeneticLegal genetic = new GeneticLegal(1000);
        genetic.call();
    }

    /**
     * Generates random legal solutions for the initial seed
     */
    public void generate() {
        Point[] points;
        Random gen = new Random();
        boolean legal;
        System.out.println("Generating.......");
        // For each
        for (int i = 0; i < seedSize; i++) {
            System.out.println("Currently generating set: " + i);
            legal = false;
            // Sets up an array for a new set of points
            points = new Point[numOfWarehouses];
            while (!legal) {
                // Generate a set of points
                for (int curPoint = 0; curPoint < numOfWarehouses; curPoint++) {
                    points[curPoint] = new Point(gen.nextInt(xMax - xMin + 1) + xMin, gen.nextInt(yMax - yMin + 1) + yMin);
                }
                // If the points are legal, break while loop
                if (isLegal(points)) {
                    legal = true;
                }
            }
            solutions.add(new Solution(points, cost(points)[0]));
        }

    }


    /**
     * Master method - contains logic for weighting crossover vs mutation
     * Uses the current method:
     * Untouched - 5%
     * Mutated - 45%
     * Children - 50%
     */
    public void breed() {
        // First Quarter untouched (successful solutions)
        Random rand = new Random();
        // Second Quarter mutated for possibly better results
        for (int cur = seedSize / 20; cur < seedSize / 2; cur++) {
            solutions.set(cur, mutate(solutions.get(cur)));
        }
        // Third and fourth replaced with newly crossed solutions
        for (int cur = seedSize / 2; cur < seedSize; cur++) {
            solutions.set(cur, crossover(solutions.get(rand.nextInt(100)), solutions.get(rand.nextInt(100))));
        }
    }

    /**
     * Mutate possible solutions through mostly minor alterations
     */
    public Solution mutate(Solution s) {
        boolean legal = false;
        int count = 0;
        Solution copy;
        while (!legal && count < 100) {
            copy = s;
            // For each point in the solution
            for (Point p : copy.getPoints()) {
                // Mutate randomly either up or down
                p.x += upOrDown();
                p.y += upOrDown();
            }
            if (isLegal(copy)) {
                return copy;
            }
            count++;
        }
        return s;
    }

    /**
     * Randomly returns either + or - 1
     *
     * @return +/- 1
     */
    public int upOrDown() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Crossover genes between solutions using uniform selection (random per point)
     */
    public Solution crossover(Solution parent1, Solution parent2) {
        Random rand = new Random();

        int sharedMin = Math.min(parent1.getPoints().length, parent2.getPoints().length);
        Solution child;
        Point[] warehouses = new Point[sharedMin];
        for (int cur = 0; cur < sharedMin; cur++) {
            if (rand.nextBoolean()) {
                warehouses[cur] = parent1.getPoints()[cur];
            } else {
                warehouses[cur] = parent2.getPoints()[cur];
            }
        }
        child = new Solution(warehouses, cost(warehouses)[0]);
        return child;
    }

    // Code meant for a uniform try
    /*while(!legal) {
        for (int cur = 0; cur < sharedMin; cur++) {
            if (rand.nextBoolean()) {
                warehouses[cur] = parent1.getPoints()[cur];
            } else {
                warehouses[cur] = parent2.getPoints()[cur];
            }
        }
        if(cost(warehouses)[1] == 1) {legal = true;}
    }*/

    public void convexHull() {
        try {
            Scanner scan = new Scanner(new File("src/p317.txt"));

            int n = Integer.valueOf(scan.nextLine());

            for (int count = 0; count < n; count++) {

                String coords = scan.nextLine();
                String[] xy = coords.split(" ");
                int x = Integer.valueOf(xy[0]);
                int y = Integer.valueOf(xy[1]);

                if (x > xMax) {
                    xMax = x;
                } else if (x < xMin) {
                    xMin = x;
                }
                if (y > yMax) {
                    yMax = y;
                } else if (y < yMin) {
                    yMin = y;
                }
                customerPoints[count] = new Point(x, y);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }

    }

    public boolean outOfConvex(Solution s){
        boolean out = false;
        for(Point p : s.getPoints()) {
            if ((xMin > p.x || xMax < p.x) || (yMin > p.y || yMax < p.y)) {out = true;}
        }
        return out;
    }

    public boolean outOfConvex(Point[] points){
        boolean out = false;
        for(Point p : points) {
            if ((xMin > p.x || xMax < p.x) || (yMin > p.y || yMax < p.y)) {out = true;}
        }
        return out;
    }

    public double[] cost(Point[] warehouses) {
        double[] results = new double[2];
        // Assume legal
        results[1] = 1;

        // Fixed cost = number of warehouses * cost per warehouse (2500?)
        double fixedCost = warehouses.length * 2500;
        double distanceCost = 0;

        // Loop through each customer point and find the nearest warehouse to minimise cost
        for (int curPoint = 0; curPoint < numOfPoints; curPoint++) {

            // In-scope variables
            double minDistance = 10E5;
            //Point closestWHouse;

            // Check the closes warehouse to each point by individually checking each one
            for (int warehouse = 0; warehouse < warehouses.length; warehouse++) {
                double distance = Point.distanceEuclidean(customerPoints[curPoint], warehouses[warehouse]);
                if (distance < minDistance) {
                    //closestWHouse = warehouses[warehouse];
                    minDistance = distance;
                }
            }
            // Minimum distance from current customer point to another warehouse
            if (minDistance > 150) {
                results[1] = 0;
            }
            distanceCost += minDistance;
        }
        results[0] = (distanceCost + fixedCost);
        return results;
    }


}
