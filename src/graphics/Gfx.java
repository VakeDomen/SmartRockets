package graphics;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sim.Simulation;

public class Gfx extends JPanel{

	
	private JFrame f;
	private FPS fps;
	private Stats stat;
	
	private Simulation sim;
	
	public Gfx(Simulation sim) {
		this.sim = sim;
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		int t0=(int)System.currentTimeMillis();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		
		sim.draw(g2d);
		stat.draw(g2d);
		
		
		
		
		//FPS set and display
		int t1=(int)System.currentTimeMillis();
		int sleep;
		if(t1-t0>1000/fps.FPS_CAP){
			sleep=0;
		}else{
			sleep=(1000/fps.FPS_CAP-(t1-t0));
		}
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int t2=(int)System.currentTimeMillis();
		fps.draw(g, 1000/(t2-t0));
		
		super.repaint();
	}
	
	
	
	
	
	
	public void init() {
		initFrame();
		initComponents();
		addComponentsToFrame();
		f.setVisible(true);
	}
	private void initFrame() {
		f = new JFrame(sim.getStage().getName());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.getContentPane().setPreferredSize(new Dimension(sim.getStage().getWidth(), sim.getStage().getHeight()));
		f.pack();
		
	}
	private void initComponents() {
		this.fps = new FPS();
		this.stat = new Stats(sim);
	}
	private void addComponentsToFrame() {
		//main panel
		f.add(this);				
	}
	
	
}
