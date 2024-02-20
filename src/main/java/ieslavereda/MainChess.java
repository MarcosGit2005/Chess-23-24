package ieslavereda;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainChess {
    public static DeletedPieceManagerList deletedPieceManagerList = new DeletedPieceManagerList();
    public static void main(String[] main) {
        Board board = new Board();
        board.initialize();
        boolean endGame = false;

        Coordinate coordinateAux;
        Coordinate coordinateSelf;
        Coordinate coordinateOther;

        while (!endGame) {
            // WHITE-TURN
            System.out.println("----------------------WHITE PLAYER'S TURN----------------------");

            do {
                clearConsole();
                System.out.println(board+"\n");

                System.out.println(board.getRemainingPiecesList()+"\n");

                System.out.println(deletedPieceManagerList+"\n");

                if (board.getKing(Piece.Color.WHITE).check())
                    System.out.println("WHITE PLAYER IS ON CHECK");

                coordinateSelf = Input.enterCoordinate("Select a WHITE piece (Example: A4): ");
            } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.WHITE));

            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

            do {
                clearConsole();
                System.out.println(board+"\n");

                System.out.println(board.getRemainingPiecesList()+"\n");

                System.out.println(deletedPieceManagerList+"\n");

                if (board.getKing(Piece.Color.WHITE).check())
                    System.out.println("WHITE PLAYER IS ON CHECK");

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

            if (!board.getCellAt(coordinateOther).isEmpty() && board.getCellAt(coordinateOther).getPiece().getType() == Piece.Type.BLACK_KING)
                endGame = true;

            board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
            board.removeHighLight();

            if (!endGame) {

                // BLACK-TURN
                System.out.println("----------------------BLACK PLAYER'S TURN----------------------");

                do {
                    clearConsole();
                    System.out.println(board+"\n");

                    System.out.println(board.getRemainingPiecesList()+"\n");

                    System.out.println(deletedPieceManagerList+"\n");

                    if (board.getKing(Piece.Color.BLACK).check())
                        System.out.println("BLACK PLAYER IS ON CHECK");

                    coordinateSelf = Input.enterCoordinate("Select a BLACK piece (Example: B7): ");
                } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.BLACK));

                board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

                do {
                    clearConsole();
                    System.out.println(board+"\n");

                    System.out.println(board.getRemainingPiecesList()+"\n");

                    System.out.println(deletedPieceManagerList+"\n");

                    if (board.getKing(Piece.Color.BLACK).check())
                        System.out.println("BLACK PLAYER IS ON CHECK");

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

                if (!board.getCellAt(coordinateOther).isEmpty() && board.getCellAt(coordinateOther).getPiece().getType() == Piece.Type.WHITE_KING)
                    endGame = true;

                board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
                board.removeHighLight();

            }
        }
    }
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
