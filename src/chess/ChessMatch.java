package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	//classe que detém as regras do jogo de xadrez
	
	private Board board;
	private int turn;   //turno
	private Color currentPlayer; //jogador atual
	private boolean check; //check significa que o seu Rei está sob a ameaça de pelo menos uma peça de seu oponente (check tem valor igual a false por padrão)
	private boolean checkMate;
	private ChessPiece enPassantVulnerable; //propriedade que representa um peão vulnerável a jogada En Passant (por padrão, começa com valor null, por isso não foi iniciada no construtor)
	
	/*	JOGADAS ESPECIAIS: 
	-> JOGADA PROMOÇÃO: quando um peão percorre o tabuleiro inteiro e chega na última casa, 
	ele será substituído por uma dessas 4 peças a sua escolha: torre, cavalo, bispo ou rainha.*/
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>(); //lista de peças no tabuleiro
	private List<Piece> capturedPieces = new ArrayList<>();  //lista de peças capturadas
	
	//construtor da partida de xadrez
	public ChessMatch() {   
		board = new Board(8,8);  //define o tabuleiro com tamanho 8x8
		turn = 1; //no início da partida o turno vale 1
		currentPlayer = Color.WHITE;  //primeiro a jogar é o jogador com peças brancas
		initialSetup(); //chama o método de iniciar partida
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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
		piecesOnTheBoard.add(piece); //já adiciona a peça na lista de 'peças no tabuleiro'
	}

	//método responsável por iniciar a partida de xadrez colocando as peças no tabuleiro nas devidas posições
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this)); //palavra this faz referência a própria partida de xadrez (chessMatch)
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
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
		
		//Testa: se o jogador moveu uma peça e se colocou em Check, é preciso desfazer o movimento e lançar uma exceção
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check"); //você não pode se colocar em xeque
		}
		
		ChessPiece movedPiece = (ChessPiece) board.piece(target); //representa uma peça que foi movida (variável para testar a jogada En Passant)
		
		//Lógica para testar a jogada especial PROMOTION (PROMOÇÃO)
		promoted = null;
		//se a peça movida foi um peão, verifica se ela chegou no fim do tabuleiro (na última casa)
		if(movedPiece instanceof Pawn) {
			//se o peão é branco e chegou até a última linha OU se o peão é preto e chegou até a última linha
			if(movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || movedPiece.getColor() == Color.BLACK && target.getRow() == 7) {
				//então a peça promovida é aquele peão que chegou ao final
				promoted = (ChessPiece) board.piece(target);
				//esse peão promovido vai ser substituído por uma peça mais poderosa - por padrão, vai começar sendo trocado pela Rainha
				promoted = replacePromotedPiece("Q");
			}
		}

		//se o oponente ficou em Check, precisa atualizar o valor do atributo check para True
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		//testa se o oponente ficou em CheckMate após a jogada
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else { //se não estiver em checkMate, passa para o próximo turno e o jogo continua
			nextTurn();
		}
		
		//Lógica para testar a jogada especial EN PASSANT
		//Se a peça movida é instancia de Peão e se andou 2 casas para a posição de destino, significa que foi um movimento inicial de peão com 2 casas
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			//então o peão está vulnerável a tomar um En Passant no próximo turno
			enPassantVulnerable = movedPiece;
		}
		else { //caso contrário, o peão não está vulnerável e não há jogada especial
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturedPiece;	
	}
		
	//método para fazer a troca da peça promovida por uma peça mais poderosa (Jogada especial PROMOTION)
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) { //se não houver peça promovida, lança uma exceção
			throw new IllegalStateException("There is no piece to be promoted");
		}//se o argumento 'type' NÃO for preenchido com uma letra igual a 'B, N, R ou Q', retorna com a peça promovida padrão, que é a Rainha 'Q'
		if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted;
		}
		Position pos = promoted.getChessPosition().toPosition(); //pega a posição da peça peão promovida
		Piece p = board.removePiece(pos); //remove o peão que estava naquela posição e guarda  ele na variável p
		piecesOnTheBoard.remove(p); //remove a peça p da lista de peças que estão no tabuleiro
		
		//instanciar uma nova peça, referente ao tipo de letra informada, e coloca essa peça na posição em que estava o peão, fazendo a substituição
		ChessPiece newPiece = newPiece(type, promoted.getColor()); //instancia da nova peça com o tipo e a mesma cor da peça promovida
		board.placePiece(newPiece, pos); //coloca a nova peça na posição da peça promovida
		piecesOnTheBoard.add(newPiece); //adiciona a nova peça na lista de peças do tabuleiro
		
		return newPiece; 
	}
	
	//método auxiliar pra instanciar uma nova peça específica para o 'replacePromotedPiece()'
	private ChessPiece newPiece(String type, Color color) {
		//testa o type e instancia a nova peça de acordo com o tipo que o jogador informar
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("N")) return new Knight(board, color);
		if(type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
	
	//método para validar se existe uma peça em tal posição de origem
	private void validateSourcePosition(Position position) {
		if(! board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position"); //não existe peça na posição de origem
		}
		//se a peça escolhida pelo jogador atual for de cor diferente da dele, lança uma exceção
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");  //a peça escolhida não é sua
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
	
	//método para fazer a troca de turno
	private void nextTurn() {
		turn++; //incrementa o turno -> turno = turno + 1
		//altera a cor do jogador atual a cada troca de turno				
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; 
						//se jogador atual for igual branco, troca para preto. Se não, troca de preto para branco
	}
	
	//método para mover uma peça de xadrez recebendo uma posição de origem e de destino
	private Piece makeMove(Position source, Position target) {
		//remove a peça que deseja mover da posição de origem
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount(); //quando move a peça, incrementa na contagem dos movimentos
		//remove uma possível peça que esteja na posição de destino (por padrão, vai ser a peça capturada do adversário)
		Piece capturedPiece = board.removePiece(target);
		//coloco a peça p na posição de destino usando o método placePiece() para colocar a peça em determinada posição no tabuleiro 
		board.placePiece(p, target);
		//testa: se a peça capturada for diferente de nulo, ela deve ser removida da lista de 'peças no tebuleiro' e adicionada a lista de 'peças capturadas'
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//implementando Lógica da jogada especial Roque Pequeno
		//se a peça p que foi movida, for uma instancia de Rei e sua posição de destino for igual a posição de origem + 2, 
		//significa que o Rei andou duas casas para a direita, o que configura uma jogada de Roque Pequeno
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			//posição de Origem da Torre = 3 colunas a direita do Rei
			Position sourceTorre = new Position(source.getRow(), source.getColumn() + 3);
			//posição de Destino da Torre = 1 coluna a direita do Rei
			Position targetTorre = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceTorre); //pega a peça Torre e remove da posição de origem
			board.placePiece(rook, targetTorre); //move a peça Torre para a posição de destino
			rook.increaseMoveCount(); //incrementa a quantidade de movimentos feito com a Torre
		}
		//implementando Lógica da jogada especial Roque Grande
		//se a peça p que foi movida, for uma instancia de Rei e sua posição de destino for igual a posição de origem - 2, 
		//significa que o Rei andou duas casas para a esquerda, o que configura uma jogada de Roque Grande
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			//posição de Origem da Torre = 4 colunas a esquerda do Rei
			Position sourceTorre = new Position(source.getRow(), source.getColumn() - 4);
			//posição de Destino da Torre = 1 coluna a esquerda do Rei
			Position targetTorre = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceTorre); //pega a peça Torre e remove da posição de origem
			board.placePiece(rook, targetTorre); //move a peça Torre para a posição de destino
			rook.increaseMoveCount(); //incrementa a quantidade de movimentos feito com a Torre
		}
		
		//implementando Lógica da jogada especial En Passant
		//se a peça p movida foi uma instancia de Peão
		if(p instanceof Pawn) {
			//se o peão andou na diagonal (coluna de origem diferente da coluna de destino) e não tem peça capturada (pelo método normal), 
			//então foi um caso de En Passant
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				//pega a posição do peão que deve ser capturado (se está a direita ou a esquerda da peça que moveu)
				Position pawnPosition;
				//se a cor da peça que moveu for branca, quer dizer que a peça a ser capturada está embaixo do meu peão 
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else { //se a cor da peça que moveu for preta, quer dizer que a peça a ser capturada está em cima do meu peão
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				
				capturedPiece = board.removePiece(pawnPosition); //a peça capturada é o peão que está sendo removido do tabuleiro
				capturedPieces.add(capturedPiece); //adiciono o peão na lista de peças capturadas
				piecesOnTheBoard.remove(capturedPiece); //remove esse peão da lista de peças do tabuleiro
			}
		}
		//e retorna a peça que foi capturada
		return capturedPiece;
	}
	
	
	//REGRA DO XEQUE: check significa que o seu Rei está sob a ameaça de pelo menos uma peça de seu oponente
	//quando um jogador está em check, ele é OBRIGADO a sair do check. Se não for possível sair do check, é check mate.
	//o jogador NÃO PODE se colocar em check, não pode deixar o Rei em check. Se isso acontecer, ele deve receber um aviso e ser impedido de fazer a jogada
	
	//método para desfazer o movimento (faz o inverso do método makeMove)
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		//remove a peça que foi colocada na posição de destino
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount(); //quando um movimento é desfeito, tbm é retirado da contagem 
		//coloca a peça de volta na posição de origem
		board.placePiece(p, source);
		
		//testa se havia peça capturada: remove a peça da lista de 'peças capturadas' e adiciona de volta na lista de 'peças no tabuleiro'
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		//Desfaz a jogada especial Roque Pequeno
		//se a peça p que foi movida, for uma instancia de Rei e sua posição de destino for igual a posição de origem + 2, 
		//significa que o Rei andou duas casas para a direita, o que configura uma jogada de Roque Pequeno
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			//posição de Origem da Torre = 3 colunas a direita do Rei
			Position sourceTorre = new Position(source.getRow(), source.getColumn() + 3);
			//posição de Destino da Torre = 1 coluna a direita do Rei
			Position targetTorre = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetTorre); //pega a peça Torre e remove da posição de destino
			board.placePiece(rook, sourceTorre); //retorna a peça Torre para a posição de origem
			rook.decreaseMoveCount(); //decrementa a quantidade de movimentos feito com a Torre
		}
		//Desfaz a jogada especial Roque Grande
		//se a peça p que foi movida, for uma instancia de Rei e sua posição de destino for igual a posição de origem - 2, 
		//significa que o Rei andou duas casas para a esquerda, o que configura uma jogada de Roque Grande
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) {
			//posição de Origem da Torre = 4 colunas a esquerda do Rei
			Position sourceTorre = new Position(source.getRow(), source.getColumn() - 4);
			//posição de Destino da Torre = 1 coluna a esquerda do Rei
			Position targetTorre = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetTorre); //pega a peça Torre e remove da posição de destino
			board.placePiece(rook, sourceTorre); //retorna a peça Torre para a posição de origem
			rook.decreaseMoveCount(); //decrementa a quantidade de movimentos feito com a Torre
		}
		
		//Desfaz a jogada especial En Passant
		//se a peça p movida foi uma instancia de Peão
		if(p instanceof Pawn) {
			//se o peão andou na diagonal (coluna de origem diferente da coluna de destino) e se a peça capturada foi uma peça vulnerável a tomar En Passant
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				//pegar a peça que está capturada e colocar ela 'manualmente' no lugar correto (que não é a posição de destino)
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				//se a peça que capturou o peão for branca, quer dizer que a peça capturada é preta e deve ir pra linha 3 
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				}
				else { //se a peça que capturou for preta, quer dizer que a peça capturada é branca e deve ir pra linha 4
					pawnPosition = new Position(4, target.getColumn());
				}	
				board.placePiece(pawn, pawnPosition);
			}
		}
	}
	
	//método que retorna o oponente de uma cor
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
		//se a cor for igual a branco, troca para preto. Se não, troca de preto para branco.
	}
	
	//método para Localizar um Rei de determinada cor
	private ChessPiece king(Color color) {
		//declara uma lista que filtra (dentro da lista de peças no tabuleiro) as peças que são da cor que foi informada no parâmetro do método 
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) { //percorre a lista verificando se p é uma instancia da classe King -> compara a instância com o tipo
			if (p instanceof King) {
				return (ChessPiece)p; //retorna p (que no caso é o Rei daquela cor)
			}
		} //caso não encontre o Rei daquela cor, lança uma exceção (porém, isso não deve acontecer, já que sem o Rei a partida está terminada)
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	//método que testa se o Rei (de determinada cor) está em xeque
	//Lógica: percorrer todas as peças adversárias e para cada peça, testar se dentre os movimentos possíveis de cada peça, algum movimento cai na casa do Rei
	private boolean testCheck(Color color) {
		//variável que recebe a posição do rei de determinada cor, já convertida para formato de matriz 
		Position kingPosition = king(color).getChessPosition().toPosition();
		//variável lista que recebe as peças do oponente
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		//para cada peça p na lista de peças do oponente, eu testo se tem algum movimento possível que leva até a posição do rei
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); //matriz de movimentos possíveis da peça adversária
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) { //se nessa matriz, na mesma linha e coluna do Rei (mesma posição do rei) o valor for True
				return true;  // retorna true (significa que o rei está em xeque)
			}
		} 
		return false;
	}
	
	//REGRA DO XEQUEMATE: o Rei está em Check e NÃO existe nenhum movimento possível que o tire do Check.
	private boolean testCheckMate(Color color) {
		//teste para eliminar a possibilidade do Rei não estar em check
		if(!testCheck(color)) { //se o Rei não estiver em check, retorna false
			return false;
		}
	//testar se todas as peças daquela cor não tem nenhum movimento possível que tire o rei do check (logo, ele estará em xequemate)
	List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
	for(Piece p : list) { //para cada peça 'p' na lista de peças que estão no tabuleiro:
		//pegar os movimentos possíveis de cada peça 'p'
		boolean [][] mat = p.possibleMoves();
		//percorrer a matriz
		for (int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				if(mat[i][j]) { //se essa posição da matriz for um movimento possível, vou testar se esse movimento pode ou não tirar o Rei do check
				//pega a peça 'p' e move para essa posição da matriz que representa um movimento possível, e então verifica se o Rei continua em check ou não
					Position source = ((ChessPiece)p).getChessPosition().toPosition(); //posição de origem da peça 'p'
					Position target = new Position(i, j); //posição de destino da peça 'p', que no caso, é a posição i,j da matriz
					Piece capuredPiece = makeMove(source, target); //movendo a peça 'p'
					boolean testCheck = testCheck(color); //testa se o Rei ainda está em Check
					undoMove(source, target, capuredPiece); //desfaz o movimento feito com a peça 'p', pois foi apenas para teste
					if(!testCheck) { //se o Rei não estava em check após a movimentação da peça 'p', retorna false, pois ele não está em xequemate
						return false;
					}
				}
			}
		}
	}
	return true; //se o Rei continuou em check após testar os movimentos possíveis de todas as peças aliadas que ainda estão no tabuleiro, então é Xequemate
	}


}

