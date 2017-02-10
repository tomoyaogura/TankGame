import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D;
import java.util.Enumeration;


public class AITank extends Tank 
{
	/*
	 *  ASSUMES Player is 1
	 */
	private Tank target;
	private Point2D duplicatePoint;
	private Point2D retreatPoint;
	private Line2D bestRoute;
	private Line2D currentRoute;
	private Line2D futureRoute;
	private boolean setRetreat;
	private int status;
	private int phase;
	
	private static final int DONE = 1;
	private static final int NOT_DONE = 2;
	private static final int FIRST_PHASE = 1;
	private static final int SECOND_PHASE = 2;
	private static final int THIRD_PHASE = 3;
	
	private static final int AGGRESSIVE_CHASE = 1; // Aggressive fire
	private static final int MODERATE_CHASE = 2; // Moderate fire
	private static final int SLOW_CHASE = 3; // Stationary fire
	private static final int EVADE = 4; // Flee
	private static final int SEARCH = 2; // Search
	private static final int ITEM = 3; // Find item
	private static final int FLEE = 5; // Health
	private static final int WAIT = 6;
	private static final int GO_TO = 7;
	
	private static double MAX_SPEED = 1.5;

	private static final double MIN_DISTANCE = 60;
	private static final double MIN_SHOOT_DISTANCE = 50;
	
	private static Point2D debugPoint;
	private static Line2D debugLine;
	private static boolean first;
	
	private static Bullet IMAGINARY_BULLET;
	
	private boolean shot;
	
	public AITank(double positionX, double positionY, int playerID) 
	{
		super(positionX, positionY, playerID);
		target = ((MachineInfo)data.get(getPlayerNum())).getOther();
		bestRoute = new Line2D.Double(getPosX(), getPosY(), getPosX(), getPosY());
		futureRoute = bestRoute;
		status = SEARCH;
		shot = false;
		IMAGINARY_BULLET = new TargetBullet(getPosX(),getPosY(),target);
		retreatPoint = new Point2D.Double(getPosX(), getPosY());
		setRetreat = false;
		phase = FIRST_PHASE;
		duplicatePoint = new Point2D.Double(getPosX(), getPosY());
		
		debugPoint =  new Point2D.Double(150, getPosY()-100);
		debugLine = new Line2D.Double(getPosX(),getPosY(),getPosX(),getPosY());
		first = true;
	}
	
	public Line2D findDirectSight(Actor other)
	{
		Line2D sight = new Line2D.Double(getCenterX(), getCenterY(), other.getCenterX(), other.getCenterY());
		return sight;	
	}
	
	public Line2D findDirectSight(Point2D other)
	{
		Line2D sight = new Line2D.Double(getCenterX(), getCenterY(), other.getX(), other.getY());
		return sight;	
	}
		
	public boolean hasClearPath(Line2D test)
	{
		boolean targetVisible = true;
		Line2D path = test;
		
		Obstacle temp;
		Enumeration<Actor> obsElements = ((MapInfo)data.get(TankFrame.MAP)).getHashtable().elements();
		while(obsElements.hasMoreElements())
		{
			temp = (Obstacle) obsElements.nextElement();
			if(temp.getBounds().intersectsLine(path))
			{
				targetVisible = false;
				break;
			}
			else
				continue;
		}
		return targetVisible;
	}
	
	public void handleXCollision()
	{
		if(getVelY() > 0)
			setVelY(MAX_SPEED);
		else if(getVelY() < 0)
			setVelY(-MAX_SPEED);
		setVelX(0);
		movePos();
	}
	
	public void handleYCollision()
	{
		if(getVelX() > 0)
			setVelX(MAX_SPEED);
		else if(getVelX() < 0)
			setVelX(-MAX_SPEED);
		setVelY(0);
		movePos();
	}
	
	public Point getLeftPoint()
	{
		return new Point((int)Math.round(getPosX() - getWidth()/2) , (int)Math.round(getPosY()));
	}
	public Point getRightPoint()
	{
		return new Point((int)Math.round(getPosX() + getWidth()/2) , (int)Math.round(getPosY()));
	}
	
