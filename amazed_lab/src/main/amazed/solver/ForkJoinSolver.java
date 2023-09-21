package amazed.solver;

import amazed.maze.Maze;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
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
    public static List<Integer> path = new ArrayList<>();
    public int start_node = start;
    private int fork_no = 0;
    static ConcurrentSkipListSet<Integer> visited = new ConcurrentSkipListSet<Integer>();
    static ConcurrentHashMap<Integer,Integer> precedessor = new ConcurrentHashMap<Integer, Integer>();
    

     public ForkJoinSolver(Maze maze)
    {
        super(maze);
        
    }
    static boolean path_found = false;
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
        if(!path_found)
        {
            System.out.printf("Start: %s\n" , start);
            System.out.printf("Framfor: %s\n", frontier);
            System.out.printf("Fork number: %s \n", fork_no);
           
            int player = maze.newPlayer(start);     // start with start node
            frontier.push(start);       // as long as not all nodes have been processed
            
            while (!frontier.empty()) 
            {        // get the new node to process
                int current = frontier.pop();
                // if current node has a goal
                if (maze.hasGoal(current)) 
                {
                    // move player to goal
                    maze.move(player, current);
                    // search finished: reconstruct and return path
                    return pathFromTo(start, current);
                }
                // if current node has not been visited yet
                if (!visited.contains(current)) 
                {
                    // move player to current node
                    maze.move(player, current);
                    // mark node as visited
                    visited.add(current);
                    // for every node nb adjacent to current
                    
                    System.out.printf("Grannar %s \n \n", maze.neighbors(current));
                    
                    int count = 0;
                    for (int nb: maze.neighbors(current)) 
                    {

                        if (!visited.contains(nb))
                        {
                            predecessor.put(nb, current);
                        }
                        frontier.push(nb);
                        ForkJoinSolver sol = new ForkJoinSolver(maze);

                        // add nb to the nodes to be processed
                        
                        // if nb has not been already visited,
                        // nb can be reached from current (i.e., current is nb's predecessor)
                        if (count == 0)
                        {
                            //frontier.push(nb);
                            System.out.printf("Framför: %s\n", frontier);
                            System.out.println("Lagt till i frontier");
                        }

                        else if (count == 1)
                        {
                            sol.start = frontier.pop();
                            sol.fork();

                            
                            
                        }

                        else if (count == 2)
                        {
                            sol.start = frontier.pop();
                            sol.fork();

                        }

                        // else if (count == 3)
                        // {
                        //     sol.fork();
                        //     //sol.compute();
                        //     sol.join();

                        
                        count = count + 1;    
                        
                    }   
                } 
            }
            return path;
        }else
        {
            return null;
        }
        
    }
}

    
//     private List<Integer> parallelSearch()
//     {
 
        
//         // one player active on the maze at start
//         int player = maze.newPlayer(start);
//         // start with start node
//         frontier.push(start);
//         // as long as not all nodes have been processed
//         while (!frontier.empty()) {
//             // get the new node to process
//             int current = frontier.pop();
//             // if current node has a goal
//             if (maze.hasGoal(current)) {
//                 // move player to goal
//                 maze.move(player, current);
//                 // search finished: reconstruct and return path
//                 return pathFromTo(start, current);
//             }
//             // if current node has not been visited yet
//             if (!visited.contains(current)) {
//                 // move player to current node
//                 maze.move(player, current);
//                 // mark node as visited
//                 visited.add(current);
//                 // for every node nb adjacent to current
//                 for (int nb: maze.neighbors(current)) {
//                     // add nb to the nodes to be processed
//                     frontier.push(nb);
//                     // if nb has not been already visited,
//                     // nb can be reached from current (i.e., current is nb's predecessor)
//                     if (!visited.contains(nb))
//                         predecessor.put(nb, current);
//                 }
//             }
//         }
//         // all nodes explored, no goal found
//         return null;
//     }



// }