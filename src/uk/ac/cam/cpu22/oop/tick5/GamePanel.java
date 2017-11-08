package uk.ac.cam.cpu22.oop.tick5;

import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    
    private World mWorld = null;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
    	/*
        // Paint the background white
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Sample drawing statements
        g.setColor(Color.BLACK);
        g.drawRect(200, 200, 30, 30);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(140, 140, 30, 30);
        g.fillRect(260, 140, 30, 30);
        g.setColor(Color.BLACK);
        g.drawLine(150, 300, 280, 300);
        g.drawString("@@@", 135,120);
        g.drawString("@@@", 255,120);
        */
    	
    	g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        if (mWorld != null){
        	
	        int sSqVert = this.getHeight() / mWorld.getHeight();
	        int sSqHori = this.getWidth() / mWorld.getWidth();
	        int sqSize = (sSqVert<sSqHori) ? (sSqVert) : (sSqHori);
	        
			g.setColor( Color.LIGHT_GRAY );
			for (int i = 0; i<=mWorld.getWidth()+1; i++){
				g.drawLine(i*sqSize,0,i*sqSize,mWorld.getHeight()*sqSize);
			}
			for (int j = 0; j<=mWorld.getHeight()+1; j++){
				g.drawLine(0,j*sqSize, mWorld.getWidth()*sqSize, j*sqSize);
			}
			
			g.setColor( Color.BLACK );
			for (int i = 0; i<mWorld.getHeight(); i++){
				for (int j = 0; j<mWorld.getWidth(); j++){
					if (mWorld.getCell(j, i)) {
						g.fillRect(1+j*sqSize, 1+i*sqSize, sqSize-2, sqSize-2);
					}
				}
			}
			
			g.drawString("Generation: " + mWorld.getGenerationCount(), 5, this.getHeight() - 10 );
			
	        
        	
        }
    	
    }

    public void display(World w) {
        mWorld = w;
        repaint();
    }
}