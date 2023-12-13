package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	//classe que detém as regras do jogo de xadrez
	
	private Board board;

	public ChessMatch() {   //partida de xadrez
		board = new Board(8,8);  //define o tabuleiro com tamanho 8x8
		initialSetup(); //chama o método de iniciar partida
	}
	
	//método que retorna uma matriz de peças da partida de xadrez
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	//método que coloca uma peça no tabuleiro recebendo as coordenadas do xadrez (ex: a1, b7, c5..) e convertendo em posição da matriz (ex: 0,0; 2,1; 7,4..)
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	//método responsável por iniciar a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
	

}
