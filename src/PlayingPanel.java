import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

/**
 * The panel the tetris game is played on.
 * @author Michael Azogu
 * @version 1.0 4/12/12
 */


public class PlayingPanel extends JPanel{
	private PlayingField board;
	private Dimension tileDimension;
	private int delay;
	private int currlevel=1;
	private Timer timer;
	private Piece active;
	private Piece future;
	private SidePanel somethingPanel;
	Random rand = new Random();
	private String loadState;
	
	/**
	 * The playing panel constructor. Makes a new playing field and sets up the
	 * timer
	 */

	PlayingPanel(SidePanel somethingPanel,String loadState) {
		super();
		delay = 1000;
		this.somethingPanel = somethingPanel;
		timer = new Timer(delay, new dropListener());
		// tileDimension = new Dimension(20,20);
		// setPreferredSize(new Dimension
		// (board.WIDTH*tileDimension.width,board.HEIGHT*tileDimension.height));
		setPreferredSize(new Dimension (200,440));
		tileDimension = new Dimension(200 / PlayingField.WIDTH, 440 / PlayingField.HEIGHT);
		somethingPanel.setTileDimension(tileDimension);
		addKeyListener(new moveListener());
		this.loadState = loadState;
	}
	/**
	 * starts the game timer
	 */
	public void gameStart(){
		board = new PlayingField(tileDimension);
//		board.draw(getGraphics());
		somethingPanel.setBoard(board);
		determinePiece();
		setPiece();
//		active.draw(getGraphics(),tileDimension);
		delay = 1000;
		timer.start();

	}
	public void resize(){
		tileDimension = new Dimension(getWidth()/PlayingField.WIDTH,getHeight()/PlayingField.HEIGHT);
		
	}
	/**
	 * creates a new active piece
	 */
	private void determinePiece(){
		int q = rand.nextInt(7);
		switch (q){
		case 0: future = new LinePiece(2,-1,tileDimension,board);
		break;
		case 1: future = new SquarePiece(2,-1,tileDimension,board);
		break;
		case 2: future = new ZPieceRight(2,-1,tileDimension,board);
		break;
		case 3: future = new ZPieceLeft(2,-1,tileDimension,board);
		break;
		case 4: future = new TPiece(2,-1,tileDimension,board);
		break;
		case 5: future = new LPieceRight(2,-1,tileDimension,board);
		break;
		case 6: future = new LPieceLeft(2,-1,tileDimension,board);
		break;
		}
		somethingPanel.setPiece(future);
	}	
	/**
	 * makes the future piece the current one.
	 */
	public void setPiece(){
		active = future;
	}
	/**
	 * draws the board when invoked
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		resize();
		board.setDimension(tileDimension);
		board.draw(g);
		active.draw(g,tileDimension);
	}
	/**
	 * Creates a new Piece object based on the piece that was previously saved.
	 * @param activeString A string array from the loaded text file
	 */
	public void loadedPiece(String[] activeString){
		System.out.println(activeString[0]);
		if(activeString[0].equals("Line")){
			future = new LinePiece(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
		else if(activeString[0].equals("Square")){
			future = new SquarePiece(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
		else if(activeString[0].equals("RightZ")){
			future = new ZPieceRight(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
		else if(activeString[0].equals("LeftZ")){
			future = new ZPieceLeft(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
		else if(activeString[0].equals("RightL")){
			future = new LPieceRight(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
		else if(activeString[0].equals("LeftL")){
			future = new LPieceLeft(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
		else if(activeString[0].equals("T")){
			future = new TPiece(Integer.parseInt(activeString[1]),Integer.parseInt(activeString[2]),tileDimension,board);
		}
	}
	/**
	 * a getter method for the timer delay
	 * @return the current delay
	 */
	public int getDelay(){
		return delay;
	}
	/**
	 * a getter for the current active piece
	 * @return the active piece
	 */
	public Piece ActivePiece(){
		return active;
	}
	/**
	 * a getter for the tile dimension
	 * @return the tiledimension
	 */
	public Dimension getDimension(){
		return tileDimension;
	}
	/**
	 * a getter for the field
	 * @return the field
	 */
	public PlayingField getField(){
		return board;
	}
	/**
	 * a timer to make the pieces drop
	 * @author Michael Azogu
	 * @version 1.0 4/12/12
	 */
	private class dropListener implements ActionListener{
		/**
		 * Moves the piece by 1 every time the timer is called. Checks for clears and if the stack gets too high  
		 */
		public void actionPerformed(ActionEvent event){
			if(somethingPanel.isLoad()){
				System.out.println("hhhhm");
				board = somethingPanel.getNewBoard();
				//determinePiece();
				repaint();
				requestFocus();
				somethingPanel.noLongerLoaded();
				
			}
			if(active==future){
				determinePiece();
			}
			Point location = active.getLocation();
			Point newLocation = new Point(location.x,location.y+1);
			if(!active.move(newLocation)){
				active.settle();
				//System.out.println(board.getScore());
				//System.out.println(board.getLevel());
				if(board.getLevel()>currlevel){
					currlevel = board.getLevel();
					//if(currlevel>3){
						//delay /=2;
					//}
					if(delay>100){	
						delay -= currlevel^2*80;
					}
				}
				timer.setDelay(delay);
				if(board.isLossConditionMet()){
					gameStart();
				}
				board.checkForClears();
				setPiece();
				
				somethingPanel.setActive(active);
				somethingPanel.setBoard(board);
				somethingPanel.setScore(board.getScore());
				somethingPanel.setLevel(board.getLevel());
				
			}
			repaint();
			//board.draw(getGraphics());
			//active.draw(getGraphics());
			

		}

	}
	/**
	 * 
	 * @author Michael Azogu
	 * @version 1.0 4/12/12
	 */
	private class moveListener implements KeyListener{
		/**
		 * moves the pieces when the left and right arrows are pressed. rotates the piece when space is pressed.
		 */
		@Override
		public void keyPressed(KeyEvent arg0) {
			int key = arg0.getKeyCode();
			if (key==KeyEvent.VK_LEFT){
				Point location = active.getLocation();
				Point newLocation = new Point(location.x-1,location.y);
				active.move(newLocation);
				repaint();
			}
			else if(key==KeyEvent.VK_RIGHT){
				Point location = active.getLocation();
				Point newLocation = new Point(location.x+1,location.y);
				active.move(newLocation);
				repaint();
			}
			else if(key==KeyEvent.VK_SPACE){
				active.rotate();
				repaint();
			}
			else if(key==KeyEvent.VK_DOWN){
				Point location = active.getLocation();
				Point newLocation = new Point(location.x,location.y+1);
				active.move(newLocation);
				repaint();
			}	
			//active.draw(getGraphics());
			//board.draw(getGraphics());
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
