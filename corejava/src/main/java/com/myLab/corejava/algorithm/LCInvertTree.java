package com.myLab.corejava.algorithm;

/**
 * leetcode 226题 翻转二叉树 即输入一个二叉树 将其每个节点的左右子节点互换
 */
public class LCInvertTree {
    public TreeNode invertTree(TreeNode root) {
        if (null != root) {
            invert(root);
        }
        return root;
    }

    private void invert(TreeNode node) {
        //退出逻辑是所有子节点都为空，意味着该节点是叶子节点
        if (null == node.left && null == node.right) {
            return;
        }
        //process 左右子节点互换
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        //递归
        if (null != node.left) {
            invert(node.left);
        }
        if (null != node.right) {
            invert(node.right);
        }
    }

    public static void main(String[] args) {

    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
