import java.util.Stack;

public class Main16_26 {
    /**
     * 计算器
     * 给定一个包含正整数、加(+)、减(-)、乘(*)、除(/)的算数表达式(括号除外)，计算其结果。
     * 表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格。整数除法仅保留整数部分。
     * 输入: "3+2*2"
     * 输出: 7
     *
     * 输入: " 3/2 "
     * 输出: 1
     *
     * 输入: " 3+5 / 2 "
     * 输出: 5
     *
     * 思路：
     * 1. 如果碰到数字， 则把数字入栈
     * 2. 如果碰到空格， 则继续下一步
     * 3. 如果碰到 '+' '-' '*' '/', 则查找下一个数字num
     *     A.如果是'+', 下一个数字num直接入栈
     *     B.如果是'-'，-num入栈
     *     C.如果是'*', num = stack.pop() * num, 入栈
     *     D.如果是'/', num = stack.pop() / num, 入栈
     * 4. 最后，把栈里的数相加就是结果
     */
    public static  int calculate(String s) {
        //删除头尾空格
        char[] cs = s.trim().toCharArray();
        Stack<Integer> st = new Stack();
        int ans = 0, i = 0;
        while(i < cs.length){
            if(cs[i] == ' ') {i++;continue;}
            char tmp = cs[i];
            if(tmp == '*' || tmp == '/' || tmp == '+' || tmp == '-'){
                i++;
                while(i < cs.length && cs[i] == ' ') i++; //碰到空格继续向后处理
            }
            int num = 0;
            //这个循环用来对两位及两位以上的数字进行取值
            while(i < cs.length && Character.isDigit(cs[i])){
                num = num * 10 + cs[i] - '0';
                i++;
            }
            switch(tmp){
                case '-':
                    num = -num;
                    break;
                case '*':
                    num = st.pop() * num;
                    break;
                case '/':
                    num = st.pop() / num;
                    break;
                default:
                    break;
            }
            st.push(num);
        }
        while(!st.isEmpty()) ans += st.pop();
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(calculate(" 3+5 / 2 "));
    }
}
