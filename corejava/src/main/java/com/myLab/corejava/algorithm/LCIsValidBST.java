package com.myLab.corejava.algorithm;

/**
 * leetcode 98题 验证二叉搜索树
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 * 假设一个二叉搜索树具有如下特征：
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 */
public class LCIsValidBST {
    long pre = Long.MIN_VALUE;
    boolean isValid = true;

    /**
     * 根据二叉搜索树的判断定义，不能只单单判断一层左节点小于根节点，右节点大于根节点，例如原来的方法check，其实只是判断了一层，这是不够的
     * 所有的节点都要大于其左子树，并且小于其右子树
     * 下面的实现思路是，借助于中序遍历，通过巧妙的pre变量赋值，可以实现正确的判断，debug一下并且思考这个实现思路。
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
//        check(root);
//        return isValid;

        if (root == null) {
            return true;
        }
        // 访问左子树
        if (!isValidBST(root.left)) {
            return false;
        }
        // 访问当前节点：如果当前节点小于等于中序遍历的前一个节点，说明不满足BST，返回 false；否则继续遍历。
        if (root.val <= pre) {
            return false;
        }
        pre = root.val;
        // 访问右子树
        return isValidBST(root.right);
    }

    private void check(TreeNode node) {
        //退出逻辑
        if (node.left == null && node.right == null) {
            return;
        }
        if (!isValid) {
            return;
        }
        //process
        if (node.left != null && node.left.val >= node.val) {
            isValid = false;
            return;
        }
        if (node.right != null && node.right.val <= node.val) {
            isValid = false;
            return;
        }
        //递归
        if (node.left != null) {
            check(node.left);
        }
        if (node.right != null) {
            check(node.right);
        }

    }

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7);
        TreeNode node3 = new TreeNode(3);
        TreeNode node1 = new TreeNode(1);
        TreeNode node4 = new TreeNode(4);
        TreeNode node3l = new TreeNode(3, node1, node4);
        TreeNode node6 = new TreeNode(6, node3, node7);
        TreeNode node5 = new TreeNode(5, node3l, node6);
        //[5,4,6,null,null,3,7]
//        TreeNode node = new TreeNode(-1);
//        TreeNode node0 = new TreeNode(0, node, null);
        LCIsValidBST LCIsValidBST = new LCIsValidBST();
        System.out.println(LCIsValidBST.isValidBST(node5));
    }
}
