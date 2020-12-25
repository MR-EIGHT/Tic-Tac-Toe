package ac.ir.urmia.Eight.DS.assignment_7.project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree<T> {

    public ArrayList<TreeNode<T>> nodes;
    private TreeNode<T> root;


    public Tree(T rootValue) {
        this.root = new TreeNode<>(rootValue);

    }

    public TreeNode<T> getRoot() {
        return root;
    }

    static class TreeNode<T> {
        private T value;
        private TreeNode<T> parent = null;
        private List<TreeNode<T>> children = new ArrayList<>();


        public TreeNode(T value) {
            this.value = value;

        }

        public TreeNode(T value, TreeNode<T> parent) {
            this.value = value;
            this.parent = parent;

        }


        @Override
        public String toString() {
            return this.value.toString();

        }

        public TreeNode<T> getParent() {
            return parent;
        }

        public List<TreeNode<T>> getChildren() {
            return children;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void addChild(TreeNode<T> child) {
            getChildren().add(child);
            child.parent = this;

        }

        public void addChildren(ArrayList<TreeNode<T>> children) {
            for (TreeNode<T> child : children) {
                getChildren().add(child);
                child.parent = this;

            }

        }

    }


}
