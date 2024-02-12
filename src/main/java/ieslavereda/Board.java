package ieslavereda;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    private Map<Coordinate, Cell> cells;

    public Board() {
        cells = new HashMap<>();

        for (int row = 1; row <= 8; row++)
            for (char col = 'A'; col <= 'H'; col++)
                cells.put(new Coordinate(col, row), new Cell(this, new Coordinate(col,row)));

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
