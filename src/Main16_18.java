public class Main16_18 {
    /**
     * 模式匹配
     * 你有两个字符串，即pattern和value。 pattern字符串由字母"a"和"b"组成，用于描述字符串中的模式。
     * 例如，字符串"catcatgocatgo"匹配模式"aabab"（其中"cat"是"a"，"go"是"b"），该字符串也匹配像"a"、"ab"和"b"这样的模式。
     * 但需注意"a"和"b"不能同时表示相同的字符串。编写一个方法判断value字符串是否匹配pattern字符串。
     * 未细看。解方程：3 * str_a.length() + 2 * str_b.length() = value.length()
     */
    private char[] global_pattern, global_value;
    private int value_len;

    public boolean patternMatching(String pattern, String value) {
        value_len = value.length();
        if (value_len == 0) return pattern.length() < 2;
        global_pattern = pattern.toCharArray();
        global_value = value.toCharArray();
        int pattern_len = pattern.length();
        if (pattern_len == 0) return false;
        int count_a = 0, count_b = 0;
        for (char ch : global_pattern) {
            if (ch == 'a') ++count_a;
            else ++count_b;
        }
        if (count_a == 0) return single_count(count_b);
        if (count_b == 0) return single_count(count_a);
        return count_a < count_b ? muti_count(count_a, count_b, 'a') : muti_count(count_b, count_a, 'b');
    }

    private boolean single_count(int count) {
        if (value_len % count != 0) return false;
        String base = new String(global_value, 0, value_len / count);
        int base_len = base.length(), idx = base_len;
        while (idx < value_len) {
            if (!check(base, idx)) return false;
            idx += base_len;
        }
        return true;
    }

        private boolean muti_count(int small_count, int big_count, char small_ch) {
            int small_sum = 0;
            search:
            while ((small_sum += small_count) < value_len) {
                int remain = value_len - small_sum;
                if (remain % big_count != 0) continue;
                int big_len = remain / big_count, small_len = small_sum / small_count;
                String small_str = null, big_str = null;
                int value_idx = 0;
                for (char cur_ch : global_pattern) {
                    if (cur_ch == small_ch) {
                        if (small_str == null)
                            small_str = new String(global_value, value_idx, small_len);
                        else if (!check(small_str, value_idx)) continue search;
                        value_idx += small_len;
                    } else {
                        if (big_str == null)
                            big_str = new String(global_value, value_idx, big_len);
                        else if (!check(big_str, value_idx)) continue search;
                        value_idx += big_len;
                    }
                }
                return true;
            }
            return single_count(small_count) || single_count(big_count);
        }
        private boolean check(String str, int value_idx) {
            int idx = 0;
            for (char ch : str.toCharArray())
                if (ch != global_value[value_idx + idx++])
                    return false;
            return true;
        }

}
