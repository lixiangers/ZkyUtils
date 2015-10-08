package com.zky.zkyutils.view;

public class CustomMenuItem {
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_LOGOUT = 2;

    private String title;
    private int id;
    private int iconId;
    private int iconIdSelected;
    private boolean selected = false;
    private int type = TYPE_NORMAL;


    public CustomMenuItem(String title, int id, int iconId, int iconIdSelected) {
        this.title = title;
        this.id = id;
        this.iconId = iconId;
        this.iconIdSelected = iconIdSelected;
    }

    public CustomMenuItem(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public CustomMenuItem(String title, int id, boolean isSelected) {
        this.title = title;
        this.id = id;
        this.selected = isSelected;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getIconIdSelected() {
        return iconIdSelected;
    }

    public void setIconIdSelected(int iconIdSelected) {
        this.iconIdSelected = iconIdSelected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
