import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Counter
{
	private final Timer timer;
	private final JTextField tf;
	private final SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");
	private long startTime;

	Counter(JTextField display)
	{
		timer = new Timer(53, e -> updateClock());
		tf = display;
		timer.setInitialDelay(0);
		tf.setHorizontalAlignment(JTextField.CENTER);
		updateClock();
		startTime = 0;
		timer.stop();
	}

	double getTime()
	{
		return System.currentTimeMillis() - startTime;
	}

	void start()
	{
		startTime = System.currentTimeMillis();
		timer.start();
	}

	void stop()
	{
		updateClock();
		timer.stop();
	}

	private void updateClock()
	{
		Date elapsed = new Date(System.currentTimeMillis() - startTime);
		tf.setText(date.format(elapsed));
	}
}
