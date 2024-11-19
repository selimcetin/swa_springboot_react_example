package com.hsesslingen.jodel.entities;

public enum VoteType {
    UP(1),
    DOWN(-1);

    public final int value;
    VoteType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VoteType getValue(int type) {
        return type > 0 ? VoteType.UP : VoteType.DOWN;
    }
}
