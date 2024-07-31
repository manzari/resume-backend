package me.manzari.resume.service;

public enum Action {
    Login("login"),
    Resume("resume"),
    File("file");

    private final String name;

    Action(String actionName) {
        this.name = actionName;
    }

    public String getName() {
        return name;
    }
}
