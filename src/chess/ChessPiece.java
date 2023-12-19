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
	
	//REGRA DO CHECK: check significa que o seu Rei está sob a ameaça de pelo menos uma peça de seu oponente
	//quando um jogador está em check, ele é OBRIGADO a sair do check. Se não for possível sair do check, é check mate.
	//o jogador NÃO PODE se colocar em check, não pode deixar o Rei em check. Se isso acontecer, ele deve receber um aviso e ser impedido de fazer a jogada
	
	//método para retornar uma posição no formato do xadrez = letra + numero
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	
	
	
}
