public class Main08_01 {
    /**
     * 三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。
     * 实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。
     * n范围在[1, 1000000]之间
     */
    //方法一：采用动态规划方法。核心思想是，最后一步走几步？
    // 如果走1步，则等同于n-1阶的方法数，如果走2步，则等同于n-2阶的方法数，如果走3步，则等同于n-3阶的方法数。相加即可。
    public static int waysToStep(int n) {
        if(n < 1){
            return n;
        }
        if(n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        if(n == 3){
            return 4;
        }
        int[] a = new int[n + 1];
        a[0] = 0;
        a[1] = 1;
        a[2] = 2;
        a[3] = 4;
        for(int i = 4; i <= n;i++){
            //取模，对两个较大的数之和取模再对整体取模，防止越界（这里也是有讲究的）
            //假如对三个dp[i-n]都 % 1000000007，那么也是会出现越界情况（导致溢出变为负数的问题）
            //因为如果本来三个dp[i-n]都接近 1000000007 那么取模后仍然不变，但三个相加则溢出
            //但对两个较大的dp[i-n]:dp[i-2],dp[i-3]之和mod 1000000007，那么这两个较大的数相加大于 1000000007但又不溢出
            //取模后变成一个很小的数，与dp[i-1]相加也不溢出
            //所以取模操作也需要仔细分析
            a[i] = (a[i - 3] + (a[i - 2] + a[i - 1])%1000000007)%1000000007;
        }
        return a[n];
    }
    //方法二：对方法一的改进，不必开辟数组空间，空间复杂度更小，运行更快。
    public int waysToStep2(int n) {
        if(n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        if( n== 3){
            return 4;
        }
        int a = 4;
        int b = 2;
        int c = 1;
        for(int i = 0; i < n - 3; i++){
            int temp_a = a;
            int temp_b = b;
            a = (temp_a + (b + c)% 1000000007)% 1000000007;
            b = temp_a;
            c = temp_b;
        }
        return a;

    }

    public static void main(String[] args) {
        System.out.print(waysToStep(76));
    }
}
