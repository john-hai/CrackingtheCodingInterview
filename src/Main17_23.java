public class Main17_23 {
    /**
     * 最大黑方阵
     * 给定一个方阵，其中每个单元(像素)非黑即白。设计一个算法，找出 4 条边皆为黑色像素的最大子方阵。
     * 返回一个数组 [r, c, size] ，其中 r, c 分别代表子方阵左上角的行号和列号，size 是子方阵的边长。
     * 若有多个满足条件的子方阵，返回 r 最小的，若 r 相同，返回 c 最小的子方阵。若无满足条件的子方阵，返回空数组。
     * 输入:
     * [
     *    [1,0,1],
     *    [0,0,1],
     *    [0,0,1]
     * ]
     * 输出: [1,0,2]
     * 解释: 输入中 0 代表黑色，1 代表白色，标粗的元素即为满足条件的最大子方阵
     *
     * 主要思路是：主要思路是预先确定最上和最左两个边界，再递归判断最右和最下边界。
     */
    static int[][] matrix;
    static int[] result=new int[]{0,0,0};

    public static int[] findSquare(int[][] matrix1) {
        matrix = new int[matrix1.length][matrix1.length];
        matrix = matrix1;
        //为空数组时，返回空数组
        if(matrix.length==0){
            return new int[]{};
        }
        for(int i=0;i<matrix.length-result[2];i++){
            for(int j=0;j<matrix.length-result[2];j++){
                if(matrix[i][j]==0){
                    int count=1;
                    //判断当前i，j坐标元素的右侧元素与下边的元素是否越界以及各自是否为0，如果满足以上条件，则给count+1
                    while(count+i<matrix.length&&count+j<matrix.length
                            &&matrix[count+i][j]==0&&matrix[i][count+j]==0
                    ){
                        count++;
                    }
                    //调用isBlackMatrix函数来得出真实的长度
                    int actualLen=isBlackMatrix(i,j,count);
                    if(actualLen>result[2]){
                        result[0]=i;
                        result[1]=j;
                        result[2]=actualLen;
                    }
                }
            }
        }
        //如果发现得出的size == 0 即没有黑点，此时返回空数组
        if(result[2]==0){
            return new int[]{};
        }
        return result;
    }

    public static int isBlackMatrix(int i,int j,int size){//recursive check 递归检查
        if(size==1){
            return size;
        }
        else{
            for(int x=0;x<size;x++){
                //x=0的时候判断下，右两个元素是否为0，x=1的情况下判断右下元素是否为0
                if(matrix[i+size-1][j+x]==0&&matrix[i+x][j+size-1]==0){
                    continue;
                }
                //如果不满足条件，则递归考察size-1
                else{
                    int miniLen=isBlackMatrix(i,j,size-1);
                    return miniLen;
                }
            }
            return size;
        }
    }

    public static void main(String[] args) {
        int[][] array = {{1,0,1},{0,0,1},{0,0,1}};
        int[] ans = findSquare(array);
        for(int i:ans){
            System.out.print(i+" ");
        }
    }
}
