package ieslavereda;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;

public class DeletedPieceManagerList{

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
    @Override
    public String toString(){
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