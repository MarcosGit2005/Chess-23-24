package ieslavereda;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Piece {

    public King(Board board, Coordinate position, Type type) {
        super(type.getType(), board!=null?board.getCellAt(position):null);
    }

    @Override
    public Set<Coordinate> getNextMovements() {

        Coordinate position = getCell().getCoordinate();
        Set<Coordinate> nextMovements = new HashSet<>();

        if(getCell()==null) return nextMovements;

        if (canAddToNextMovements(position.up()))
            nextMovements.add(position.up());

        if (canAddToNextMovements(position.up().right()))
            nextMovements.add(position.up().right());

        if (canAddToNextMovements(position.right()))
            nextMovements.add(position.right());

        if (canAddToNextMovements(position.down().right()))
            nextMovements.add(position.down().right());

        if (canAddToNextMovements(position.down()))
            nextMovements.add(position.down());

        if (canAddToNextMovements(position.down().left()))
            nextMovements.add(position.down().left());

        if (canAddToNextMovements(position.left()))
            nextMovements.add(position.left());

        if (canAddToNextMovements(position.up().left()))
            nextMovements.add(position.up().left());

        return nextMovements;
    }

    private void checkCoordinate(Coordinate coordinate, Set<Coordinate> nextMovements) {

        if (getCell() == null) return;

        Board board = getCell().getBoard();

        if (!board.contains(coordinate)) return;

        if (board.getCellAt(coordinate).isEmpty() || board.getCellAt(coordinate).getPiece().getColor() != getColor())
            nextMovements.add(coordinate);

    }
    public boolean check(){
        return getCell().getBoard()
                .getNextMovements(getColor()==Color.BLACK?Color.WHITE:Color.BLACK)
                .contains(getCell().getCoordinate());
    }
    public boolean checkMate(){ // PENDIENTE
        Board board = getCell().getBoard();
        for (Piece piece: getCell().getBoard().getCells().values().stream().filter(c -> !c.isEmpty()).map(Cell::getPiece)
                .filter(p -> p.getColor()==getColor()).collect(Collectors.toList())){
            for (Coordinate coordinate: piece.getNextMovements()){
                if (!board.checkIfCheck(piece,coordinate))
                    return false;
            }
        }
        return true;
    }

    public enum Type {
        BLACK(Piece.Type.BLACK_KING), WHITE(Piece.Type.WHITE_KING);
        private Piece.Type type;

        Type(Piece.Type type) {
            this.type = type;
        }

        public Piece.Type getType() {
            return type;
        }
    }
}
