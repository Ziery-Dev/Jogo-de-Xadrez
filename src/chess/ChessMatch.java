package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList();
	private List<Piece> capturedPieces = new ArrayList();

	// construtor
	public ChessMatch() {
		board = new Board(8, 8); // dimensão do tabuleiro de xadrez
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();

	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	// metodos
	public ChessPiece[][] getPieces() { // vai retornar uma matriz de peças de xadrez pra essa partida
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	// indicação de possiveis movimentos
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	// operação para mover as peças
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		// implementação de quando o jogador se coloca em xeque
		if (testeCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Você não pode se colocar em check!");
		}

		check = testeCheck(opponent(currentPlayer)) ? true : false;

		// implementação do xeque-mate
		if (testeCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();	
		}
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p =(ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		// implementação da remoção das listas de peças no tabuleiro e adição em peças
		// capturadas
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		//Movimento especial castling kingside rook (rook pequeno)
		if(p instanceof King && target.getColumn() == source.getColumn() +2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()+3);
			Position targetT  = new Position(source.getRow(), source.getColumn()+1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		//Movimento especial castling quennside rook (rook Grande)
		if(p instanceof King && target.getColumn() == source.getColumn() -2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()-4);
			Position targetT  = new Position(source.getRow(), source.getColumn()-1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		return capturedPiece;
	}

	// metodo para desfazer movimento quando o jogador se coloca em check por conta
	// própria
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		// quando houver uma peça capturada nesse movimento
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);

		}
		
		//Movimento especial castling kingside rook (rook pequeno)
				if(p instanceof King && target.getColumn() == source.getColumn() +2) {
					Position sourceT = new Position(source.getRow(), source.getColumn()+3);
					Position targetT  = new Position(source.getRow(), source.getColumn()+1);
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
				}
				//Movimento especial castling quennside rook (rook Grande)
				if(p instanceof King && target.getColumn() == source.getColumn() -2) {
					Position sourceT = new Position(source.getRow(), source.getColumn()-4);
					Position targetT  = new Position(source.getRow(), source.getColumn()-1);
					ChessPiece rook = (ChessPiece)board.removePiece(targetT);
					board.placePiece(rook, sourceT);
					rook.decreaseMoveCount();
				}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsApiece(position)) {
			throw new ChessException("Não existe peça na posição de origem");
		}
		// verificar se o jogador está tentando mover a peça do adiversário
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("A peça que está tentando mover não é sua");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Não existe movimentos possiveis para a peça selecionada");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
		}
	}

	// método que troca o turno
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	// metodo que retorna a cor do oponente
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	// método que busca nas peças em jogo o rei de determinada cor
	private ChessPiece king(Color color) {
		List<Piece> lista = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : lista) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Não existe o rei dar cor" + color + " no tabueliro");
	}

	// testar se o rei está em cheque
	private boolean testeCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();// posição do rei no formato de matriz
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());// lista de
																											// peças do
																											// oponente
		// teste se cada peça do oponente leva à posição do rei (check)
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;

	}

	// lógica que testa se a jogada é xeque mate
	private boolean testeCheckMate(Color color) {
		if (!testeCheck(color)) {
			return false;
		}
		List<Piece> lista = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : lista) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testeCheck = testeCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testeCheck) {
							return false;
						}

					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	// responsável por iniciar a partida, colocando as peças no tabuleiro
	private void initialSetup() {
  
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}

	/*
	 * private void initialSetup() { placeNewPiece('c', 1, new Rook(board,
	 * Color.WHITE)); placeNewPiece('c', 2, new Rook(board, Color.WHITE));
	 * placeNewPiece('d', 2, new Rook(board, Color.WHITE)); placeNewPiece('e', 2,
	 * new Rook(board, Color.WHITE)); placeNewPiece('e', 1, new Rook(board,
	 * Color.WHITE)); placeNewPiece('d', 1, new King(board, Color.WHITE));
	 * 
	 * placeNewPiece('c', 7, new Rook(board, Color.BLACK)); placeNewPiece('c', 8,
	 * new Rook(board, Color.BLACK)); placeNewPiece('d', 7, new Rook(board,
	 * Color.BLACK)); placeNewPiece('e', 7, new Rook(board, Color.BLACK));
	 * placeNewPiece('e', 8, new Rook(board, Color.BLACK)); placeNewPiece('d', 8,
	 * new King(board, Color.BLACK));
	 * 
	 * }
	 */
}
