package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece { //BISPO

	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	//REGRA DO BISPO: pode se mover apenas nas diagonais enquanto tiver casas livres.
	@Override
	public boolean[][] possibleMoves() {
		//matriz auxiliar de booleanos que possui a mesma dimensão do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; //inicialmente todas as posições valem false	
		//testar as posições livres
		Position p = new Position(0,0);
		
		//DIAGONAL NOROESTE (aponta pra cima e pra esquerda - linha - 1, coluna - 1)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto a posição p existir e não tiver uma peça lá
			mat[p.getRow()][p.getColumn()] = true; //a matriz naquela posição é true, indicando que minha peça pode se mover pra lá
			p.setValues(p.getRow() - 1, p.getColumn() - 1); //faz a linha dessa posição, andar mais uma casa pra cima e pra esquerda enquanto tiver livre
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) { //enquanto a posição p existir e tiver uma peça adversária lá
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		//DIAGONAL NORDESTE (aponta pra cima e pra direita -  linha - 1, coluna + 1)
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setValues(p.getRow() - 1, p.getColumn() + 1); //faz a linha dessa posição, andar mais uma casa pra cima e pra direita enquanto tiver livre
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		//DIAGONAL SUDOESTE (aponta pra baixo e pra esquerda - linha + 1, coluna - 1)
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setValues(p.getRow() + 1, p.getColumn() - 1); //faz a linha dessa posição, andar mais uma casa pra baixo e pra esquerda enquanto tiver livre
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		//DIAGONAL SUDESTE (aponta pra baixo e pra direita - linha + 1, coluna + 1)
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setValues(p.getRow() + 1, p.getColumn() + 1); //faz a linha dessa posição, andar mais uma casa pra baixo e pra direita enquanto tiver livre
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) { //enquanto a posição p existir e tiver uma peça adversária lá
			mat[p.getRow()][p.getColumn()] = true;
		}
				
		return mat;			
	} 
		
		
}
