package com.zero.maze.entity;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private static final long serialVersionUID = 8560966501052956230L;
    
    private int x;
    private int y;
    
    public Coordinate() {
        
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate))  {
            return false;
        }
        
        Coordinate another = (Coordinate) obj;
        return this.x == another.getX() && this.y == another.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
