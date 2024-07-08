package chess;

import boardGame.Board;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	
	//construtor
	public ChessMatch() {
		board = new Board(8,8);	//dimensão do tabuleiro de xadrez
		 initialSetup();

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
	
	//responsável por iniciar a partida, colocando as peças no tabuleiro
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		board.placePiece(new King(board, Color.BLACK), new Position(0,4));
		board.placePiece(new King(board, Color.WHITE), new Position(7,4));
	}

}
