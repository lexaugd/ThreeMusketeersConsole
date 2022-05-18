package lab2;

import java.util.Random;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     *
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() { // TODO
        Move randomPossibleMove = null;
        Random rand = new Random();

        do {
            for (int i = 0; i < board.getPossibleMoves().size(); i++) {
                int randomIndex = rand.nextInt(board.getPossibleMoves().size());
                randomPossibleMove = board.getPossibleMoves().get(randomIndex);
            }
        }
        while (!board.isValidMove(randomPossibleMove));
        System.out.printf("Moving piece [%s] to [%s].", randomPossibleMove.fromCell.getCoordinate(), randomPossibleMove.toCell.getCoordinate());
        return randomPossibleMove;
    }
}
