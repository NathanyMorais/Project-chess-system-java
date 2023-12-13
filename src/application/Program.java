package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch(); //declarando um objeto da classe ChessMatch (partida de xadrez)
		
		//função para imprimir o tabuleiro com as peças dessa partida
		UI.printBoard(chessMatch.getPieces());

	}

}
