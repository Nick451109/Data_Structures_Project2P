/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TDA;

/**
 *
 * @author CAELOS JR 2018
 */
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E> {

    private BinaryTreeNode<E> root;
    private Comparator<E> cmpContent;

//    public BinaryTree(E rootContent) {
//        this.root = new BinaryTreeNode<>(rootContent);
//    }

    public BinaryTree(E rootContent, Comparator<E> cmpContent) {
        this.root = new BinaryTreeNode<>(rootContent);
        this.cmpContent = cmpContent;
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

    public void setLeft(BinaryTree<E> tree) {
        this.root.setLeft(tree);
    }

    public void setRight(BinaryTree<E> tree) {
        this.root.setRight(tree);
    }

    public BinaryTree<E> getLeft() {
        return this.root.getLeft();
    }

    public BinaryTree<E> getRight() {
        return this.root.getRight();
    }

    public int countLevelsRecursive() {
        if (this.isEmpty()) {
            return 0;
        }
        if (this.isLeaf()) {
            return 1;
        }

        int levelsLeft = 0;
        int levelsRight = 0;

        if (this.root.getLeft() != null) {
            levelsLeft = this.root.getLeft().countLevelsRecursive();
        }

        if (this.root.getRight() != null) {
            levelsRight = this.root.getRight().countLevelsRecursive();
        }

        if (levelsLeft > levelsRight) {
            return levelsLeft + 1;
        }
        return levelsRight + 1;
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
            this.setRight(new BinaryTree(contenido,cmpContent));
            this.setLeft(new BinaryTree(contenido,cmpContent));
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
            this.setRight(new BinaryTree(contenido,cmpContent));
            this.setLeft(new BinaryTree(contenido,cmpContent));
            return;
        }
        if (this.getLeft() != null) {
            this.getLeft().setLeaves(contenido);
        }
        if (this.getRight() != null) {
            this.getRight().setLeaves(contenido);
        }
    }

    public boolean recursiveSet(Queue<E> respuestas, E positivo, E negativo) {
        if (this.isEmpty()) {
            return false;
        } else {
            E resptActual = respuestas.poll();
            if (cmpContent.compare(resptActual,positivo) == 0) { //cmpContent.compare(resptActual,positivo) == 0
                this.getLeft().recursiveSet(respuestas, positivo, negativo);
            } else if ((cmpContent.compare(resptActual, negativo) == 0)) {
                this.getRight().recursiveSet(respuestas, positivo, negativo);
            } else {
                this.setRootContent(resptActual);
                return true;
            }
            return true;
        }
    }

    //le paso el contenido de donde sea mi ultima pregunta y me retorna el arbol desde esa raiz
    public BinaryTree<E> iterativeTreeSearch(E content) {
        Stack<BinaryTree<E>> stack = new Stack<>();
        if (this.isEmpty()) {
            return null;
        }

        stack.push(this);
        while (!stack.isEmpty()) {
            BinaryTree<E> subtree = stack.pop();
            if (cmpContent.compare(subtree.root.getContent(), content) == 0) { //cmpContent.compare(subtree.root.getContent(),content) == 0
                return subtree;
            }
            if (subtree.root.getLeft() != null) {
                stack.push(subtree.root.getLeft());
            }
            if (subtree.root.getRight() != null) {
                stack.push(subtree.root.getRight());
            }
        }
        return null;
    }

    public BinaryTree<E> iterativeParentTreeSearch(E content) {
        Stack<BinaryTree<E>> stack = new Stack<>();
        if (this.isEmpty() || this.countLevelsRecursive() == 1) {
            return null;
        }
        stack.push(this);
        while (!stack.isEmpty()) {
            BinaryTree<E> subtree = stack.pop();
            System.out.println("este es el subtree que sale");
            if (cmpContent.compare(subtree.getLeft().getRootContent(), content) == 0 || cmpContent.compare(subtree.getRight().getRootContent(), content) == 0 ) {
                return subtree;
            }
            if (subtree.root.getLeft().getLeft() != null && subtree.root.getLeft().getRight() != null) {
                stack.push(subtree.root.getLeft());
            }
            if (subtree.root.getRight().getLeft() != null && subtree.root.getRight().getRight() != null) {
                stack.push(subtree.root.getRight());
            }
        }
        return null;
    }
    
    //debo pasarle el nodo exacto de la ultima pregunta que se haga para poder retornar los posibles animales
    public static void printLeafNodes(BinaryTree<String> node) {

        // base case 
        if (node == null) {
            return;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            System.out.printf(node.getRootContent());
        }
        printLeafNodes(node.root.getLeft());
        printLeafNodes(node.root.getRight());
    }

    public LinkedList<E> getLeafs(E content) { //a ver si se resuelve lo de los espacios 
        LinkedList<E> traversal = new LinkedList<>();
        Queue<BinaryTree<E>> q = new LinkedList<>();
        q.offer(this);
        while (!q.isEmpty()) {
            BinaryTree<E> tree = q.poll();
            if (tree.isLeaf()) {
                traversal.add(tree.getRootContent());
            }
            if (tree.getLeft() != null && !tree.getLeft().isEmpty() && !(cmpContent.compare(tree.getLeft().getRootContent(), content) == 0)) { //cmpContent.compare(tree.getLeft().getRootContent()," ") == 0
                q.offer(tree.getLeft());
            }
            if (tree.getRight() != null && !tree.getRight().isEmpty() && !(cmpContent.compare(tree.getRight().getRootContent(), content) == 0)) {
                q.offer(tree.getRight());
            }
        }
        return traversal;
    }

}
