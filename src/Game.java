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
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Game extends Canvas implements KeyListener
{
	private static final long serialVersionUID = 1L;
    private Image user;
    private BufferStrategy bs;

    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;
    private boolean running;
    private boolean shooting;
    static boolean Gamerunning = true;

    private long lastTimeShot = 0;
  
    static Map map;
    static Entity player;
    static Bullet bullet;
    static Enemy enemy;
    
    private Game()
    {
        try
        {
            URL playerPath = Thread.currentThread().getContextClassLoader().getResource("img/player.png");
            if(playerPath == null) {
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
        setBounds(0,0,700,750);
        frame.add(this);
        frame.setSize(700,750);
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
        player = new Entity(1.5f, 1.5f, 0, user, map, 50);
        spawnEnemies(4); //will change amount according to stage
        shooting = false;
        bullet = new Bullet(player.getX(), player.getY(), player.getAngle(), 0, 0, false);

        gameLoop();
    }

    private void gameLoop()
    {
        long lastTime = System.nanoTime();

        while(Gamerunning)
        {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            
            g.setColor(Color.black);
            g.fillRect(0,0,700,750);
            
            g.translate(50,60);
            map.paint(g);
            player.paint(g);
            bullet.paint(g);
            enemy.paint(g);
            
            g.dispose();
            bs.show();
            
            try { Thread.sleep(4); } catch (Exception e) {};
            
            long delta = (System.nanoTime() - lastTime) / 1000000;
            lastTime = System.nanoTime();
            
            for(int i=0; i<delta / 5; i++)
                logic(5);
            
            if((delta % 5) != 0)
                logic(delta % 5);   
        }
    }
        
    private void logic(long delta)
    {
        float dx = 0;
        float dy = 0;

        if(up)
            dy -= 1;
        if(right)
            dx += 1;
        if(down)
            dy += 1;
        if(left)
            dx -= 1;
        if(shooting) {
            if(System.currentTimeMillis() - lastTimeShot > 500) {
                player.shoot( 1);
                lastTimeShot = System.currentTimeMillis();
            }
        }

        if((dx != 0) || (dy != 0)) {
            if(running == true)
                player.move(dx * delta * 0.006f, dy * delta *0.006f);
            else
                player.move(dx * delta * 0.003f, dy * delta *0.003f);
        }
        
        ArrayList<Bullet> tmpBullets = player.getBullets();
        for(int i = 0; i < tmpBullets.size(); i++) {
            Bullet tmpBullet = (Bullet) tmpBullets.get(i);
            tmpBullet.moveForward(3);
        }
        ArrayList<Enemy> tmpEnemies = map.getEnemies();
            for(int i = 0; i < tmpEnemies.size(); i++) {
                Enemy en = (Enemy) tmpEnemies.get(i);
                en.move();
            }
    }

    private void spawnEnemies(int num)
    {
        for(int i = 0; i < num; i++) {
            enemy = new Enemy(10.5f + i*2, 10.5f + i*2, 0, user, map, 10, false);
            map.addEnemy(enemy);
        }
    }

    public void keyTyped(KeyEvent e) {}
        
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = true;
        if (e.isShiftDown())
            running = true;
        if (e.getKeyCode() == KeyEvent.VK_S)
            shooting = true;
    }

    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            up = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = false;
        if (!e.isShiftDown())
            running = false;
        if (e.getKeyCode() == KeyEvent.VK_S)
            shooting = false;
    }

    public static void main(String[] args)
    {
        new Game();
    }
}
