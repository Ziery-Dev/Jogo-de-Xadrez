package chess;
import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;

public abstract class ChessPiece extends Piece {
	private Color color;
	private int moveCount; //contagem de movimentos

	//Construtor
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	public int getMoveCount() {
		return moveCount;
	}
	
	
	//incrementar movimento
	public void increaseMoveCount() {
		moveCount++;
	}
	//decrementar movimento
	public void decreaseMoveCount() {
		moveCount--;
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
