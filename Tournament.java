public class Tournament {

    private int numEvaluated = 0;

    public int getNumEvaluated() {
        return numEvaluated;
    }

    public BoardNode MaxMinIM(int count, MorrisGameBoard b, BoardNode board, int depth, int alpha, int beta) {
        if (count < 18) {
            if (depth == 0) {
                board.value = b.openingStaticImproved(board.position);
                numEvaluated++;
                return board;
            } else {
                board.child = b.genMoveOpening(board.position);
            }
        } else {
            int[] nums = b.countNums(board.position);
            if (depth == 0 || nums[0] < 3){
                board.value = b.staticMidEndImproved(board.position, depth);
                numEvaluated++;
                return board;
            } else {
                board.child = b.genMoveMidEnd(board.position);
            }
        }

        if (board.child.size() == 0){
            board.value = b.staticMidEndImproved(board.position, depth);
            numEvaluated++;
            return board;
        }

        int maxValue = Integer.MIN_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++) {
            BoardNode result = MinMaxIM(count + 1, b, board.child.get(i), depth - 1, alpha, beta);
            if (maxValue < result.value) {
//                retboard = result;
                maxValue = result.value;
                retboard = board.child.get(i);
                retboard.value = maxValue;
            }

            if (maxValue >= beta){
                return retboard;
            } else if (maxValue > alpha){
                alpha = maxValue;
            }
        }
        return retboard;
    }

    public BoardNode MinMaxIM(int count, MorrisGameBoard b, BoardNode board, int depth, int alpha, int beta) {
        if (count < 18) {
            if (depth == 0) {
                board.value = b.openingStaticImproved(board.position);
                numEvaluated++;
                return board;
            } else {
                board.child = b.genMoveOpeningBlack(board.position);
            }
        } else {
            int[] nums = b.countNums(board.position);
            if (depth == 0 || nums[1] < 3){
                board.value = b.staticMidEndImproved(board.position, depth);
                numEvaluated++;
                return board;
            } else {
                board.child = b.genMoveMidEndBlack(board.position);
            }
        }

        if (board.child.size() == 0){
            board.value = b.staticMidEndImproved(board.position, depth);
            numEvaluated++;
            return board;
        }

        int minValue = Integer.MAX_VALUE;
        BoardNode retboard = null;
        int len = board.child.size();
        for (int i = 0; i < len; i++) {
            BoardNode result = MaxMinIM(count+1, b, board.child.get(i), depth - 1, alpha, beta);
            if (minValue > result.value) {
//                retboard = result;
                minValue = result.value;
                retboard = board.child.get(i);
                retboard.value = minValue;
            }

            if (minValue <= alpha){
                return retboard;
            } else if (minValue > beta){
                beta = minValue;
            }
        }
        return retboard;
    }

}
