package ieslavereda;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {

    public Knight(Board board, Coordinate position, Type type) {
        super(type.getType(), board.getCellAt(position));
    }

    public Set<Coordinate> getNextMovements() {

        Set<Coordinate> nextMovements = new HashSet<>();

        Coordinate myPosition = getCell().getCoordinate();
        Coordinate c;

        //Up
        c = myPosition.up().up().left();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        c = myPosition.up().up().right();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        //Down
        c = myPosition.down().down().left();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        c = myPosition.down().down().right();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        //Left
        c = myPosition.left().left().up();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        c = myPosition.left().left().down();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        //Right
        c = myPosition.right().right().up();
        if(canAddToNextMovements(c)) nextMovements.add(c);

        c = myPosition.right().right().down();
        if(canAddToNextMovements(c)) nextMovements.add(c);


        return nextMovements;
    }

    public enum Type {
        BLACK(Piece.Type.BLACK_KNIGHT), WHITE(Piece.Type.WHITE_KNIGHT);
        private Piece.Type type;

        Type(Piece.Type type) {
            this.type = type;
        }

        public Piece.Type getType() {
            return type;
        }
    }
}