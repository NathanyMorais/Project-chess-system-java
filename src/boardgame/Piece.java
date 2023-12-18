package boardgame;

public abstract class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}
	
	//protected: apenas classes e subclasses do pacote podem acessar o tabuleiro de uma peça
	protected Board getBoard() {
		return board;
	}
	
	//método abstrato para implementar possíveis movimentos de uma peça
	public abstract boolean[][] possibleMoves();  //o método deve ser implementado na classe referente a cada peça do xadrez
												//retorna uma matriz de booleanos (true onde o movimento for possível e false onde não for possível)						
	
	//método para testar se uma peça pode mover para uma determinada posição
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	//método para verificar se existe pelo menos um movimento possível para a peça em questão (pelo menos uma posição que seja true)
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for(int i=0; i < mat.length; i++) {
			for(int j=0; j < mat.length; j++) {
				if(mat[i][j]) {    //se a matriz na linha i e coluna j for verdadeira, posso afirmar que existe um movimento possível
					return true;
				}
			}
		}  //se a varredura da matriz esgotar e não retornar true em nunhuma posição, significa que a peça está travada e não pode se mover, então retorna false
		return false; 
	}
	
	
	
}
