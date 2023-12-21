package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{ //PEÃO

	public Pawn(Board board, Color color) {
		super(board, color);
	}
	
	//REGRA DO PEÃO: o peão só pode mover 1 casa para frente por vez. Exceto se for seu primeiro movimento (ou seja, moveCount = 0), então pode mover duas casas para frente.
	//Se houver uma peça adversária na diagonal a frente do peão, é permitido ele mover uma casa para essa diagonal apenas para capturar a peça.
	@Override
	public boolean[][] possibleMoves() {
		//matriz auxiliar de booleanos que possui a mesma dimensão do tabuleiro
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; //inicialmente todas as posições valem false	
		//testar as posições livres
		Position p = new Position(0,0);
		
		//posições livres para o peão de cor BRANCA (sempre anda pra cima no tabuleiro)
		if(getColor() == Color.WHITE) {
			//testando apenas a primeira casa acima do peão branco
			p.setValues(position.getRow() - 1, position.getColumn()); //se a posição acima do peão branco existir e estiver vazia, então pode se mover
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testando as duas casas acima do peão branco para que ele possa mover caso seja seu primeiro movimento
			p.setValues(position.getRow() - 2, position.getColumn()); //segunda casa acima do peão branco 
			Position p2 = new Position(position.getRow() - 1, position.getColumn()); //primeira casa acima do peão branco
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0     //se as 2 posições existirem, estiverem vazias
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {              // e a contagem de movimento for igual a 0, então pode se mover   
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testando as casas nas diagonais a frente do peão (caso exista peça ali, ele pode se mover para capturar)
			p.setValues(position.getRow() - 1, position.getColumn() - 1); //posição da diagonal esquerda a frente do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e tiver uma peça do oponente lá, então pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() - 1, position.getColumn() + 1); //posição da diagonal direita a frente do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e tiver uma peça do oponente lá, então pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		//posições livres para o peão de cor PRETA (sempre anda pra baixo no tabuleiro)
		else {
			//testando apenas a primeira casa abaixo do peão preto
			p.setValues(position.getRow() + 1, position.getColumn()); 
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testando as duas casas abaixo do peão preto para que ele possa mover caso seja seu primeiro movimento
			p.setValues(position.getRow() + 2, position.getColumn()); 
			Position p2 = new Position(position.getRow() + 1, position.getColumn()); 
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0    
					&& getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2)) {              
				mat[p.getRow()][p.getColumn()] = true;
			}
			//testando as casas nas diagonais a frente do peão (caso exista peça ali, ele pode se mover para capturar)
			p.setValues(position.getRow() + 1, position.getColumn() - 1); //posição da diagonal esquerda a frente do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e tiver uma peça do oponente lá, então pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(position.getRow() + 1, position.getColumn() + 1); //posição da diagonal direita a frente do peão
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existir e tiver uma peça do oponente lá, então pode se mover
				mat[p.getRow()][p.getColumn()] = true;
			}	
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	
}
