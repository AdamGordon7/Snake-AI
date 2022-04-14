
package com.adamgordon;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent
{
    Snake mySnake = null;
    Snake[] snakeGloabl;
    public static void main(String[] args)
    {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    public boolean checkNode(Node neighbour, boolean[][] snakePos, boolean[][] visited)
    {
        if(neighbour.y < 0 || neighbour.y == 50 || neighbour.x < 0 || neighbour.x == 50 )
        {
            return false;
        }

        return !snakePos[neighbour.y][neighbour.x] && !visited[neighbour.y][neighbour.x];
    }

    public Node[] Neighbours(Node top)
    {
        return new Node[]{new Node((top.y-1),(top.x),0),new Node((top.y+1),(top.x),1),new Node((top.y),(top.x-1),2),new Node((top.y),(top.x+1),3)};
    }

    public int getMove(Snake mySnake, Node goal, boolean[][] snakePos)
    {
        Queue<Node> points = new LinkedList<>();
        boolean[][] visited = new boolean[50][50];

        Node head = mySnake.getHead();
        Node start = new Node(head);
        Node end = new Node(goal);
        points.add(start);

        Node next = start;

        while (!points.isEmpty())
        {
            Node top = points.poll();

            if (top.manhattenDist(goal) <= next.manhattenDist(goal))
            {
                next = top;
            }

            if (top.equals(end))
            {
                while (top.parent != null && !top.parent.equals(start))
                {
                    top = top.parent;
                }
                return top.move;
            }

            Node[]neighbours=Neighbours(top);
            for(Node neighbour: neighbours)
            {
                if(checkNode(neighbour,snakePos,visited))
                {
                    points.add(neighbour);
                    neighbour.parent = top;
                    visited[neighbour.y][neighbour.x] = true;
                }
            }

        }
        while (next.parent != null && !next.parent.equals(start))
        {
            next = next.parent;
        }

        return next.move;
    }

    @Override
    public void run()
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);

            while (true)
            {
                ArrayList<Node> otherSnakeHeadNeighb=new ArrayList<>();
                boolean[][] snakePos = new boolean[50][50];
                ArrayList<Snake>snakes=new ArrayList<>();
                String line = br.readLine();
                if (line.contains("Game Over"))
                    break;

                String apple1=line;
                String apple2 = br.readLine();
                // do stuff with apples

                int mySnakeNum = Integer.parseInt(br.readLine());
                for (int i = 0; i < nSnakes; i++)
                {
                    Node app2 = new Node(apple2.split(" "));
                    String snakeLine = br.readLine();
                    Snake curr = new Snake(snakeLine, snakePos,true,app2);
                    if (i == mySnakeNum)
                    {
                        mySnake = curr;
                        mySnake.otherSnake=false;
                    }
                    else
                    {
                        snakes.add(curr);
                    }
                }
                Node app1 = new Node(apple1.split(" "));
                Node app2 = new Node(apple2.split(" "));

                System.out.println(getMove(mySnake, mySnake.getGoal(app1,app2,mySnake,snakes), snakePos));


            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
