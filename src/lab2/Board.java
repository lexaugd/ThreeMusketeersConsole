package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Board {
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     *
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     *
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     *
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { // TODO
        Cell cell = new Cell(board[coordinate.row][coordinate.col].getCoordinate());
        cell.setPiece(board[coordinate.row][coordinate.col].getPiece());
        return cell;
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }

    /**
     * Gets all the Musketeer cells on the board.
     *
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() { // TODO
        ArrayList<Cell> musketCellsList = new ArrayList<Cell>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col].hasPiece()) {
                    if (board[row][col].getPiece().getSymbol().equals("X")) {
                        musketCellsList.add(new Cell(board[row][col]));
                    }
                }
            }
        }
        return musketCellsList;
    }

    /**
     * Gets all the Guard cells on the board.
     *
     * @return List of cells
     */
    public List<Cell> getGuardCells() { // TODO
        ArrayList<Cell> guardCellsList = new ArrayList<Cell>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col].hasPiece()) {
                    if (board[row][col].getPiece().getSymbol().equals("O")) {
                        guardCellsList.add(new Cell(board[row][col]));
                    }
                }
            }
        }
        return guardCellsList;
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     *
     * @param move a valid move
     */
    public void move(Move move) { // TODO
        board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].setPiece(board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].getPiece()); //move piece to a given coordinate
        board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].removePiece(); //delete piece that was moved
        if (getTurn() == Piece.Type.MUSKETEER) {
            this.turn = Piece.Type.GUARD;
        } else {
            this.turn = Piece.Type.MUSKETEER;
        }
    }


    /**
     * Undo the move given.
     *
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) { // TODO
        Piece musk = new Musketeer();
        Piece guard = new Guard();
        board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].setPiece(board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].getPiece());
        if (getTurn() == Piece.Type.MUSKETEER) {
            this.turn = Piece.Type.GUARD;
            board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].removePiece();


        } else {
            this.turn = Piece.Type.MUSKETEER;
            board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].setPiece(guard);

        }

    }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     *
     * @param move a move
     * @return True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) { // TODO
        for (int i = 0; i < getPossibleMoves().size(); i++) {
            if (move.toCell.getCoordinate().col == getPossibleMoves().get(i).toCell.getCoordinate().col && move.toCell.getCoordinate().row == getPossibleMoves().get(i).toCell.getCoordinate().row
                    && move.fromCell.getCoordinate().col == getPossibleMoves().get(i).fromCell.getCoordinate().col && move.fromCell.getCoordinate().row == getPossibleMoves().get(i).fromCell.getCoordinate().row) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given row and column are valid. Things to check:
     * (1) row
     * (2) column
     *
     * @param row a row
     * @param col a column
     * @return True, if the given row and column are valid, false otherwise
     */
    private boolean isOutOfBoundaries(int row, int col) { // TODO custom
        if (row < 0 || row > 4 || col < 0 || col > 4) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the neighbour from the above of the given cell is out of boundaries
     *
     * @param cell a cell
     * @return coordinate if it is valid, null otherwise
     */
    private Coordinate checkUpHelper(Cell cell) { // TODO custom
        if (isOutOfBoundaries(cell.getCoordinate().row, cell.getCoordinate().col - 1)) {
            return null;
        }
        int row = cell.getCoordinate().row;
        int column = cell.getCoordinate().col - 1;
        return new Coordinate(row, column);

    }

    /**
     * Checks if the neighbour from the above of the given cell is out of boundaries
     * and if it is possible to move onto it with the current piece type
     *
     * @param cell a cell
     * @return true if it is valid and is possible to move onto it, false otherwise
     */
    private boolean checkCellUp(Cell cell) { // TODO custom
        if (checkUpHelper(cell) == null) {
            return false;
        }
        Coordinate coordinate = checkUpHelper(cell);
        if (getTurn() == Piece.Type.MUSKETEER) {
            Musketeer musketeer = new Musketeer();
            return musketeer.canMoveOnto(getCell(coordinate));
        } else {
            Guard guard = new Guard();
            return guard.canMoveOnto(getCell(coordinate));
        }
    }

    /**
     * Checks if the neighbour from the below of the given cell is out of boundaries
     *
     * @param cell a cell
     * @return coordinate if it is valid, null otherwise
     */
    private Coordinate checkDownHelper(Cell cell) { // TODO custom
        if (isOutOfBoundaries(cell.getCoordinate().row, cell.getCoordinate().col + 1)) {
            return null;
        }
        int row = cell.getCoordinate().row;
        int column = cell.getCoordinate().col + 1;
        Coordinate coordinate = new Coordinate(row, column);
        return coordinate;

    }

    /**
     * Checks if the neighbour from the below of the given cell is out of boundaries
     * and if it is possible to move onto it with the current piece type
     *
     * @param cell a cell
     * @return true if it is valid and is possible to move onto it, false otherwise
     */
    private boolean checkCellDown(Cell cell) { // TODO custom
        if (checkDownHelper(cell) == null) {
            return false;
        }
        Coordinate coordinate = checkDownHelper(cell);
        if (getTurn() == Piece.Type.MUSKETEER) {
            Musketeer musketeer = new Musketeer();
            return musketeer.canMoveOnto(getCell(coordinate));
        } else {
            Guard guard = new Guard();
            return guard.canMoveOnto(getCell(coordinate));
        }
    }

    /**
     * Checks if the neighbour from the left of the given cell is out of boundaries
     *
     * @param cell a cell
     * @return coordinate if it is valid, null otherwise
     */
    private Coordinate checkLeftHelper(Cell cell) { // TODO custom
        if (isOutOfBoundaries(cell.getCoordinate().row - 1, cell.getCoordinate().col)) {
            return null;
        }
        int row = cell.getCoordinate().row - 1;
        int column = cell.getCoordinate().col;

        return new Coordinate(row, column);

    }

    /**
     * Checks if the neighbour from the left of the given cell is out of boundaries
     * and if it is possible to move onto it with the current piece type
     *
     * @param cell a cell
     * @return true if it is valid and is possible to move onto it, false otherwise
     */
    private boolean checkCellLeft(Cell cell) { // TODO custom
        //true if possible move left
        if (checkLeftHelper(cell) == null) {
            return false;
        }
        Coordinate coordinate = checkLeftHelper(cell);
        if (getTurn() == Piece.Type.MUSKETEER) {
            Musketeer musketeer = new Musketeer();
            return musketeer.canMoveOnto(getCell(coordinate));
        } else {
            Guard guard = new Guard();
            //Cell checkCell = new Cell(coordinate);
            return guard.canMoveOnto(getCell(coordinate));
        }
    }

    /**
     * Checks if the neighbour from the right of the given cell is out of boundaries
     *
     * @param cell a cell
     * @return coordinate if it is valid, null otherwise
     */
    private Coordinate checkRightHelper(Cell cell) { // TODO custom
        //get coordinates of possible move right
        if (isOutOfBoundaries(cell.getCoordinate().row + 1, cell.getCoordinate().col)) {
            return null;
        }
        int row = cell.getCoordinate().row + 1;
        int column = cell.getCoordinate().col;

        return new Coordinate(row, column);

    }

    /**
     * Checks if the neighbour from the right of the given cell is out of boundaries
     * and if it is possible to move onto it with the current piece type
     *
     * @param cell a cell
     * @return true if it is valid and is possible to move onto it, false otherwise
     */
    private boolean checkCellRight(Cell cell) { // TODO custom
        if (checkRightHelper(cell) == null) {
            return false;
        }
        Coordinate coordinate = checkRightHelper(cell);
        if (getTurn() == Piece.Type.MUSKETEER) {
            Musketeer musketeer = new Musketeer();
            return musketeer.canMoveOnto(getCell(coordinate));

        } else {
            Guard guard = new Guard();
            return guard.canMoveOnto(getCell(coordinate));

        }
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     *
     * @return Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() { // TODO
        ArrayList<Cell> possibleCells = new ArrayList<Cell>();
        if (getTurn() == Piece.Type.MUSKETEER) {
            for (int i = 0; i < getMusketeerCells().size(); i++) {
                if (checkCellRight(getMusketeerCells().get(i)) || checkCellLeft(getMusketeerCells().get(i)) || checkCellUp(getMusketeerCells().get(i)) || checkCellDown(getMusketeerCells().get(i))) {
                    possibleCells.add(getMusketeerCells().get(i));
                }
            }
        } else {
            for (int i = 0; i < getGuardCells().size(); i++) {
                if (checkCellRight(getGuardCells().get(i)) || checkCellLeft(getGuardCells().get(i)) || checkCellUp(getGuardCells().get(i)) || checkCellDown(getGuardCells().get(i))) {
                    possibleCells.add(getGuardCells().get(i));
                }
            }
        }
        return possibleCells;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     *
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) { // TODO
        List<Cell> possibleDestFromCell = new ArrayList<Cell>();

        // if (fromCell.getPiece().getType().equals(Piece.Type.MUSKETEER)) {//because this wont work
        if (board[fromCell.getCoordinate().row][fromCell.getCoordinate().col].hasPiece()) {
            // if (board[fromCell.getCoordinate().row][fromCell.getCoordinate().col].getPiece().getType().equals(Piece.Type.MUSKETEER)) {
            if (checkCellRight(fromCell)) {
                possibleDestFromCell.add(getCell(checkRightHelper(fromCell)));
            }
            if (checkCellLeft(fromCell)) {
                possibleDestFromCell.add(getCell(checkLeftHelper(fromCell)));
            }
            if (checkCellUp(fromCell)) {
                possibleDestFromCell.add(getCell(checkUpHelper(fromCell)));
            }
            if (checkCellDown(fromCell)) {
                possibleDestFromCell.add(getCell(checkDownHelper(fromCell)));
            }
        }
        return possibleDestFromCell;
    }

    /**
     * Get all the possible moves that can be made this turn.
     *
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() { // TODO
        List<Move> list = new ArrayList<>();

        if (getTurn() == Piece.Type.MUSKETEER) {
            for (int i = 0; i < getMusketeerCells().size(); i++) {
                for (int j = 0; j < getPossibleDestinations(getMusketeerCells().get(i)).size(); j++) {
                    Move move = new Move(getMusketeerCells().get(i), getPossibleDestinations(getMusketeerCells().get(i)).get(j));
                    list.add(move);
                }
            }
        } else {
            for (int i = 0; i < getGuardCells().size(); i++) {
                for (int j = 0; j < getPossibleDestinations(getGuardCells().get(i)).size(); j++) {
                    Move move = new Move(getGuardCells().get(i), getPossibleDestinations(getGuardCells().get(i)).get(j));
                    list.add(move);
                }
            }
        }
        return list;
    }

    /**
     * Checks the Guard is a winner of the game
     *
     * @return true if the Guard is a winner of the game, false otherwise
     */
    private boolean isGuardWinner() {
        int col, row, i;
        int countCol = 0, countRow = 0;
        col = getMusketeerCells().get(0).getCoordinate().col;
        row = getMusketeerCells().get(0).getCoordinate().row;
        for (i = 1; i < getMusketeerCells().size(); i++) {//check col
            if (getMusketeerCells().get(i).getCoordinate().col == col) {
                countCol = countCol + 1;
            }
        }
        for (i = 1; i < getMusketeerCells().size(); i++) {//check rows
            if (getMusketeerCells().get(i).getCoordinate().row == row) {
                countRow = countRow + 1;
            }
        }
        if (countCol == 2 || countRow == 2) {
            return true;
        }
        return false;
    }

    /**
     * Checks the Musketeer is a winner of the game
     *
     * @return true if the Musketeer is a winner of the game, false otherwise
     */
    private boolean isMusketWinner() {

        return getPossibleMoves().isEmpty() && !isGuardWinner();
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     *
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() { // TODO
        if (isMusketWinner()) {
            this.winner = Piece.Type.MUSKETEER;
            return true;
        }
        if (isGuardWinner()) {
            this.winner = Piece.Type.GUARD;
            return true;
        }
        return false;
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row : board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell : row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     *
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece : pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
}
