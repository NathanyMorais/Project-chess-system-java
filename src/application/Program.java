package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		ChessMatch chessMatch = new ChessMatch(); //declarando um objeto da classe ChessMatch (partida de xadrez)
		
		while (true) {
			//função para imprimir o tabuleiro com as peças
			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			
			//leitura da posição que o usuário quer mover uma peça
			System.out.print("Origem: ");
			ChessPosition source = UI.readChessPosition(sc);
			System.out.println();
			System.out.print("Destino: ");
			ChessPosition target = UI.readChessPosition(sc);
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
		


	}

}
