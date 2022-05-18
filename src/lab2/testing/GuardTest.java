package lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import edu.toronto.cs.jam.annotations.Description;


public class GuardTest {
    Guard guard;
    Cell cell;


    @Before
    public void setup() {
        guard = new Guard();
        cell = new Cell(new Coordinate(0, 0));
    }

    @Test(timeout=1000)
    //@Description(description="Testing guard.canMoveOnto() method move onto empty cell case")
    public void testCanMoveOnto_MoveOntoEmptyCell() {
        Assert.assertTrue(guard.canMoveOnto(cell));
    }

    @Test(timeout=1000)
    // @Description(description="Testing guard.canMoveOnto() method move onto guard cell case")
    public void testCanMoveOnto_MoveOntoGuard() {
        cell.setPiece(new Guard());
        Assert.assertFalse(guard.canMoveOnto(cell));
    }

    @Test(timeout=1000)
    // @Description(description="Testing guard.canMoveOnto() method move onto musketeer cell case")
    public void testCanMoveOnto_MoveOntoMusketeer() {
        cell.setPiece(new Musketeer());
        Assert.assertFalse(guard.canMoveOnto(cell));
    }
}
