package org.example.networking;

public class bugReport {
    private String type;       // e.g., UI bug, game logic issue, etc
    private String comment;    // user's description

    public bugReport(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
