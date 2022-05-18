package lab2;

import java.util.*;

public class HumanAgent extends Agent {
    private final Scanner scanner = new Scanner(System.in);

    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     *
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() { // TODO
        Move move;
        String from, to;
        boolean containsPsbCells = false;
        boolean containsPsbDst = false;
        Cell cellFrom, cellTo;
        ArrayList<Coordinate> posibCells = new ArrayList<>();
        ArrayList<Coordinate> posibDst = new ArrayList<>();
        for (Cell cell : board.getPossibleCells()) {
            posibCells.add(cell.getCoordinate());
        }


        do {
            System.out.printf("[%s] Possible pieces are %s. Enter the piece you want to move: ", board.getTurn().getType(), posibCells);
            while (!scanner.hasNext("[a-eA-e][1-5]")) {
                System.out.printf("[%s] is an invalid piece.\n[%s] Possible pieces are %s. Enter the piece you want to move: ", scanner.next().toUpperCase(), board.getTurn().getType(), posibCells);

            }
            from = scanner.next().toUpperCase();
            //System.out.println(from);
            int from0 = Utils.charLetterToInt(from.charAt(0));
            int from1 = Utils.charNumberToInt(from.charAt(1));
            Coordinate coordinateFrom = new Coordinate(from1, from0);
            cellFrom = new Cell(coordinateFrom);
            for (Cell cell : board.getPossibleCells()) { // find if input corresponds to possible cells
                if (cell.getCoordinate().row == cellFrom.getCoordinate().row && cell.getCoordinate().col == cellFrom.getCoordinate().col) {
                    containsPsbCells = true;
                    break;
                }
            }
            if (!containsPsbCells) {
                System.out.printf("[%s] is an invalid piece.\n", from);
            }
        } while (!containsPsbCells);

        for (Cell cell : board.getPossibleDestinations(cellFrom)) {//fill all dst for typed cell
            posibDst.add(cell.getCoordinate());
        }

        do {
            System.out.printf("[%s] Possible destinations are %s. Enter where you want to move: ", board.getTurn().getType(), posibDst);
            while (!scanner.hasNext("[a-eA-e][1-5]")) {
                System.out.printf("[%s] is an invalid destination.\n[%s] Possible destinations are %s. Enter where you want to move: ", scanner.next().toUpperCase(), board.getTurn().getType(), posibDst);
            }
            to = scanner.next().toUpperCase();
            int to0 = Utils.charLetterToInt(to.charAt(0));
            int to1 = Utils.charNumberToInt(to.charAt(1));
            Coordinate coordinateTo = new Coordinate(to1, to0);
            cellTo = new Cell(coordinateTo);
            move = new Move(cellFrom, cellTo);
            for (Coordinate coordinate : posibDst) {
                if (cellTo.getCoordinate().row == coordinate.row && cellTo.getCoordinate().col == coordinate.col) {
                    containsPsbDst = true;
                    break;
                }
            }
            if (!board.isValidMove(move) || !containsPsbDst) {
                System.out.printf("[%s] is an invalid destination.\n", to);
            }
            containsPsbDst = false;
        } while (!board.isValidMove(move));

        return move;

    }
}
