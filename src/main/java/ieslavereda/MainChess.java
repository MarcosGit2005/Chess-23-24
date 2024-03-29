package ieslavereda;

import java.io.*;
import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;

public class MainChess {
    private static String playerOneName;
    private static String playerTwoName;
    private static Board board;
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

        fileName="";

        boolean correctCoordinate = true;

        Coordinate coordinateAux=new Coordinate('z',9);
        Coordinate coordinateSelf=new Coordinate('z',9);
        Coordinate coordinateOther=new Coordinate('z',9);

        try {
            if (Input.enterOption("Do you want to load a saved game (Y/N)?: ")){
                load();
            } else if (Input.enterOption("Do you want to save the game (Y/N)?: ")){
                fileName = Input.enterString("How do you want to name the file (without the termination)?: ");
                saveGame=true;
            }
        } catch (Exception e){
            System.out.println(colorize("The file does not exists or it's corrupted",Attribute.TEXT_COLOR(255,0,0)));
        }

        if (!gameLoaded){
            board.initialize();

            System.out.print("Enter the name of player one (white): ");
            do {
                playerOneName = Input.enterNombre().toUpperCase();
            } while (playerOneName.isEmpty());

            System.out.print("Enter the name of player two (black): ");
            do {
                playerTwoName = Input.enterNombre().toUpperCase();
            } while (playerTwoName.isEmpty());
        }

        King whiteKing = board.getKing(Piece.Color.WHITE);
        King blackKing = board.getKing(Piece.Color.BLACK);

        while (!endGame) {

            if (board.isWhiteTurn()){ // with this I can save the last turn

                // -------------------------- WHITE-TURN --------------------------------

                do {

                    Output.OutputPlayerOne(board,playerOneName);
                    if (!correctCoordinate)
                        System.err.println("Enter a valid coordinate");

                    try { // the exception is only in case that the coordinate doesn't exist in the board
                        coordinateSelf = Input.enterCoordinate("Select a WHITE piece (Example: A4): ");
                    } catch (Exception e){
                        correctCoordinate=false;
                    }

                } while (!board.contains(coordinateSelf) || board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.WHITE));
                correctCoordinate=true; // gets back to its default value

                board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

                do {

                    Output.OutputPlayerOne(board,playerOneName);
                    if (!correctCoordinate)
                        System.err.println("Enter a valid coordinate");

                    try {
                        coordinateAux = Input.enterCoordinate("Select either another WHITE piece or a valid coordinate to attack/move (Example: A4): ");
                    } catch (Exception e){
                        correctCoordinate=false;
                        coordinateAux=null;
                    }

                    if (board.contains(coordinateAux) && !board.getCellAt(coordinateAux).isEmpty()) {
                        if (board.getCellAt(coordinateAux).getPiece().getColor() == Piece.Color.WHITE) {
                            coordinateSelf = coordinateAux;
                            board.removeHighLight();
                            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());
                            correctCoordinate=true; // because another piece was selected
                        }
                    }
                    coordinateOther = coordinateAux;
                } while (!board.getCellAt(coordinateSelf).getPiece().getNextMovements().contains(coordinateAux));
                correctCoordinate=true;

                board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
                board.removeHighLight();

                board.changeTurn(); // I have to change it before I save

                if (!board.containsKing(whiteKing) || whiteKing.checkMate()){
                    endGame=true;
                    finalMessage = "CHECKMATE\n"+playerTwoName+" WINS (BLACK)";
                } else if (!board.containsKing(blackKing) || blackKing.checkMate()){
                    endGame=true;
                    finalMessage = "CHECKMATE\n"+playerOneName+"  WINS (WHITE)";
                } else if (saveGame){ // it doesn't save the checkmate
                    try{
                        save();
                    } catch (IOException e){
                        System.out.println(colorize("The directory where the games are saved has been deleted (SAVED_GAMES)",Attribute.TEXT_COLOR(255,0,0)));
                    }
                }
            }

            if (!endGame) { // if endGame is true (checkmate or dead king) the black turn is skipped

                // --------------------------------- BLACK-TURN ---------------------------------

                do {

                    Output.OutputPlayerTwo(board,playerTwoName);
                    if (!correctCoordinate)
                        System.err.println("Enter a valid coordinate");
                    try {
                        coordinateSelf = Input.enterCoordinate("Select a BLACK piece (Example: B7): ");
                    } catch (Exception e){
                        correctCoordinate=false;
                    }

                } while (!board.contains(coordinateSelf) || board.getCellAt(coordinateSelf).isEmpty() || !(board.getCellAt(coordinateSelf).getPiece().getColor() == Piece.Color.BLACK));
                correctCoordinate=true;

                board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());

                do {

                    Output.OutputPlayerTwo(board,playerTwoName);
                    if (!correctCoordinate)
                        System.err.println("Enter a valid coordinate");

                    try {
                        coordinateAux = Input.enterCoordinate("Select either another BLACK piece or a valid coordinate to attack/move (Example: A4): ");
                    } catch (Exception e){
                        correctCoordinate=false;
                        coordinateAux=null;
                    }

                    if (board.contains(coordinateAux) && !board.getCellAt(coordinateAux).isEmpty()) {
                        if (board.getCellAt(coordinateAux).getPiece().getColor() == Piece.Color.BLACK) {
                            coordinateSelf = coordinateAux;
                            board.removeHighLight();
                            board.highLight(board.getCellAt(coordinateSelf).getPiece().getNextMovements());
                            correctCoordinate=true; // because another piece was selected
                        }
                    }
                    coordinateOther = coordinateAux;
                } while (!board.getCellAt(coordinateSelf).getPiece().getNextMovements().contains(coordinateAux));
                correctCoordinate=true;

                board.getCellAt(coordinateSelf).getPiece().moveTo(coordinateOther); // Move the piece
                board.removeHighLight();

                board.changeTurn();

                if (!board.containsKing(whiteKing) || whiteKing.checkMate()){
                    endGame=true;
                    finalMessage = "CHECKMATE\n"+playerTwoName+" WINS (BLACK)";
                } else if (!board.containsKing(blackKing) || blackKing.checkMate()){
                    endGame=true;
                    finalMessage = "CHECKMATE\n"+playerOneName+"  WINS (WHITE)";
                } else if (saveGame){
                    try{
                        save();
                    } catch (IOException e){
                        System.out.println(colorize("The directory where the games are saved has been deleted (SAVED_GAMES)",Attribute.TEXT_COLOR(255,0,0)));
                    }
                }
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

            gameLoaded = true;
            saveGame = true;

        }
    }
}
