package com.myLab.corejava.algorithm;

/**
 * leetcode 111题，给给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明: 叶子节点是指没有子节点的节点。
 */
public class LCMinDepth {

    /**
     * 如果我们知道了左子树和右子树的最小深度 l 和 r，那么该二叉树的最大深度即为
     * min(l,r)+1
     * 而左子树和右子树的最大深度又可以以同样的方式进行计算。因此我们可以用「深度优先搜索」的方法来计算二叉树的最大深度。具体而言，在计算当前二叉树的最小深度时，可以先递归计算出其左子树和右子树的最小深度，递归在访问到空节点时退出。
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = minDepth(root.left);
            int rightHeight = minDepth(root.right);
            return Math.min(leftHeight, rightHeight) + 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(Math.pow(2,3));
    }
}
