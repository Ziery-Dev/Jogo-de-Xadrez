package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;

	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) { // peças brancas

			// movimento do peão após o primeiro movimento
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// primeiro movimento do peão, podendo avançar 2 casas
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			// testa se as 2 linhas a frente estão vazias
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsApiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// testa se há peças adversárias na diagonal esquerda para que possa mover pra
			// lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// testa se há peças adversárias na diagonal direita para que possa mover pra
			// lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// movimento esp especial en passant white
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				Position rigth = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(rigth) && isThereOpponentPiece(rigth)
						&& getBoard().piece(rigth) == chessMatch.getEnPassantVulnerable()) {
					mat[rigth.getRow() - 1][rigth.getColumn()] = true;
				}
			}
		} else { // peças pretas
					// movimento do peão após o primeiro movimento
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// primeiro movimento do peão, podendo avançar 2 casas
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			// testa se as 2 linhas a frente estão vazias
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsApiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// testa se há peças adversárias na diagonal esquerda para que possa mover pra
			// lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// testa se há peças adversárias na diagonal direita para que possa mover pra
			// lá, tirando a peça adversária de jogo
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// movimento especial en passant black
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				Position rigth = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(rigth) && isThereOpponentPiece(rigth)
						&& getBoard().piece(rigth) == chessMatch.getEnPassantVulnerable()) {
					mat[rigth.getRow() + 1][rigth.getColumn()] = true;
				}
			}
		}

		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
