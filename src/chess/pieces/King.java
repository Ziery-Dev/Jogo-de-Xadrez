package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		
	}
	
	
	@Override
	public String toString() {
		return "K";
	}
	
	
	//método que diz se o rei pode mover pra uma determinada posição
	private boolean canMove(Position position){
		ChessPiece p =(ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	//método auxiliar para testar a condição de rook
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}


	@Override
	public boolean[][] possibleMoves() {
		boolean [][]mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//Posição Acima
		p.setValues(position.getRow() -1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//abaixo
		p.setValues(position.getRow() +1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//esquerda
		p.setValues(position.getRow() , position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//a direita 
		p.setValues(position.getRow() , position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//a noroeste
		p.setValues(position.getRow() -1 , position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//a nordeste
		p.setValues(position.getRow() -1 , position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//a sudoeste
		p.setValues(position.getRow()+1 , position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//dudeste
		p.setValues(position.getRow()+1 , position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// Movimento especial castling
		if(getMoveCount()== 0 && !chessMatch.getCheck()) {
			//rook pequeno
			Position posT1 = new  Position(position.getRow(), position.getColumn() + 3);
			if(testRookCastling(posT1)) {
				Position p1 =  new  Position(position.getRow(), position.getColumn() + 1);
				Position p2 =  new  Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().piece(p1) == null & getBoard().piece(p2)==null) {
					mat[position.getRow()][position.getColumn() +2] = true;
				}
			}
			
			//rook grande
			Position posT2 = new  Position(position.getRow(), position.getColumn() -4);
			if(testRookCastling(posT2)) {
				Position p1 =  new  Position(position.getRow(), position.getColumn() -1);
				Position p2 =  new  Position(position.getRow(), position.getColumn() -2);
				Position p3 =  new  Position(position.getRow(), position.getColumn() -3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() -2] = true;
				}
			}
		}
		
		
		
		return mat;
	}

}
