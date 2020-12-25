package ac.ir.urmia.Eight.DS.assignment_7.project2;

import java.util.*;

public class TicTocToeGame {

    public static Tree<GameConf> gameTree = new Tree<>(new GameConf());

    public static boolean isMovesLeft(GameConf conf) {
        for (int i = 0; i < 9; i++) {
            if (conf.get(i) == ' ') return true;
        }
        return false;
    }

    public static int miniMax(Tree.TreeNode<GameConf> node, int depth, boolean maximizingPlayer) {

        int score = evaluate(node.getValue());
        int value;

        if (score == 10)
            return score;


        if (score == -10)
            return score;


        if (!isMovesLeft(node.getValue()))
            return 0;


        if (depth == 0 || node.getChildren().size() == 0) {
            return -1;
        }

        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;
            for (Tree.TreeNode<GameConf> child : node.getChildren())
                value = Math.max(value, miniMax(child, depth - 1, false));
            return value;
        } else {
            value = Integer.MAX_VALUE;
            for (Tree.TreeNode<GameConf> child : node.getChildren())
                value = Math.min(value, miniMax(child, depth - 1, true));
        }
        return value;

    }


    public static void scoreTree(Tree.TreeNode<GameConf> node, int depth) {

        if (depth == 9) ;
        else {
            for (Tree.TreeNode<GameConf> child : node.getChildren()) {
                child.getValue().score = miniMax(child, 8, depth % 2 == 0);
                scoreTree(child, depth + 1);
            }
        }


    }


    public static void main(String[] args) {
        populateTree(gameTree.getRoot(), 0);
        scoreTree(gameTree.getRoot(), 0);

        printGuide();

        Scanner in = new Scanner(System.in);
        Random randFirstMove = new Random();
        int start = randFirstMove.nextInt(8);
        print(gameTree.getRoot().getChildren().get(start).getValue());
        Tree.TreeNode<GameConf> node = gameTree.getRoot().getChildren().get(start);


        for (int i = 0; i < 5; i++) {

            if (winCheck(node)) break;


            if (i == 4 && evaluate(node.getValue()) == 0) {
                System.out.println("No one Won!");
                break;


            }
            System.out.print("Your Move:");
            int x = in.nextInt();
            while (node.getValue().get(x) != ' ') {
                System.out.print("Your Move:");
                x = in.nextInt();
            }

            for (Tree.TreeNode<GameConf> child : node.getChildren()) {
                if (child.getValue().get(x) == 'O') {
                    node = child;
                    break;
                }
            }
            print(node.getValue());

            winCheck(node);


            int score = node.getValue().score;
            for (Tree.TreeNode<GameConf> child : node.getChildren()) {
                if (child.getValue().score <= score) {
                    node = child;
                    score = child.getValue().score;
                }
            }
            print(node.getValue());

        }


    }


    private static void printGuide() {
        System.out.println(


                " Tic-Tac Toe Board \n" +
                        "  \n" +
                        "  1 ¦ 2 ¦ 3\n" +
                        "  --+---+--\n" +
                        "  4 ¦ 5 ¦ 6\n" +
                        "  --+---+--\n" +
                        "  7 ¦ 8 ¦ 9\n" +
                        "    \n\n"


        );
    }


    private static boolean winCheck(Tree.TreeNode<GameConf> node) {
        if (evaluate(node.getValue()) == -10) {
            System.out.println("X Won The Game!");
            return true;
        }
        if (evaluate(node.getValue()) == 10) {
            System.out.println("O Won The Game!");
            return true;
        }
        return false;
    }


    public static int evaluate(GameConf conf) {

        //checking rows

        if (conf.get(1) == conf.get(2) && conf.get(2) == conf.get(3))
            if (conf.get(2) == 'O') return +10;
            else if (conf.get(2) == 'X') return -10;

        if (conf.get(4) == conf.get(5) && conf.get(5) == conf.get(6))
            if (conf.get(5) == 'O') return +10;
            else if (conf.get(5) == 'X') return -10;


        if (conf.get(7) == conf.get(8) && conf.get(8) == conf.get(9))
            if (conf.get(8) == 'O') return +10;
            else if (conf.get(8) == 'X') return -10;

        //checking columns


        if (conf.get(1) == conf.get(4) && conf.get(4) == conf.get(7))
            if (conf.get(1) == 'O') return +10;
            else if (conf.get(1) == 'X') return -10;

        if (conf.get(2) == conf.get(5) && conf.get(5) == conf.get(8))
            if (conf.get(2) == 'O') return +10;
            else if (conf.get(2) == 'X') return -10;

        if (conf.get(3) == conf.get(6) && conf.get(6) == conf.get(9))
            if (conf.get(3) == 'O') return +10;
            else if (conf.get(3) == 'X') return -10;


        //checking diagonals

        if (conf.get(1) == conf.get(5) && conf.get(5) == conf.get(9))
            if (conf.get(1) == 'O') return +10;
            else if (conf.get(1) == 'X') return -10;


        if (conf.get(3) == conf.get(5) && conf.get(5) == conf.get(7))
            if (conf.get(3) == 'O') return +10;
            else if (conf.get(3) == 'X') return -10;


        // none have won
        return 0;

    }

    public static void populateTree(Tree.TreeNode<GameConf> node, int depth) {


        ArrayList<Tree.TreeNode<GameConf>> list = possible(node.getValue(), depth);
        if (depth == 9) ;
        else {
            node.addChildren(Objects.requireNonNull(list));
            for (Tree.TreeNode<GameConf> child : node.getChildren()) {

                populateTree(child, depth + 1);
            }
        }


    }


    public static ArrayList<Tree.TreeNode<GameConf>> possible(GameConf conf, int turn) {
        char val;
        if (turn % 2 == 0) val = 'X';
        else val = 'O';

        ArrayList<Tree.TreeNode<GameConf>> possibles = new ArrayList<>();

        for (int i = 1; i < 10; i++) {

            if (conf.get(i) == ' ') {
                GameConf co = new GameConf();
                for (int j = 1; j < 10; j++)
                    co.set(j, conf.get(j));

                co.set(i, val);
                //co.score = miniMax(new Tree.TreeNode<GameConf>(co), turn, turn % 2 == 0);
                Tree.TreeNode<GameConf> newNode = new Tree.TreeNode<>(co);
                possibles.add(newNode);


            }
        }

        return possibles;

    }

    public static void print(GameConf conf) {


        System.out.printf(" %c ¦ %c ¦ %c \n" +
                        "---+---+--- \n" +
                        " %c ¦ %c ¦ %c \n" +
                        "---+---+--- \n" +
                        " %c ¦ %c ¦ %c \n\n", conf.get(1), conf.get(2), conf.get(3),
                conf.get(4), conf.get(5), conf.get(6), conf.get(7), conf.get(8), conf.get(9));
    }

    private static class GameConf {

        public char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        int score;


        public GameConf() {

        }


        public void set(int i, char val) {
            switch (i) {
                case 1:
                    this.board[0][0] = val;
                    break;
                case 2:
                    this.board[0][1] = val;
                    break;

                case 3:
                    this.board[0][2] = val;
                    break;
                case 4:
                    this.board[1][0] = val;
                    break;
                case 5:
                    this.board[1][1] = val;
                    break;
                case 6:
                    this.board[1][2] = val;
                    break;
                case 7:
                    this.board[2][0] = val;
                    break;
                case 8:
                    this.board[2][1] = val;
                    break;
                case 9:
                    this.board[2][2] = val;
                    break;
            }
        }


        public char get(int i) {
            switch (i) {
                case 1:
                    return this.board[0][0];

                case 2:
                    return this.board[0][1];


                case 3:
                    return this.board[0][2];

                case 4:
                    return this.board[1][0];

                case 5:
                    return this.board[1][1];

                case 6:
                    return this.board[1][2];

                case 7:
                    return this.board[2][0];

                case 8:
                    return this.board[2][1];

                case 9:
                    return this.board[2][2];
            }

            return ' ';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameConf conf = (GameConf) o;
            return Arrays.equals(board, conf.board);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(board);
        }

        @Override
        public String toString() {
            return Arrays.deepToString(this.board);

        }
    }


}

