package ieslavereda;

public class MainChess {
    public static void main(String[] main) {
        Board board = new Board();
        board.initialize();
        boolean kingDead = false;

        Coordinate coordinateAux;
        Coordinate coordinateSelf;
        Coordinate coordinateOther;

        while (!kingDead) {
            // WHITE-TURN
            System.out.println("----------------------WHITE PLAYER'S TURN----------------------");

            do {
                clearConsole();
                System.out.println(board);

                coordinateSelf = Input.enterCoordinate("Select a WHITE piece (Example: A4): ");
            } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.WHITE));

            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

            do {
                clearConsole();
                System.out.println(board);

                coordinateAux = Input.enterCoordinate("Select either another WHITE piece or a valid coordinate to attack/move (Example: A4): ");

                if (!board.getCellAt(coordinateAux).isEmpty()) {
                    if (board.getCellAt(coordinateAux).getPiece().getColor() == Piece.Color.WHITE) {
                        coordinateSelf = coordinateAux;
                        board.removeHighLight();
                        board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());
                    }
                }
                coordinateOther = coordinateAux;
            } while (!board.getCellAt(coordinateSelf).getPiece().getNextMovements().contains(coordinateAux));

            if (board.getCellAt(coordinateOther).getPiece().getType() == Piece.Type.BLACK_KING)
                kingDead = true;

            board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
            board.removeHighLight();

            if (!kingDead) {
                {
                    // BLACK-TURN
                    System.out.println("----------------------BLACK PLAYER'S TURN----------------------");

                    do {
                        clearConsole();
                        System.out.println(board);

                        coordinateSelf = Input.enterCoordinate("Select a BLACK piece (Example: B7): ");
                    } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.BLACK));

                    board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

                    do {
                        clearConsole();
                        System.out.println(board);

                        coordinateAux = Input.enterCoordinate("Select either another BLACK piece or a valid coordinate to attack/move (Example: C7): ");

                        if (!board.getCellAt(coordinateAux).isEmpty()) {
                            if (board.getCellAt(coordinateAux).getPiece().getColor() == Piece.Color.BLACK) {
                                coordinateSelf = coordinateAux;
                                board.removeHighLight();
                                board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());
                            }
                        }
                        coordinateOther = coordinateAux;
                    } while (!board.getCellAt(coordinateSelf).getPiece().getNextMovements().contains(coordinateAux));

                    if (board.getCellAt(coordinateOther).getPiece().getType() == Piece.Type.WHITE_KING)
                        kingDead = true;

                    board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
                    board.removeHighLight();

                }
            }
        }
    }
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