	public void defensive_act()
	{
		/*
		 * Find the closet Bullet
		 */
		Bullet closestBullet = IMAGINARY_BULLET;
	//	Bullet secondClosestBullet = IMAGINARY_BULLET;
		
		Actor machines;
		Enumeration<Actor> opponent = ((MachineInfo)data.get(TankFrame.PLAYER_1)).getHashtable().elements();
		while(opponent.hasMoreElements())
		{
			machines = (Actor) opponent.nextElement();
			if(machines instanceof Bullet)
			{
				if(machines.getPoint().distance(getPoint()) < MIN_SHOOT_DISTANCE)
				{
					closestBullet = (Bullet)machines;
					Line2D aim = new Line2D.Double(getCenterX(), getCenterY(), closestBullet.getPosX(), closestBullet.getPosY() );
					System.out.println(aim.getP1().distance(aim.getP2()));
					if(hasClearPath(aim) && aim.getP1().distance(aim.getP2()) < MIN_SHOOT_DISTANCE)
						aimAndShoot(aim);
					
				}
			/*	if(machines.getPoint().distance(getPoint()) > secondClosestBullet.getPoint().distance(getPoint()) 
					&& machines.getPoint().distance(getPoint()) < closestBullet.getPoint().distance(getPoint()))
					secondClosestBullet = (Bullet)machines;*/
			}
		}
		
		
		/*
		 *  Find the line
		 */
		/*if(!closestBullet.equals(IMAGINARY_BULLET))
		{
			Line2D bulletPath = new Line2D(getLeftPoint(), closestBullet.getPoint());
			Line2D aim = new Line2D(getCenterX(), getCenterY(), (bulletPath.getX2()+bulletPath.getX1())/2,(bulletPath.getY2()+bulletPath.getY1())/2 );
			if(hasClearPath(aim) && aim.getP1().distance(aim.getP2()) < MIN_SHOOT_DISTANCE)
				aimAndShoot(aim);
			/*bulletPath = new Line2D(getRightPoint(), closestBullet.getPoint());
			aim = new Line2D(posX, posY, (bulletPath.getX2()+bulletPath.getX1())/2,(bulletPath.getY2()+bulletPath.getY1())/2 );
			if(hasClearPath(aim))
				aimAndShoot(aim);
			bulletPath = new Line2D(getPoint(), closestBullet.getPoint());
			aim = new Line2D(posX, posY, (bulletPath.getX2()+bulletPath.getX1())/2,(bulletPath.getY2()+bulletPath.getY1())/2 );
			if(hasClearPath(aim))
				aimAndShoot(aim);*/
			
		//}
	/*	if(!secondClosestBullet.equals(IMAGINARY_BULLET))
		{
			Line2D bulletPath = new Line2D(getLeftPoint(), secondClosestBullet.getPoint());
			Line2D aim = new Line2D(posX, posY, (bulletPath.getX2()+bulletPath.getX1())/2,(bulletPath.getY2()+bulletPath.getY1())/2 );
			if(hasClearPath(aim))
				aimAndShoot(aim);
		}*/

	}
	
	public void act()
	{
		moderate_act();
		super.act();
	}
	
	public void aimAndShoot(Line2D aim)
	{
		setAngle(aim);
		shoot();
	}
	
	public void goToPoint(Point2D destination)
	{
		if(hasClearPath(findDirectSight(destination)))
		{
			phase = FIRST_PHASE;
			currentRoute = findDirectSight(destination);
			if(followLine(currentRoute))
				status = WAIT;
		}
		else
		{
			if(phase == FIRST_PHASE)
			{
				duplicatePoint = findFuturePath(destination).getP1();
				phase = SECOND_PHASE;
			}
			else if(phase == SECOND_PHASE)
			{
				while(phase == SECOND_PHASE)
				{
					pointFollowLine(findFuturePath(destination));
					debugPoint = duplicatePoint;
					if(!hasClearPath(findDirectSight(duplicatePoint)))
						phase = THIRD_PHASE;
				}
			}
			else if(phase == THIRD_PHASE)
			{	
				currentRoute = findDirectSight(duplicatePoint);
				debugLine =  currentRoute;
				followLine(currentRoute);
			}
			
		}
	}
	
	
	
	// never called by self
	public boolean followLine(Line2D path)
	{
		if(phase != DONE)
		{
			setAngle(path);
			//debugLine = path;
			setVelX(2*Math.cos(Math.toRadians(mcnAngle)));
			setVelY(-2*Math.sin(Math.toRadians(mcnAngle)));
			if(getPoint().distance(path.getP2()) < 10)
			{
				phase = DONE;
				return true;
			}
		}
		return false;
	}
	
	public void pointFollowLine(Line2D path)
	{
		double ratio;
		int angle;
		if(path.getX2() - path.getX1() < 0)
		{
			ratio = (path.getY1()-path.getY2()) / (path.getX1() - path.getX2());
			angle = 180 - (int) Math.round(Math.toDegrees((Math.atan(ratio))));
		}
		else
		{
			ratio = (path.getY1()-path.getY2()) / (path.getX2() - path.getX1());
			angle = (int) Math.round(Math.toDegrees((Math.atan(ratio))));
			if(angle < 0)
				angle += 360;
		}
		
		duplicatePoint.setLocation(	duplicatePoint.getX() +8*Math.cos(Math.toRadians(angle)), 
									duplicatePoint.getY() + -8*Math.sin(Math.toRadians(angle) ));
	}
	
