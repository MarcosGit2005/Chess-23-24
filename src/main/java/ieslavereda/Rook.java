package ieslavereda;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
    public Rook(Board board, Coordinate position, Type type) {
        super(type.getType(), board!=null?board.getCellAt(position):null);
    }

    //put your task here
    public Set<Coordinate> getNextMovements(){
        return getNextMovementsAsRook(this);
    }

    //put your task here
    public static Set<Coordinate> getNextMovementsAsRook(Piece piece) {
        if(piece.getCell()==null) return new HashSet<>();

        Set<Coordinate> nextMovements = new HashSet<>();
        Coordinate posicion = piece.getCell().getCoordinate();
        Coordinate c;
        Board board = piece.getCell().getBoard();

        // Diagonal superior
        c = posicion;
        do {
            c = c.up();
            if(piece.canAddToNextMovements(c))
                nextMovements.add(c);
        } while (board.contains(c) && board.getCellAt(c).getPiece() == null);

        // Diagonal  der
        c = posicion;
        do {
            c = c.right();
            if(piece.canAddToNextMovements(c))
                nextMovements.add(c);
        } while (board.contains(c) && board.getCellAt(c).getPiece() == null);

        // Diagonal  izq
        c = posicion;
        do {
            c = c.left();
            if(piece.canAddToNextMovements(c))
                nextMovements.add(c);
        } while (board.contains(c) && board.getCellAt(c).getPiece() == null);

        // Diagonal inferior
        c = posicion;
        do {
            c = c.down();
            if(piece.canAddToNextMovements(c))
                nextMovements.add(c);
        } while (board.contains(c) && board.getCellAt(c).getPiece() == null);

        return nextMovements;
    }

    public enum Type {
        BLACK(Piece.Type.BLACK_ROOK), WHITE(Piece.Type.WHITE_ROOK);
        private Piece.Type type;

        Type(Piece.Type type) {
            this.type = type;
        }

        public Piece.Type getType() {
            return type;
        }
    }
}
