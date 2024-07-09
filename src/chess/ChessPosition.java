package chess;

import boardGame.Position;

public class ChessPosition {
	private char column; // char pois vai de A a H
	private int row;
	
	
	//construtor
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException(" Erro instanciando ChessPosition. Valores validos de a1 a h8");
		}
		this.column = column;
		this.row = row;
	}


	
	//getters e setters
	public char getColumn() {
		return column;
	}


	public int getRow() {
		return row;
	}
	
	//metodos
	protected Position toPosition() {
		return new Position(8-row, column - 'a');	
	}
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	@Override 
	public String toString() {
		return "" + column + row;
	}



	
	
	
	
	
}