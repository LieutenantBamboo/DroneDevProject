import java.util.*;

public class RandomPoints { 
  
    public static void main(String[] args) throws Exception {
	int n      = Integer.parseInt(args[0]);
	int xMax   = Integer.parseInt(args[1]);
	int yMax   = Integer.parseInt(args[2]);
	Random gen = new Random();
	System.out.println(n);
	for (int i=0;i<n;i++)
	    System.out.println(gen.nextInt(xMax) +" "+ gen.nextInt(yMax));
    }
}
