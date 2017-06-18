import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;


public class SidePanel extends JPanel{
	String[] boardBox;
	String[] activeBox;
	private int score;
	private int level;
	private String savedActive;
	private JButton save;
	private Piece active;
	private JButton load;
	private JLabel scoreLabel;
	private JLabel levelLabel;
	private Piece change;
	private Timer copyTimer;
	private int delay = 1000;
	private int loadedScore;
	private Dimension tileDimension;
	private JPanel control;
	private JPanel drawPanel;
	private Graphics h;
	private File saveFile;
	private PlayingField board;
	private boolean loaded;
	SidePanel(){
		setBackground(Color.white);
		setLayout(new BorderLayout());
		control = new JPanel();
		drawPanel = new JPanel();
		control.setLayout(new GridLayout(2,2));
		save = new JButton("Save");
		load = new JButton("Load");
		scoreLabel = new JLabel("Score: 0");
		levelLabel = new JLabel("Level: 1");
		copyTimer = new Timer(10,new copyListener());
		save.addActionListener(new saveListener());
		load.addActionListener(new loadListener());
		setPreferredSize(new Dimension(220,100));
		control.add(scoreLabel);
		control.add(levelLabel);
		control.add(save);
		control.add(load);
		add(control, BorderLayout.SOUTH);
		//add(drawPanel, BorderLayout.CENTER);
		h = drawPanel.getGraphics();
		loaded = false;
		copyTimer.start();
		saveFile = new File("tetris.txt");
	}
	/**
	 * draws the next piece on the side panel
	 */
	public void paintComponent(Graphics g){
		setBackground(Color.white);
		super.paintComponent(g);
		int[][] model = change.getModel();
		for(int col=0;col<4;col++){
			for(int row=0;row<4;row++){
				if(model[col][row]==1){
					g.setColor(change.getColor());
					g.fillRect((PlayingField.WIDTH/3+row)*tileDimension.width,
							(PlayingField.HEIGHT/2+col)*tileDimension.height,
							tileDimension.width,
							tileDimension.height);
				}
			}	
		}	
		//change.draw(g, tileDimension);
		//possibly redraw squares, get model info.
	}
	/**
	 * a setter for the tile dimension
	 * @param tileDimension the current tiledimension
	 */
	public void setTileDimension(Dimension tileDimension){
		this.tileDimension = tileDimension;
	}
	/**
	 * sets the score
	 * @param score the score
	 */
	public void setScore(int score){
		this.score = score;
	}
	/**
	 * a boolean stating whether or not the game was loaded
	 * @return a boolean
	 */
	public boolean isLoad(){
		return loaded;
	}
	/**
	 * returns the score
	 * @return the current score
	 */
	public int getScore(){
		return loadedScore;
	}
	/**
	 * returns the level
	 * @return current level
	 */
	public int getLevel(){
		return level;
	}
	/**
	 * makes loaded false
	 */
	public void noLongerLoaded(){
		loaded = false;
	}
	/**
	 * sets the board object
	 * @param board a copy of the board
	 */
	public void setBoard(PlayingField board){
		this.board = board;
	}
	/**
	 * sets the level
	 * @param level the current level
	 */
	public void setLevel(int level){
		this.level = level;
	}
	/**
	 * sets the delay
	 * @param delay the current delay
	 */
	public void setDelay(int delay){
		this.delay = delay;
	}
	/**
	 * the future piece
	 * @param change
	 */
	public void setPiece(Piece change){
		this.change = change;
		repaint();
		//change.move(new Point(PlayingField.WIDTH/2,PlayingField.HEIGHT/2));
	}
	/**
	 * Gets the active piece from playing field and stores it in active.
	 * @param active
	 */
	public void setActive(Piece active){
		this.active = active;
	}
	/**
	 * a string array representing the saved board
	 * @return a string array
	 */
	public PlayingField getNewBoard(){
		return board;
		
	}
	/**
	 * a string array containing information about the saved piece
	 * @return a string array
	 */
	public String[] getNewPiece(){
		return activeBox;
	}
	/**
	 * a timer that's synched with the playing panel timer.
	 * @author Michael Azogu
	 * @version 1.0 4/22/12
	 */
	private class copyListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			copyTimer.setDelay(delay);
			scoreLabel.setText("Score: "+score);
			levelLabel.setText("Level: " + level);
//			repaint();
			
		}
	}
	/**
	 * an action listener for the save button, saves the board, score, and active piece to a text file
	 * @author Michael Azogu
	 * @version 1.0 4/22/12
	 */
	private class saveListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try{
				FileOutputStream save = new FileOutputStream("tetris.txt",false);
				ObjectOutputStream saveBoard = new ObjectOutputStream(save);
				saveBoard.writeObject(board);
				save.close();
				saveBoard.close();
				loaded = false;
				}	
			catch(IOException e){
					JOptionPane.showMessageDialog(drawPanel, "Save failed");
			}	
		}
	}
	/**
	 * reads a text file and loads the board, or at least it's supposed to. It doesn't show though
	 * @author Michael Azogu
	 * @version 1.0 4/22/12
	 */
	private class loadListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try{
				if(!loaded){
					FileInputStream load = new FileInputStream("tetris.txt");
					ObjectInputStream newBoard = new ObjectInputStream(load);
					board = (PlayingField) newBoard.readObject();
					load.close();
					newBoard.close();
					loaded = true;
					score = board.getScore();
				}
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(drawPanel, "Load Failed");
			}
			catch(ClassNotFoundException c){
				JOptionPane.showMessageDialog(drawPanel, "Something Broke");
			}
			
		}
	}
}
