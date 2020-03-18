package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        int[][] start = new int[3][3];
        int[][] goal = {{0,1,2},{3,4,5},{6,7,8}};
        List<Node> Open = new ArrayList<>();
        List<Node> Closed = new ArrayList<>();
        String file = "";
        boolean readFile = false; // -input "filename.txt"
        boolean writeToStdOutSeq = false; // -solseq
        boolean writeToStdOutCost = false; // -pcost
        boolean writeToStdOutVisited = false; // -nvisited
        int heuristic = 1; // -h 1 / -h 2

        if (args.length != 0) {
            for (int i = 0; i < args.length; ++i) {
                switch (args[i]) {
                    case "-input": {
                        file = args[++i];
                        readFile = true;
                        System.out.println(file);
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
                        heuristic = Integer.parseInt(args[++i]);
                        break;
                    }
                }
            }
        }


        if (readFile) {
            try {
                File myObj = new File(file);
                Scanner reader = new Scanner(myObj);
                while (reader.hasNextInt()) {
//                    String data = reader.nextLine();
                    int num = reader.nextInt();
                    System.out.println(num);
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {

            Scanner in = new Scanner(System.in);

            while (in.hasNextInt()) {
                System.out.println(in.nextInt());
            }
        }

    }


    public void aStar(int[][] startState) {
        Node startNode = new Node(startState);
        startNode.setG(0);


    }

    public Node getLowestFNode(List<Node> Open) {
        Node minF = null;
        if (!Open.isEmpty()) {
            minF = Open.get(0);
            for (Node node : Open) {
                if (node.getF() < minF.getF()) {
                    minF = node;
                } else if (node.getF() == minF.getF() && node.getH() < minF.getH()) {
                    minF = node;
                }
            }
        }
        return minF;
    }

    public int heuristicOne(int[][] goal, int[][] state) {

        int h = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] != goal[i][j] && state[i][j] != 0) {
                    h++;
                }
            }
        }

        return h;
    }


}
