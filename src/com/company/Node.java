package com.company;

public class Node {
    private int[][] state;
    private int g;
    private int h;
    private int f;
    private Node parent;
    private int[][] goal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
//    private int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    public Node() {
    }

    public Node(int[][] state) {
        this.state = state;
    }

    public Node(int[][] state, Node parent) {
        this.state = state;
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[][] getState() {
        return state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public boolean isGoal() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.state[i][j] != this.goal[i][j] && this.state[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] getPositionOfZero() {
        int i = 0, j = 0;
        int[] position = new int[2];
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 3; ++j) {
                if (this.state[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public boolean compareState(Node node) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.state[i][j] != node.getState()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printNode() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(this.getState()[i][j] + " ");
            }
            System.out.println();
        }
    }


}
