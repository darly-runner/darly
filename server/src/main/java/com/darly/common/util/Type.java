package com.darly.common.util;

public enum Type {
    Lock('L'),
    Free('F'),
    Ready('R'),
    UnReady('N'),
    Waiting('W'),
    Start('S'),
    End('E');

    private final Character label;

    Type(Character label) {
        this.label = label;
    }

    public Character getLabel() {
        return label;
    }
}
