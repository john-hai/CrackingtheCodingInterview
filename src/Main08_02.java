import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main08_02 {
    /**
     * 设想有个机器人坐在一个网格的左上角，网格 r 行 c 列。机器人只能向下或向右移动，但不能走到一些被禁止的网格（有障碍物）。
     * 设计一种算法，寻找机器人从左上角移动到右下角的路径。
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。
     * 返回一条可行的路径，路径由经过的网格的行号和列号组成。左上角为 0 行 0 列。如果没有可行的路径，返回空数组。
     * 输入:
     * [
     *   [0,0,0],
     *   [0,1,0],
     *   [0,0,0]
     * ]
     * 输出: [[0,0],[0,1],[0,2],[1,2],[2,2]]
     */

    private static int  m;
    private static int n;
    private static int[][] grid;

    /**
     *
     * @param row 表示当前考察位置的横座标
     * @param col 表示当前考察位置的纵座标
     * @param visited 标记数组，初始值皆为false，表示都还未经历，如果经历过，则设为true
     * @param pathList 保存总的路径用的数组
     * @return true or false
     */
    private static boolean dfs(int row, int col, boolean[][] visited, List<List<Integer>> pathList) {
        //异常情况：行坐标超出范围 或 列坐标超出范围 或 格子里是障碍物 或 访问过这个格子
        if (row >= m || col >= n || grid[row][col] == 1 || visited[row][col]) {
            return false;
        }

        pathList.add(Arrays.asList(row, col));
        //到最后一个格子了，也就是完成任务了
        if (row == m - 1 && col == n - 1) {
            return true;
        }
        //如果不是上边所列的异常情况，将当前格子标记为已经历过
        visited[row][col] = true;
        //如果右边格子或者下边格子是true的话，返回true。这里有一点需要注意，因为||的性质，前者，即右格为true的时候，就先不执行下格了。只有右格之后的路径难以为继的时候，才会来这里继续执行下格
        if (dfs(row, col + 1, visited, pathList) || dfs(row + 1, col, visited, pathList)) {
            return true;
        }
        //如果已经到了末路，即当前格子没有障碍，但右、下格子都是障碍的话，但还没有到达最后的格子，删掉数组pathList的最后一个元素。并返回false
        pathList.remove(pathList.size() - 1);
        return false;
    }

    public static List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
        //特例判断
        ///棋盘为空的情况下，没有有效路径，返回空就可以了。
        if(obstacleGrid == null){
            return new ArrayList<>(0);
        }
        ///棋盘为空的情况下，没有有效路径，返回空就可以了。
        if(obstacleGrid.length == 0){
            return new ArrayList<>(0);
        }
        ///棋盘为空的情况下，没有有效路径，返回空就可以了。
        if(obstacleGrid[0] == null){
            return new ArrayList<>(0);
        }
        ///棋盘为空的情况下，没有有效路径，返回空就可以了。
        if(obstacleGrid[0].length == 0){
            return new ArrayList<>(0);
        }
        ///如果最后一个格子内有障碍物的话，那一定无法完成
        if(obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1] == 1){
            return new ArrayList<>(0);
        }

        grid = obstacleGrid;
        m = grid.length;
        n = grid[0].length;
        //保存路径用的数组
        List<List<Integer>> ansList = new ArrayList<>();
        //执行dfs函数
        dfs(0, 0, new boolean[m][n], ansList);
        return ansList;
    }

    public static void main(String[] args) {
        int[][] array = {{0, 0, 0}, {0, 1, 1}, {0, 0, 0}};
        List<List<Integer>> ans = pathWithObstacles(array);
        for(int i = 0; i < ans.size(); i++){
            System.out.print("[");
            for(int j = 0; j < ans.get(i).size(); j++){
                if(j == ans.get(i).size() - 1){
                    System.out.print(ans.get(i).get(j));
                }else
                    System.out.print(ans.get(i).get(j)+",");
            }
            if(i == ans.size() - 1){
                System.out.print("]");
            }else
                System.out.print("],");
        }
    }
}
