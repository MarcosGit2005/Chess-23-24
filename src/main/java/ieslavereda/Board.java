package ieslavereda;

public class Board {

    private Cell[][] cells;
    public Board(){
        cells = new Cell[8][8];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell(this,new Coordinate((char)(j+65),i+1));
            }
        }
    }

    public boolean contains(Coordinate c) {
        return c.getNumber()>=1 && c.getNumber()<=8 && c.getLetter()>='A' && c.getLetter()<='H';
    }

    public Cell getCellAt(Coordinate c) {
        if (contains(c))
            return cells[c.getNumber()-1][c.getLetter()-65];
        return null;
    }
    @Override
    public String toString(){
        String out = "    A  B  C  D  E  F  G  H\n";
        for (int i = 0; i < cells.length; i++) {
            out += " "+(i+1)+" ";
            for (int j = 0; j < cells[0].length; j++) {
                out += cells[i][j].toString();
            }
            out += " "+(i+1)+"\n";
        }
        out += "    A  B  C  D  E  F  G  H";
        return out;
    }
}