package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI { // classe que representa a interface do usuario

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	//método para limpar a tela a medida que o usuário for jogando - Fonte: https://stackoverflow.com/questions/2979383/java-clear-the-console
	//Funciona no git bash e no cmd (entrando na pasta bin). 
	public static void clearScreen() { 
		System.out.print("\033[H\033[2J"); 
		System.out.flush(); 
	} 
	
	//método para ler uma posição que o usuário digitar (ex: a1, b5, f8...)
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0); //lê o primeiro caracter digitado, que é referente a coluna do tabuleiro
			int row = Integer.parseInt(s.substring(1)); //"recorta a string" para ler o segundo valor digitado, referente a linha do tabuleiro
			return new ChessPosition(column, row); 
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8."); //erro ao ler a posição de xadrez. 
			                                                                                                  // Valores válidos são a1 ate h8.
		}
	}

	// método para imprimir o tabuleiro de xadrez no console
	public static void printBoard(ChessPiece[][] pieces) {
		System.out.println();
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	// método para imprimir o tabuleiro de xadrez no console (colorindo as posições de possível movimento)
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		System.out.println();
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	// método auxiliar para imprimir UMA peça e colorir ou não o fundo
	private static void printPiece(ChessPiece piece, boolean background) {
		if(background) { //se backgroung for true, destaca o fundo
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} 
		else {
			if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET); //a cor amarela representa as peças pretas
            }
		}
		System.out.print(" "); // espaço em branco para que as peças não fiquem coladas umas nas outras
	}
	
	// método para imprimir a partida de xadrez (vai mostrar o tabuleiro, as peças capturadas, o turno, o jogador atual)
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces()); //imprime o tabuleiro
		System.out.println();
		printCapturedPieces(captured); //imprime peças capturadas
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
		if(chessMatch.getCheck() == true) {
			System.out.println("CHECK!");
		}
	}
	
	//método para imprimir as peças capturadas recebendo uma lista (com as peças que forem capturadas)
	private static void printCapturedPieces(List<ChessPiece> captured) {
		//declara uma lista de peças brancas  (filtra as peças brancas da lista de peças capturadas)
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		//declara uma lista de peças pretas  (filtra as peças pretas da lista de peças capturadas)
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		
		//lógica para imprimir as listas na tela
		System.out.println("Captured pieces:");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE); //garante que as peças serão impressas na cor branca
		System.out.println(Arrays.toString(white.toArray())); //método padrão para imprimir um array de valores no Java
		System.out.print(ANSI_RESET); //reseta a cor
		
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW); //garante que as peças serão impressas na cor amarela (que está representando as peças Pretas)
		System.out.println(Arrays.toString(black.toArray())); 
		System.out.print(ANSI_RESET); 
		
	}

}
