
import javax.swing.*;
import java.awt.*;
/**
 * The driver for the tetris game
 * @author Michael Azogu
 * @version 1.0 4/12/12
 */
public class Tetris{
	/**
	 * The main method. Sets up the JFrame and starts the game.
	 * @param args
	 */
	public static void main(String[] args){
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		SidePanel system = new SidePanel();
		PlayingPanel panel = new PlayingPanel(system,"");
		frame.setLayout(new BorderLayout());
		//panel.setPreferredSize(new Dimension(200,440));
		
		frame.setLayout(new BorderLayout());
		panel.setFocusable(true);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(system, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
		panel.gameStart();
		
	}
}
