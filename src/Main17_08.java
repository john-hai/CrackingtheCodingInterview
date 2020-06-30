import java.util.Arrays;

public class Main17_08 {
    /**马戏团人塔
     * 有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。
     * 出于实际和美观的考虑，在上面的人要比下面的人矮一点且轻一点。
     * 已知马戏团每个人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。
     * 输入：height = [65,70,56,75,60,68] weight = [100,150,90,190,95,110]
     * 输出：6
     * 解释：从上往下数，叠罗汉最多能叠 6 层：(56,90), (60,95), (65,100), (68,110), (70,150), (75,190)
     *
     * 思路：
     * 先根据身高 升序排序，若身高一样则根据体重 降序排序。
     * 身高排序好之后，剩余待处理的就是体重。
     * 处理体重的问题就是处理最长递增子序列的问题。
     */
    public static int bestSeqAtIndex(int[] height, int[] weight) {
        int len = height.length;
        int[][] person = new int[len][2];
        for (int i = 0; i < len; ++i)
            person[i] = new int[]{height[i], weight[i]};
        //排序，身高升序，身高相同的人，按体重进行降序
        Arrays.sort(person, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int[] dp = new int[len];
        int res = 0;    //用来保存dp数组的长度。
        //这是一个给dp数组赋值的过程。dp的作用见代码后面的解释。
        for (int[] pair : person) {
            //这里利用到了自带的二分查找返回值的一些特征，可以参考 https://blog.csdn.net/CXHPLY/article/details/49423501
            int i = Arrays.binarySearch(dp, 0, res, pair[1]);   //fromIndex和toIndex都为0的时候，返回-1
            if (i < 0)
                i = -(i + 1);
            dp[i] = pair[1];
            if (i == res)   //说明赋最高位的值了，长度应该加一
                ++res;
        }
        return res; //数组d的长度即为最长子序列的长度
    }

    public static void main(String[] args) {
        int[] height = {65,70,56,75,60,68};
        int[] weight = {100,150,90,190,95,110};
        System.out.println(bestSeqAtIndex(height, weight));
        int[] a = new int[] {4, 5, 0, 0, 0, 0};
        int x1 = Arrays.binarySearch(a, 0, 0, 6);
        System.out.println(x1);
    }
}
/**无序列表最关键的一句在于： 数组 d[i]表示长度为 i 的最长上升子序列的末尾元素的最小值，
 * 即在数组 1,2,3,4,5,6中长度为3的上升子序列可以为 1,2,3也可以为 2,3,4等等但是d[3]=3，即子序列末尾元素最小为3。
 * 无序列表解释清了数组d的含义之后，我们接着需要证明数组d具有单调性，即证明i<j时，d[i]<d[j]，使用反证法，
 * 假设存在k<j时，d[k]>d[j]，但在长度为j，末尾元素为d[j]的子序列A中，将后j-i个元素减掉，可以得到一个长度为i的子序列B，
 * 其末尾元素t1必然小于d[j]（因为在子序列A中，t1的位置上在d[j]的后面），而我们假设数组d必须符合表示长度为 i 的最长上升子序列的末尾元素的最小值，
 * 此时长度为i的子序列的末尾元素t1<d[j]<d[k]，即t1<d[k]，所以d[k]不是最小的，与题设相矛盾，因此可以证明其单调性
 * 无序列表证明单调性有两个好处：1.可以使用二分法；2.数组d的长度即为最长子序列的长度；
 * 以下是上面这段话的原文，也包含了这个最长上升子序列问题：
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/291805
 */