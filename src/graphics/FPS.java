package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class FPS {

	public final int FPS_CAP = 70;
	private final int x = 20;
	private final int y = 30;

	public void draw(Graphics g, int fps){
		Graphics2D g2d=(Graphics2D)g;
		g2d.setColor(Color.GREEN);
		g2d.drawString("FPS: "+fps, x, y);
		
		return;
	}
}
