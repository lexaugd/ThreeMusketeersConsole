package lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//import edu.toronto.cs.jam.annotations.Description;

public class BoardTest {

    private Board board;
    private boolean testingResubmission = true; // set to true to run tests for re-submission

    @Before
    public void setup() {
        if (testingResubmission) {
            this.board = new Board("boards/ResubmissionBoard.txt");
        } else {
            this.board = new Board();
        }
    }

    @Test(timeout=2000)
 //   @Description(description="Testing getMuskeeteerCells() method")
    public void testGetMusketeerCells() {
        List<Cell> cells = board.getMusketeerCells();
        List<Cell> expectedCells = getAllCells().stream()
                .filter(cell -> cell.hasPiece() && cell.getPiece().getType() == Piece.Type.MUSKETEER)
                .toList();
        Assert.assertEquals(expectedCells, cells);
    }

    @Test(timeout=2000)
    //@Description(description="Testing getGuardCells() method")
    public void testGetGuardCells() {
        List<Cell> cells = board.getGuardCells();
        List<Cell> expectedCells = getAllCells().stream()
                .filter(cell -> cell.hasPiece() && cell.getPiece().getType() == Piece.Type.GUARD)
                .toList();
        Assert.assertEquals(expectedCells, cells);
    }

    @Test(timeout=2000)
   // @Description(description="Testing getGetCell() method on coordinate [0, 0]")
    public void testGetCell() {
        Cell cell = board.getCell(new Coordinate(0, 0));
        Assert.assertEquals(cell, board.board[0][0]);
    }

    @Test(timeout=2000)
    //@Description(description="Testing board.move() method 2,3 -> 3,3")
    public void testMove() {
        Cell fromCell;
        Cell toCell;
        if (testingResubmission) {
            fromCell = board.getCell(new Coordinate(2, 3));
            toCell = board.getCell(new Coordinate(3, 3));
        } else {
            fromCell = board.getCell(new Coordinate(2, 2));
            toCell = board.getCell(new Coordinate(2, 3));
        }
        Piece piece = fromCell.getPiece();
        Move move = new Move(fromCell, toCell);

        board.move(move);

        Assert.assertNull(fromCell.getPiece());
        Assert.assertEquals(piece, toCell.getPiece());
    }

    @Test(timeout=2000)
   // @Description(description="Testing board.undomove() method 2,3 -> 3,3")
    public void testUndoMove() {
        Cell fromCell;
        Cell toCell;
        if (testingResubmission) {
            fromCell = board.getCell(new Coordinate(2, 3));
            toCell = board.getCell(new Coordinate(3, 3));
        } else {
            fromCell = board.getCell(new Coordinate(2, 2));
            toCell = board.getCell(new Coordinate(2, 3));
        }
        Piece piece1 = fromCell.getPiece();
        Piece piece2 = toCell.getPiece();
        Move move = new Move(fromCell, toCell);
        Move moveCopy = new Move(move);
        board.move(move);
        board.undoMove(moveCopy);

        Assert.assertEquals(piece1, fromCell.getPiece());
        Assert.assertEquals(piece2, toCell.getPiece());
    }

    @Test(timeout=2000)
    //@Description(description="Testing board.isValidMove() method 3,0 -> 3,1")
    public void testIsValidMove() {
        Cell fromCell;
        Cell toCell;
        if (testingResubmission) {
            fromCell = board.getCell(new Coordinate(3, 0));
            toCell = board.getCell(new Coordinate(3, 1));
        } else {
            fromCell = board.getCell(new Coordinate(2, 2));
            toCell = board.getCell(new Coordinate(2, 3));
        }
        Move move = new Move(fromCell, toCell);
        Assert.assertTrue(board.isValidMove(move));
    }

    @Test(timeout=2000)
    //@Description(description="Testing board.isValidMove() method not text to case")
    public void testIsValidMove_Invalid_NotNextTo() {
        Cell fromCell;
        Cell toCell;
        if (testingResubmission) {
            fromCell = board.getCell(new Coordinate(3, 0));
            toCell = board.getCell(new Coordinate(4, 1));
        } else {
            fromCell = board.getCell(new Coordinate(2, 2));
            toCell = board.getCell(new Coordinate(3, 3));
        }
        Move move = new Move(fromCell, toCell);
        Assert.assertFalse(board.isValidMove(move));
    }

