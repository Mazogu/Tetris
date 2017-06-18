import java.awt.Color;
import java.awt.Dimension;
/**
 * The z piece that faces right in a game of tetris
 * @author Michael Azogu
 * @version 1.0 4/12/12
 */


public class ZPieceRight extends Piece{
	private static int[][] model1= new int[][]{
		{0,0,0,0},
		{0,0,1,1},
		{0,1,1,0,},
		{0,0,0,0,}};
	private static int[][] model2 = new int[][]{
		{0,0,0,0},
		{0,1,0,0},
		{0,1,1,0,},
		{0,0,1,0,}};
	private static int[][] model3 = new int[][]{
		{0,0,0,0},
		{0,1,1,0},
		{1,1,0,0,},
		{0,0,0,0,}};
	private static int[][] model4 = new int[][]{
		{0,1,0,0},
		{0,1,1,0},
		{0,0,1,0,},
		{0,0,0,0,}};
	private static int[][][] modelZr = {model1,model2,model3,model4};


	/**
	 * the constructor of the right z piece
	 * @param x the x position
	 * @param y	the y position
	 * @param tileDimension the size to scale the piece to
	 * @param field	the tetris board the piece will enter
	 */

	public ZPieceRight(int x, int y,Dimension tileDimension, PlayingField field) {
		super(x, y, new Color(0,200,255), field,modelZr);




	}
	/**
	 * a toString method
	 */
	public String toString(){
		return "RightZ";
	}
}
