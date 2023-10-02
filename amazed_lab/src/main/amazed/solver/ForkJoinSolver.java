package amazed.solver;

import amazed.maze.Maze;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * <code>ForkJoinSolver</code> implements a solver for
 * <code>Maze</code> objects using a fork/join multi-thread
 * depth-first search.
 * <p>
 * Instances of <code>ForkJoinSolver</code> should be run by a
 * <code>ForkJoinPool</code> object.
 */


public class ForkJoinSolver
    extends SequentialSolver
{   
    /**
     * Creates a solver that searches in <code>maze</code> from the
     * start node to a goal.
     *
     * @param maze   the maze to be searched
     */
    public static int stop = 10;
    static int goal_node = 0;
    //public List<Integer> path = new ArrayList<>();
    public int start_node = start;
    static ConcurrentSkipListSet<Integer> visited = new ConcurrentSkipListSet<Integer>();
    static ConcurrentHashMap<Integer,Integer> predecessor = new ConcurrentHashMap<Integer, Integer>();
    List<ForkJoinSolver> forks= new ArrayList<ForkJoinSolver>();
    List<Integer> path_before_fork = new ArrayList<Integer>();
    int new_start = start;
    List<Integer> fork_id = new ArrayList<Integer>(0);
    int fork_counter = 0;
    int goal = 0;
    boolean goal_found = false;

    
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

     public ForkJoinSolver(Maze maze)
    {
        super(maze);
        
    }
 
    /**
     * Creates a solver that searches in <code>maze</code> from the
     * start node to a goal, forking after a given number of visited
     * nodes.
     *
     * @param maze        the maze to be searched
     * @param forkAfter   the number of steps (visited nodes) after
     *                    which a parallel task is forked; if
     *                    <code>forkAfter &lt;= 0</code> the solver never
     *                    forks new tasks
     */
    public ForkJoinSolver(Maze maze, int forkAfter)
    {
        this(maze);
        this.forkAfter = forkAfter;
    }

    /**
     * Searches for and returns the path, as a list of node
     * identifiers, that goes from the start node to a goal node in
     * the maze. If such a path cannot be found (because there are no
     * goals, or all goals are unreacheable), the method returns
     * <code>null</code>.
     *
     * @return   the list of node identifiers from the start node to a
     *           goal node in the maze; <code>null</code> if such a path cannot
     *           be found.
     */
    @Override
    public List<Integer> compute()
    {
            
            int player = maze.newPlayer(new_start);     // start with start node
            frontier.push(new_start);       // as long as not all nodes have been processed
            
            while (!frontier.empty()) 
            {      // get the new node to process
                int current = frontier.pop();
                // if current node has a goal
                if (maze.hasGoal(current)) 
                {
                    // move player to goal
                    maze.move(player, current);
                    // search finished: reconstruct and return path
                    
                    goal_node = current;
                    System.out.printf("Start %s, Goal %s", start, current);
                    break;
                    //return pathFromTo(start, current);
                    //return pathFromTo(start, current);
                }
                // if current node has not been visited yet
                if (visited.add(current)) 
                {
                    // move player to current node
                    maze.move(player, current);
                    // mark node as visited
                    // for every node nb adjacent to current
                
                    int count = 1;
                    for (int nb: maze.neighbors(current)) 
                    {   //System.out.printf("Granne %s", nb);
                        if (!visited.contains(nb)){
                            predecessor.put(nb, current); 
                            if(count == 1 && !visited.contains(nb) )
                            {
                                frontier.push(nb); 
                            }

                            if(count > 1 && !visited.contains(nb))
                            {
                                ForkJoinSolver sol = new ForkJoinSolver(maze);
                                sol.new_start = nb;
                                sol.fork();
                                forks.add(sol);

                            }
                            count = count + 1;
                        }          
                    }
                } 
            }
        
        if(!forks.isEmpty());
        {
            for( ForkJoinSolver fork: forks)
            {   
                fork.join();   
            }
            
        }
        
        return pathFromTo(start, goal_node);
        
    }
}