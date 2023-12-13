package chess;

import boardgame.Position;

public class ChessPosition {
	//Classe que representa o sistema de coordenadas desse jogo de xadrez
	//As colunas são representada pelas letras de A até H e as linhas são representadas por números de 1 até 8
													/*	8 - - - - - - - - 
														7 - - - - - - - - 
														6 - - - - - - - - 
														5 - - - - - - - - 
														4 - - - - - - - - 
														3 - - - - - - - - 
														2 - - - - - - - - 
														1 - - - - - - - - 
														  a b c d e f g h
													*/	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if(column < 'a' || column > 'h' || row < 1 || row > 8) { //condição para testar a posição
			throw new ChessException("Error instatiating ChessPosition. Valid values are from a1 to h8");//erro ao instanciar a posição do xadrez. Valores válidos são de a1 até h8.
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	//método para transformar as coordenadas do xadrez (ChessPosition) em uma posição normal da Matriz (Position)
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	
	//método que converte uma posição normal da Matriz em coordenada/posição de xadrez
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
