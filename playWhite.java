import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class playWhite {

    public static void main(String[] args){
        int depth = Integer.valueOf(args[0]);
//        int depth = 6;
        MorrisGameBoard b = new MorrisGameBoard(depth);
        Tournament t = new Tournament();
        try {
            String fileName = "input_and_output_board.txt";
            String fileNameBackup = "all_boards_white.txt";
            String[] rFile = read(fileName);
            String move = rFile[0];
//            String move = "WWBWBWxxxWBxBBxxxxxBxWWW";
//            System.out.println(move);

            write(fileNameBackup, move.toCharArray(), "\n", true);

            if (move.matches("white win") || move.matches("black win")){
                System.out.println(move);
                System.exit(0);
            }
            long t1 = System.currentTimeMillis();

            int count = Integer.valueOf(read(fileNameBackup)[1]) - 1;
//            int count = 17;

            BoardNode root = new BoardNode(move.toCharArray());
            BoardNode result = t.MaxMinIM(count-1, b, root, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);

            long t2 = System.currentTimeMillis();

            if (String.valueOf(result.position).matches(move)){
                System.out.println("white no move");
                System.out.println("black win");
                System.exit(0);
            }

            write(fileName, result.position, "", false);

            System.out.println(String.valueOf(result.position) + " " + count + " white " +
                    "\t" + result.value + "\t" + "\t" +  ((double)(t2 - t1)/(double)1000));
//            System.out.println(t.getNumEvaluated());
//            System.out.println(root);

            if (result.child.size() == 0 && result.value >= 10000){
                System.out.println("black no move");
                System.out.println("white win");
                System.exit(0);
            }

            if (count > 18){
                int numBlack = b.countNums(result.position)[1];
                if (numBlack < 3){
                    write(fileNameBackup, "white win".toCharArray(), "\n", true);
                    System.out.println("white win");
                    System.exit(0);

                }
            }
            write(fileNameBackup, result.position, "\n", true);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] read(String fileName){
        String[] result = new String[2];
        try{
            Scanner sc = new Scanner(new File(fileName));
            int count = 0;
            String move = "";
            while (sc.hasNextLine()){
                move = sc.nextLine();
                count++;
            }

            result[0] = move;
            result[1] = String.valueOf(count);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return result;

    }


    private static void write (String newFileName, char[] move, String newLine, boolean flag){
        FileWriter fw;
        try {
            fw = new FileWriter(newFileName, flag);

            fw.write(newLine + String.valueOf(move));
            fw.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