	public void moderate_act()
	{
		if(hasClearPath(findDirectSight(target)))
		{
			if(!setRetreat)
			{
				setRetreat = true;
				retreatPoint = new Point2D.Double (getPosX(), getPosY());
			}
			
			if(status == MODERATE_CHASE)
			{
				bestRoute = findDirectSight(target);
				aimAndShoot(bestRoute);
				setVelX(2*Math.cos(Math.toRadians(mcnAngle)));
				setVelY(-2*Math.sin(Math.toRadians(mcnAngle)));
				if(getPoint().distance(target.getPoint()) < 30)
					status = FLEE;
			}
			if(status == FLEE)
			{
				findFuturePath(retreatPoint);
				if(futureRoute.getX1() < findDirectSight(retreatPoint).getX1())
					setVelX(-1.5);
				if(futureRoute.getX1() > findDirectSight(retreatPoint).getX1())
					setVelX(1.5);
				if(futureRoute.getY1() < findDirectSight(retreatPoint).getY1())
					setVelY(-1.5);
				if(futureRoute.getY1() > findDirectSight(retreatPoint).getY1())
					setVelY(1.5);
				setAngle(futureRoute);
				if(getPoint().distance(retreatPoint) < 30)
					status = MODERATE_CHASE;
			}
		}
		else 
		{
			findFuturePath(target);
			if(futureRoute.getX1() < findDirectSight(target).getX1())
				setVelX(-1.5);
			if(futureRoute.getX1() > findDirectSight(target).getX1())
				setVelX(1.5);
			if(futureRoute.getY1() < findDirectSight(target).getY1())
				setVelY(-1.5);
			if(futureRoute.getY1() > findDirectSight(target).getY1())
				setVelY(1.5);
		}
	}
	
	public void agressive_act()
	{
		if(hasClearPath(findDirectSight(target)))
		{
			bestRoute = findDirectSight(target);
			setAngle(bestRoute);
			setVelX(2*Math.cos(Math.toRadians(mcnAngle)));
			setVelY(-2*Math.sin(Math.toRadians(mcnAngle)));
			shoot();
		}
		else 
		{
			findFuturePath(target);
			if(futureRoute.getX1() < findDirectSight(target).getX1())
				setVelX(-1.5);
			if(futureRoute.getX1() > findDirectSight(target).getX1())
				setVelX(1.5);
			if(futureRoute.getY1() < findDirectSight(target).getY1())
				setVelY(-1.5);
			if(futureRoute.getY1() > findDirectSight(target).getY1())
				setVelY(1.5);
		}
	}
	
	public Line2D findFuturePath(Actor other)
	{
		//System.out.println("Checking path");
		futureRoute = new Line2D.Double(findDirectSight(other).getP1(),findDirectSight(other).getP2());
		int pathTrial = 10;
		boolean checkPosX = true;
		boolean checkNegX = true;
		boolean checkPosY = true;
		boolean checkNegY = true;
		boolean moveablePosX = true;
		boolean moveableNegX = true;
		boolean moveablePosY = true;
		boolean moveableNegY = true;
		Line2D temp = null;
		while(true)
		{
			
			if(checkPosX)
			{
				temp = new Line2D.Double(futureRoute.getX1()+ pathTrial, futureRoute.getY1(),futureRoute.getX2(),futureRoute.getY2());
				moveablePosX = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveablePosX)
				{
					futureRoute = temp;
					break;
				}
			}
			
			if(temp.getX1() > TankFrame.FRAME_WIDTH || !moveablePosX)
				checkPosX = false;
			
			if(checkNegX)
			{
				temp = new Line2D.Double(futureRoute.getX1()- pathTrial, futureRoute.getY1(),futureRoute.getX2(),futureRoute.getY2());
				moveableNegX = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveableNegX)
				{
					futureRoute = temp;
					break;
				}
			}
			
			if(temp.getX1() < 0 || !moveableNegX)
				checkNegX = false;
			
