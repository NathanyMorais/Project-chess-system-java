package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;


public class King extends ChessPiece{ //REI
	
	/*	JOGADAS ESPECIAIS: 
	-> JOGADA ROQUE: se o seu Rei e uma Torre estiverem ainda sem nenhum movimento, as casas entre eles estiverem vagas e o Rei não estiver em check,
	é possível mover o Rei duas casas e, na mesma jogada, a Torre vai mover para o lado do Rei. 
	Isso pode ocorrer entre o Rei e a Torre da direita: jogada conhecida como Roque Pequeno = roque do lado do Rei
	Ou entre o Rei e a Torre da esquerda: jogada conhecida como Roque Grande = roque do lado da Rainha
*/
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
	
	//método auxiliar que ajuda testar se a Torre está apta para o Roque
	private boolean testRookCastling(Position position) {
		//testar se em determinada posição existe uma Torre e se a mesma está apta para Roque (ou seja, quantidade de movimentos é igual a Zero)
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		//retorna verdadeiro se: peça p não nula, se é uma instancia de Torre, se a cor é a mesma do jogador atual e se a qtde de movimentos é zero
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
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
		
		//Movimento especial de ROQUE para o REI (testar se o REI está apto para a jogada de Roque) 
		//Se qtde de movimento é zero e se a partida não está em Check.
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			
			//Roque PEQUENO: testar se as duas posições a direita entre Rei e Torre estão vagas 
			//posição da Torre 1 = mesma linha do Rei e 3 colunas para a direita(é a última peça Torre, que fica no canto inferior direito)
			Position posTorre1 = new Position(position.getRow(), position.getColumn() + 3);
			//testa se nessa posição existe uma peça Torre apta para Roque
			if(testRookCastling(posTorre1)) {
				//testa se as casas ao lado direito do Rei estão vazias
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); //primeira casa do lado direito do rei
				Position p2 = new Position(position.getRow(), position.getColumn() + 2); //segunda casa do lado direito do rei
				//se no tabuleiro, as peças p1 e p2 forem nulas, significa que há casa vazia e o Rei pode se movimentar 2 casas para a direita
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}	
			//Roque GRANDE: testar se as 3 posições a esquerda entre Rei e Torre estão vagas 
			//posição da Torre 2 = mesma linha do Rei e 4 colunas para a esquerda(é a última peça Torre, que fica no canto inferior esquerdo)
			Position posTorre2 = new Position(position.getRow(), position.getColumn() - 4);
			//testa se nessa posição existe uma peça Torre apta para Roque
			if(testRookCastling(posTorre2)) {
				//testa se as casas ao lado esquerdo do Rei estão vazias
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); //primeira casa do lado esquerdo do rei
				Position p2 = new Position(position.getRow(), position.getColumn() - 2); //segunda casa do lado esquerdo do rei
				Position p3 = new Position(position.getRow(), position.getColumn() - 3); //terceira casa do lado esquerdo do rei
				//se no tabuleiro, as peças p1, p2 e p3 forem nulas, significa que há casa vazia e o Rei pode se movimentar 2 casas para a esquerda
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}	
		}
		
		return mat;
	}
	
}
