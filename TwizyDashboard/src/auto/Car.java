package auto;

public class Car {

	private static double KWH=6.1;
	private volatile double speed;
	private volatile double battery;
	private volatile float power;
	private volatile double length;
	private double batteryFull;
	private double dec;
	
	public Car() {
		this.battery = 100;
		this.batteryFull = battery;
		this.speed = 0;
		this.power = 0;
		this.length = 0;
	}
	
	public synchronized int getBatteryPercent() {
		
		return (int)(100*battery/batteryFull);
	}
	
	public synchronized void setBattery() {
		battery = 100;
	}
	
	public synchronized double getSpeed() {
	    return speed;
	}
	
	public synchronized float getPower() {
		return power;
	}
	
	public synchronized void increaseSpeed(double inc) {
		if (battery > 0) {
			if(speed + inc < 80) {
				power+=0.1;
				speed+=inc;
			}
		    else
		    	speed=80;
		    if (battery-power >= 0) {
		    	battery-=power;
		    }
		    else {
		    	battery = 0;
		    }
		}
		else 
			decreaseSpeed(inc, false);
	}

	public synchronized void decreaseSpeed(double dec, boolean brake) {
		 if(speed - dec > 0) {
			 speed-=dec;
			 if (power - 0.1>0)
				 power-=0.1;
			 else 
				 power = 0;
		 }
		 else
			 speed=0;
		 
		 if (power - 0.1>0)
			 power-=0.1;
		 else
			 power = 0;
		 
		 if (brake && speed>0) {
			 if(battery>0 && battery+0.2<batteryFull)
				 battery+=0.2;
			 else if (battery>0)
				 battery=batteryFull;
		 }
	}
	
	public synchronized double getDec() {
		return dec;
	}
	
	public synchronized void setDec(double dec) {
		this.dec = dec;
	}
	
}