			if(checkNegY)
			{
				temp = new Line2D.Double(futureRoute.getX1(), futureRoute.getY1()- pathTrial,futureRoute.getX2(),futureRoute.getY2());
				moveableNegY = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveableNegY)
				{
					futureRoute = temp;
					break;
				}
			}
			if(temp.getY1() < 0 || !moveableNegY)
				checkNegY = false;
			
			if(checkPosY)
			{
				temp = new Line2D.Double(futureRoute.getX1(), futureRoute.getY1()+ pathTrial,futureRoute.getX2(),futureRoute.getY2());
				moveablePosY = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveablePosY)
				{
					futureRoute = temp;
					break;
				}
			}
			if(temp.getY1() > TankFrame.FRAME_HEIGHT || !moveablePosY)
				checkPosY = false;
			
			pathTrial += 3;
			
			if(!checkNegY && !checkPosY && !checkPosX && !checkNegX)
				break;
		}	
		
		return futureRoute;
	}
	
	public Line2D findFuturePath(Point2D other)
	{
		//System.out.println("Checking path");
		futureRoute = new Line2D.Double(findDirectSight(other).getP1(),findDirectSight(other).getP2());
		int pathTrial = 10;
		boolean checkPosX = true;
		boolean checkNegX = true;
		boolean checkPosY = true;
		boolean checkNegY = true;
		boolean moveablePosX = true;
		boolean moveableNegX = true;
		boolean moveablePosY = true;
		boolean moveableNegY = true;
		Line2D temp = null;
		while(true)
		{
			
			if(checkPosX)
			{
				temp = new Line2D.Double(futureRoute.getX1()+ pathTrial, futureRoute.getY1(),futureRoute.getX2(),futureRoute.getY2());
				moveablePosX = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveablePosX)
				{
					futureRoute = temp;
					break;
				}
			}
			
			if(temp.getX1() > TankFrame.FRAME_WIDTH || !moveablePosX)
				checkPosX = false;
			
			if(checkNegX)
			{
				temp = new Line2D.Double(futureRoute.getX1()- pathTrial, futureRoute.getY1(),futureRoute.getX2(),futureRoute.getY2());
				moveableNegX = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveableNegX)
				{
					futureRoute = temp;
					break;
				}
			}
			
			if(temp.getX1() < 0 || !moveableNegX)
				checkNegX = false;
			
			if(checkNegY)
			{
				temp = new Line2D.Double(futureRoute.getX1(), futureRoute.getY1()- pathTrial,futureRoute.getX2(),futureRoute.getY2());
				moveableNegY = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveableNegY)
				{
					futureRoute = temp;
					break;
				}
			}
			if(temp.getY1() < 0 || !moveableNegY)
				checkNegY = false;
			
			if(checkPosY)
			{
				temp = new Line2D.Double(futureRoute.getX1(), futureRoute.getY1()+ pathTrial,futureRoute.getX2(),futureRoute.getY2());
				moveablePosY = hasClearPath(new Line2D.Double(findDirectSight(other).getP1(), temp.getP1()));
				if(hasClearPath(temp) && moveablePosY)
				{
					futureRoute = temp;
					break;
				}
			}
			if(temp.getY1() > TankFrame.FRAME_HEIGHT || !moveablePosY)
				checkPosY = false;
			
			pathTrial += 3;
			
			if(!checkNegY && !checkPosY && !checkPosX && !checkNegX)
			{	
				System.out.print(moveablePosX + " "+ moveableNegX + " " + moveablePosY + " " +moveableNegY );
				break;
			}
		}	
		
		return futureRoute;
	}
	
	public void setAngle(Line2D path)
	{
		double ratio;
		int angle;
		if(path.getX2() - path.getX1() < 0)
		{
			ratio = (path.getY1()-path.getY2()) / (path.getX1() - path.getX2());
			angle = 180 - (int) Math.round(Math.toDegrees((Math.atan(ratio))));
		}
		else
		{
			ratio = (path.getY1()-path.getY2()) / (path.getX2() - path.getX1());
			angle = (int) Math.round(Math.toDegrees((Math.atan(ratio))));
			if(angle < 0)
				angle += 360;
		}
		
		//System.out.println(angle);
		//if ((Math.abs(mcnAngle - angle) >= 1))
				setMcnAngle(angle);//rotate((int) Math.round((angle - mcnAngle)/10.0));
	}
	

	
    public void paintScreen(Graphics g)
    {
    	super.paintScreen(g);
    	//g.drawLine((int)posX, (int)posY, (int)target.getPosX(), (int) target.getPosY());
    	if(hasClearPath(bestRoute))
    		g.drawLine((int)bestRoute.getX1(), (int)bestRoute.getY1(), (int)bestRoute.getX2(), (int) bestRoute.getY2());
    	g.setColor(Color.green);
    	g.drawLine((int)futureRoute.getX1(), (int)futureRoute.getY1(), (int)futureRoute.getX2(), (int) futureRoute.getY2());
    	g.drawOval((int)debugPoint.getX(), (int)debugPoint.getY(), 5, 5);
    	g.drawLine((int)debugLine.getX1(), (int)debugLine.getY1(), (int)debugLine.getX2(), (int)debugLine.getY2());
    }

}
