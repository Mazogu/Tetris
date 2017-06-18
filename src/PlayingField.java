import java.awt.*;
import java.io.Serializable;
/**
 * The playing field that the game is played on
 * @author Michael Azogu
 * @version 1.0 4/12/12
 */

public class PlayingField implements Serializable{
	private static final long serialVersionUID = -5458290879133625054L;
	public Color[][] board;
	final static int WIDTH = 10;
	final static int HEIGHT = 22;
	private Dimension tileDimension;
	private Color black;
	private int score;
	private static int level;
	final static int THRESHOLD = 100;
	/**
	 * the constructor.
	 * @param tileDimension the relative size of the tiles that form the pieces
	 */
	PlayingField(Dimension tileDimension,String loadState){
		this.tileDimension = tileDimension;
		black = new Color(0,0,0);
		board = new Color[HEIGHT][WIDTH];
		score = 0;
		level = 1;
		for(int row=0;row<HEIGHT;row++){
			for(int col=0;col<WIDTH;col++){
				board[row][col] = black;
			}
		}
	}	
	PlayingField(Dimension tileDimension){
		this.tileDimension = tileDimension;
		black = new Color(0,0,0);
		board = new Color[HEIGHT][WIDTH];
		score = 0;
		level = 1;
		for(int row=0;row<HEIGHT;row++){
			for(int col=0;col<WIDTH;col++){
				board[row][col] = black;
			}
		}
	}	
	/**
	 * checks to see if the bottom row is filled with colors and if so clear that row.
	 */
	public void checkForClears(){
		int row = 0;
		int rowsCleared = 0;
		for(row=HEIGHT-1;row>-1;row--){
			int count = 0;
			for(int col=0;col<WIDTH;col++){
				if(board[row][col]!=black){
					count++;
				}
				else{
					scoring(rowsCleared,HEIGHT-row);
					rowsCleared = 0;	
				}
					
			}
			if (count==WIDTH){
				int x=0;
				int y=0;
				rowsCleared++;
				for(y=row;y>0;y--){
					for(x=0;x<WIDTH;x++){
						board[y][x]=board[y-1][x];
					}
				}
				row += 1;
			}
		}
	}
	/**
	 * checks to see if the pieces pass over the upper bound of the board. 
	 * @return returns a boolean that says whether or not the current game is lost
	 */
	public boolean isLossConditionMet(){
		for(int col=0;col<WIDTH;col++){
			if(board[2][col]!=black){
				return true;
			}	
		}
		return false;
	}
	/**
	 * checks to see if the location in question is vacant
	 * @param x the x position of the point
	 * @param y the y position of the point
	 * @return whether or not the space is empty
	 */
	public boolean isTileVacant(int x, int y){
		if (board[y][x]==black)
			return true;
		return false;
	}
	/**
	 * fills the tile with the color of the active piece
	 * @param x the x position of the tile
	 * @param y the y position of the tile
	 * @param color the color of the active piece
	 */
	public void fillTile(int x, int y,Color color){
		board[y][x] = color;
	
	}
	/**
	 * draws the board and changes the color when needed.
	 * @param g the graphics object.
	 */
	public void draw(Graphics g){
		for(int col=0;col<WIDTH;col++){
			for(int row=0;row<HEIGHT;row++){
				g.setColor(board[row][col]);
				g.fillRect(col*tileDimension.width,row*tileDimension.height,tileDimension.width,tileDimension.height);
			}
		}	
	}
	
	/**
	 * a getter method for the size of the tiles
	 * @return returns the size of the tiles for outside use
	 */
	public void setDimension(Dimension tileDimension){
		this.tileDimension = tileDimension;
	}
	/**
	 * a scoring system
	 * @param numRows the amount of rows cleared at once
	 * @param row the row number
	 */
	public void scoring(int numRows, int row){
		score += numRows*numRows*(row)*THRESHOLD*Math.pow(level, 2);
		System.out.println(score);
		for(int l=level;l<100;l++){
			if(score >= (l*(l+1)*level*THRESHOLD*3)){
				level++;
			}
		}
		
	}
	/**
	 * gets information from each tile in the board and turns it into a string
	 * @return a string
	 */
	public String toFile(){
		String fileString = "";
		for(int row=0;row<HEIGHT;row++){
			for (int col=0;col<WIDTH;col++){
				fileString += board[row][col].toString();
			}
			
		}
		return fileString;
	}
	public int getScore(){
		return score;
	}
	/**
	 * a getter for the score
	 * @param score
	 */
	public void setScore(int score){
		this.score = score;
		
	}
	/**
	 * returns the current level
	 * @return the level
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * returns the board
	 * @return the current state of the board
	 */
	public Color[][] getBoard(){
		return board;
	}
	/**
	 * redefines the board based off of information passed from a string array
	 * @param loadedBoard a string array
	 */
	public void redraw(String[] loadedBoard){
		int count = 0;
		for(int row=0;row<HEIGHT;row++){
			for(int col=0;col<WIDTH;col++){
				count++;
				String nums = loadedBoard[count].replace("[", "").replace("]", "");
				String[] splitApart = nums.split(",");
				int r = Integer.parseInt(splitApart[0].substring(2));
				int g = Integer.parseInt(splitApart[1].substring(2));
				int b = Integer.parseInt(splitApart[2].substring(2));
				Color newColor = new Color(r,g,b);
				board[row][col] = newColor;
				//System.out.println(board[row][col]);
				
				//fillTile(col,row, null);
				
			}
		}
	}
	
}