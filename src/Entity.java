import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Entity
{
  public float x;
  public float y;
  public float angle;
  private int reloadTime;
  private int width;
  private int height;
  
  private ArrayList bullets;
  
  private Image person;
  private Map map;

  public Entity(float x, float y, int width, int height, float angle, Image person, Map map)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.angle = angle;
    this.person = person;
    this.map = map;
    
    bullets = new ArrayList();
    reloadTime = 0;
  }
  
  public float getX()
  {
    return x;
  }
  
  public float getY()
  {
    return y;
  }
  
  public float getAngle()
  {
    return angle;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public ArrayList getBullets()
  {
    return bullets;
  }

  public void setX(float x)
  {
    this.x = x;
  }
  
  public void setY(float y)
  {
    this.y = y;
  }

  public void setAngle(float angle)
  {
    this.angle = angle;
  }
  
  public boolean move(float dx, float dy)
  {
    float nx = x + dx;
    float ny = y + dy;
    
    if(map.validLocation(nx, ny))
    {    
      x = nx;
      y = ny;
      
      angle = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
      return true;
    }
    return false;
  }
  
  public void paint(Graphics2D p)
  {
    int xp = (int) (map.TILE_SIZE * x);
    int yp = (int) (map.TILE_SIZE * y);
            
    p.rotate(angle, xp, yp);
    p.drawImage(person, (int) (xp - 10), (int) (yp - 10), null);
    p.rotate(-angle, xp, yp);
  }
  
  public void shoot(int load, int amount)
  {
    if(reloadTime == 0)
    {
      for(int i=0; i < amount; i++)
      {
        Game.bullet.setX(((int)(map.TILE_SIZE * x)));
        Game.bullet.setY(((int)(map.TILE_SIZE * y)));
        Game.bullet.setAngle((float) (Game.player.getAngle() + (Math.PI / 2)));
        Game.bullet.setWidth(5);
        Game.bullet.setHeight(5);
        
        bullets.add(new Bullet(Game.bullet.getX(), Game.bullet.getY(), Game.bullet.getAngle(), Game.bullet.getWidth(), Game.bullet.getHeight()));
      }
      reloadTime = load;
    }
    else
    {
      reloadTime -= 1;
    }
  }
}
