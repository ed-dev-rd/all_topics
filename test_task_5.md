A* для поиска пути
-

public static List<int[]> aStar(int[][] grid) 
{
    int n = grid.length, m = grid[0].length;
    
    PriorityQueue<Node> open = new PriorityQueue<>();
    
    Set<String> closed = new HashSet<>();
    
    Map<String, Node> cameFrom = new HashMap<>();
    
    Map<String, Integer> gScore = new HashMap<>();
    
    open.add(new Node(0, 0, 0, heuristic(0, 0, n-1, m-1)));
    
    gScore.put("0,0", 0);
    
    while (!open.isEmpty()) 
    {
        Node curr = open.poll();
        
        String currKey = curr.x + "," + curr.y;
        
        if (curr.x == n-1 && curr.y == m-1) 
        
            return reconstructPath(cameFrom, curr);
            
        if (closed.contains(currKey)) continue;
        
        closed.add(currKey);
        
        // Генерация соседей
        
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        
        for (int[] dir : directions)
        {
            int nx = curr.x + dir[0], ny = curr.y + dir[1];
            
            if (nx < 0 || nx >= n || ny < 0 || ny >= m || grid[nx][ny] == 1) 
            
                continue;
                
            String neighborKey = nx + "," + ny;
            
            if (closed.contains(neighborKey)) continue;
            
            int tentativeG = gScore.get(currKey) + 1;
            
            if (tentativeG < gScore.getOrDefault(neighborKey, Integer.MAX_VALUE)) 
            {
                cameFrom.put(neighborKey, curr);
                
                gScore.put(neighborKey, tentativeG);
                
                int fScore = tentativeG + heuristic(nx, ny, n-1, m-1);
                
                open.add(new Node(nx, ny, tentativeG, fScore));
            }
        }
    }
    return new ArrayList<>(); // путь не найден
}

private static List<int[]> reconstructPath(Map<String, Node> cameFrom, Node end) 
{
    List<int[]> path = new ArrayList<>();
    
    Node current = end;
    
    while (current != null)
    {
        path.add(0, new int[]{current.x, current.y});
        
        current = cameFrom.get(current.x + "," + current.y);
    }
    return path;
}

private static int heuristic(int x1, int y1, int x2, int y2)
{
    return Math.abs(x1 - x2) + Math.abs(y1 - y2); // манхэттенское расстояние
}

static class Node implements Comparable<Node>
{
    int x, y, g, f;
    
    Node(int x, int y, int g, int f) { this.x = x; this.y = y; this.g = g; this.f = f;
    }
    public int compareTo(Node other) { return Integer.compare(this.f, other.f);
    }
}


