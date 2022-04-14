package com.adamgordon;

import java.io.IOException;
import java.util.ArrayList;

public class Snake
{
    Node head;
    Node tail;

    String state;
    Node[] snakeBody;
    boolean otherSnake=false;
    Node[] snakeHeadNeighb;

    double distToApple=-1;
    public void setDistToApple(Node apple)
    {
        int x1=head.x;
        int y1= head.y;

        int x2=apple.x;
        int y2= apple.y;

        this.distToApple=Math.abs(x1-x2)+Math.abs(y1-y2);
    }
    public void drawSankes(Node[] snakeBody, boolean[][]grid)
    {
        for(int i=0; i< snakeBody.length;++i)
        {
            Node curr=snakeBody[i];
            if(i== snakeBody.length-1)
            {
                break;
            }
            Node next=snakeBody[i+1];

            grid[curr.y][curr.x]=true;

            //curr is left of next
            if(curr.x<next.x)
            {
                int x1=curr.x;
                int x2=next.x;

                while(x1!=x2)
                {
                    x1+=1;
                    grid[curr.y][x1]=true;
                }
            }
            //curr is right of next
            if(curr.x>next.x)
            {
                int x1=curr.x;
                int x2=next.x;

                while(x1!=x2)
                {
                    x1-=1;
                    grid[curr.y][x1]=true;

                }
            }
            //curr is above next
            if(curr.y<next.y)
            {
                int y1=curr.y;
                int y2=next.y;

                while(y1!=y2)
                {
                    y1+=1;
                    grid[y1][curr.x]=true;
                }
            }
            //curr is below next
            if(curr.y>next.y)
            {
                int y1=curr.y;
                int y2=next.y;

                while(y1!=y2)
                {
                    y1-=1;
                    grid[y1][curr.x]=true;
                }
            }
        }
    }
    public Snake(String snakeLine, boolean[][] snakes,boolean otherSnake,Node apple) throws IOException {
        if(otherSnake)
        {
            this.otherSnake=true;
        }
        String[] body = snakeLine.split(" ");
        this.state = body[0];
        int headPos=3;
        if(state.equals("invisible"))
        {
            headPos=5;
        }
        //snakebody is length of whole snack-head pos
        snakeBody = new Node[body.length - headPos];

        for (int i = headPos; i < body.length; i++)
        {
            Node curr = new Node(body[i]);
            snakeBody[i - headPos] = curr;
        }

        if (state.equals("dead") || snakeBody.length == 0)
        {
            return;
        }
        drawSankes(snakeBody,snakes);
        head = snakeBody[0];
        tail = snakeBody[snakeBody.length - 1];
        if(!state.equals("dead"))
        {
            setDistToApple(apple);
        }

    }
    public Node getHead()
    {
        return head;
    }

    public Node getGoal(Node apple1, Node apple2,Snake mySnake,ArrayList<Snake>otherSnakes)
    {
        Node goal=new Node(tail);
        if(apple1.x!=-1 && apple1.y!=-1 && apple2.x==-1 && apple2.y==-1)
        {
            return apple1;
        }
        else if (apple2.x!=-1 && apple2.y!=-1 && apple1.x==-1 && apple1.y==-1)
        {
            for(Snake i : otherSnakes)
            {
                if(mySnake.distToApple>=i.distToApple && mySnake.distToApple!=-1 && i.distToApple!=-1)
                {
                    return mySnake.tail;
                }
            }
            return apple2;
        }
        else
        {
            if (head.manhattenDist(apple1) < head.manhattenDist(apple2) )
            {
                return apple1;
            }
            else if (head.manhattenDist(apple1) > head.manhattenDist(apple2))
            {
                return apple2;
            }

        }
        return goal;
    }
}
