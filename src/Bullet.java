import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

class Bullet
{
	private float x;
	private float y;
	private float angle;
	private int width;
	private int height;
	private boolean hit;

	Bullet(float x, float y, float angle, int width, int height, boolean hit)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.width = width;
		this.height = height;
		this.hit = hit;
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

	int getWidth()
	{
		return width;
	}

	int getHeight()
	{
		return height;
	}

	boolean getHit()
	{
		return hit;
	}

	void setX(float x)
	{
		this.x = x;
	}

	void setY(float y)
	{
		this.y = y;
	}

	void setAngle(float angle)
	{
		this.angle = angle;
	}

	void setWidth(int width)
	{
		this.width = width;
	}

	void setHeight(int height)
	{
		this.height = height;
	}

	void setHit(boolean hit)
	{
		this.hit = hit;
	}

	void paint(Graphics2D g)
	{
		ArrayList<Bullet> bullets = Game.player.getBullets();
		for(int i = 0; i < bullets.size(); i++) {
			Bullet tmpBullet = (Bullet) bullets.get(i);
			if(!tmpBullet.getHit()) {
				g.setColor(new Color(255, 255, 0));
				g.fillRect((int) tmpBullet.getX(), (int) tmpBullet.getY(), tmpBullet.getWidth(), tmpBullet.getHeight());
			}
			else 
				bullets.remove(tmpBullet);
		}
	}

	void moveForward(int speed)
	{
		float tmpx = x;
		float tmpy = y;
		double dx = Math.floor(Game.enemy.getX()*Game.map.TILE_SIZE) - Math.floor(x);
		double dy = Math.floor(Game.enemy.getY()*Game.map.TILE_SIZE) - Math.floor(y);
		tmpx += Math.cos(angle)*speed;
		tmpy += Math.sin(angle)*speed;
		if(!Game.map.blocked(tmpx/Game.map.TILE_SIZE, tmpy/Game.map.TILE_SIZE)) {
		  	x = tmpx;
		  	y = tmpy;
			if(Game.map.getEnemies().size() > 0) {
				if((dx <= 1 && dy <= 1) || (dx >= 1 && dy >= -1)) { //IM HERE
					this.setHit(true);
					Game.enemy.setHealth(Game.enemy.getHealth() - 2);
					System.out.println(Game.enemy.getHealth());
					if (Game.enemy.getHealth() == 0)
						Game.enemy.setHit(true);
				}
			}
		}
		else 
			this.setHit(true);
	}
}
