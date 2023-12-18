package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
	
	//REGRA DA TORRE: SÓ PODE SE MOVER EM LINHA RETA, PARA CIMA, PARA BAIXO OU PARA OS LADOS
	@Override
	public boolean[][] possibleMoves() {
		//matriz auxiliar de booleanos que possui a mesma dimensão do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; //inicialmente todas as posições valem false
		
		//testar as posições livres
		Position p = new Position(0,0);
		
		//Lógica para marcar como True as posições livres ACIMA da minha peça (mesma coluna, linha - 1)
		p.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto a posição p existir e não tiver uma peça lá
			mat[p.getRow()][p.getColumn()] = true; //a matriz naquela posição é true, indicando que minha peça pode se mover pra lá
			p.setRow(p.getRow() - 1); //faz a linha dessa posição, andar mais uma casa pra cima
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) { //enquanto a posição p existir e tiver uma peça adversária lá
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Lógica para marcar como True as posições livres DO LADO ESQUERDO da minha peça (mesma linha, coluna - 1)
		p.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setColumn(p.getColumn() - 1); //faz a coluna dessa posição, andar mais uma casa pra esquerda
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Lógica para marcar como True as posições livres DO LADO DIREITO da minha peça (mesma linha, coluna + 1)
		p.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { 
			mat[p.getRow()][p.getColumn()] = true; 
			p.setColumn(p.getColumn() + 1); //faz a coluna dessa posição, andar mais uma casa pra direita
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Lógica para marcar como True as posições livres ABAIXO da minha peça (mesma coluna, linha + 1)
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto a posição p existir e não tiver uma peça lá
			mat[p.getRow()][p.getColumn()] = true; //a matriz naquela posição é true, indicando que minha peça pode se mover pra lá
			p.setRow(p.getRow() + 1); //faz a linha dessa posição, andar mais uma casa pra cima
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p) ) { //enquanto a posição p existir e tiver uma peça adversária lá
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
		
	} 
	
}
