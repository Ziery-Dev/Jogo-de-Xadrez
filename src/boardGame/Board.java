package boardGame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	
	
	//construtors
	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) {
			throw new BoardException ("Erro criando tabuleiro, é necessário que haja pelo menos uma linha e uma coluna");
		}
		
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}


	
	//getters e setters
	public int getRows() {
		return rows;
	}


	public int getColumns() {
		return columns;
	}

	
	//metodos
	//retorna a peça dada uma linha e uma coluna
	public Piece piece (int row, int column ){
		if(!positionExists(row, column)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		
		return pieces[row][column];
	}
	
	//retorna agora pela posição
	public Piece piece (Position position ){
		if(!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	
	//responsável por colocar a peça na posição especificado do tabuleiro
	public void placePiece(Piece piece, Position position) {
		if(thereIsApiece(position)) {
			throw new BoardException("Já existe uma peça nessa posição" + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	
	//metodo de remover peças
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		} 
		
		if(piece(position) == null) {
			return null;
		}
		
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	//verifica se a posição existe
	private boolean positionExists(int row, int column) {
		return row >=0 && row < rows && column >= 0 && column < columns;
	}
	private boolean positionExists(Position position) {
		return  positionExists(position.getRow(), position.getColumn());
	}
	
	//verifica se tem uma peça numa determinada posição
	public boolean thereIsApiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Posição não existe no tabuleiro");
		}
		return piece (position) != null;
	}

}
