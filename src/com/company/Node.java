package com.company;

public class Node {
    private int[][] puzzle;
    private int g;
    private int h;
    private int f;
    private Node parent;
    private int[][] start;
    private int[][] goal;

    public Node(int[][] puzzle) {
        this.puzzle = puzzle;
    }

    public Node(int[][] puzzle, Node parent) {
        this.puzzle = puzzle;
        this.parent = parent;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
