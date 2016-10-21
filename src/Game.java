import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.applet.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Game extends Canvas implements KeyListener
{
  
  private ArrayList bullets;
  private Image user;
  private BufferStrategy bs;
  
  private boolean up;
  private boolean right;
  private boolean down;
  private boolean left;
  private boolean running;
  private boolean shooting;
  public boolean shot = false;
  
  private int numToShoot;
  private int reload;
  
  private long lastTimeShot = 0;
  
  private Map map;
  public static Entity player;
  public static Bullet bullet;
    
  public Game()
  {
    try
    {
      URL playerPath = Thread.currentThread().getContextClassLoader().getResource("img/player.png");
      if(playerPath == null)
      {
        System.err.println("Unable to find PLAYER sprite");
        System.exit(0);
      }
      user = ImageIO.read(playerPath);
    }
    catch(IOException e)
    {
      System.err.println("Unable to LOAD sprite");
      System.exit(0);
    }
    
    Frame frame = new Frame("Game");
    frame.setLayout(null);
    setBounds(0,0,700,700);
    frame.add(this);
    frame.setSize(700,700);
    frame.setResizable(false);

    frame.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });
    
    frame.addKeyListener(this);
    addKeyListener(this);
    
    frame.setVisible(true);
    
    createBufferStrategy(2);
    bs = getBufferStrategy();
    
    map = new Map();
    player = new Entity(1.5f, 1.5f, 10, 10, 0, user, map);
    
    shooting = false;
    bullet = new Bullet(player.getX(), player.getY(), player.getAngle(), 0, 0);
    bullets = player.getBullets();
    reload = 30;
    numToShoot = 1;
    
    gameLoop();
  }
  
  public void gameLoop()
  {
    boolean Gamerunning = true;
    long lastTime = System.nanoTime();
    
    while(Gamerunning)
    {
      Graphics2D g = (Graphics2D) bs.getDrawGraphics();
      
      g.setColor(Color.black);
      g.fillRect(0,0,700,700);
      
      g.translate(50,60);
      map.paint(g);
      player.paint(g);
      bullet.paint(g);
      
      g.dispose();
      bs.show();
      
      try { Thread.sleep(4); } catch (Exception e) {};
      
      long delta = (System.nanoTime() - lastTime) / 1000000;
      lastTime = System.nanoTime();
      
      for(int i=0; i<delta / 5; i++)
      {
        logic(5);
      }
      
      if((delta % 5) != 0)
      {
        logic(delta % 5);
      }
    }
  }
      
  public void logic(long delta)
  {
    float dx = 0;
    float dy = 0;
    
    if(up)
    {
      dy -= 1;
    }
    if(right)
    {
      dx += 1;
    }
    if(down)
    {
      dy += 1;
    }
    if(left)
    {
      dx -= 1;
    }
    if(shooting)
    {
      if(System.currentTimeMillis() - lastTimeShot > 200)
      {
        player.shoot(1, 1);
        shot = true;
        lastTimeShot = System.currentTimeMillis();
      }
    }
    
    if((dx != 0) || (dy != 0))
    {
      if(running == true)
      {
        player.move(dx * delta * 0.006f, dy * delta *0.006f);
      }
      else
      {
        player.move(dx * delta * 0.003f, dy * delta *0.003f);
      }
    }
    /*if(shot)
    {*/
      ArrayList tmpBullets = player.getBullets();
      for(int i = 0; i < tmpBullets.size(); i++)
      {
        Bullet tmpBullet = (Bullet) tmpBullets.get(i);
        tmpBullet.moveForward(3);
        /*if(map.validLocation(tmpBullet.getX(), tmpBullet.getY()))
        {
          shot = false;
        }*/
      }
    /*}*/
  }
  
  public void keyTyped(KeyEvent e)
  {
  }
      
  public void keyPressed(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_UP) 
    {
      up = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) 
    {
      left = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) 
    {
      down = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
    {
      right = true;
    }
    if (e.isShiftDown() == true)
    {
      running = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_S)
    {
      shooting = true;
    }
  }
  
  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_UP) 
    {
      up = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) 
    {
      left = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) 
    {
      down = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
    {
      right = false;
    }
    if (e.isShiftDown() == false)
    {
      running = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_S)
    {
      shooting = false;
    }
  }
  
  public static void main(String[] args)
  {
    new Game();
  }
}
