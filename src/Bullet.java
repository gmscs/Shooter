import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;

public class Bullet
{
  private float x;
  private float y;
  private float angle;
  private int width;
  private int height;
  public boolean hit;
  
  private Game game;
  
  public Bullet(float x, float y, float angle, int width, int height, boolean hit)
  {
    this.x = x;
    this.y = y;
    this.angle = angle;
    this.width = width;
    this.height = height;
    this.hit = hit;
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
  
  public boolean getHit()
  {
    return hit;
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
  
  public void setWidth(int width)
  {
    this.width = width;
  }
  
  public void setHeight(int height)
  {
    this.height = height;
  }
  
  public void setHit(boolean hit)
  {
    this.hit = hit;
  }

  public void paint(Graphics2D g)
  {
    ArrayList bullets = Game.player.getBullets();
    for(int i = 0; i < bullets.size(); i++)
    {
      Bullet tmpBullet = (Bullet) bullets.get(i);
      if(tmpBullet.getHit() == false)
      {
	g.setColor(new Color(255, 255, 0));
	g.fillRect((int) tmpBullet.getX(), (int) tmpBullet.getY(), tmpBullet.getWidth(), tmpBullet.getHeight());
      }
      else 
      {
	bullets.remove(tmpBullet);
      }
    }
  }

  public void moveForward(int speed)
  {
    float tmpx = x;
    float tmpy = y;
    tmpx += Math.cos(angle)*speed;
    tmpy += Math.sin(angle)*speed;
    
    if(!Game.map.blocked(tmpx/Game.map.TILE_SIZE, tmpy/Game.map.TILE_SIZE)) {
      x = tmpx;
      y = tmpy;
    }
    else 
    {
      this.setHit(true);
    }
  }
}