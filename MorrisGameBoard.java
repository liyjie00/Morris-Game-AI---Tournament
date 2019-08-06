import java.util.ArrayList;

public class MorrisGameBoard {
//    public BoardNode board;
//
//    public MorrisGameBoard(BoardNode board) {
//        this.board = board;
//    }
    public int runDepth;
    MorrisGameBoard(int runDepth){
        this.runDepth = runDepth;
    }

    public ArrayList<BoardNode> genMoveOpening(char[] position) {
        return genAdd(position);
    }

    private ArrayList<BoardNode> genAdd(char[] position) {
        ArrayList<BoardNode> posList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'x') {
                char[] pos = position.clone();
                pos[i] = 'W';
                if (closeMill(i, pos)) {
                    posList = genRemove(pos, posList);
                } else {
                    posList.add(new BoardNode(pos));
                }
            }
        }
        return posList;
    }

    public char[] reverse(char[] position) {
        char[] revPos = new char[position.length];
        for (int i = 0; i < revPos.length; i++) {
            revPos[i] = position[i];
            if (revPos[i] == 'W') {
                revPos[i] = 'B';
            } else if (revPos[i] == 'B') {
                revPos[i] = 'W';
            }
        }
        return revPos;
    }

    public ArrayList<BoardNode> genMoveOpeningBlack(char[] position) {
        char[] pos = position;
        ArrayList<BoardNode> posList = genAdd(reverse(pos));
        for (BoardNode aPosList : posList) {
            aPosList.position = reverse(aPosList.position);
        }
        return posList;
    }

    private ArrayList<BoardNode> genRemove(char[] position, ArrayList<BoardNode> posList) {
        boolean closeMillFlag = true;
        int size = position.length;

        for (int i = 0; i < size; i++) {
            if (position[i] == 'B') {
                if (!closeMill(i, position)) {
                    char[] pos = position.clone();
                    pos[i] = 'x';
                    posList.add(new BoardNode(pos));
                    closeMillFlag = false;
                }
            }
        }

        if (closeMillFlag) {
            posList.add(new BoardNode(position));
        }

        return posList;
    }

    public int[] countNums(char[] position) {
        int[] counts = new int[2];
        char pos;
        for (char aPosition : position) {
            pos = aPosition;
            if (pos == 'W') {
                counts[0]++;
            }
            if (pos == 'B') {
                counts[1]++;
            }
        }
        return counts;
    }

    public int openingStatic(char[] position) {
        int[] count = countNums(position);
        return count[0] - count[1];
    }

    public ArrayList<BoardNode> genMoveMidEndBlack(char[] board) {
        char[] revPos = reverse(board);

        ArrayList<BoardNode> posList = genMoveMidEnd(revPos);

        for (BoardNode b : posList) {
            b.position = reverse(b.position);
        }

        return posList;
    }


    public ArrayList<BoardNode> genMoveMidEnd(char[] position) {
        int[] nums = countNums(position);

        if (nums[0] == 3) {
            return genHopping(position);
        } else {
            return genMove(position);
        }
    }


    private ArrayList<BoardNode> genHopping(char[] position) {
        ArrayList<BoardNode> posList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'W') {
                for (int j = 0; j < position.length; j++) {
                    if (position[j] == 'x') {
                        char[] pos = position.clone();
                        pos[j] = 'W';
                        pos[i] = 'x';

                        if (closeMill(j, pos)) {
                            posList = genRemove(pos, posList);
                        } else {
                            posList.add(new BoardNode(pos));
                        }
                    }
                }
            }
        }
        return posList;
    }


    private ArrayList<BoardNode> genMove(char[] position) {
        ArrayList<BoardNode> posList = new ArrayList<>();
        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'W') {
                int[] nList = neighbor(i);
                for (int j : nList) {
                    if (position[j] == 'x') {
                        char[] pos = position.clone();
                        pos[i] = 'x';
                        pos[j] = 'W';

                        if (closeMill(j, pos)) {
                            posList = genRemove(pos, posList);
                        } else {
                            posList.add(new BoardNode(pos));
                        }
                    }
                }
            }
        }
        return posList;
    }



    public int staticMinEnd(char[] position, int depth) {
//        System.out.println(String.valueOf(position));
        int[] nums = countNums(position);
        Boolean myNextTurn = (runDepth - depth) % 2 == 0;
        if (nums[1] <= 2) {
             return 10000;
        } else if (nums[0] <= 2) {
            return -10000;
        }

        ArrayList<BoardNode> list = new ArrayList<>();
        char[] revPos = reverse(position);
        list = genMoveMidEnd(revPos);


        int numBlackMoves = list.size();
//        System.out.println(numBlackMoves);
        if (numBlackMoves == 0) {
            return 10000;
        } else {
            return 1000 * (nums[0] - nums[1]) - numBlackMoves;
        }

    }

    public int openingStaticImproved(char[] position) {
        // hidden
        return 0;
    }


    public int staticMidEndImproved(char[] position, int depth) {
        // hidden
        return 0;
    }


    private int doubleMills(char[] position) {
        int num = 0;
        int len = position.length;
        for (int i = 0; i < len; i++) {
            if (position[i] == 'W') {
                int[] neList = neighbor(i);
                for (int n : neList) {
                    if (position[n] == 'x') {
                        char[] pos = position.clone();
                        pos[n] = 'W';
                        if (closeMill(n, pos)) {
                            num++;
                        }
                    }
                }
            }
        }
        return num;
    }


    private int countMills(char[] position) {
        char[] pos = position.clone();
        int len = pos.length;
        int numMills = 0;
        for (int i = 0; i < len; i++) {
            if (position[i] == 'W') {
                int[][] millPos = getMills(i);
                for (int[] mill : millPos) {
                    if (isMill(position, i, mill) && (pos[i] != '*' || pos[mill[0]] != '*' || pos[mill[1]] != '*')) {
                        numMills++;
                        pos[i] = '*';
                        pos[mill[0]] = '*';
                        pos[mill[1]] = '*';
                    }
                }
            }
        }
        return numMills;
    }

    private Boolean isMill(char[] position, int loc, int[] locList) {
        return position[loc] == position[locList[0]] && position[loc] == position[locList[1]];
    }

    private int count2PiecesConf(char[] position) {
        // hidden
        return 0;
    }

    private int count2PiecesW(char[] position) {
        // hidden
        return 0;
    }

    private int count2PiecesB(char[] position) {
        // hidden
        return 0;
    }


    public int[][] getMills(int loc) {
        switch (loc) {
            case 0:
                return new int[][]{{8,20}, {3,6}, {1,2}};
            case 1:
                return new int[][]{{0,2}};
            case 2:
                return new int[][]{{5,7}, {0,1}, {13,22}};
            case 3:
                return new int[][]{{0,6}, {9,17}, {4,5}};
            case 4:
                return new int[][]{{3,5}};
            case 5:
                return new int[][]{{2,7}, {3,4}, {12,19}};
            case 6:
                return new int[][]{{0,3}, {10,14}};
            case 7:
                return new int[][]{{11,16}, {2,5}};
            case 8:
                return new int[][]{{0,20}, {9,10}};
            case 9:
                return new int[][]{{8,10}, {3,17}};
            case 10:
                return new int[][]{{6,14}, {8,9}};
            case 11:
                return new int[][]{{7,16}, {12,13}};
            case 12:
                return new int[][]{{11,13}, {5,19}};
            case 13:
                return new int[][]{{11,12}, {2,22}};
            case 14:
                return new int[][]{{17,20}, {6,10}, {15,16}};
            case 15:
                return new int[][]{{14,16}, {18,21}};
            case 16:
                return new int[][]{{14,15}, {7,11}, {19,22}};
            case 17:
                return new int[][]{{14,20}, {18,19}, {3,9}};
            case 18:
                return new int[][]{{17,19}, {15,21}};
            case 19:
                return new int[][]{{16,22}, {17,18}, {5,12}};
            case 20:
                return new int[][]{{14,17}, {21,22}, {0,8}};
            case 21:
                return new int[][]{{15,18}, {20,22}};
            case 22:
                return new int[][]{{16,19}, {20,21}, {2,13}};
            default:
                return null;
        }
    }


    public int[] neighbor(int loc) {
        switch (loc) {
            case 0:
                return new int[]{1, 3, 8};
            case 1:
                return new int[]{0, 2, 4};
            case 2:
                return new int[]{1, 5, 13};
            case 3:
                return new int[]{0, 4, 6, 9};
            case 4:
                return new int[]{1, 3, 5};
            case 5:
                return new int[]{2, 4, 7, 12};
            case 6:
                return new int[]{3, 7, 10};
            case 7:
                return new int[]{5, 6, 11};
            case 8:
                return new int[]{0, 9, 20};
            case 9:
                return new int[]{3, 8, 10, 17};
            case 10:
                return new int[]{6, 9, 14};
            case 11:
                return new int[]{7, 12, 16};
            case 12:
                return new int[]{5, 11, 13, 19};
            case 13:
                return new int[]{2, 12, 22};
            case 14:
                return new int[]{10, 15, 17};
            case 15:
                return new int[]{14, 16, 18};
            case 16:
                return new int[]{11, 15, 19};
            case 17:
                return new int[]{9, 14, 18, 20};
            case 18:
                return new int[]{15, 17, 19, 21};
            case 19:
                return new int[]{12, 16, 18, 22};
            case 20:
                return new int[]{8, 17, 21};
            case 21:
                return new int[]{18, 20, 22};
            case 22:
                return new int[]{13, 19, 21};
            default:
                return null;
        }
    }

    private Boolean closeMill(int loc, char[] position) {
        int[][] mills = getMills(loc);
        char move = position[loc];
        boolean hasMill = false;
        for (int[] mill : mills) {
            hasMill = hasMill || (position[mill[0]] == move && position[mill[1]] == move);
        }
        return hasMill;
    }

}
