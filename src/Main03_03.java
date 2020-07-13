import java.util.ArrayList;

public class Main03_03{
    /**
     * 堆盘子。
     * 设想有一堆盘子，堆太高可能会倒下来。因此，在现实生活中，盘子堆到一定高度时，我们就会另外堆一堆盘子。
     * 请实现数据结构SetOfStacks，模拟这种行为。SetOfStacks应该由多个栈组成，并且在前一个栈填满时新建一个栈。
     * 此外，SetOfStacks.push()和SetOfStacks.pop()应该与普通栈的操作方法相同（也就是说，pop()返回的值，应该跟只有一个栈时的情况一样）。
     * 进阶：实现一个popAt(int index)方法，根据指定的子栈，执行pop操作。
     * 当某个栈为空时，应当删除该栈。当栈中没有元素或不存在该栈时，pop，popAt 应返回 -1.
     * ["StackOfPlates", "push", "push", "push", "popAt", "popAt", "popAt"]
     * [[2], [1], [2], [3], [0], [0], [0]]
     *  输出：
     * [null, null, null, null, 2, 1, 3]
     * 思路：用ArrayList的结构进行存储，层高cap即为子数组的大小。提交实现的时候用StackOfPlates代替Main03_03即可。
     */
    static ArrayList<ArrayList<Integer>> list;
    static int cap;
    public Main03_03(int cap1) {
        list = new ArrayList<>();
        cap = cap1;
    }

    public void push(int val) {
        //cap不正常的时候，即小于1的时候，这种情况下没有办法push，因为层高不能为0或负数
        if(cap <= 0){
            return;
        }
        if(list.size() == 0 || list.get(list.size() - 1).size() == cap){
            ArrayList<Integer> newlist = new ArrayList<>();
            newlist.add(val);
            list.add(newlist);
        }else{
            list.get(list.size() - 1).add(val);
        }
    }

    public int pop() {
        //如果list里面没有任何子数组了，按题目要求，此时返回-1
        if(list.size() == 0){
            return -1;
        }else{
            int ans = list.get(list.size() - 1).remove(list.get(list.size() - 1).size() - 1);
            //如果remove后子数组空了，就把最后的这个空子数组删掉
            if(list.get(list.size() - 1).size() == 0){
                list.remove(list.size() - 1);
            }
            return ans;
        }
    }

    public int popAt(int index) {
        //按题目要求，排出index非法的情况
        if(index < 0 || index >= list.size()){
            return -1;
        }else{
            int ans = list.get(index).remove(list.get(index).size() - 1);
            //如果remove后子数组空了，就把空子数组删掉，后面的自动前进1位
            if(list.get(index).size() == 0){
                list.remove(index);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        Main03_03 plates = new Main03_03(2);
        plates.push(1);
        plates.push(2);
        plates.push(3);
        System.out.println(plates.popAt(0));
        System.out.println(plates.popAt(0));
        System.out.println(plates.popAt(0));
    }
}
