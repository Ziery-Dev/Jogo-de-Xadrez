package boardGame;

public class Piece {
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


	
	
	
	
	
}
