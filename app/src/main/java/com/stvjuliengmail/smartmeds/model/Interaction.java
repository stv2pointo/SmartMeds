package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 2/23/2018.
 */

public class Interaction {
    private String name;
    private String description;
    private String severity;

    public Interaction(String name, String description, String severity) {
        this.name = name;
        this.description = description;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
