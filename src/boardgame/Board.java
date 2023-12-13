package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	
	public Board(int rows, int columns) {
		if(rows < 1 || columns < 1) { //testa quantidade de linha e coluna quando criar o tabuleiro
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column"); //erro ao criar tabuleiro: é necessário pelo menos 1 linha e 1 coluna
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}


	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) { //testa se a posição existe
			throw new BoardException("Position not on the board"); //a posição não existe no tabuleiro
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) { //testa se a posição existe
			throw new BoardException("Position not on the board"); //a posição não existe no tabuleiro
		}	
		return pieces[position.getRow()][position.getColumn()];
	}
	
	//método para colocar a peça em determinada posição no tabuleiro 
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) { //testa se já tem peça naquela posição
			throw new BoardException("There is already a piece on position " + position); //já existe uma peça na posição 'tal'
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	// método auxiliar (pois em determinado momento é mais prático testar pela linha e coluna do que pela posição)
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns; //condição pra saber se uma posição existe (a posição só existe quando está dentro do tabuleiro)
	}

	// método que testa se existe aquela posição no tabuleiro
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	//método para testar se tem uma peça em determinada posição
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) { //primeiro testa se existe uma posição no tabuleiro
			throw new BoardException("Position not on the board"); 
		}
		return piece(position) != null;
	}
}
