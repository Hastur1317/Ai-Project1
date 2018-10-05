import java.util.HashMap;
import java.util.Set;

public class AStar
{
	private FakeTree tree;
	private HashMap<Integer, String[]> turns;
	
	public AStar(String state, boolean h1)
	{
		tree = new FakeTree(new Puzzle(state), h1);
		turns = new HashMap<Integer, String[]>();
	}
	
	public int solve()
	{
		String[] start = tree.getString().split("\\r?\\n");
		turns.put(tree.getDepth(), start);
		while(true)
		{
			int x = tree.makeMove();
			if(turns.containsKey(tree.getDepth()))
			{
				String[] rows2 = tree.getString().split("\\r?\\n");
				String[] rows = turns.get(tree.getDepth());
				rows[0] = rows[0] + "\t" + rows2[0];
				rows[1] = rows[1] + "\t" + rows2[1];
				rows[2] = rows[2] + "\t" + rows2[2];
				turns.put(tree.getDepth(), rows);
			}
			else
			{
				String[] rows = tree.getString().split("\\r?\\n");
				turns.put(tree.getDepth(), rows);
			}
			if(x == 0)
			{
				return tree.getCost();
			}
		}
	}
	
	public int getDepth()
	{
		return tree.getDepth();
	}
	
	public void print()
	{
		for(int x=0; x<=tree.getDepth(); x++)
		{
			String[] rows = turns.get(x);
			System.out.print("\ndepth\t" + rows[0] + "\n" +
								x + "\t" + rows[1] + "\n\t" +
								rows[2] + "\n");
		}
	}
	
}

class FakeTree
{
	private Puzzle puzzle;
	private HashMap<String, Info> states;
	private HashMap<String, Info> moves;
	private boolean h1;
	private int cost;
	
	public FakeTree(Puzzle puzzle, boolean h1)
	{
		this.puzzle = puzzle;
		states = new HashMap<String, Info>();
		moves = new HashMap<String, Info>();
		this.h1 = h1;
		states.put(puzzle.stateString(), new Info(0, puzzle.getHeuristic(h1)));
		cost = 1;
		fillMoves();
	}
	
	private void fillMoves()
	{
		int depth = states.get(puzzle.stateString()).getDepth() + 1;
		int empty = puzzle.getEmpty();
		//up
		if(empty/3 > 0)
		{
			puzzle.move(1);
			String key = puzzle.stateString();
			if(!states.containsKey(key) && !moves.containsKey(key))
			{
				moves.put(key, new Info(depth, puzzle.getHeuristic(h1)));
				cost++;
			}
			puzzle.move(3);
		}
		//down
		if(empty /3 < 2)
		{
			puzzle.move(3);
			String key = puzzle.stateString();
			if(!states.containsKey(key) && !moves.containsKey(key))
			{
				moves.put(key, new Info(depth, puzzle.getHeuristic(h1)));
				cost++;
			}
			puzzle.move(1);
		}
		//right
		if(empty %3 < 2)
		{
			puzzle.move(2);
			String key = puzzle.stateString();
			if(!states.containsKey(key) && !moves.containsKey(key))
			{
				moves.put(key, new Info(depth, puzzle.getHeuristic(h1)));
				cost++;
			}
			puzzle.move(4);
		}
		//left
		if(empty %3 > 0)
		{
			puzzle.move(4);
			String key = puzzle.stateString();
			if(!states.containsKey(key) && !moves.containsKey(key))
			{
				moves.put(key, new Info(depth, puzzle.getHeuristic(h1)));
				cost++;
			}
			puzzle.move(2);
		}
	}

	public int makeMove()
	{
		Set<String> keys = moves.keySet();
		String bestMove = null;
		int f = Integer.MAX_VALUE;
		if(moves.size() == 0)
		{
			System.out.println("Out of Moves");
			System.out.println(moves.toString() + "\n" + states.toString());
			System.exit(0);
		}
		for(String key : keys)
		{
			if(f == moves.get(key).getF())
			{
				if(Math.random() >=0.5)
				{
					bestMove = key;
					f = moves.get(key).getH();
				}
			}
			
			if(f > moves.get(key).getF())
			{
				bestMove = key;
				f = moves.get(key).getF();
			}
		}
		if(bestMove == null)
		{
			System.out.println("No best Move"); //unlikely
			System.exit(0);
		}
		states.put(bestMove, moves.get(bestMove));
		moves.remove(bestMove);
		puzzle.setFill(bestMove.split(" "));
		fillMoves();
		return states.get(bestMove).getH();
	}
	
	public String getString()
	{
		return puzzle.toString();
	}
	
	public int getDepth()
	{
		return states.get(puzzle.stateString()).getDepth();
	}
	
	public int getCost()
	{
		return cost;
	}
	
}

class Info
{
	private int depth;
	private int h;
	private int f;
	
	public Info(int g, int h)
	{
		this.h = h;
		depth = g;
		f = g + h;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public int getH()
	{
		return h;
	}
	
	public int getF()
	{
		return f;
	}
	
}