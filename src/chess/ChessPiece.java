package chess;
import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;

public abstract class ChessPiece extends Piece {
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
	
	//retornar a posição da peça
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position); //convertendo a posição para ChessPosition
		
	}
	
	//saber se tem uma peça adversária numa determinada posição
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color;
	
	}
}
