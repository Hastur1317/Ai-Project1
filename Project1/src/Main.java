import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args)
	{
		System.out.println("Enter a State String (if none is entered, Random is chosen):");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		normalRun(input);
		sc.close();
	}
	
	public static ArrayList<Integer> getTiles(String state)
	{
		if(state == "")
		{
			return null;
		}
		
		ArrayList<Integer> tiles = new ArrayList<Integer>();
		String[] input = state.split(" ");
		if(input.length != 9)
		{
			return null;
		}
		for(int x=0; x<input.length; x++)
		{
			tiles.add(Integer.parseInt(input[x]));
		}
		return tiles;
	}
	
	public static void normalRun(String input)
	{
		//test if its ok
		ArrayList<Integer> tiles = getTiles(input);
		
		if(tiles != null)
		{
			if(!Puzzle.isSolvable(tiles))
			{
				System.out.println("Puzzle is not solvable");
				System.exit(0);
			}
		}
		System.out.println("Heuristic 1");
		AStar ai = new AStar( input, true);
		ai.solve();
		ai.print();
		System.out.println("Heuristic 2");
		ai = new AStar( input, true);
		ai.solve();
		ai.print();
	}
	
	public static void largeRun(int x)
	{
		int averageCost = 0;
		int averageDepth = 0;
		double averageTime =0;
		
		for(int n=0; n<x; n++)
		{
			AStar ai = new AStar( "", true);
			double start = System.currentTimeMillis();
			int cost = ai.solve();
			double end = System.currentTimeMillis();
			end = end-start;
			int depth = ai.getDepth();
			
			averageCost = averageCost + cost;
			averageDepth = averageDepth + depth;
			averageTime = averageTime + end;
		}
		
		averageCost = averageCost /x;
		averageDepth = averageDepth /x;
		averageTime = averageTime /x;
		
		System.out.println("h1\n" + 
							"Runs = " + x + "\n" +
							"Average Depth\tAverage Cost\tAverageTime\n" + 
							averageDepth + "\t\t" + averageCost + "\t\t" + averageTime + " ms\n");
		
		averageCost = 0;
		averageDepth = 0;
		averageTime =0;
		
		for(int n=0; n<x; n++)
		{
			AStar ai = new AStar( "", false);
			double start = System.currentTimeMillis();
			int cost = ai.solve();
			double end = System.currentTimeMillis();
			end = end-start;
			int depth = ai.getDepth();
			
			averageCost = averageCost + cost;
			averageDepth = averageDepth + depth;
			averageTime = averageTime + end;
		}
		
		averageCost = averageCost /x;
		averageDepth = averageDepth /x;
		averageTime = averageTime /x;
		
		System.out.println("h2\n" + 
							"Runs = " + x + "\n" +
							"Average Depth\tAverage Cost\tAverageTime\n" + 
							averageDepth + "\t\t" + averageCost + "\t\t" + averageTime + " ms\n");
		
	}

}

