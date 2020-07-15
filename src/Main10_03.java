public class Main10_03 {
    /**
     * 搜索旋转数组。
     * 给定一个排序后的数组，包含n个整数，但这个数组已被旋转过很多次了，次数不详。
     * 请编写代码找出数组中的某个元素，假设数组元素原先是按升序排列的。若有多个相同元素，返回索引值最小的一个。
     * 输入: arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 5
     * 输出: 8（元素5在该数组中的索引）
     *
     * 输入：arr = [15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14], target = 11
     * 输出：-1（没有找到）
     *
     * 本题要义在于，旋转数组不管旋转多少次，都只有一个阶梯，即仍是两个有序数组。本题可以和剑指offer第11题结合来看。
     */
    public static int search(int[] arr, int target) {
        if(arr == null || arr.length == 0){
            return -1;
        }
        int lo = 0;
        int hi = arr.length - 1;
        int mid = lo;//如果是旋转了0个元素，则还是非递减数列，可以在直接返回头值
        while (arr[lo] >= arr[hi]){
            if(hi - lo == 1){   //针对{-1, -2} 找4 这种情况
                if(target == arr[lo]){
                    return lo;
                }
                else if(target == arr[hi]){
                    return hi;
                }
                else return -1;
            }
            mid = lo + (hi - lo)/2;
            //如果头尾中间值相等，如{10111}、{11101｝都可以看成是{01111}的旋转
            //那么下边的判断法则就失效了，因为三者相等情况下，不知中值是左部分还是右部分，就遍历好了
            if(arr[lo] == arr[mid] && arr[lo] == arr[hi]){  // 1 1 1 1 (1) 2 1 1 1 找2
                for(int i = 0; i < arr.length; i++){
                    if(arr[i] == target){
                        return i;
                    }
                }
                return -1;

            }
            if(target > arr[mid]){
                if(target < arr[lo]){   //间隔在mid左边，右边是递增的，答案也可能在右侧 15 16 19 20 1 1 3 4 5 7 10
                    return rank(target, arr, mid + 1, hi);
                }
                else if(target > arr[lo]){
                    if(arr[lo] > arr[mid]){ //间断点在中点左侧，答案在左侧，45678 1 223333 找5
                        hi = mid - 1;
                    }
                    else if(arr[lo] < arr[mid]) {//间断点在中点右侧，答案可能在中点到间断点之间 11111 4 567 000 找5
                        lo = mid + 1;
                    }
                    else{   //45678 4 444444 找5 间断点在中点左侧，答案在lo与间断点之间
                        hi = mid - 1;
                    }
                }
                else{   //target==arr[lo]
                    return lo;
                }
            }
            else if(target < arr[mid]){ //19 20 25 1 (3) 4 5 7 10 14 找1
                if(arr[lo] > arr[mid]){ //19 20 25 1 (3) 4 5 7 10 14 找1 间断点在中点左侧，答案在左侧
                    hi = mid - 1;
                }
                else if(arr[lo] < arr[mid]) {//2 2 2 2 (3) 4 5 7 10 1 找1 间断点在中点右侧，答案可能在中点到间断点之间
                    if(target < arr[lo]){ //2 2 2 2 (3) 4 5 7 10 1 找1
                        lo = mid + 1;
                    }
                    else if(target > arr[lo]){  //0.5 1 2 2 (3) 4 5 7 10 0.1 找1
                        lo = lo + 1;
                        hi = mid - 1;
                    }
                    else{   //target = arr[lo]
                        return lo;
                    }
                }
                else{   //arr[lo] = arr[mid]  5 5 (5) 5 4 3 找4 或 5 4 (5) 5 5 5 间断点在中点左侧，答案在lo与间断点之间
                    if(target >= arr[hi]){  //5 5 (5) 5 4 3 找4
                        lo = mid + 1;
                    }
                    else{   //5 4 (5) 5 5 5 找4
                        hi = mid - 1;
                    }
                }
            }
            else{   //target = arr[mid]
                hi = mid;
            }
        }
        return rank(target, arr, lo, hi); //如果是旋转了0个元素，则还是非递减数列，可以在这里直接返回头值，不必经过中间繁杂的步骤。

    }

    /**
     * 典型的二分查找，算法第四版p15
     * @param key 要找的目标数值
     * @param a 有序数组
     * @param lo 起始点
     * @param hi 终止点，考察时包含该点的
     * @return 返回索引点，如果有序数组中有重复元素，返回第一个索引点
     */
    public static int rank(int key, int[] a, int lo, int hi){
        if(lo > hi)return -1;
        int mid = lo + (hi - lo)/2;
        if(key < a[mid]) return rank(key, a, lo, mid - 1);
        else if(key > a[mid]) return rank(key, a, mid + 1, hi);
        else {
            //while这里是针对有有序数组中有重复元素时的情况，比如3 4 4 6 11 13 找4，
            // 如果是寻常的二分查找办法（即没有下边的while部分，直接return mid），此时会返回2，即直接命中的mid，
            // 但显然，1位置也是4，题目要求在多个结果下输出最小索引
            //这里的实现方式是向前遍历，但也没有更好的办法了
            while(mid > lo){
                if(key == a[mid - 1]){
                    mid--;
                }else{
                    break;
                }
            }
            return mid;
        }
    }

    public static void main(String[] args) {
        int[] arr = {-1,-2};
        int target = 4;
        System.out.println(search(arr, target));
    }
}
