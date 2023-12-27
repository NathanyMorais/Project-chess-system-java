package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{ //PEÃO
	
	/*	JOGADAS ESPECIAIS: 
	-> JOGADA EN PASSANT: é uma captura especial do Peão adversário, quando o mesmo foi movido 2 casas pelo jogador oponente.
	 No avanço de duas casas do peão adversário, caso seu peão esteja na coluna ao lado e na mesma fileira, você pode capturar o peão que foi movido 
	 como se tivesse havido um avanço de somente uma casa.
*/
	
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			
			//Movimento especial de EN PASSANT para o Peão BRANCO
			//quando um peão branco ocupa a linha 3 da matriz (linha 5 na coordenada tabuleiro de xadrez), pode estar apto a fazer En Passant se for o caso
			if(position.getRow() == 3) {
				//testa se tem uma peça adversária do Lado Esquerdo do meu Peão (mesma linha do peão, uma coluna para a esquerda)
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				//se posição esquerda existe E se tem uma peça oponente lá E se essa peça está vulnerável para tomar a jogada En Passant
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					//então meu peão pode capturar essa peça (mas ele não se move para o lugar dela, e sim para uma linha Acima)
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				
				//testa se tem uma peça adversária do Lado Direito do meu Peão (mesma linha do peão, uma coluna para a direita)
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				//se posição direita existe E se tem uma peça oponente lá E se essa peça está vulnerável para tomar a jogada En Passant
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					//então meu peão pode capturar essa peça (mas ele não se move para o lugar dela, e sim para uma linha Acima)
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
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
			
			//Movimento especial de EN PASSANT para o Peão PRETO
			//quando um peão Preto ocupa a linha 4 da matriz (linha 4 na coordenada tabuleiro de xadrez), pode estar apto a fazer En Passant se for o caso
			if(position.getRow() == 4) {
				//testa se tem uma peça adversária do Lado Esquerdo do meu Peão (mesma linha do peão, uma coluna para a esquerda)
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				//se posição esquerda existe E se tem uma peça oponente lá E se essa peça está vulnerável para tomar a jogada En Passant
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					//então meu peão pode capturar essa peça (mas ele não se move para o lugar dela, e sim para uma linha Abaixo)
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				
				//testa se tem uma peça adversária do Lado Direito do meu Peão (mesma linha do peão, uma coluna para a direita)
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				//se posição direita existe E se tem uma peça oponente lá E se essa peça está vulnerável para tomar a jogada En Passant
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					//então meu peão pode capturar essa peça (mas ele não se move para o lugar dela, e sim para uma linha Abaixo)
					mat[right.getRow() + 1][right.getColumn()] = true;
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
