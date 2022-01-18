import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Driver
{
	private static final JFrame gui = new JFrame("Minesweeper");
	private static Board gameBoard;
	private static Map map;
	private static int difficulty = 0;
	private static Counter clock;

	public static void main(String[] args)
	{
		map = new Map();
		MapGenerator.initSizes();
		Tile.initColors();
		gameBoard = new Board(new int[1][1]);

		gui.setContentPane(map.panel);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setResizable(false);
		gui.pack();
		gui.setVisible(true);

		EventQueue.invokeLater(() -> clock = new Counter(map.time));
	}

	/**
	 * Sets the game board
	 */
	public static void newGame()
	{
		gameBoard = MapGenerator.newGame(difficulty);
		gameBoard.resetFlags();
		clock.start();
		HashMap<Integer, int[]> sizes = MapGenerator.getSizes();
		map.setSize(2);
		int boardSize = map.getSize();
		Tile.setSize(boardSize / MapGenerator.getSizes().get(difficulty)[0]);
		map.body.setLayout(new GridLayout(sizes.get(difficulty)[1],
				sizes.get(difficulty)[0], 0, 0));
		render();
	}

	/**
	 * Refreshes the window.
	 */
	public static void render()
	{
		if (gameBoard.checkWin())
			clock.stop();
		map.body.removeAll();
		for (Tile[] row : gameBoard.getBoard())
			for (Tile tile : row)
				map.body.add(tile.getButton());
		map.setProgress(Tile.getFlags(), MapGenerator.getSizes().get(difficulty)[2]);
		gui.revalidate();
		gui.repaint();
	}

	/**
	 * Shows all pieces on the board
	 */
	public static void gameOver()
	{
		for (Tile[] row : gameBoard.getBoard())
			for (Tile tile : row)
				tile.show(true);
		clock.stop();
	}

	/**
	 * Sets the difficulty level for the next generated map
	 *
	 * @param difficulty 0-5 difficulty level
	 */
	public static void setDifficulty(int difficulty)
	{
		Driver.difficulty = difficulty;
	}

	public static void showSurroundingTiles(int ID)
	{
		Tile[][] board = gameBoard.getBoard();
		int[] coords = gameBoard.getTileCoords(ID);
		int i = coords[0], j = coords[1], h = board.length, w = board[0].length;

		for (int a = i - 1; a <= i + 1; a++)
			for (int b = j - 1; b <= j + 1; b++)
				if (a >= 0 && b >= 0 && a < h && b < w)
					board[a][b].show(false);
	}

	public static int getFlaggedNeighbors(int ID)
	{
		Tile[][] board = gameBoard.getBoard();
		int[] coords = gameBoard.getTileCoords(ID);
		int flags = 0, i = coords[0], j = coords[1], h = board.length, w = board[0].length;

		for (int a = i - 1; a <= i + 1; a++)
			for (int b = j - 1; b <= j + 1; b++)
				if (a >= 0 && b >= 0 && a < h && b < w)
					if (board[a][b].getState() == 2)
						flags++;
		return flags;
	}

	public static void showUnflaggedTiles(int ID)
	{
		Tile[][] board = gameBoard.getBoard();
		int[] coords = gameBoard.getTileCoords(ID);
		int i = coords[0], j = coords[1], h = board.length, w = board[0].length;
		for (int a = i - 1; a <= i + 1; a++)
			for (int b = j - 1; b <= j + 1; b++)
				if (a >= 0 && b >= 0 && a < h && b < w && board[a][b].getState() == 0)
					board[a][b].show(false);
	}


}

