import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BoardNode {
    public int value;
    public char[] position;
    public ArrayList<BoardNode> child;

    public BoardNode(){
        this.child = new ArrayList<BoardNode>();
    }

    public BoardNode(char[] position){
        this.position = position;
        this.child = new ArrayList<BoardNode>();
    }

    @Override
    public String toString(){
        String s = "";
        Queue<BoardNode> queue = new LinkedList<>();
        queue.add(this);
        BoardNode pos;
        int size;
        while (!queue.isEmpty()){
            pos = queue.poll();
            size = pos.position.length;
            for (int i = 0; i < size; i++){
                s += pos.position[i];
            }
            s = s + " " + String.valueOf(pos.value) + "\n";
            queue.addAll(pos.child);
        }
        return s;
    }
}
