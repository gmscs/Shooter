import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Enemy
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

  public Enemy(float x, float y, int width, int height, float angle, Image person, Map map)
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
  
  public void move()
  {
    double dx = Game.player.getX() - x;
    double dy = Game.player.getY() - y;
    angle = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
    if(dx > 1 || dx < -1 || dy > 1 || dy < -1)
    {
      float direction = (float) (Math.atan2(dy, dx));
      float speed = 0.009f;
      x += speed * Math.cos(direction);
      y += speed * Math.sin(direction);
    }
  }
  
  public void paint(Graphics2D p)
  {
    int xp = (int) (map.TILE_SIZE * x);
    int yp = (int) (map.TILE_SIZE * y);
            
    p.rotate(angle, xp, yp);
    p.drawImage(person, (int) (xp - 10), (int) (yp - 10), null);
    p.rotate(-angle, xp, yp);
  }
}
