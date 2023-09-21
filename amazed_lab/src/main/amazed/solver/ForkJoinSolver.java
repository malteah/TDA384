package amazed.solver;

import amazed.maze.Maze;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


public class ForkJoinSolver
    extends SequentialSolver
{   

    public static int stop = 10;
    public static List<Integer> path = new ArrayList<>();
    public int start_node = start;
    public static int goal_node;
    private int fork_no = 0;
    static ConcurrentSkipListSet<Integer> visited = new ConcurrentSkipListSet<Integer>();
    static ConcurrentHashMap<Integer,Integer> precedessor = new ConcurrentHashMap<Integer, Integer>();
    List<ForkJoinSolver> forks= new ArrayList<ForkJoinSolver>();
    private boolean move = false;
    

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

                    List<Integer> nb_list = new ArrayList<>(maze.neighbors(current));

                    if(maze.neighbors(current).size() == 1)
                    {
                        frontier.push(nb_list.get(0));
                        if (!visited.contains(nb_list.get(0)))
                        {
                            predecessor.put(nb_list.get(0), current);
                        }
                    }

                    else if(maze.neighbors(current).size() == 2)
                    {
                        frontier.push(nb_list.get(0));
                        frontier.push(nb_list.get(1));

                        if (!visited.contains(nb_list.get(0)))
                        {
                            predecessor.put(nb_list.get(0), current);
                        }

                        if (!visited.contains(nb_list.get(1)))
                        {
                            predecessor.put(nb_list.get(1), current);
                        }

                        ForkJoinSolver sol1 = new ForkJoinSolver(maze);

                        sol1.start = frontier.pop();
                        forks.add(sol1);
                        sol1.fork();
                    } 
                    
                    else if(maze.neighbors(current).size() == 3)
                    {
                        frontier.push(nb_list.get(0));
                        frontier.push(nb_list.get(1));
                        frontier.push(nb_list.get(2));

                        if (!visited.contains(nb_list.get(0)))
                        {
                            predecessor.put(nb_list.get(0), current);
                        }

                        if (!visited.contains(nb_list.get(1)))
                        {
                            predecessor.put(nb_list.get(1), current);
                        }

                        if (!visited.contains(nb_list.get(2)))
                        {
                            predecessor.put(nb_list.get(2), current);
                        }

                        ForkJoinSolver sol1 = new ForkJoinSolver(maze);
                        ForkJoinSolver sol2 = new ForkJoinSolver(maze);
 
                        sol1.start = frontier.pop();
                        sol2.start = frontier.pop();

                        forks.add(sol1);
                        forks.add(sol2);

                        sol1.fork();
                        sol2.fork();
                    }

                    else if(maze.neighbors(current).size() == 4)
                    {
                        frontier.push(nb_list.get(0));
                        frontier.push(nb_list.get(1));
                        frontier.push(nb_list.get(2));
                        frontier.push(nb_list.get(3));

                        if (!visited.contains(nb_list.get(0)))
                        {
                            predecessor.put(nb_list.get(0), current);
                        }

                        if (!visited.contains(nb_list.get(1)))
                        {
                            predecessor.put(nb_list.get(1), current);
                        }

                        if (!visited.contains(nb_list.get(2)))
                        {
                            predecessor.put(nb_list.get(2), current);
                        }

                        if (!visited.contains(nb_list.get(3)))
                        {
                            predecessor.put(nb_list.get(3), current);
                        }

                        ForkJoinSolver sol1 = new ForkJoinSolver(maze);
                        ForkJoinSolver sol2 = new ForkJoinSolver(maze);
                        ForkJoinSolver sol3 = new ForkJoinSolver(maze);
 
                        sol1.start = frontier.pop();
                        sol2.start = frontier.pop();
                        sol3.start = frontier.pop();

                        forks.add(sol1);
                        forks.add(sol2);
                        forks.add(sol3);

                        sol1.fork();
                        sol2.fork();
                        sol3.fork();
                    }

                }
            }
            
            if(forks.size() != 0);
            {
                for( ForkJoinSolver fork: forks)
                {
                    fork.join();
                }
            }
        } else //path_found == true
        {   
            return pathFromTo(start_node, goal_node);
        }
        
        return null;
    }
}
