package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	//método auxiliar que verifica se o Rei pode mover pra uma determinada posição
	private boolean canMove(Position position) {
		//variável p que recebe a peça que estiver naquela posição
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		//verifica se essa peça p é nula (vazia) OU se é adversária (nesses casos o Rei pode se mover pra essa posição)
		return p == null || p.getColor() != getColor();
	}
	
	
	//REGRA DO REI: SÓ PODE MOVER UMA CASA, EM QUALQUER UMA DAS DIREÇÕES
	@Override
	public boolean[][] possibleMoves() {
		//matriz auxiliar de booleanos que possui a mesma dimensão do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0,0);
		
		//Lógica para marcar como True a posiçã0 livre ACIMA do Rei (mesma coluna do Rei, linha - 1)
		p.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Lógica para marcar como True a posiçã0 livre ABAIXO do Rei (mesma coluna do Rei, linha + 1)
		p.setValues(position.getRow() + 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Lógica para marcar como True a posiçã0 livre a ESQUERDA do Rei (mesma linha do Rei, coluna - 1)
		p.setValues(position.getRow(), position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Lógica para marcar como True a posiçã0 livre a DIREITA do Rei (mesma linha do Rei, coluna + 1)
		p.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//DIAGONAL NOROESTE (aponta pra cima e pra esquerda - linha do Rei - 1, coluna do Rei - 1)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//DIAGONAL NORDESTE (aponta pra cima e pra direita - linha do Rei - 1, coluna do Rei + 1)
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//DIAGONAL SUDOESTE (aponta pra baixo e pra esquerda - linha do Rei + 1, coluna do Rei - 1)
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		//DIAGONAL SUDESTE (aponta pra baixo e pra direita - linha do Rei + 1, coluna do Rei + 1)
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	} 

}
