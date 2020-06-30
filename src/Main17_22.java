import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main17_22 {
    /**
     * 给定字典中的两个词，长度相等。写一个方法，把一个词转换成另一个词， 但是一次只能改变一个字符。每一步得到的新词都必须能在字典中找到。
     * 编写一个程序，返回一个可能的转换序列。如有多个可能的转换序列，你可以返回任何一个。
     * 输入:
     * beginWord = "hit",
     * endWord = "cog",
     * wordList = ["hot","dot","dog","lot","log","cog"]
     *
     * 输出:
     * ["hit","hot","dot","lot","log","cog"]
     *
     * 输入:
     * beginWord = "hit"
     * endWord = "cog"
     * wordList = ["hot","dot","dog","lot","log"]
     *
     * 输出: []
     *
     * 解释: endWord "cog" 不在字典中，所以不存在符合要求的转换序列
     *
     */
    public static List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        //如果wordList为null的话，也输出null
        if(wordList == null){
            return null;
        }
        //如果wordList为空列表，则输出空列表
        if(wordList.size() == 0){
            return new ArrayList<>(0);
        }
        //以下的情况为wordList为非空列表
        ///如果beginWord和endWord其中一个为null，则输出null
        if(beginWord == null||endWord == null)
            return null;
        ///如果beginWord和endWord长度不相等，则输出null
        if(beginWord.length() != endWord.length()){
            return null;
        }
        ///长度相等，但是长度都为零，即都是""，此时endWord肯定无法在字典wordList中找到，故输出null
        if(beginWord.equals("")){
            return null;
        }
        ///以下的情况即都是长度相等，且都有字符的情况
        ////看endWord时候在字典中，如果不在，则输出空列表
        boolean isInwordList = false;
        for(int i = 0; i < wordList.size(); i++){
            if(wordList.get(i).equals(endWord)){    //如果在的话，就吧endWord交换到最后
                String temp = wordList.get(wordList.size() - 1);
                wordList.set(i, temp);
                wordList.set(wordList.size() - 1, endWord);
                isInwordList = true;
                break;
            }
        }
        if(!isInwordList){
            return new ArrayList<>(0);
        }
        ////以下情况endWord都是可以在字典中找到的
        ////尾巴固定住，即为endWord
        List<String> ans = new ArrayList<>();
        int difference = howManyCharDifferent(endWord, beginWord);
        if(wordList.size() < difference){   //如果字典中String数小于不同位数，那肯定是找不到的。大于等于的话，可以继续找
            return new ArrayList<>(0);
        }
        //用类似于冒泡排序的方法，将第一个和尾元素差一位的元素调换到后面
        for(int i = 0; i < wordList.size(); i++){
            for(int j = 0; j < wordList.size() - i - 1; j++){
                if(howManyCharDifferent(wordList.get(j), wordList.get(wordList.size() - i - 1)) == 1){
                    String temp = wordList.get(wordList.size() - i - 2);
                    wordList.set(wordList.size() - i - 2, wordList.get(j));
                    wordList.set(j, temp);
                    if(howManyCharDifferent(wordList.get(wordList.size() - i - 2), beginWord) == 1 || wordList.get(wordList.size() - i - 2).equals(beginWord)){
                        if(howManyCharDifferent(wordList.get(wordList.size() - i - 2), beginWord) == 1){
                            ans.add(beginWord);
                        }
                        for(int k = wordList.size() - i - 2; k < wordList.size();k++){
                            ans.add(wordList.get(k));
                        }
                        return ans;
                    }
                    break;
                }
                if(j == wordList.size() - i - 2){   //说明没有和比较对象差一位的元素这时有两种情况，
                    // 第一种情况是，和不是endWord的元素不匹配，此时，有可能有其他元素和比较元素匹配，所以，把之前匹配好的删除掉，j=0，i--，重新寻找元素匹配。
                    // 还有情况就是，要么本来就没有和endWord匹配的元素，或者是像第一种情况那样，被删光了达到这种状态，所以此时输出空即可。
                    if(i == 0){
                        return new ArrayList<>(0);
                    }
                    wordList.remove(wordList.get(wordList.size() - i - 1));
                    j = 0;
                    i--;
                }
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * 该函数用来返回两个长度相同的String之间有多少个字符不同
     */
    public static int howManyCharDifferent(String first, String second){
        int cnt = 0;
        for(int i = 0; i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i)){
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * 该函数用来判断两个长度相同的String之间是否只有一个字符不同
     */
    public static boolean isOnlyOneCharDifferent(String first, String second){
        int cnt = 0;
        for(int i = 0; i < first.length(); i++){
            if(first.charAt(i) != second.charAt(i)){
                cnt++;
            }
            if(cnt > 1){
                return false;
            }
        }
        return cnt != 0;
    }

    public static void main(String[] args) {
        String beginWord = "talk";
        String endWord = "taii";
        //List list = Arrays.asList("peale","wilts","place","fetch","purer","pooch","peace","poach","berra","teach","rheum","peach");
        List list = Arrays.asList("talk","tons","fall","tail","taii","gale","hall","negs");
        //List list = Arrays.asList("wilt","vows","pity","shat","foot","eden","boom","anne","huhs","java","gnus","yeps","floe","eyck","died","flab","urge","styx","iota","gybe","grit","anus","crab","zebu","pods","cabs","kurt","bald","rips","pace","jays","acts","ryan","acne","inti","snip","heep","cruz","grin","last","rake","rush","whir","crew","kane","rasp","slop","shim","fain","howl","tuns","bias","junk","zeke","jock","kens","rope","mars","arty","tuna","naps","pols","judo","gone","rule","sale","guff","jove","porn","self","etta","sump","ibex","saar","grus","rive","arid","face","ante","grab","hobs","clam","brad","zits","alps","acid","grub","fade","sulk","rent","nick","puns","west","pies","quiz","colo","hock","whiz","dick","zibo","hack","care","hurl","open","lift","fend","pros","beth","neva","drag","colt","ants","tamp","amen","visa","nets","ache","turd");
        ArrayList wordList = new ArrayList<>(list);
        List<String> ans = findLadders(beginWord, endWord, wordList);
        for(String str:ans){
            System.out.print(str + " ");
        }
    }
}
