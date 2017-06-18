import java.awt.*;
/**
 *A class that represents the functionality of all tetris pieces 
 *@author Michael Azogu
 *@version 1.0 4/12/12
 */
public abstract class Piece{
	protected int x,y;
	protected int[][][] model;
	protected Color color;
	protected PlayingField field;
	protected int rotation;
	public Piece(int x,int y,Color color, PlayingField field,int[][][] model){
		this.x = x;
		this.y= y;
		this.color = color;
		this.field = field;
		rotation = 0;
		this.model = model;
	}
	/**
	 * A method that moves the piece and returns a boolean value
	 * @param location
	 * @return returns whether or not the move was successful
	 */
	public boolean move(Point location){
		for(int col=0;col<4;col++){
			for(int row=0;row<4;row++){
				int newX = row +location.x;
				int newY = col + location.y;
				if (model[rotation][col][row]==1){
					if(0>newX || newX > PlayingField.WIDTH-1){
						return false;
					}	
					if (0>newY || newY>PlayingField.HEIGHT-1){
						return false;
					}	
					if(!field.isTileVacant(row+location.x, col+location.y)){
						return false;
					}
				}
			}
		}
		x = location.x;
		y = location.y;

		return true;
	}
	/**
	 * rotates the piece, returns a boolean.
	 * @return a boolean saying whether or not the piece was able to be rotated.
	 */
	public boolean rotate(){
		int copyrotate = rotation;
		rotation = (rotation+1)%4;
		if(move(new Point(x,y))){
			return true;
		}
		else{
			rotation = copyrotate;
			return false;
		}	
	}
	/**
	 * fuses the piece with the board when it stops
	 */
	public void settle(){
		for(int col=0;col<4;col++){
			for(int row=0;row<4;row++){
				if (model[rotation][row][col]==1){
					if(x>=-2 && x+col<=PlayingField.WIDTH&&y>-2&&y+row<PlayingField.HEIGHT){
						field.fillTile(x+col,y+row,color);
					}
				}
			}	
		}
	}
	/**
	 * draws the piece
	 * @param g the graphics object
	 */
	public void draw(Graphics g,Dimension tileDimension){
		for(int col=0;col<4;col++){
			for(int row=0;row<4;row++){
				if(model[rotation][col][row]==1){
					g.setColor(color);
					g.fillRect((x+row)*tileDimension.width,
							(y+col)*tileDimension.height,
							tileDimension.width,
							tileDimension.height);
				}
			}
		}
	}
	/**
	 * Creates a string object based off of the piece's current condition
	 * @return a string object
	 */
	public String toFile(){
		String savePiece = this.toString() +" " + x + " "+ y + " "+rotation;
		return savePiece;
	}
	/**
	 * a getter for the location point of the piece
	 * @return the location as a point object
	 */
	public Point getLocation(){
		Point loc = new Point(x,y);
		return loc;
	}
	/**
	 * a getter for the current model
	 * @return
	 */
	public int[][] getModel(){
		return model[rotation];
	}	
	/**
	 * a getter for the current color
	 * @return
	 */
	public Color getColor(){
		return color;
	}
}
