import java.awt.Color;
import java.awt.Graphics2D;

import java.util.Random;

public class Map
{
  public static final int WIDTH = 30;
  public static final int HEIGHT = 30;
  
  public static final int TILE_SIZE = 20;

  private static final int FLOOR = 0;
  private static final int WALL = 1;
  private static final int BOX = 2;
  private static final int GRASS = 3;

  private static final int MAXBOX = 15; //should be changed
  private static final int MAXGRASS = 10; //change as well
  
  private int[][] data = new int[WIDTH][HEIGHT];
  private float size = 0.3f;
  
  public Map()
  {
    for (int i=0; i<HEIGHT; i++) 
    {
        data[0][i] = WALL;
        data[WIDTH-1][i] = WALL;
        data[i][0] = WALL;
        data[i][HEIGHT-1] = WALL;
    }
    
    for(int j=0; j<MAXBOX; j++)
    {
      int rx = (int)(28 * Math.random()) + 1;
      int ry = (int)(28 * Math.random()) + 1;
    
      data[rx][ry] = BOX;
    }
    
    for(int k=0; k<MAXGRASS; k++)
    {
      int rx = (int)(28 * Math.random()) + 1;
      int ry = (int)(28 * Math.random()) + 1;
    
      data[rx][ry] = GRASS;
    }
  }
  
  public void paint(Graphics2D m)
  {
    for(int x=0; x<WIDTH; x++)
    {
      for(int y=0; y<HEIGHT; y++)
      {
        m.setColor(Color.darkGray);
        if(data[x][y] == WALL)
        {
          m.setColor(Color.gray);
        }
        
        if(data[x][y] == BOX)
        {
          m.setColor(new Color(133, 94, 66));
        }
        
        if(data[x][y] == GRASS)
        {
          m.setColor(new Color(34, 139, 34));
        }
        
        m.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
        m.setColor(m.getColor().darker());
        m.drawRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }
    }
  }

  public boolean blocked(float x, float y)
  {
    return 
      data[(int) x][(int) y] == WALL || 
      data[(int) x][(int) y] == BOX;
  }

  public boolean validLocation(float nx, float ny)
  {
    if (blocked(nx - size, ny - size)) 
    {
      return false;
    }
    if (blocked(nx + size, ny - size)) 
    {
      return false;
    }
    if (blocked(nx - size, ny + size)) 
    {
      return false;
    }
    if (blocked(nx + size, ny + size)) 
    {
      return false;
    }
    return true;
  }
}
