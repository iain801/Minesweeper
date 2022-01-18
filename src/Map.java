import javax.swing.*;
import java.awt.*;

public class Map
{
	public JPanel panel;
	public JButton newGameButton;
	public JButton quitButton;
	public JToolBar topBar;
	public JProgressBar progressBar;
	public JComboBox difficulty;
	public JPanel body;
	public JTextField time;

	public Map()
	{
		newGameButton.addActionListener(e -> Driver.newGame());
		quitButton.addActionListener(e -> Driver.gameOver());
		difficulty.addActionListener(e -> Driver.setDifficulty(((JComboBox) e.getSource()).getSelectedIndex()));
	}

	public void setSize(int mult)
	{
		body.setPreferredSize(new Dimension(800*mult, 600*mult));
		topBar.setPreferredSize(new Dimension(800*mult, 20*mult));
	}

	public void setProgress(int flags, int total)
	{
		progressBar.setValue(flags * 100 / total);
		progressBar.setString(flags + "/" + total);
	}

	public int getSize()
	{
		return body.getHeight();
	}

}
