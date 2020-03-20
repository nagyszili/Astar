package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {

    private final static int[][] goal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    //    final static int[][] goal = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private static int visitedNodeCounter = 0;
    private static int costCounter = 0;
    private static boolean readFile = false; // -input "filename.txt"
    private static boolean writeToStdOutSeq = false; // -solseq
    private static boolean writeToStdOutCost = false; // -pcost
    private static boolean writeToStdOutVisited = false; // -nvisited
    private static int heuristicType = 1; // -h 1 / -h 2

    public static void main(String[] args) {

        int[][] start = new int[3][3];
//        int[][] goal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        String file = "";


        if (args.length != 0) {
            for (int i = 0; i < args.length; ++i) {
                switch (args[i]) {
                    case "-input": {
                        file = args[++i];
                        readFile = true;
                        break;
                    }
                    case "-solseq": {
                        writeToStdOutSeq = true;
                        break;
                    }
                    case "-pcost": {
                        writeToStdOutCost = true;
                        break;
                    }

                    case "-nvisited": {
                        writeToStdOutVisited = true;
                        break;
                    }

                    case "-h": {
                        heuristicType = Integer.parseInt(args[++i]);
                        break;
                    }
                }
            }
        }


        if (readFile) {
            try {
                File myObj = new File(file);
                Scanner reader = new Scanner(myObj);
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        start[i][j] = reader.nextInt();
                    }
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Pleas enter the puzzle!");
            Scanner reader = new Scanner(System.in);
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    start[i][j] = reader.nextInt();
                }
            }
        }

        aStar(start, heuristicType, openList, closedList);

    }


    public static Node aStar(int[][] startState, int heuristicType, List<Node> openList, List<Node> closedList) {

        Node startNode = new Node(startState);
        System.out.println("Start Node");
        startNode.printNode();
        System.out.println("The Goal I want to reach");
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(goal[i][j] + " ");
            }
            System.out.println();
        }


        startNode.setG(0);
        startNode.setH(getHeuristic(startState, heuristicType));
        startNode.setF(startNode.getG() + startNode.getH());
        startNode.setParent(null);
        openList.add(startNode);

        Node currentNode;

        while (!openList.isEmpty()) {

            currentNode = getLowestFNode(openList);
            closedList.add(currentNode);
            openList.remove(currentNode);

            ++visitedNodeCounter;

            if (currentNode.isGoal()) {
                System.out.println("The Goal is Reached");
                if (writeToStdOutVisited) {
                    System.out.println("Visited Nodes: " + visitedNodeCounter);
                }
                if (writeToStdOutCost) {
                    System.out.println("Cost: " + currentNode.getG());
                }


                return currentNode;
            }

            if (writeToStdOutSeq) {
                currentNode.printNode();
            }


            for (int i = 0; i < 4; ++i) {
                Node child = slide(currentNode, i);
                if (child == null) {
                    continue;
                }
                child.setH(getHeuristic(child.getState(), heuristicType));
                child.setF(child.getG() + child.getH());

                Node X = find(openList, child);
                if (X != null) {
                    if (child.getF() <= X.getF()) {
                        continue;
                    } else {
                        openList.remove(X);
                    }
                }
                Node Y = find(closedList, child);
                if (Y != null) {
                    if (child.getF() <= Y.getF()) {
                        continue;
                    } else {
                        closedList.remove(Y);
                    }
                }
                openList.add(child);

            }

        }

        return null;


    }

    private static Node find(List<Node> list, Node child) {

        for (Node node : list) {
            if (child.compareState(node)) {
                return child;
            }
        }

        return null;
    }

    private static Node slide(Node parent, int toDirection) {

        Node node = new Node();
        int[][] state = new int[parent.getState().length][];
        for (int i = 0; i < parent.getState().length; i++) {
            state[i] = parent.getState()[i].clone();
        }

        int i = parent.getPositionOfZero()[0], j = parent.getPositionOfZero()[1];


        switch (toDirection) {
            case 0: {  //move up
                if (i >= 1) {
                    int up = state[i - 1][j];
                    state[i][j] = up;
                    state[i - 1][j] = 0;
                } else {
                    return null;
                }

                break;
            }
            case 1: {  //move down
                if (i < 2) {
                    int down = state[i + 1][j];
                    state[i][j] = down;
                    state[i + 1][j] = 0;
                } else {
                    return null;
                }

                break;
            }
            case 2: {  //move left
                if (j >= 1) {
                    int left = state[i][j - 1];
                    state[i][j] = left;
                    state[i][j - 1] = 0;
                } else {
                    return null;
                }

                break;
            }
            case 3: {  //move right
                if (j < 2) {
                    int right = state[i][j + 1];
                    state[i][j] = right;
                    state[i][j + 1] = 0;
                } else {
                    return null;
                }

                break;
            }
        }

        node.setParent(parent);
        node.setState(state);
        node.setG(parent.getG() + 1);

        return node;
    }

    public static Node getLowestFNode(List<Node> open) {
        Node minF = null;
        if (!open.isEmpty()) {
            minF = open.get(0);
            for (Node node : open) {
                if (node.getF() < minF.getF()) {
                    minF = node;
                } else if (node.getF() == minF.getF() && node.getH() < minF.getH()) {
                    minF = node;
                }
            }
        }
        return minF;
    }

    public static int getHeuristic(int[][] state, int heuristic) {
        int h = 0;
        if (heuristic == 1) {


            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (state[i][j] != goal[i][j] && state[i][j] != 0) {
                        h++;
                    }
                }
            }

            return h;
        } else if (heuristic == 2) {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (state[i][j] != 0) {
                        h += manhattanDistance(state, i, j);
                    }
                }
            }
            return h;
        }

        return 0;
    }

    public static int manhattanDistance(int[][] state, int x, int y) {

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (goal[i][j] == state[x][y]) {
                    return abs(x - i) + abs(y - j);
                }
            }
        }
        return 0;
    }


}
