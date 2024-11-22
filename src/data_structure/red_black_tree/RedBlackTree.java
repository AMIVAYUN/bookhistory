package data_structure.red_black_tree;

public class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;

    class Node {
        int data, color;
        Node left, right, parent;

        Node(int data) {
            this.data = data;
            this.color = RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root;

    public RedBlackTree() {
        root = null;
    }


    private void leftRotate(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rightRotate(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }


    public void insert(int data) {
        Node node = new Node(data);
        root = bstInsert(root, node);
        fixInsert(node);
    }


    private Node bstInsert(Node root, Node node) {
        if (root == null) {
            return node;
        }

        if (node.data < root.data) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else if (node.data > root.data) {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }

        return root;
    }


    private void fixInsert(Node node) {
        Node parent = null;
        Node grandParent = null;

        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;

                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        leftRotate(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    rightRotate(grandParent);
                    int temp = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = temp;
                    node = parent;
                }
            } else {
                Node uncle = grandParent.left;

                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        rightRotate(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    leftRotate(grandParent);
                    int temp = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = temp;
                    node = parent;
                }
            }
        }

        root.color = BLACK;
    }


    public Node getRoot() {
        return root;
    }


}