package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		ChessMatch chessMatch = new ChessMatch(); //declarando um objeto da classe ChessMatch (partida de xadrez)
		
		List<ChessPiece> captured = new ArrayList<>(); //declarando uma lista de peças capturadas (inicialmente vazia)
		
		while (true) {
			try {
				UI.clearScreen(); 
				
				//função para imprimir o tabuleiro com as peças
				UI.printMatch(chessMatch, captured);
				System.out.println();
				
				//leitura da posição que o usuário quer mover uma peça
				System.out.print("Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves); //imprime o tabuleiro destacando as posições possíveis para onde a peça de origem pode se mover

				System.out.println();
				System.out.print("Destino: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);		
				//testa: se a peça capturada for diferente de nulo, significa que alguma peça foi capturada de fato
				if(capturedPiece != null) {
					captured.add(capturedPiece); //adiciona a peça na lista de peças capturadas
				}
				
				
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}	
		}
	}
}
