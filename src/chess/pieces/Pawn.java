package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);

	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);
		
		if(getColor() == Color.WHITE) { //peças brancas
			
			//movimento do peão após o primeiro movimento
			p.setValues(position.getRow() -1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//primeiro movimento do peão, podendo avançar 2 casas
			p.setValues(position.getRow() -2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			//testa se as 2 linhas a frente estão vazias
			if(getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsApiece(p2) && getMoveCount() == 0) { 
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testa se há peças adversárias na diagonal esquerda para que possa mover pra lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() -1, position.getColumn()-1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testa se há peças adversárias na diagonal direita para que possa mover pra lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() -1, position.getColumn()+1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		else { //peças pretas
			//movimento do peão após o primeiro movimento
			p.setValues(position.getRow() +1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//primeiro movimento do peão, podendo avançar 2 casas
			p.setValues(position.getRow() +2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			//testa se as 2 linhas a frente estão vazias
			if(getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsApiece(p2) && getMoveCount() == 0) { 
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testa se há peças adversárias na diagonal esquerda para que possa mover pra lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() +1, position.getColumn()-1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testa se há peças adversárias na diagonal direita para que possa mover pra lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() +1, position.getColumn()+1);
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;
	}
	@Override
	public String toString() {
		return "p";
	}

}
