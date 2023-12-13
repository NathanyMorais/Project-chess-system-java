package application;

import chess.ChessPiece;

public class UI {  //classe de interface do usuario
	
	//método para imprimir o tabuleiro de xadrez no console
	public static void printBoard(ChessPiece[][] pieces) {
		System.out.println();
		for (int i=0; i<pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for(int j=0; j<pieces.length; j++) {
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	//método auxiliar para imprimir UMA peça
	private static void printPiece(ChessPiece piece) {
		if(piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		System.out.print(" "); //espaço em branco para que as peças não fiquem coladas umas nas outras
	}

}
