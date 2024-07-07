package boardGame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	
	
	//construtors
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}


	
	//getters e setters
	public int getRows() {
		return rows;
	}



	public void setRows(int rows) {
		this.rows = rows;
	}



	public int getColumns() {
		return columns;
	}



	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	//metodos
	//retorna a peça dada uma linha e uma coluna
	public Piece piece (int row, int column ){
		return pieces[row][column];
	}
	
	//retorna agora pela posição
	public Piece piece (Position position ){
		return pieces[position.getRow()][position.getColumn()];
	}
	
	
	

}
