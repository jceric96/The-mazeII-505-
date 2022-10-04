import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MazeIIBFS {

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        // BFS
        // Time:O(xLen * yLen)
        // Space:O(xLen * yLen)
        Queue<Integer> queue = new LinkedList<>();
        int xLen = maze.length, yLen = maze[0].length;
        // create a visitedMap same with maze
        // to record arriving at each position needs how many steps
        int[][] visitedMap = new int[xLen][yLen];

        // fill the Max integer into unvisited map position
        for (int i = 0; i < xLen; i++) {
            Arrays.fill(visitedMap[i], Integer.MAX_VALUE);
        }
        // create two array to save dx and dy steps
        int[] dx = { -1, 0, 1, 0 };
        int[] dy = { 0, 1, 0, -1 };

        // add start position into queue
        queue.offer(start[0] * yLen + start[1]);
        // change start positon to as 0
        visitedMap[start[0]][start[1]] = 0;

        int shortDis = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            // get ball current position
            int temp = queue.poll();
            int x = temp / yLen;
            int y = temp % yLen;
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                int add = 0;
                while (nx >= 0 && nx < xLen && ny >= 0 && ny < yLen && maze[nx][ny] == 0) {
                    // record steps
                    ++add;
                    nx += dx[i];
                    ny += dy[i];
                }
                // back one step to avoid out of bounds
                nx -= dx[i];
                ny -= dy[i];
                // compare and save shortest steps
                boolean visited = false;
                if (visitedMap[x][y] + add < visitedMap[nx][ny]) {
                    visitedMap[nx][ny] = visitedMap[x][y] + add;
                    visited = !visited;
                }
                // check if arriving at destination point
                if (nx == destination[0] && ny == destination[1]) {
                    shortDis = Math.min(shortDis, visitedMap[nx][ny]);
                }
                // if update the shorest steps add it into queue and go
                if (visited) {
                    queue.offer(nx * yLen + ny);
                }
            }
        }
        return shortDis == Integer.MAX_VALUE ? -1 : shortDis;
    }

    public static void main(String[] args) {
        MazeIIBFS bfs = new MazeIIBFS();
        int[][] maze = new int[][] {
                { 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0 },
                { 1, 1, 0, 1, 1 },
                { 0, 0, 0, 0, 0 }
        };
        int[] start = new int[] { 0, 4 };
        int[] destination = new int[] { 3, 2 };
        System.out.println(bfs.shortestDistance(maze, start, destination));
    }
}
