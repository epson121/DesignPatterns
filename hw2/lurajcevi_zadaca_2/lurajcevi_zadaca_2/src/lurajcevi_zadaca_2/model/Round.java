
package lurajcevi_zadaca_2.model;

import java.util.List;

/**
 *
 * @author luka
 */
public class Round {
    
    private int id;
    private Table table = null;
    private List<Result> results;

    public Round(int id, Table table, List<Result> results) {
        this.id = id;
        if (!table.tableChanged()) {
            this.table = null;
        } else {
            this.table = table;
        }
        this.results = results;
    }

    public void printResults() {
        for (Result r : results) {
            r.printResult();
        }
    }
    
    public void printTable() {
        if (table != null) {
            table.printTable();
        } else {
            System.out.println("Table was not changed.");
        }
    }
    
    public int getId() {
        return id;
    }

    public Table getTable() {
        return table;
    }

    public List<Result> getResults() {
        return results;
    }
    
    public boolean tableChanged() {
        return table.tableChanged();
    }

    public RoundArchiveItem getArchivedRound() {
        if (table != null){
            return new RoundArchiveItem(id, table.createArchive() , results);
        }
        return new RoundArchiveItem(id, null, results);
    }
   
}
