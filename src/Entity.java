import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Entity
{
	private float x;
	private float y;
	private float angle;
	private int health;

	private ArrayList<Bullet> bullets;

	private Image person;
	private Map map;

	Entity(float x, float y, float angle, Image person, Map map, int health)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.person = person;
		this.map = map;
		this.health = health;

		bullets = new ArrayList<Bullet>();
	}

	float getX()
	{
		return x;
	}

	float getY()
	{
		return y;
	}

	float getAngle()
	{
		return angle;
	}

	ArrayList<Bullet> getBullets()
	{
		return bullets;
	}
	
	int getHealth()
	{
        return health;
	}

	void setHealth(int health)
	{
        this.health = health;
	}

	void move(float dx, float dy)
	{
		float nx = x + dx;
		float ny = y + dy;

		if(!map.blocked(nx, ny)) {    
			x = nx;
			y = ny;

			angle = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
		}
	}

	void paint(Graphics2D p)
	{
		int xp = (int) (map.TILE_SIZE * x);
		int yp = (int) (map.TILE_SIZE * y);
				
		p.rotate(angle, xp, yp);
		p.drawImage(person, (int) (xp - 10), (int) (yp - 10), null);
		p.rotate(-angle, xp, yp);
	}

	void shoot(int amount)
	{
		for(int i=0; i < amount; i++) {
			Game.bullet.setX(((int)(map.TILE_SIZE * x)));
			Game.bullet.setY(((int)(map.TILE_SIZE * y)));
			Game.bullet.setAngle((float) (Game.player.getAngle() + (Math.PI / 2)));
			Game.bullet.setWidth(5);
			Game.bullet.setHeight(5);

			bullets.add(new Bullet(Game.bullet.getX(), Game.bullet.getY(), Game.bullet.getAngle(), Game.bullet.getWidth(), Game.bullet.getHeight(), Game.bullet.getHit()));
		}
	}
}
