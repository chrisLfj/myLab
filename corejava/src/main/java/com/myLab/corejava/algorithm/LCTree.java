package com.myLab.corejava.algorithm;

import java.util.ArrayList;
import java.util.List;

public class LCTree {
    /**
     * leetcode 94题，二叉树的中序遍历
     * 中序遍历，采用递归调用，退出逻辑是节点为空，遍历左节点   打印   遍历右节点
     * 递归的方法会扫描每个节点，因此时间复杂度为O(n)，空间复杂度为O(h)，h为二叉树的高度
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.val);
        inorder(node.right, result);
    }

    /**
     * leetcode 144题，二叉树的前序遍历
     * 前序遍历，采用递归调用，退出逻辑是节点为空，打印   遍历左节点   遍历右节点
     * 递归的方法会扫描每个节点，因此时间复杂度为O(n)，空间复杂度为O(h)，h为二叉树的高度
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    private void preOrder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    /**
     * leetcode 590题 N叉树的后序遍历
     * 与二叉树类似，后序遍历采用递归实现，退出条件为节点为空，执行顺序是遍历各个子节点   打印
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(NTreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(NTreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        for(NTreeNode children: node.childrens)
            postorder(children, result);
        result.add(node.val);
    }

    /**
     * leetcode 589题 N叉树的前序遍历
     * 与二叉树类似，前序遍历采用递归实现，退出条件为节点为空，执行顺序是打印   遍历各个子节点
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(NTreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(NTreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        for(NTreeNode children: node.childrens)
            preorder(children, result);
    }

    /**
     * leetcode 104题，二叉树的最大深度
     * @param args
     */

    /**
     * 二叉树节点类
     */
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {

        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * N叉树节点类
     */
    class NTreeNode {
        int val;
        List<NTreeNode> childrens;

        public NTreeNode(int val, List<NTreeNode> childrens) {
            this.val = val;
            this.childrens = childrens;
        }

        public NTreeNode(int val) {
            this.val = val;
        }

        public NTreeNode() {
        }
    }
}
