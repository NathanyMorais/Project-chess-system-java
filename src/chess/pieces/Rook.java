package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{ //classe Torre (Rook)

	public Rook(Board board, Color color) {
		super(board, color);	
	}

	@Override
	public String toString() {
		return "R";
	} 
	
	@Override
	public boolean[][] possibleMoves() {
		//matriz auxiliar de booleanos que possui a mesma dimensão do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return mat;
	} 
	
}
