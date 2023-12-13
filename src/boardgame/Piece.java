package boardgame;

public class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}
	
	//apenas classes e subclasses do pacote podem acessar o tabuleiro de uma peça
	protected Board getBoard() {
		return board;
	}
	
	

}
