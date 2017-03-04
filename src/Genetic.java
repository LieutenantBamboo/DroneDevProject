import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by matthewconnorday on 01/03/17.
 */
public class Genetic {

    int numOfPoints = 317;
    int numOfWarehouses = 12;
    double mixingRatio = 0.1;
    int seedSize;
    int xMax;
    int yMax;
    double costDifference = 10E3;
    double bestCost = 10E5;
    Point[] customerPoints = new Point[numOfPoints];
    List<Solution> solutions;

    /**
     * Default constructor
     */
    public Genetic() {
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
    public Genetic(int seedSize) {
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

        while (solutions.get(0).getCost() > 50000) {
            breed();
            sort();
            bestCost = solutions.get(0).getCost();
            /* For inidivdual solution tracing
            for(Solution solution : solutions){
                System.out.println(solution.toString());
            }
            */
            System.out.print("Iteration " + iteration + ": ");
            System.out.println(bestCost);
            iteration++;
        }
        System.out.println(bestCost);

    }

    public void sort(){
        solutions.sort(Comparator.comparing(Solution::getCost));
    }

    public static void main(String[] args) {
        Genetic genetic = new Genetic(100000);
        genetic.call();
    }

    /**
     * Generates random legal solutions for the initial seed
     */
    public void generate() {
        Point[] points;
        Random gen = new Random();
        for (int i = 0; i < seedSize; i++) {
            points = new Point[numOfWarehouses];
            for (int curPoint = 0; curPoint < numOfWarehouses; curPoint++) {
                points[curPoint] = new Point(gen.nextInt(xMax), gen.nextInt(yMax));
            }
            solutions.add(new Solution(points, cost(points)));
        }

    }

    /**
     * Master method - contains logic for weighting crossover vs mutation
     * Uses the current method:
     * Untouched - 25%
     * Mutated - 25%
     * Children - 50%
     */
    public void breed() {
        // First Quarter untouched (successful solutions)
        Random rand = new Random();
        // Second Quarter mutated for possibly better results
        for(int cur = seedSize/10; cur < seedSize / 2; cur++){
            solutions.set(cur, mutate(solutions.get(cur)));
        }
        // Third and fourth replaced with newly crossed solutions
        for(int cur = seedSize / 2; cur < seedSize; cur++){
            solutions.set(cur, crossover(solutions.get(rand.nextInt(1000)),solutions.get(rand.nextInt(1000))));
        }
    }

    /**
     * Mutate possible solutions through mostly minor alterations
     */
    public Solution mutate(Solution s) {
        // For each point in the solution
        for(Point p : s.getPoints()) {
            // Mutate randomly either up or down
            p.x += upOrDown();
            p.y += upOrDown();
        }
        return s;
    }

    /**
     * Randomly returns either + or - 1
     * @return +/- 1
     */
    public int upOrDown(){
        Random rand = new Random();
        if(rand.nextBoolean()){return 1;}
        else{return -1;}
    }

    /**
     * Crossover genes between solutions using uniform selection (random per point)
     */
    public Solution crossover(Solution parent1, Solution parent2) {
        Random rand = new Random();

        int sharedMin = Math.min(parent1.getPoints().length, parent2.getPoints().length);
        Solution child;
        Point[] warehouses = new Point[sharedMin];

        for(int cur = 0; cur < sharedMin; cur++){
            if(rand.nextBoolean()){warehouses[cur] = parent1.getPoints()[cur];}
            else{warehouses[cur] = parent2.getPoints()[cur];}
        }
        child = new Solution(warehouses, cost(warehouses));
        return child;
    }

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
                }
                if (y > yMax) {
                    yMax = y;
                }
                customerPoints[count] = new Point(x, y);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }

    }

    // TODO - finish cost function
    public double cost(Point[] warehouses) {

        // Fixed cost = number of warehouses * cost per warehouse (2500?)
        double fixedCost = warehouses.length * 2500;
        double distanceCost = 0;

        // Loop through each customer point and find the nearest warehouse to minimise cost
        for (Point cusPoint : customerPoints) {

            // In-scope variables
            double minDistance = 10E5;
            // Point closestWHouse;

            // Check the closes warehouse to each point by individually checking each one
            for (int warehouse = 0; warehouse < warehouses.length; warehouse++) {
                double distance = Point.distanceEuclidean(cusPoint, warehouses[warehouse]);
                if (distance < minDistance) {
                    // closestWHouse = warehouses[warehouse];
                    minDistance = distance;
                }
            }
            // Minimum distance from current customer point to another warehouse
            distanceCost += minDistance;
        }

        return (distanceCost + fixedCost);
    }


}
