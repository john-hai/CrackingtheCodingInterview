public class Main04_05 {
    /**
     * 实现一个函数，检查一棵二叉树是否为二叉搜索树。
     *
     * 二叉搜索树是指一棵空树或者具有下列性质的二叉树：
     * 1.若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
     * 2.若任意节点的右子树不空，则右子树上所有节点的值均大于或等于它的根节点的值；
     * 3.任意节点的左、右子树也分别为二叉查找树；
     */

    /**
     * 二叉树的定义
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static boolean isValidBST(TreeNode root) {
        if(root == null){
            return true;
        }else{
            //左右节点都为空
            if(root.left == null && root.right == null)
                return isValidBST(root.left) && isValidBST(root.right);
            //左节点为空，右节点不为空
            if(root.left == null && root.right != null)
                return isValidBST(root.left) && root.right.val >= root.val && isValidBST(root.right);
            //右节点为空，左节点不为空
            if(root.right == null && root.left != null)
                return isValidBST(root.right) && root.left.val < root.val && isValidBST(root.left);
            //左节点，右节点都不为空
            if(root.left != null && root.right != null)
                return isValidBST(root.left) && root.right.val >= root.val && root.left.val < root.val && isValidBST(root.right);
        }
    }
    public static void main(String[] args){
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println(isValidBST(root));
    }
}
