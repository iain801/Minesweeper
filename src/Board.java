public class Board
{
	private final Tile[][] board;

	public Board(int[][] array)
	{

		board = new Tile[array.length][array[0].length];
		//Set bombs to -1
		for (int i = 0, j = 0; i < board.length; i++, j = 0)
			for (; j < board[0].length; j++)
				board[i][j] = new Tile(array[i][j]);
		//Give other tiles neighbor values
		for (int i = 0, j = 0; i < board.length; i++, j = 0)
			for (; j < board[0].length; j++)
				if (board[i][j].getNeighbors() != -1)
					board[i][j].setNeighbors(findAdjacentBombs(i, j));
	}

	public Tile[][] getBoard()
	{
		return board;
	}

	public int[] getTileCoords(int ID)
	{
		int i = 0, h = board.length, w = board[0].length;
		for (; i < h; i++)
			for (int j = 0; j < w; j++)
				if (board[i][j].getID() == ID)
					return new int[]{i, j};
		return new int[]{-1, -1};
	}

	private int findAdjacentBombs(int i, int j)
	{
		int h = board.length;
		int w = board[0].length;
		int count = 0;
		for (int a = i - 1; a <= i + 1; a++)
			for (int b = j - 1; b <= j + 1; b++)
				if (a >= 0 && b >= 0 && a < h && b < w)
					if (board[a][b].getNeighbors() == -1)
						count++;
		return count;
	}

	public void resetFlags()
	{
		Tile.setFlags(0);
	}

	public boolean checkWin()
	{
		for (Tile[] row : board)
			for (Tile tile : row)
				if (tile.getNeighbors() == -1 && tile.getState() != 2)
					return false;
		return true;
	}

}