    @Test(timeout=2000)
   // @Description(description="Testing board.isValidMove() wrong piece case")
    public void testIsValidMove_Invalid_WrongPieceType() {
        Cell fromCell;
        Cell toCell;
        if (testingResubmission) {
            fromCell = board.getCell(new Coordinate(3, 1));
            toCell = board.getCell(new Coordinate(3, 2));
        } else {
            fromCell = board.getCell(new Coordinate(0, 0));
            toCell = board.getCell(new Coordinate(0, 1));
        }
        Move move = new Move(fromCell, toCell);
        Assert.assertFalse(board.isValidMove(move));
    }

    @Test(timeout=2000)
   // @Description(description="Testing getPossibleCells()")
    public void testGetPossibleCells() {
        List<Cell> cells = board.getPossibleCells();
        List<Cell> expectedCells = new ArrayList<>();
        if (testingResubmission) {
            expectedCells.add(board.getCell(new Coordinate(1, 4)));
            expectedCells.add(board.getCell(new Coordinate(2, 3)));
            expectedCells.add(board.getCell(new Coordinate(3, 0)));
        } else {
            expectedCells.add(board.getCell(new Coordinate(0, 4)));
            expectedCells.add(board.getCell(new Coordinate(2, 2)));
            expectedCells.add(board.getCell(new Coordinate(4, 0)));
        }

        Comparator<Cell> compareByCoordinate = Comparator.comparing((Cell c) -> c.getCoordinate().toString());
        Collections.sort(cells, compareByCoordinate);
        Collections.sort(expectedCells, compareByCoordinate);
        Assert.assertEquals(expectedCells, cells);
    }

    @Test(timeout=2000)
    //@Description(description="Testing getPossibleDestinations()")
    public void testGetPossibleDestinations() {
        Cell fromCell;
        List<Cell> expectedDestinations = new ArrayList<>();
        if (testingResubmission) {
            fromCell = board.getCell(new Coordinate(2, 3));
            expectedDestinations.add(board.getCell(new Coordinate(1, 3)));
            expectedDestinations.add(board.getCell(new Coordinate(2, 2)));
            expectedDestinations.add(board.getCell(new Coordinate(2, 4)));
            expectedDestinations.add(board.getCell(new Coordinate(3, 3)));
        } else {
            fromCell = board.getCell(new Coordinate(0, 4));
            expectedDestinations.add(board.getCell(new Coordinate(1, 4)));
            expectedDestinations.add(board.getCell(new Coordinate(0, 3)));
        }
        List<Cell> destinations = board.getPossibleDestinations(fromCell);

        Comparator<Cell> compareByCoordinate = Comparator.comparing((Cell c) -> c.getCoordinate().toString());
        Collections.sort(destinations, compareByCoordinate);
        Collections.sort(expectedDestinations, compareByCoordinate);
        Assert.assertEquals(expectedDestinations, destinations);
    }

    @Test(timeout=2000)
  //  @Description(description="Testing isGameOver Guard win by same row case")
    public void testIsGameOver_GuardWon_SameRow() {
        board = new Board(" boards/GameOverGuardWonSameRow.txt");
        Assert.assertTrue(board.isGameOver());
        Assert.assertEquals(Piece.Type.GUARD, board.getWinner());
    }

    @Test(timeout=2000)
    //  @Description(description="Testing isGameOver Guard win by same col case")
    public void testIsGameOver_GuardWon_SameCol() {
        board = new Board(" boards/GameOverGuardWonSameCol.txt");
        Assert.assertTrue(board.isGameOver());
        Assert.assertEquals(Piece.Type.GUARD, board.getWinner());
    }

    @Test(timeout=2000)
    // @Description(description="Testing isGameOver Musketeer win case")
    public void testIsGameOver_MusketeerWon() {
        board = new Board(" boards/GameOverMusketeerWon.txt");
        Assert.assertTrue(board.isGameOver());
        Assert.assertEquals(Piece.Type.MUSKETEER, board.getWinner());
    }

    @Test(timeout=2000)
    // @Description(description="Testing isGameOver not over case")
    public void testIsGameOver_NotOver() {
        board = new Board(" boards/NearEnd.txt");
        Assert.assertFalse(board.isGameOver());
        Assert.assertNull(board.getWinner());
    }

    @Test(timeout=2000)
    //  @Description(description="Testing isGameOver guard can move case")
    public void testIsGameOver_NotOver_GuardCanMove() {
        board = new Board("boards/NearEndGuardCanMove.txt");
        Assert.assertFalse(board.isGameOver());
        Assert.assertNull(board.getWinner());
    }

    private List<Cell> getAllCells() {
        return Arrays.stream(board.board).flatMap(Arrays::stream).collect(Collectors.toList());
    }
}
