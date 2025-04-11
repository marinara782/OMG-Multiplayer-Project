package org.example.networking;


/**
 * The bugReport class represents a report submitted by a user describing a bug.
 * Each report includes a type (e.g., UI bug, logic issue) and an optional comment
 * describing the issue in detail.
 */
public class bugReport {
    private String type;       // e.g., UI bug, game logic issue, etc
    private String comment;    // user's description

    //constructor
    public bugReport(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    //GETTER METHODS
    //get type method
    public String getType() {
        return type;
    }

    //get comment method
    public String getComment() {
        return comment;
    }

    //SETTER METHODS
    //set type method
    public void setType(String type) {
        this.type = type;
    }

    //set comment method
    public void setComment(String comment) {
        this.comment = comment;
    }
}
