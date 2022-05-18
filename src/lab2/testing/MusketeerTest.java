package lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import edu.toronto.cs.jam.annotations.Description;


public class MusketeerTest {
    Musketeer musketeer;
    Cell cell;


    @Before
    public void setup() {
        musketeer = new Musketeer();
        cell = new Cell(new Coordinate(0, 0));
    }

    @Test(timeout=1000)
    // @Description(description="Testing musketeer.canMoveOnto() method move onto empty cell case")
    public void testCanMoveOnto_MoveOntoEmptyCell() {
        Assert.assertFalse(musketeer.canMoveOnto(cell));
    }

    @Test(timeout=1000)
    // @Description(description="Testing musketeer.canMoveOnto() method move onto guard cell case")
    public void testCanMoveOnto_MoveOntoGuard() {
        cell.setPiece(new Guard());
        Assert.assertTrue(musketeer.canMoveOnto(cell));
    }

    @Test(timeout=1000)
    // @Description(description="Testing musketeer.canMoveOnto() method move onto musketeer cell case")
    public void testCanMoveOnto_MoveOntoMusketeer() {
        cell.setPiece(new Musketeer());
        Assert.assertFalse(musketeer.canMoveOnto(cell));
    }
}
