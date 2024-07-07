package chess;
import boardGame.Board;
import boardGame.Piece;

public class ChessPiece extends Piece {
	private Color color;

	//Construtor
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	//get
	public Color getColor() {
		return color;
	}

	
	
	
}
