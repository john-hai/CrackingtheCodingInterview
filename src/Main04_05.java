import java.util.ArrayList;
import java.util.List;

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

    //方法一：采用中序遍历，将待判断的树的节点放在ArrayList中，然后再判断是否是递增的序列（leetcode上要求后边的元素不能大于等于前一个元素，只能大于）。
    // 因为是采用了额外的空间来保存所有节点，并且进行比较，所以复杂度比较高。不如方法二。
    static ArrayList<Integer> list;
    public static boolean isValidBST1(TreeNode root){
        if(root == null){
            return true;
        }
        list = new ArrayList<>();
        middleOrderTraversel(root); //调用中序遍历
        int min = Integer.MIN_VALUE;
        for(int i = 0; i < list.size(); i++){
            if (list.get(i) <= min) {
                if(i == 0 && list.get(i) == Integer.MIN_VALUE){ //这里注意，对根节点元素为Integer.MIN_VALUE的情况做一下特判，否则{Integer.MIN_VALUE,null,null}无法通过
                    min = list.get(i);
                }
                else{
                    return false;
                }
            } else {
                min = list.get(i);
            }
        }
        return true;
    }

    /**
     *中序遍历之递归实现
     */
    public static void middleOrderTraversel(TreeNode root){
        if(root != null){
            middleOrderTraversel(root.left);
            list.add(root.val);
            middleOrderTraversel(root.right);
        }
    }
    //方法二：采用递归的方法，找寻左子树中的最右（数值最大）节点，找寻右子树中的最左（数值最小）节点，然后判断当前层是否合法。双百。
    public static boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        TreeNode maxLeft = root.left, minRight = root.right;
        // 找寻左子树中的最右（数值最大）节点
        while (maxLeft != null && maxLeft.right != null)
            maxLeft = maxLeft.right;
        // 找寻右子树中的最左（数值最小）节点
        while (minRight != null && minRight.left != null)
            minRight = minRight.left;
        // 当前层是否合法
        boolean ret = (maxLeft == null || maxLeft.val < root.val) && (minRight == null || root.val < minRight.val);
        // 进入左子树和右子树并判断是否合法
        return  ret && isValidBST(root.left) && isValidBST(root.right);
    }

    public static void main(String[] args){
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(20);
        System.out.println(isValidBST(root));
    }
}
