package auto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Window extends JFrame {

	private JLabel power = new JLabel("0");
	private JLabel speed = new JLabel("0");
	private JLabel battery = new JLabel("0");
	private Timer timer;
	private volatile boolean pressed;
	private static int VAL = 0;
	private Car car = new Car();
	
	class Decrementer extends Thread {
		public void run() {
			while(!pressed && car.getSpeed()!=0) {
				synchronized(this) {
					try {
						wait(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				car.decreaseSpeed(car.getDec(),false);
			}
		}
	}
	
	
	public Window() {
		setSize(1335,700);
		setLayout(new BorderLayout());
	    setContentPane(new JLabel(new ImageIcon("dash.png")));
	    add(power);
	    power.setHorizontalAlignment(SwingConstants.CENTER);
	    power.setFont(new Font(battery.getName(), Font.PLAIN, 50));
	    power.setForeground(Color.BLUE);
	    power.setBounds(210, 370, 200, 100);
	    add(speed);
	    speed.setHorizontalAlignment(SwingConstants.CENTER);
	    speed.setFont(new Font(battery.getName(), Font.PLAIN, 50));
	    speed.setForeground(Color.YELLOW);
	    speed.setBounds(610, 180, 100, 100);
	    add(battery);
	    battery.setHorizontalAlignment(SwingConstants.CENTER);
	    battery.setFont(new Font(battery.getName(), Font.PLAIN, 50));
	    battery.setForeground(Color.GREEN);
	    battery.setBounds(960, 370, 100, 100);
	    
	    addKeyListener(new KeyListener(){
	    		@Override
               public void keyPressed(KeyEvent e) {
                   if(e.getKeyCode() == KeyEvent.VK_UP){
                	   pressed = true;
                	   car.increaseSpeed(5);
                   }
                   else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                	   pressed = true;
                	   car.decreaseSpeed(5,true);
                   }
               }

				@Override
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_UP){
						car.setDec(5);
						pressed = false;
						new Decrementer().start();
					}
				}

				@Override
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == 'r')
						car.setBattery();
						
				}
       });
	    timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				speed.setText(car.getSpeed() + "");
				battery.setText(car.getBatteryPercent() + "");
				power.setText(car.getPower() + "");
			}
        });
	    setVisible(true);
	    addWindowListener(new WindowAdapter() {
	    	@Override
            public void windowClosing(WindowEvent e)
            {
                timer.stop();
            }
	    });
	    timer.start();
	}
	
	public synchronized boolean getPressed() {
		return pressed;
	}
	
	public static void main(String args[]) {
		new Window();
	}
}
