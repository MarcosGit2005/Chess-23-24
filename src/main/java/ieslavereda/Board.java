package ieslavereda;

import com.diogonunes.jcolor.Attribute;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Board implements Serializable{

    private Map<Coordinate, Cell> cells;
    private DeletedPieceManagerList deletedPieceManagerList;
    private Boolean whiteTurn;
    public Board(){
        whiteTurn = true;

        deletedPieceManagerList = new DeletedPieceManagerList();

        cells = new HashMap<>();

        for (int row = 1; row <= 8; row++)
            for (char col = 'A'; col <= 'H'; col++)
                cells.put(new Coordinate(col, row), new Cell(this, new Coordinate(col,row)));

    }
    public void initialize(){
        for (Cell cell:cells.values()){
            Coordinate coordinate = cell.getCoordinate();

            if (coordinate.getNumber()==1){
                if (coordinate.getLetter()=='A' || coordinate.getLetter()=='H')
                    cell.setPiece(new Rook(this,coordinate,Rook.Type.BLACK));
                if (coordinate.getLetter()=='B' || coordinate.getLetter()=='G')
                    cell.setPiece(new Knight(this,coordinate,Knight.Type.BLACK));
                if (coordinate.getLetter()=='C' || coordinate.getLetter()=='F')
                    cell.setPiece(new Bishop(this,coordinate,Bishop.Type.BLACK));
                if (coordinate.getLetter()=='D')
                    cell.setPiece(new Queen(this,coordinate,Queen.Type.BLACK));
                if (coordinate.getLetter()=='E')
                    cell.setPiece(new King(this,coordinate,King.Type.BLACK));
            }

            if (coordinate.getNumber()==2)
                cell.setPiece(new Pawn(this,coordinate,Pawn.Type.BLACK));

            if (coordinate.getNumber()==7)
                cell.setPiece(new Pawn(this,coordinate,Pawn.Type.WHITE));

            if (coordinate.getNumber()==8){
                if (coordinate.getLetter()=='A' || coordinate.getLetter()=='H')
                    cell.setPiece(new Rook(this,coordinate,Rook.Type.WHITE));
                if (coordinate.getLetter()=='B' || coordinate.getLetter()=='G')
                    cell.setPiece(new Knight(this,coordinate,Knight.Type.WHITE));
                if (coordinate.getLetter()=='C' || coordinate.getLetter()=='F')
                    cell.setPiece(new Bishop(this,coordinate,Bishop.Type.WHITE));
                if (coordinate.getLetter()=='D')
                    cell.setPiece(new Queen(this,coordinate,Queen.Type.WHITE));
                if (coordinate.getLetter()=='E')
                    cell.setPiece(new King(this,coordinate,King.Type.WHITE));
            }
        }
    }
    public void changeTurn(){
        whiteTurn = !whiteTurn;
    }
    public boolean isWhiteTurn(){
        return whiteTurn;
    }
    public boolean contains(Coordinate c) {
        return cells.containsKey(c);
    }

    public Cell getCellAt(Coordinate c) {
        return cells.get(c);
    }

    public Set<Coordinate> getNextMovements(Piece.Color pieceColor){
        return cells.values().stream().filter(cell -> !cell.isEmpty())
                .map(Cell::getPiece).filter(piece -> piece.getColor()==pieceColor)
                .flatMap(piece -> piece.getNextMovements().stream()).collect(Collectors.toSet());
    }

    public King getKing(Piece.Color pieceColor){
        return cells.values().stream().filter(cell -> !cell.isEmpty())
                .map(Cell::getPiece).filter(piece -> piece instanceof King)
                .map(piece -> (King) piece).filter(king -> king.getColor()==pieceColor)
                .findFirst().get();
    }
    public boolean containsKing(King king){
        return cells.values().stream().filter(c -> !c.isEmpty()).map(Cell::getPiece).collect(Collectors.toList()).contains(king);
    }
    public Map<Coordinate, Cell> getCells(){
        return cells;
    }
    public boolean checkIfCheck(Piece piece, Coordinate coordinateDestination){
        boolean check = false;
        Coordinate coordinateBefore = piece.getCell().getCoordinate();

        if (getCellAt(coordinateDestination).isEmpty()){
            piece.moveTo(coordinateDestination);
            check = getKing(piece.getColor()).check();
            piece.getBackTo(coordinateBefore);
        } else {
            piece.moveTo(coordinateDestination);
            check = getKing(piece.getColor()).check();
            piece.getBackTo(coordinateBefore);

            Piece pieceDeleted = deletedPieceManagerList.removeLast();
            pieceDeleted.setCell(getCellAt(coordinateDestination));
            pieceDeleted.place();
        }
        return check;
    }
    public void highLight(Collection<Coordinate> coordinates) {
        for (Coordinate c : coordinates)
            getCellAt(c).highlight();
    }

    public void removeHighLight() {
        cells.values().stream().forEach(cell -> cell.removeHighLight());
    }
    public DeletedPieceManagerList getDeletedPieceManagerList(){
        return deletedPieceManagerList;
    }
    private String getBlackBoardCells(){
        String chain="";
        for (int row = 8; row >= 1; row--) {
            chain += " " + row + " ";
            for (char col = 'H'; col >= 'A'; col--)
                chain += cells.get(new Coordinate(col, row));

            chain += " " + row + "\n";
        }
        return chain;
    }
    public String getBlackBoard(){
        String chain = "    H  G  F  E  D  C  B  A\n";

        chain += getBlackBoardCells();

        chain += "    H  G  F  E  D  C  B  A";
        return chain;
    }
    public String getWhiteBoard(){
        return toString();
    }
    private String getWhiteBoardCells(){
        String chain="";
        for (int row = 1; row <= 8; row++) {
            chain += " " + row + " ";
            for (char col = 'A'; col <= 'H'; col++)
                chain += cells.get(new Coordinate(col, row));

            chain += " " + row + "\n";
        }
        return chain;
    }
    @Override
    public String toString() {
        String chain = "    A  B  C  D  E  F  G  H\n";

        chain += getWhiteBoardCells();

        chain += "    A  B  C  D  E  F  G  H";
        return chain;
    }
}
