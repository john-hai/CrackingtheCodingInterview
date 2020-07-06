import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main16_08 {
    /**
     * 整数的英文表示
     * 示例 1:
     *
     * 输入: 123
     * 输出: "One Hundred Twenty Three"
     * 示例 2:
     *
     * 输入: 12 345
     * 输出: "Twelve Thousand Three Hundred Forty Five"
     * 示例 3:
     *
     * 输入: 1 234 567
     * 输出: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
     * 示例 4:
     *
     * 输入: 1 234 567 891
     * 输出: "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"
     */
    public static String numberToWords(int num) {
        List<String> ans = new ArrayList<>();
        //考察三位
        for (int i = 1000000000; 1 < i; i /= 1000) {
            if (i <= num) {
                ans.addAll(threeDigit(num / i));
                ans.add(map.get(i));
                num %= i;
            }
        }
        if (0 < num || ans.size() == 0) {
            ans.addAll(threeDigit(num));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ans.get(0));
        //加空格
        for (int i = 1; i < ans.size(); ++i) {
            sb.append(' ');
            sb.append(ans.get(i));
        }
        return sb.toString();
    }

    private static List<String> threeDigit(int num) {
        List<String> ans = new ArrayList<>();
        if (num == 0) {
            ans.add(map.get(0));
        } else {
            if (99 < num) {
                ans.add(map.get(num / 100));
                ans.add(map.get(100));
            }
            if (0 < num % 100) {
                int twoDigit = num % 100;
                if (twoDigit < 20) {
                    ans.add(map.get(twoDigit));
                } else {
                    ans.add(map.get(twoDigit / 10 * 10));
                    if (0 < twoDigit % 10) {
                        ans.add(map.get(twoDigit % 10));
                    }
                }
            }
        }
        return ans;
    }

    private static Map<Integer, String> map = new HashMap<Integer, String>() {{
        put(0, "Zero");
        put(1, "One");
        put(2, "Two");
        put(3, "Three");
        put(4, "Four");
        put(5, "Five");
        put(6, "Six");
        put(7, "Seven");
        put(8, "Eight");
        put(9, "Nine");
        put(10, "Ten");
        put(11, "Eleven");
        put(12, "Twelve");
        put(13, "Thirteen");
        put(14, "Fourteen");
        put(15, "Fifteen");
        put(16, "Sixteen");
        put(17, "Seventeen");
        put(18, "Eighteen");
        put(19, "Nineteen");
        put(20, "Twenty");
        put(30, "Thirty");
        put(40, "Forty");
        put(50, "Fifty");
        put(60, "Sixty");
        put(70, "Seventy");
        put(80, "Eighty");
        put(90, "Ninety");
        put(100, "Hundred");
        put(1000, "Thousand");
        put(1000000, "Million");
        put(1000000000, "Billion");
    }};

    public static void main(String[] args) {
        int i1 = 1234567891;
        System.out.println(numberToWords(i1));
    }
}
