package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	//método para verificar se existe uma peça adversária em determinada posição
	protected boolean isThereOpponentPiece(Position position) {
		//variável p que recebe a peça que estiver naquela posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		//verificar se 'p' é uma peça adversária (se não é nula e se a cor é diferente)
		return p != null && p.getColor() != color;
	}
	
	
}
