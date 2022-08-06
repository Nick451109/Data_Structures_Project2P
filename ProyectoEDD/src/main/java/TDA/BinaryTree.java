/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDA;

/**
 *
 * @author CAELOS JR 2018
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E> {

    private BinaryTreeNode<E> root;

    public BinaryTree(E rootContent) {
        this.root = new BinaryTreeNode<>(rootContent);
    }

    public E getRootContent() {
        return this.root.getContent();
    }

    public BinaryTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean isLeaf() {
        return this.root.getLeft() == null && this.root.getRight() == null;
    }

    public void setRootContent(E content) {
        this.root = new BinaryTreeNode<>(content);
    }

    private BinaryTreeNode<E> getRoot() {
        return root;
    }

    private void setRoot(BinaryTreeNode<E> root) {
        this.root = root;
    }

    public void setLeft(BinaryTree tree) {
        this.root.setLeft(tree);
    }

    public void setRight(BinaryTree tree) {
        this.root.setRight(tree);
    }

    public BinaryTree getLeft() {
        return this.root.getLeft();
    }

    public BinaryTree getRight() {
        return this.root.getRight();
    }

    public LinkedList<E> preOrderTraversalRecursive() {
        LinkedList<E> traversal = new LinkedList<>();
        if (!this.isEmpty()) {
            traversal.add(this.getRootContent());
        }
        if (this.getLeft() != null) {
            traversal.addAll(this.getLeft().preOrderTraversalRecursive());
        }
        if (this.getRight() != null) {
            traversal.addAll(this.getRight().preOrderTraversalRecursive());
        }
        return traversal;
    }

    public LinkedList<E> preOrderTraversalIterative() {
        LinkedList<E> traversal = new LinkedList<>();
        Stack<BinaryTree<E>> s = new Stack<>();
        s.push(this);
        while (!s.isEmpty()) {
            BinaryTree<E> tree = s.pop();
            if (!tree.isEmpty()) {
                traversal.add(tree.getRootContent());
            }
            if (tree.getRight() != null && !tree.getRight().isEmpty()) {
                s.push(tree.getRight());
            }
            if (tree.getLeft() != null && !tree.getLeft().isEmpty()) {
                s.push(tree.getLeft());
            }
        }
        return traversal;
    }

    public LinkedList<E> OrderTraversalRecursive() {
        LinkedList<E> traversal = new LinkedList<>();
        //System.out.println("------------------"+this.getRootContent()+"entrada");
        if (this.getLeft() != null && !this.isEmpty()) {
            //System.out.println("------------------"+this.getRootContent()+"mueve izquierda");
            traversal.addAll(this.getLeft().OrderTraversalRecursive());
        }
        if (this.getLeft() == null) {
            //System.out.println("------------------"+this.getRootContent()+"no hay izquierda");
            traversal.add(this.getRootContent());
            //System.out.println(this.getRootContent()+"Se agrega  ---------------------------");
        }
        if (this.getRight() != null) {
            traversal.addAll(this.getRight().OrderTraversalRecursive());
        }
        return traversal;
    }

    public LinkedList<E> breadthTraversal() {
        LinkedList<E> traversal = new LinkedList<>();
        Queue<BinaryTree<E>> q = new LinkedList<>();
        q.offer(this);
        while (!q.isEmpty()) {
            BinaryTree<E> tree = q.poll();
            if (!tree.isEmpty()) {
                traversal.add(tree.getRootContent());
            }
            if (tree.getLeft() != null && !tree.getLeft().isEmpty()) {
                q.offer(tree.getLeft());
            }
            if (tree.getRight() != null && !tree.getRight().isEmpty()) {
                q.offer(tree.getRight());
            }
        }
        return traversal;
    }

    public void setLeaves(E contenido) {
        if (this.isLeaf()) {
            this.setRight(new BinaryTree(contenido));
            this.setLeft(new BinaryTree(contenido));
            return;
        }
        if (this.getLeft() != null) {
            this.getLeft().setLeaves(contenido);
        }
        if (this.getRight() != null) {
            this.getRight().setLeaves(contenido);
        }
    }

    public void setAnswer(E contenido) {
        if (this.isLeaf()) {
            this.setRight(new BinaryTree(contenido));
            this.setLeft(new BinaryTree(contenido));
            return;
        }
        if (this.getLeft() != null) {
            this.getLeft().setLeaves(contenido);
        }
        if (this.getRight() != null) {
            this.getRight().setLeaves(contenido);
        }
    }
    
    public boolean recursiveSet(Queue<E> respuestas,E positivo,E negativo) {
        if (this.isEmpty()) {
            return false;
        } else {
            E resptActual = respuestas.poll();
            if (resptActual.equals(positivo)) {
                this.getLeft().recursiveSet(respuestas,positivo,negativo);
            } else if (resptActual.equals(negativo)) {
                this.getRight().recursiveSet(respuestas,positivo,negativo);
            } else {
                this.setRootContent(resptActual);
                return true;
            }
            return true;
        }
    }

}
