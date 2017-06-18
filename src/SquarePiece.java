import java.awt.Color;
import java.awt.Dimension;
/**
 * The square piece in a game of tetris
 * @author Michael Azogu
 * @version 1.0 4/12/12
 */


public class SquarePiece extends Piece{
	private static int[][] model1= new int[][]{
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0,},
			{0,0,0,0,}};
	private static int[][] model2 = new int[][]{
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0,},
			{0,0,0,0,}};
	private static int[][] model3 = new int[][]{
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0,},
			{0,0,0,0,}};
	private static int[][] model4 = new int[][]{
			{0,0,0,0},
			{0,1,1,0},
			{0,1,1,0,},
			{0,0,0,0,}};
	private static int[][][] modelS = {model1,model2,model3,model4};
	
	
	/**
	 * the constructor of the square piece
	 * @param x the x position
	 * @param y	the y position
	 * @param tileDimension the size to scale the piece to
	 * @param field	the tetris board the piece will enter
	 */

	public SquarePiece(int x, int y,Dimension tileDimension, PlayingField field) {
		super(x, y, new Color(0,0,255), field,modelS);
		
							
				

	}
	/**
	 * a toString method
	 */
	public String toString(){
		return "Square";
	}

}

