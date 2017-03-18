package com.littlecodeshop.todos;

/**
 * Created by rribier on 17/03/2017.
 */
public class Todo {
    private String text;
    private Boolean checked;



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }
}
