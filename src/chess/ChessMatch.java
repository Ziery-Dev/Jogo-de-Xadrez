package chess;

import boardGame.Board;

public class ChessMatch {
	private Board board;
	
	//construtor
	public ChessMatch() {
		board = new Board(8,8);	//dimensão do tabuleiro de xadrez

	}
	

	//metodos
	public ChessPiece[][] getPieces(){ 	//vai retornar uma matriz de peças de xadrez pra essa partida
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

}
