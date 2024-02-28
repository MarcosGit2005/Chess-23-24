package ieslavereda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;

public class DeletedPieceManagerList implements Serializable {

    List<Piece> pieceList;

    public DeletedPieceManagerList(){
        pieceList = new ArrayList<>();
    }
    public void addPiece(Piece piece){
        pieceList.add(piece);
    }
    public Piece removeLast(){
        return pieceList.remove(pieceList.size()-1);
    }
    public int count(Piece.Type type){
        int cont=0;
        for (Piece piece:pieceList){
            if (piece.getType().equals(type))
                cont++;
        }
        return cont;
    }
    public int countRemaining(Piece.Type type){
        if (type == Piece.Type.BLACK_PAWN || type == Piece.Type.WHITE_PAWN)
            return 8 - count(type);
        else if (type == Piece.Type.BLACK_KNIGHT || type == Piece.Type.WHITE_KNIGHT)
            return 2 - count(type);
        else if (type == Piece.Type.BLACK_BISHOP || type == Piece.Type.WHITE_BISHOP)
            return 2 - count(type);
        else if (type == Piece.Type.BLACK_ROOK || type == Piece.Type.WHITE_ROOK)
            return 2 - count(type);
        else if (type == Piece.Type.BLACK_QUEEN || type == Piece.Type.WHITE_QUEEN)
            return 1 - count(type);
        else // Kings
            return 1 - count(type);

    }
    public String getRemainingPiecesList(){
        Set<Piece> setPieces = getUpperPartFromList();

        String remainingPieces = setPieces.stream()
                .map(p -> colorize(" " + p.toString(), Attribute.BACK_COLOR(100,100,100)) + colorize(" ",Attribute.BACK_COLOR(100,100,100)))
                .collect(Collectors.joining()) + "\n"; // Upper part of the remaining pieces list

        for (Piece piece:setPieces){
            remainingPieces += colorize(" " + countRemaining(piece.getType()),Attribute.BACK_COLOR(180,180,180),Attribute.TEXT_COLOR(100,100,100)) +
                    colorize(" ",Attribute.BACK_COLOR(180,180,180));
        }

        return "REMAINING PIECES:\n" + remainingPieces;
    }
    public String getDeletedPiecesList(){
        return toString();
    }
    private Set<Piece> getUpperPartFromList(){
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

        return setPieces;
    }
    @Override
    public String toString(){
        Set<Piece> setPieces = getUpperPartFromList();

        String deletedPieces = setPieces.stream()
                .map(p -> colorize(" " + p.toString(),Attribute.BACK_COLOR(100,100,100)) + colorize(" ",Attribute.BACK_COLOR(100,100,100)))
                .collect(Collectors.joining()) + "\n"; // Upper part of the deleted pieces list

        for (Piece piece:setPieces){
            deletedPieces += colorize(" " + count(piece.getType()),Attribute.BACK_COLOR(180,180,180),Attribute.TEXT_COLOR(100,100,100)) +
                    colorize(" ",Attribute.BACK_COLOR(180,180,180));
        }

        return "DELETED PIECES:\n" + deletedPieces;
    }
}