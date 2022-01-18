import java.util.HashMap;
import java.util.Random;

class MapGenerator
{
	private static final HashMap<Integer, int[]> sizes = new HashMap<>();

	/**
	 * Fills the sizes HashMap with difficulty to map parameters
	 */
	static void initSizes()
	{
		sizes.put(0, new int[]{8, 6, 10});
		sizes.put(1, new int[]{12, 9, 20});
		sizes.put(2, new int[]{20, 15, 45});
		sizes.put(3, new int[]{24, 18, 95});
		sizes.put(4, new int[]{32, 24, 200});
	}

	/**
	 * Creates a new Board with random mines for the given difficulty
	 *
	 * @param diff The difficulty of the generated Board
	 * @return The randomly generated Board
	 */
	static Board newGame(int diff)
	{
		int[] size = sizes.get(diff);
		return new Board(placeBombs(new int[size[1]][size[0]], size[2]));
	}

	/**
	 * Places mines randomly across the board
	 *
	 * @param grid  The board represented as a 2D integer array
	 * @param bombs The number of bombs to place
	 * @return A 2D integer array with bombs(-1 values)
	 */
	private static int[][] placeBombs(int[][] grid, int bombs)
	{
		Random rd = new Random();
		for (int i = 0; i < bombs; i++)
		{
			int r = rd.nextInt(grid.length);
			int c = rd.nextInt(grid[0].length);
			if (grid[r][c] != -1)
				grid[r][c] = -1;
			else
				i--;
		}
		return grid;
	}

	static HashMap<Integer, int[]> getSizes()
	{
		return sizes;
	}

}
