
package com.adamgordon;

public class Node
{
    int x;
    int y;

    //default go straight
    int move = 5;
    Node parent;

    public Node(int y, int x)
    {
        this.x= x;
        this.y =y ;
    }
    public Node(int y, int x, int move)
    {
        this.x= x;
        this.y =y ;
        this.move=move;
    }

    public Node(Node point) {
        this.x=point.x;
        this.y=point.y;
    }

    public Node(String point)
    {
        String[] points = point.split(",");
        this.x= Integer.parseInt(points[0]);
        this.y = Integer.parseInt(points[1]);
    }

    public Node(String[] coordString)
    {
        this.x = Integer.parseInt(coordString[0]);
        this.y = Integer.parseInt(coordString[1]);
    }

    public double manhattenDist(Node node)
    {
        //manhatten distnace
        int x1=this.x;
        int y1=this.y;

        int x2=node.x;
        int y2=node.y;

        double dist= Math.abs(x1-x2)+Math.abs(y1-y2);
        return dist;
    }

    public boolean equals(Node node)
    {

        if(this.x==node.x && this.y==node.y)
        {
            return true;
        }
        return false;
    }

}
