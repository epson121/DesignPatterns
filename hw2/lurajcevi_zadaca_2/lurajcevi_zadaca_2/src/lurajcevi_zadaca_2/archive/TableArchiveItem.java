package lurajcevi_zadaca_2.archive;

/**
 *
 * @author luka
 */
public class TableArchiveItem {

    private int position, points, id;
    private String name;

    public TableArchiveItem(int position, int points, int id, String name) {
        this.position = position;
        this.points = points;
        this.id = id;
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
