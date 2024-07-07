package aplicacao;

import chess.ChessMatch;

public class Programa {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		
		//função para imprimir as peças da partida
		UI.printBoard(chessMatch.getPieces());
	}

}
