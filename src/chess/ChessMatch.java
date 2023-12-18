package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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

	//método responsável por iniciar a partida de xadrez colocando as peças no tabuleiro nas devidas posições
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        
	}
	
	//operação de movimentos possíveis dado uma posição (retorna uma matriz boolean contendo as posições possíveis)
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		//converter posição de xadrez para uma posição de matriz normal
		Position position = sourcePosition.toPosition();
		//validar a posição
		validateSourcePosition(position);
		//retornar os movimentos possíveis da peça dessa posição
		return board.piece(position).possibleMoves();
	}
	
	//método para mover as peças no tabuleiro (parâmetros: posição de origem, posição de destino) 
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		//converter as posições de origem e destino para posições de matriz
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		
		//validar se realmente existe peça na posição de origem
		validateSourcePosition(source);
		//validar se a posição de origem é válida em relação a posição de destino
		validateTargetPosition(source, target);
		
		//variável 'peça capturada' recebe o método de mover a peça considerando a origem e o destino (já no formato de matriz)
		Piece capturedPiece = makeMove(source, target);
		
		return (ChessPiece)capturedPiece;		
	}
	
	//método para validar se existe uma peça em tal posição de origem
	private void validateSourcePosition(Position position) {
		if(! board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position"); //não existe peça na posição de origem
		}
		//testar se existe movimentos possíveis para a peça
		if(! board.piece(position).isThereAnyPossibleMove()) { //se não tiver nenhum movimento possível, lança uma exceção
			throw new ChessException("There is no possible moves for the chosen piece"); //não há movimentos possíveis para essa peça
		}
	}
	
	//método para validar se a posição de origem é válida em relação a posição de destino
	private void validateTargetPosition(Position source, Position target) {
		if(! board.piece(source).possibleMove(target)) { //se para a peça de origem, a posição de destino não é um movimento possível, então não posso mover a peça
			throw new ChessException("The chosen piece can't move to target position"); //a peça escolhida não pode se mover para a posição de destino
		}
	}
	
	//método para mover uma peça de xadrez recebendo uma posição de origem e de destino
	private Piece makeMove(Position source, Position target) {
		//remove a peça que deseja mover da posição de origem
		Piece p = board.removePiece(source);
		//remove uma possível peça que esteja na posição de destino (por padrão, vai ser a peça capturada do adversário)
		Piece capturedPiece = board.removePiece(target);
		//coloco a peça p na posição de destino usando o método placePiece() para colocar a peça em determinada posição no tabuleiro 
		board.placePiece(p, target);
		//e retorna a peça que foi capturada
		return capturedPiece;
	}

}
