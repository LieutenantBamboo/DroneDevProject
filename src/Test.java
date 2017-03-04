import java.util.Random;

/**
 * Created by matthewconnorday on 02/03/17.
 */
public class Test {

    int xMin = 5;
    int xMax = 10;

    public static void main(String[] args) {
        Test test = new Test();
        test.go();
    }

    public void go(){
        Random gen = new Random();
        for(int x = 0; x < 100; x++) {
            System.out.println(gen.nextInt(xMax - xMin + 1) + xMin);
        }
    }
}
