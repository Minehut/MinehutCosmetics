package com.minehut.cosmetics.util.structures;

public class Pair<T, Z> {

    private T left;
    private Z right;

    public Pair(T left, Z right) {
        this.left = left;
        this.right = right;
    }

    public T left() {
        return left;
    }

    public Z right() {
        return right;
    }


    public void left(T left) {
        this.left = left;
    }

    public void right(Z right) {
        this.right = right;
    }

    public static <T, Z> Pair<T, Z> of(T left, Z right) {
        return new Pair<>(left, right);
    }
}