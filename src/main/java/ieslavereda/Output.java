package ieslavereda;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Output {
    public static void OutputPlayerOne(Board board,String playerOneName){
        clearConsole();
        System.out.println("----------------------TURN OF "+playerOneName+" (WHITE)----------------------"+(MainChess.saveGame?" GAME SAVED!":""));
        System.out.println(board+"\n");

        System.out.println(board.getRemainingPiecesList()+"\n");

        System.out.println(MainChess.deletedPieceManagerList+"\n");

        if (board.getKing(Piece.Color.WHITE).check())
            System.out.println(colorize("CHECK", Attribute.TEXT_COLOR(255,255,255),Attribute.BACK_COLOR(255,0,0)));
    }
    public static void OutputPlayerTwo(Board board,String playerTwoName){
        clearConsole();
        System.out.println("----------------------TURN OF "+playerTwoName+" (BLACK)----------------------");
        System.out.println(board+"\n");

        System.out.println(board.getRemainingPiecesList()+"\n");

        System.out.println(MainChess.deletedPieceManagerList+"\n");

        if (board.getKing(Piece.Color.BLACK).check())
            System.out.println(colorize("CHECK",Attribute.TEXT_COLOR(255,255,255),Attribute.BACK_COLOR(255,0,0)));
    }
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
