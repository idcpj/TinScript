package parser.utils;

import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 操作符的优先级
 */
public class PriorityTable {
    private List<List<String>> table = new ArrayList<>();
    public PriorityTable(){
        table.add(Arrays.asList("&","|","^"));
        table.add(Arrays.asList("==","!=","!=",">","<",">=","<="));
        table.add(Arrays.asList("+","-"));
        table.add(Arrays.asList("*","/"));
        table.add(Arrays.asList("<<",">>"));
    }

    public int size(){
        return table.size();
    }

    public List<String> get(int index){
        return table.get(index);
    }
}
