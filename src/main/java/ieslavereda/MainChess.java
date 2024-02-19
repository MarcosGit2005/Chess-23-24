package ieslavereda;

public class MainChess {
    public static void main(String[] main){
        Board board = new Board();
        board.initialize();

        Coordinate coordinateAux;
        Coordinate coordinateSelf;
        Coordinate coordinateOther;

        while (true){
            // WHITE-TURN
            System.out.println("----------------------WHITE PLAYER'S TURN----------------------");

            do {
                System.out.println(board);
                coordinateSelf = Input.enterCoordinate("Select a WHITE piece (Example: A4): ");
            } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.WHITE));

            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

            do {
                System.out.println(board);
                coordinateAux = Input.enterCoordinate("Select either another WHITE piece or a valid coordinate to attack/move (Example: A4): ");

                if (!board.getCellAt(coordinateAux).isEmpty()){
                    if (board.getCellAt(coordinateAux).getPiece().getColor() == Piece.Color.WHITE){
                        coordinateSelf = coordinateAux;
                        board.removeHighLight();
                        board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());
                    }
                }
                coordinateOther = coordinateAux;
            } while (!board.getCellAt(coordinateSelf).getPiece().getNextMovements().contains(coordinateAux));

            board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
            board.removeHighLight();

            if (board.getCellAt(coordinateOther).getPiece().getType()== Piece.Type.BLACK_KING){

            } else {
                // BLACK-TURN
                System.out.println("----------------------BLACK PLAYER'S TURN----------------------");

                do {
                    System.out.println(board);
                    coordinateSelf = Input.enterCoordinate("Select a BLACK piece (Example: B7): ");
                } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.BLACK));

                board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

                do {
                    System.out.println(board);
                    coordinateAux = Input.enterCoordinate("Select either another BLACK piece or a valid coordinate to attack/move (Example: C5): ");

                    if (!board.getCellAt(coordinateAux).isEmpty()){
                        if (board.getCellAt(coordinateAux).getPiece().getColor() == Piece.Color.BLACK){
                            coordinateSelf = coordinateAux;
                            board.removeHighLight();
                            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());
                        }
                    }
                    coordinateOther = coordinateAux;
                } while (!board.getCellAt(coordinateSelf).getPiece().getNextMovements().contains(coordinateAux));

                board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
                board.removeHighLight();

                if (board.getCellAt(coordinateOther).getPiece().getType()== Piece.Type.WHITE_KING){

                }
            }
        }
    }
}
