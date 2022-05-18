package lab2.testing;

import lab2.*;
import org.junit.*;

class BoardTest {

    private Board board;

    @Before
    public void setup() {
        this.board = new Board();
    }

    @Test
    public void testGetCell() {
        Cell cell = board.getCell(new Coordinate(1, 4));
        Assert.assertNotNull(cell.getPiece());
    }
    @Test
    public void getMusketeerCells() {
        System.out.println(board.getMusketeerCells());
    }
    @Test
    public void getGuardCells() {
        System.out.println(board.getGuardCells());
    }
    @Test
    public void possibleCells() {
        System.out.println(board.getPossibleCells());
    }
    @Test
    public void MuskCanMoveOnTo() {
        Board board = new Board();
        Musketeer mu = new Musketeer();
        Coordinate coordinate = new Coordinate(0,3);
        Cell cell = new Cell(board.getCell(coordinate));
        System.out.println(cell+ " is here ");
        System.out.println(mu.canMoveOnto(cell));
    }
    @Test
    public void possibleDestinationsFromOneCell() {
        Board board = new Board();
        Coordinate coordinate = new Coordinate(0,4);
        Cell cell = new Cell(board.getCell(coordinate));
        System.out.println(cell+ " is here ");
        System.out.println(board.getPossibleDestinations(cell));
    }

    @Test
    public void getPossibleMoves() {
        Board board = new Board();
       // board.setTurn(Piece.Type.GUARD);
        System.out.println(board.getTurn());

        System.out.println("\n" + board);
        Coordinate coordinateFrom = new Coordinate(2, 2);
        Coordinate coordinateTo = new Coordinate(2, 1);
        Cell cellFrom = new Cell(coordinateFrom);
        Cell cellTo = new Cell(coordinateTo);
        Move move = new Move(cellFrom, cellTo);
        System.out.println( board.getPossibleDestinations(cellFrom));
        if(board.isValidMove(move)){
            board.move(move);
        }
        System.out.println("\n" + board);
        System.out.println(board.getPossibleDestinations(cellFrom)+"suka");


    }

    @Test
    public void testGetCell2() {
        //board.setTurn(Piece.Type.GUARD);
        System.out.println( board.getTurn());
        Cell cell = board.getCell(new Coordinate(4, 2));
     System.out.println(cell.toString());
    }
    @Test
    public void getdst() {
    //    board.setTurn(Piece.Type.GUARD);
        System.out.println( board.getTurn());
        System.out.println(board.getPossibleMoves());
    }
}
