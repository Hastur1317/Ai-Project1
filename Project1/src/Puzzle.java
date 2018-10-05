import java.util.ArrayList;
import java.util.Collections;


public class Puzzle
{
	private int[] puzzle;
	int empty;
	
	public Puzzle()
	{
		puzzle = new int[9];
		randomFill();
	}
	
	public Puzzle(String state)
	{
		puzzle = new int[9];
		String[] input = state.split(" ");
		if(input.length != 9)
		{
			randomFill();
		}
		else
		{
			setFill(input);
		}
	}
	
	private void randomFill()
	{
		ArrayList<Integer> reference = new ArrayList<Integer>();
		reference.add(0);
		reference.add(1);
		reference.add(2);
		reference.add(3);
		reference.add(4);
		reference.add(5);
		reference.add(6);
		reference.add(7);
		reference.add(8);
		while(true)
		{
			Collections.shuffle(reference);
			if( isSolvable(reference) )
			{
				for(int x =0; x<9; x++)
				{
					puzzle[x] = reference.get(x).intValue();
					if(puzzle[x] == 0)
					{
						 empty=x;
					}
				}
				return;
			}
		}
	}
	
	public void setFill(String[] tiles)
	{
		for(int x = 0; x<9; x++)
		{
			 puzzle[x] = Integer.parseInt(tiles[x]);
			 if(puzzle[x] == 0)
				 empty=x;
		}
	}

	public static boolean isSolvable(ArrayList<Integer> reference)
	{
		int inversions = 0;
		for(int x = 0; x<8; x++)
		{
			for(int y=x+1; y<9; y++)
			{
				if(reference.get(x) == 0 || reference.get(y) == 0)
				{
					continue;
				}
				if(reference.get(x) > reference.get(y))
				{
					inversions++;
				}
			}
		}
		if(inversions % 2 == 0)
			return true;
		return false;
	}
	
	public String toString()
	{
		return  "[ " + puzzle[0] + " ] " + "[ " + puzzle[1] + " ] " + "[ " + puzzle[2] + " ] \n" +
				"[ " + puzzle[3] + " ] " + "[ " + puzzle[4] + " ] " + "[ " + puzzle[5] + " ] \n" +
				"[ " + puzzle[6] + " ] " + "[ " + puzzle[7] + " ] " + "[ " + puzzle[8] + " ] \n";
	}
	
	public String stateString()
	{
		return  puzzle[0] + " " + puzzle[1] + " " + puzzle[2] + " " + 
				puzzle[3] + " " + puzzle[4] + " " + puzzle[5] + " " +
				puzzle[6] + " " + puzzle[7] + " " + puzzle[8];
	}
	
	public int getEmpty()
	{
		return empty;
	}
	
	//Directions
	//d = 1
	//  4   2
	//    3
	public void move(int d)
	{
		
		if(d == 2) //right
		{
			puzzle[empty] = puzzle[empty+1];
			puzzle[empty+1] = 0;
			empty = empty+1;
		}
		else if(d == 1) //up
		{
			puzzle[empty] = puzzle[empty-3];
			puzzle[empty-3] = 0;
			empty = empty-3;
		}
		else if(d == 4) //Left
		{
			puzzle[empty] = puzzle[empty-1];
			puzzle[empty-1] = 0;
			empty = empty-1;
		}
		else //Down
		{
			puzzle[empty] = puzzle[empty+3];
			puzzle[empty+3] = 0;
			empty = empty+3;
		}
	}
	
	public int getHeuristic(boolean h1)
	{
		int h = 0;
		
		if(h1)
		{
			for(int x=0; x<9; x++)
			{
				if(puzzle[x] != x)
					h++;
			}
		}
		else
		{
			for(int x=0; x<9; x++)
			{
				//off by x
				h = h + Math.abs(((puzzle[x] % 3) - (x % 3)));
				
				//off by y
				h = h + Math.abs(((puzzle[x] / 3) - (x / 3)));
			}
		}
		
		return h;
	}
	
	
}
