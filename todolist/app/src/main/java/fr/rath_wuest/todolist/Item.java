package fr.rath_wuest.todolist;


import java.util.Date;

/**
 * Created by phil on 24/02/15.
 */
public class Item {
    private long id;
    private String label;
    private Date date;

    public Item(String s) {
        label = s;
        id = -1;
        date = null;
    }

    public Item(String s,Date d) {
        label = s;
        id = -1;
        date=d;
    }

    public Item(long id, String label) {
        this.id = id;
        this.label = label;
        date = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getDate() { return this.date; }

    public void setDate(Date d) {this.date= d;}

    public void save(AddItemActivity addItemActivity) {
        if (id == -1)
            saveNewItem(addItemActivity);
    }

    private void saveNewItem(AddItemActivity addItemActivity) {
        TodoBase.addItem(addItemActivity, label,date);
    }



}
