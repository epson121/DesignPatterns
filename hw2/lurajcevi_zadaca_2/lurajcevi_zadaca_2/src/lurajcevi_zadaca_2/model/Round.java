
package lurajcevi_zadaca_2.model;

import java.util.List;

/**
 *
 * @author luka
 */
public class Round {
    
    private int id;
    private Table table;
    private List<Result> results;

    public Round(int id, Table table, List<Result> results) {
        this.id = id;
        this.table = table;
        this.results = results;
    }
    
}
