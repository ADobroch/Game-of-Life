import javax.swing.*;

public class Main {
	static int width=1640;
	static int height=1080;
	static int simwidth=1640;
	static int simheight=1080;
	public static void main(String[] args) {

		Frame f = new Frame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setResizable(true);



		width=f.getWidth();
		height=f.getWidth();
		f.createScreen();

		long lastFrame = System.currentTimeMillis();
		while(true) {

			long thisFrame = System.currentTimeMillis();
			float tslf = (float)((thisFrame - lastFrame) / 1000.0);
			lastFrame = thisFrame;

			f.update(tslf);
			f.repaint();

			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
