import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Tile
{
	private static final HashMap<Integer, Color> tileColors = new HashMap<>();
	private static int size;
	private static int flags;
	private static int currentId = 0;
	private final int ID_NUMBER;
	private int neighbors; //-1 for bomb
	private int state; //O hidden, 1 shown, 2 flagged
	private JButton b;

	public boolean isUpdated()
	{
		return updated;
	}

	private boolean updated = true;

	public Tile(int adj)
	{
		neighbors = adj;
		ID_NUMBER = currentId++;
		state = 0;
		b = new JButton();
		b.setSize(size, size);
		b.setBackground(Color.LIGHT_GRAY);
		b.setBorder(BorderFactory.createRaisedBevelBorder());
		b.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (SwingUtilities.isRightMouseButton(e))
					if (state == 0)
						flag();
					else
						hide();
				else
					show(false);
				Driver.render();
			}
		});
	}

	public static void initColors()
	{
		tileColors.put(0, Color.LIGHT_GRAY);
		tileColors.put(1, Color.BLUE);
		tileColors.put(2, Color.GREEN);
		tileColors.put(3, Color.RED);
		tileColors.put(4, Color.BLUE.darker());
		tileColors.put(5, Color.RED.darker());
		tileColors.put(6, Color.CYAN.darker());
		tileColors.put(7, Color.BLACK);
		tileColors.put(8, Color.GRAY);
	}

	/**
	 * @param size the tile size
	 */
	public static void setSize(int size)
	{
		Tile.size = size;
	}

	/**
	 * @return Total flags on the board
	 */
	public static int getFlags()
	{
		return flags;
	}

	/**
	 * @param flags total number of flags on the board
	 */
	public static void setFlags(int flags)
	{
		Tile.flags = flags;
	}

	private void hide()
	{
		if (state == 2)
		{
			state = 0;
			flags--;
			updated = true;
		}
	}

	public void show(boolean gameOver)
	{
		if (gameOver && state != 1)
		{
			state = 1;
			updated = true;
		} else if (state == 0)
		{
			state = 1;
			updated = true;
			if (neighbors == -1)
				Driver.gameOver();
			else if (neighbors == 0)
				Driver.showSurroundingTiles(ID_NUMBER);
		} else if (state == 1 && Driver.getFlaggedNeighbors(ID_NUMBER) == neighbors)
			Driver.showUnflaggedTiles(ID_NUMBER);

	}

	private void flag()
	{
		if (state == 0)
		{
			state = 2;
			flags++;
			updated = true;
		}
	}

	/**
	 * @return Number of nearby bombs
	 */
	public int getNeighbors()
	{
		return neighbors;
	}

	/**
	 * @param neighbors number of nearby bombs
	 */
	public void setNeighbors(int neighbors)
	{
		this.neighbors = neighbors;
	}

	/**
	 * @return Tile state
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * @return Tile's unique ID number
	 */
	public int getID()
	{
		return ID_NUMBER;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Tile && ID_NUMBER == ((Tile) obj).ID_NUMBER;
	}

	public JButton getButton()
	{
		if (updated)
		{
			b.setBackground(Color.LIGHT_GRAY);
			b.setIcon(null);
			if (state == 1 && neighbors != -1)
			{
				b.setForeground(tileColors.get(neighbors));
				b.setFont(new Font("Arial", Font.PLAIN, size * 3 / 4));
				b.setText(String.valueOf(neighbors));
				b.setBorder(BorderFactory.createLoweredBevelBorder());
			} else if (state == 1)
			{
				b.setText("");
				b.setBorder(BorderFactory.createLoweredBevelBorder());
				try
				{
					b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("bomb.png"))
							.getScaledInstance(size, size, Image.SCALE_FAST)));
				} catch (Exception e)
				{
					System.out.println("File Not Found");
				}
			} else if (state == 2)
			{
				b.setText("");
				b.setBorder(BorderFactory.createRaisedBevelBorder());
				try
				{
					b.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("flag.png"))
							.getScaledInstance(size, size, Image.SCALE_FAST)));
				} catch (Exception e)
				{
					System.out.println("File Not Found");
				}
			}
			updated = false;
		}
		return b;
	}

}
