import java.awt.Graphics2D;
import java.awt.Image;

class Enemy
{
	private float x;
	private float y;
	private float angle;
	private boolean hit;
	private int health;

	private Image person;
	private Map map;

	Enemy(float x, float y, float angle, Image person, Map map, int health, boolean hit)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.person = person;
		this.map = map;
		this.health = health;
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
  
	int getHealth()
	{
		return health;
	}

	boolean getHit()
	{
		return hit;
	}	

	void setHealth(int health)
	{
		this.health = health;
	}	

	void setHit(boolean hit)
	{
		this.hit = hit;
	}
  
	void move()
	{
		double dx = Game.player.getX() - x;
		double dy = Game.player.getY() - y;
		angle = (float) (Math.atan2(dy, dx) - (Math.PI / 2));
		if(dx > 1 || dx < -1 || dy > 1 || dy < -1)
		{
			float direction = (float) (Math.atan2(dy, dx));
			float speed = 0.009f;
			double nx = x + speed * Math.cos(direction);
			double ny = y + speed * Math.sin(direction);
			if(!map.blocked((float) nx, (float) ny)) {   
				x = (float)nx;
				y = (float)ny;
			}
		}
		else {
            Game.player.setHealth(Game.player.getHealth() - 5);
		}
	}

	void paint(Graphics2D m)
	{
		for(int i = 0; i < Game.map.getEnemies().size(); i++) {
			Enemy en = (Enemy) Game.map.getEnemies().get(i);
			if(!en.getHit()) {
				int xp = (int) (map.TILE_SIZE * en.getX());
				int yp = (int) (map.TILE_SIZE * en.getY());
			
				m.rotate(angle, xp, yp);
				m.drawImage(person, (int) (xp - 10), (int) (yp - 10), null);
				m.rotate(-angle, xp, yp);
			}
			else
				Game.map.getEnemies().remove(en);
		}
	}
}
