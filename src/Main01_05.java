public class Main01_05 {
    /**
     * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。
     * 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑
     */
    public static boolean oneEditAway(String first, String second) {
        //如果二者都为null，返回true
        if(first == null && second == null){
            return true;
        }
        //first为null
        if(first == null){
            return second.length() == 0 || second.length() == 1;
        }
        //second为null
        if(second == null){
            return first.length() == 0 || first.length() == 1;
        }
        //以下的情况二者都不为空
        //二者相等
        if(first.equals(second)){
            return true;
        }
        //二者不相等，先用长度比较，如过长度差大于1，则一次操作不能完成
        if(Math.abs(first.length() - second.length()) > 1){
            return false;
        }else { //长度差为1或0的情况
            //长度差为0的情况，如果只有一个位置字符不同，替换就可以了，如果有两个或两个以上的位置不同，那么一次操作显然不可
            if(first.length() == second.length()){
                int cnt = 0;
                for(int i = 0; i < first.length(); i++){
                    if(first.charAt(i) != second.charAt(i)){
                        cnt++;
                        if(cnt > 1){
                            return false;
                        }
                    }
                }
                return true;
            }else{  //长度差为1的情况
                if(first.equals("") || second.equals(""))   //如果其中一个为空，另一个只有一个字符，则用添加的方式就可以了。这是可以一次完成的
                    return true;
                //采用首尾双指针对撞的方法
                int i = 0;
                int j = Math.max(first.length(), second.length()) - 1;
                while (i < j && first.charAt(i) == second.charAt(i)){
                    i++;
                }
                //first长
                if(first.length() > second.length()){
                    while (j > 0 && first.charAt(j) == second.charAt(j-1)){     //这个j>0是为了保障second.charAt(j-1)不越界
                        j--;
                    }
                }else{ //second长
                    while (j > 0 && first.charAt(j-1) == second.charAt(j)){
                        j--;
                    }
                }
                return i == j;
            }
        }
    }

    public static void main(String[] args) {
        String first = "is";
        String second = "s";
        System.out.println(oneEditAway(first, second));
    }
}
