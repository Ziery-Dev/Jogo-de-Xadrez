package boardGame;

public abstract class Piece {
	protected Position position; 
	private Board board;
	
	
	//construtor
	public Piece(Board board) {
		this.board = board;
	}
	
	
	//getters e setters

	protected Board getBoard() { //para não ser acessado por camadas externas
		return board;
	}
	
	//possiveis movimentos da peça
	public abstract boolean [][] possibleMoves();
	
	//recebe uma posição e retorna se é possivel ou não mover-se para aquele local
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	//informa se existe pelo menos um mivimento possivel para determinada peça
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for(int j =0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
}
