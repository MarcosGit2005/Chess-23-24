package ieslavereda;

import java.io.*;
import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;

public class MainChess {
    private static String playerOneName;
    private static String playerTwoName;
    private static Board board;
    public static DeletedPieceManagerList deletedPieceManagerList = new DeletedPieceManagerList();
    // Save variables
    public static Boolean gameLoaded;
    public static Boolean saveGame;
    private static String fileName;
    public static void main(String[] main) {
        board = new Board();
        boolean endGame = false;
        String finalMessage="";

        saveGame = false;
        gameLoaded = false;
        boolean firstTurn = true;

        fileName="";

        Coordinate coordinateAux;
        Coordinate coordinateSelf;
        Coordinate coordinateOther;

        try {
            if (Input.enterOption("Do you want to load a saved game (Y/N)?: ")){
                load();
            } else if (Input.enterOption("Do you want to save the game (Y/N)?: ")){
                fileName = Input.enterString("How do you want to name the file (without the termination)?: ");
                saveGame=true;
            }
        } catch (Exception e){
            System.err.println("The file does not exist or it's corrupted");
        }

        if (!gameLoaded){
            board.initialize();

            System.out.print("Enter the name of player one (white): ");
            do {
                playerOneName = Input.enterNombre().toUpperCase();
            } while (playerOneName.isEmpty());

            System.out.print("Enter the name of player two (white): ");
            do {
                playerTwoName = Input.enterNombre().toUpperCase();
            } while (playerTwoName.isEmpty());
        }

        King whiteKing = board.getKing(Piece.Color.WHITE);
        King blackKing = board.getKing(Piece.Color.BLACK);

        while (!endGame) {
            // -------------------------- WHITE-TURN --------------------------------
            do {

                Output.OutputPlayerOne(board,playerOneName);

                if (!firstTurn && saveGame) // it doesn't save until the black player turn is finished
                    System.out.println(colorize("GAME SAVED!",Attribute.TEXT_COLOR(255,255,255),Attribute.BACK_COLOR(50,50,255)));

                coordinateSelf = Input.enterCoordinate("Select a WHITE piece (Example: A4): ");

            } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.WHITE));

            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

            do {

                Output.OutputPlayerOne(board,playerOneName);

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

            board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
            board.removeHighLight();

            if (whiteKing.check() && whiteKing.checkMate()){
                endGame=true;
                finalMessage = "CHECKMATE\n"+playerTwoName+" WINS (BLACK)";
            }

            if (blackKing.check() && blackKing.checkMate()){
                endGame=true;
                finalMessage = "CHECKMATE\n"+playerOneName+"  WINS (WHITE)";
            }

            if (!endGame) {

                // --------------------------------- BLACK-TURN ---------------------------------

                do {

                    Output.OutputPlayerTwo(board,playerTwoName);

                    coordinateSelf = Input.enterCoordinate("Select a BLACK piece (Example: B7): ");
                } while (board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.BLACK));

                board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

                do {

                    Output.OutputPlayerTwo(board,playerTwoName);

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

                board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
                board.removeHighLight();

                if (saveGame){
                    try{
                        save();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                if (whiteKing.check() && whiteKing.checkMate()){
                    endGame=true;
                    finalMessage = "CHECKMATE\n"+playerTwoName+" WINS (BLACK)";
                }

                if (blackKing.check() && blackKing.checkMate()){
                    endGame=true;
                    finalMessage = "CHECKMATE\n"+playerOneName+"  WINS (WHITE)";
                }

                firstTurn=false;

            }
        }

        System.out.println(board);
        System.out.println(finalMessage);

    }
    public static void save() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("SAVED_GAMES/"+fileName+".dat")
                )
        )){

            objectOutputStream.writeObject(playerOneName);
            objectOutputStream.writeObject(playerTwoName);
            objectOutputStream.writeObject(board);
            objectOutputStream.writeObject(deletedPieceManagerList);

        }
    }
    public static void load() throws IOException, ClassNotFoundException{
        fileName = Input.enterString("Which file do you want to load (without the termination)?: ");
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("SAVED_GAMES/"+fileName+".dat")
                )
        )){

            playerOneName = (String) objectInputStream.readObject();
            playerTwoName = (String) objectInputStream.readObject();
            board = (Board) objectInputStream.readObject();
            deletedPieceManagerList = (DeletedPieceManagerList) objectInputStream.readObject();

            gameLoaded = true;
            saveGame = true;

        }
    }
}
