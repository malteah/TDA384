package amazed.solver;
import amazed.maze.Maze;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


public class ForkJoinSolver
    extends SequentialSolver
{   
    public int start_node = start;
    public static int goal_node;
    static ConcurrentSkipListSet<Integer> visited = new ConcurrentSkipListSet<Integer>();
    static ConcurrentHashMap<Integer,Integer> predecessor = new ConcurrentHashMap<Integer, Integer>();
    List<ForkJoinSolver> forks= new ArrayList<ForkJoinSolver>();

     public ForkJoinSolver(Maze maze)
    {
        super(maze);
    }

    static boolean path_found = false;
    
    public ForkJoinSolver(Maze maze, int forkAfter)
    {
        this(maze);
        this.forkAfter = forkAfter;
    }

    @Override
    public List<Integer> compute()
    {
        if(!path_found)
        {
            int player = maze.newPlayer(start);
            frontier.push(start);
            
            while (!frontier.empty()) 
            {
                int current = frontier.pop();

                if (maze.hasGoal(current)) 
                {
                    maze.move(player, current);
                    goal_node = current;
                    path_found = true;
                    break;
                }

                if (!visited.contains(current)) 
                {
                    maze.move(player, current);
                    visited.add(current);

                    for(int nb : maze.neighbors(current)){
                            if(!visited.contains(nb)){
                                frontier.push(nb);
                                predecessor.put(nb, current);
                            }
                        }

                    if(maze.neighbors(current).size() > 1 && !frontier.empty())
                    {
                        ForkJoinSolver sol1 = new ForkJoinSolver(maze);

                        sol1.start = frontier.pop();

                        forks.add(sol1);

                        sol1.fork();
                    } 
                    

                }

            } //end of while loop (frontier is empty)
        
            for( ForkJoinSolver fork: forks)
                {
                    fork.join();
                }
                forks.clear();

        } else //path_found == true
        {}
        
        return pathFromTo(start_node, goal_node);
    }
    
    @Override
    protected List<Integer> pathFromTo(int from, int to) {
        List<Integer> path = new LinkedList<>();
        Integer current = to;
        while (current != from) {
            path.add(current);
            current = predecessor.get(current);
            if (current == null)
                return null;
        }
        path.add(from);
        Collections.reverse(path);
        return path;
    }
}
