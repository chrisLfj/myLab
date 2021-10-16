package com.myLab.corejava.algorithm;

/**
 * leetcode 111题，给给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明: 叶子节点是指没有子节点的节点。
 */
public class LCMinDepth {

    /**
     * 如果我们知道了左子树和右子树的最小深度 l 和 r，那么该二叉树的最大深度即为
     * 而左子树和右子树的最大深度又可以以同样的方式进行计算。因此我们可以用「深度优先搜索」的方法来计算二叉树的最大深度。具体而言，在计算当前二叉树的最小深度时，可以先递归计算出其左子树和右子树的最小深度，递归在访问到空节点时退出。
     * 整理一下思路，叶子节点是指左右节点都为空的节点，找到叶子节点即可，我们都知道这题要用递归，这题递归的返回逻辑才是重点
     * 1.左右子节点都为空，则说明是叶子节点，返回1
     * 2.左右子节点有一个为空时，需要返回不为空的子节点的深度
     * 3.左右子节点都不为空时，则返回左右子节点较小深度值
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if(root == null)
            return 0;
        //1.左右子节点都为空，返回1
        if(root.left == null && root.right == null)
            return 1;
        //2.只有一个子节点为空，则返回不为空子节点的深度
        if(root.left != null && root.right == null)
            return minDepth(root.left) + 1;
        if(root.left == null && root.right != null)
            return minDepth(root.right) + 1;
        //3.两个子节点都不为空，则返回深度较小的值
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    public int minDepthBetter1(TreeNode root) {
        if(root == null) return 0;
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        //因为节点为空的话它的深度为0，因此可以将1，2两种情况合并，代码优化如下
        return root.left == null || root.right == null ? leftDepth + rightDepth + 1 : Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    public static void main(String[] args) {
        System.out.println(Math.pow(2,3));
        TreeNode right1 = new TreeNode(5,null,null);
        TreeNode right2 = new TreeNode(4, null, right1);
        TreeNode right3 = new TreeNode(3, null, right2);
        TreeNode right4 = new TreeNode(2, null, right3);
        TreeNode right5 = new TreeNode(1,null,right4);
        LCMinDepth test = new LCMinDepth();
        System.out.println(test.minDepth(right5));
    }
}
