package ieslavereda;

import com.diogonunes.jcolor.Attribute;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.diogonunes.jcolor.Ansi.colorize;

public abstract class Piece implements Serializable {

    private Type type;
    private Cell cell;
    public Piece(Type type, Cell cell){
        this.type = type;
        this.cell = cell;
        place();
    }
    protected void place() {
        if(cell!=null)
            cell.setPiece(this);
    }
    protected boolean canAddToNextMovements(Coordinate c) {

        Board board = getCell().getBoard();

        if(!board.contains(c)) return false;

        if(board.getCellAt(c).isEmpty()) return true;

        if(board.getCellAt(c).getPiece().getColor()!=getColor()) return true;

        return false;
    }
    public boolean canMoveTo(Coordinate coordinate){
        return getNextMovements().contains(coordinate);
    }

    public void remove(){
        if(cell!=null)
            cell.setPiece(null);
        cell = null;
        MainChess.deletedPieceManagerList.addPiece(this);
    }

    public boolean moveTo(Coordinate coordinate){
        if(cell == null) return false;
        if(!canMoveTo(coordinate)) return false;

        Cell destination = cell.getBoard().getCellAt(coordinate);

        if(!destination.isEmpty())
            destination.getPiece().remove();

        cell.setPiece(null);
        cell = destination;

        place();

        return true;
    }
    public void getBackTo(Coordinate coordinate){ // Only to get back in the checkmate function
        Cell destination = cell.getBoard().getCellAt(coordinate);

        cell.setPiece(null);
        cell = destination;

        place();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Type getType() {
        return type;
    }

    public Cell getCell() {
        return cell;
    }

    public Color getColor(){
        return type.getColor();
    }

    public abstract Set<Coordinate> getNextMovements();
    @Override
    public String toString(){
        String resultado;

        if(cell==null){
            resultado = colorize(type.getShape(),type.getColor().getAttribute());
        }else{
            resultado = colorize(type.getShape(),type.getColor().getAttribute(),cell.getColor().getAttribute());
        }
        return resultado;
    }

    public enum Color {

        WHITE(Attribute.BRIGHT_WHITE_TEXT()),
        BLACK(Attribute.TEXT_COLOR(0, 0, 0));

        private Attribute color;

        Color(Attribute color) {
            this.color = color;
        }

        public Attribute getAttribute() {
            return color;
        }

    }

    public enum Type {

        WHITE_KING("♚", Color.WHITE),
        WHITE_QUEEN("♛", Color.WHITE),
        WHITE_ROOK("♜", Color.WHITE),
        WHITE_BISHOP("♝", Color.WHITE),
        WHITE_KNIGHT("♞", Color.WHITE),
        WHITE_PAWN("♟", Color.WHITE),
        BLACK_KING("♚", Color.BLACK),
        BLACK_QUEEN("♛", Color.BLACK),
        BLACK_ROOK("♜", Color.BLACK),
        BLACK_BISHOP("♝", Color.BLACK),
        BLACK_KNIGHT("♞", Color.BLACK),
        BLACK_PAWN("♟", Color.BLACK);

        private final String shape;
        private final Color color;

        Type(String shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public String getShape() {
            return shape;
        }
        public Color getColor(){
            return color;
        }
    }

}