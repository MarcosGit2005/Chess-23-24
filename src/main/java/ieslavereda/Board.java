package ieslavereda;

import com.diogonunes.jcolor.Attribute;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Board implements Serializable {

    private Map<Coordinate, Cell> cells;

    public Board() {
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
    public String getRemainingPiecesList(){
        Set<Piece> setPieces = new LinkedHashSet<>();
        setPieces.add(new King(null,null,King.Type.WHITE));
        setPieces.add(new Queen(null,null,Queen.Type.WHITE));
        setPieces.add(new Rook(null,null,Rook.Type.WHITE));
        setPieces.add(new Bishop(null,null,Bishop.Type.WHITE));
        setPieces.add(new Knight(null,null,Knight.Type.WHITE));
        setPieces.add(new Pawn(null,null,Pawn.Type.WHITE));
        setPieces.add(new King(null,null,King.Type.BLACK));
        setPieces.add(new Queen(null,null,Queen.Type.BLACK));
        setPieces.add(new Rook(null,null,Rook.Type.BLACK));
        setPieces.add(new Bishop(null,null,Bishop.Type.BLACK));
        setPieces.add(new Knight(null,null,Knight.Type.BLACK));
        setPieces.add(new Pawn(null,null,Pawn.Type.BLACK));

        String remainingPieces = setPieces.stream()
                .map(p -> colorize(" " + p.toString(), Attribute.BACK_COLOR(100,100,100)) + colorize(" ",Attribute.BACK_COLOR(100,100,100)))
                .collect(Collectors.joining()) + "\n"; // Upper part of the remaining pieces list

        for (Piece piece:setPieces){
            remainingPieces += colorize(
                    " " + cells.values().stream().filter(c -> !c.isEmpty()).map(Cell::getPiece)
                            .filter(p -> p.getType()==piece.getType())
                            .count(),Attribute.BACK_COLOR(180,180,180),Attribute.TEXT_COLOR(100,100,100)
            ) + colorize(" ",Attribute.BACK_COLOR(180,180,180));
        }

        return "REMAINING PIECES:\n" + remainingPieces;
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
    public Map<Coordinate, Cell> getCells(){
        return cells;
    }

    public void highLight(Collection<Coordinate> coordinates) {
        for (Coordinate c : coordinates)
            getCellAt(c).highlight();
    }

    public void removeHighLight() {
        cells.values().stream().forEach(cell -> cell.removeHighLight());
    }

    @Override
    public String toString() {
        String aux = "    A  B  C  D  E  F  G  H\n";

        for (int row = 1; row <= 8; row++) {
            aux += " " + row + " ";
            for (char col = 'A'; col <= 'H'; col++)
                aux += cells.get(new Coordinate(col, row));

            aux += " " + row + "\n";
        }
        aux += "    A  B  C  D  E  F  G  H";
        return aux;
    }
}
