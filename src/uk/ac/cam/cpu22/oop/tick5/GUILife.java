package uk.ac.cam.cpu22.oop.tick5;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
import uk.ac.cam.cpu22.oop.tick5.ArrayWorld;
import uk.ac.cam.cpu22.oop.tick5.PackedWorld;
import uk.ac.cam.cpu22.oop.tick5.PatternStore;
import uk.ac.cam.cpu22.oop.tick5.World;
*/

//http://www.cl.cam.ac.uk/teaching/1617/OOProg/ticks/life.txt

public class GUILife extends JFrame {
	private World mWorld;
	private PatternStore mStore;
	private ArrayList<World> mCachedWorlds;
	private GamePanel mGamePanel;
	private JButton mPlayButton;
	private Timer mTimer;
	private boolean mPlaying;
	
	public GUILife(PatternStore ps) {
	    super("Game of Life");
	    mStore=ps;
	    mCachedWorlds = new ArrayList<World>();
	    
	    /*
	    try{
	    	mWorld = new ArrayWorld(ps.getPatternByName("glider"));
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    }
	    */
	    
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setSize(1024,768);

	    add(createPatternsPanel(),BorderLayout.WEST);
	    add(createControlPanel(),BorderLayout.SOUTH);
	    add(createGamePanel(),BorderLayout.CENTER);
	    
	    mGamePanel.display(mWorld);
	    

	}
	
	private void addBorder(JComponent component, String title) {
	    Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	    Border tb = BorderFactory.createTitledBorder(etch,title);
	    component.setBorder(tb);
	}

	private JPanel createGamePanel() {
	    mGamePanel = new GamePanel();
	    addBorder(mGamePanel,"Game Panel");
	    return mGamePanel;
	}

	/*
	 * 
	 * add(new JButton("Centre"));
add(new JButton("North"), BorderLayout.NORTH);
add(new JButton("South"), BorderLayout.SOUTH);
add(new JButton("West"), BorderLayout.WEST);
add(new JButton("East"), BorderLayout.EAST);
	 */
	private JPanel createPatternsPanel() {
	    JPanel patt = new JPanel();
	    addBorder(patt,"Patterns");
	    // TODO
	    GridLayout gLayout = new GridLayout(0,1);
	    patt.setLayout(gLayout);
	    DefaultListModel<Pattern> model = new DefaultListModel<Pattern>();
	    
	    for (Pattern p : mStore.getPatternsNameSorted()){
	    	model.addElement(p);
	    }
	    JList<Pattern> list = new JList(mStore.getPatternsNameSorted().toArray());
	    
	    list.addListSelectionListener(new ListSelectionListener(){
	    	
	    	@Override
		    public void valueChanged(ListSelectionEvent e) {
		        JList<Pattern> list = (JList<Pattern>) e.getSource();
		        Pattern p = list.getSelectedValue();
		        
		        if (mPlaying) {runOrPause();}
		        
		        // TODO
		        // Based on size, create either a PackedWorld or ArrayWorld
		        // from p. Clear the cache, set mWorld and put it into
		        // the now-empty cache. Tell the game panel to display
		        // the new mWorld.
		        
		        try{
		        	if (p.getHeight()*p.getWidth() <= 64){
		        		mWorld = new PackedWorld(p);
		        	}
		        	else{
		        		mWorld = new ArrayWorld(p);
		        	}
			    } catch (PatternFormatException e1){
			    	System.out.println(e1.getMessage());
			    }
		        finally{
		        	mCachedWorlds.clear();
		        	mCachedWorlds.add(mWorld);
		        	mGamePanel.display(mWorld);
		        	
		        }
		    }
	    	
	    });
	    
	    
	    
	    
	    
	    JScrollPane scrl = new JScrollPane(list);
	    patt.add(scrl);
	    return patt; 
	}

	private JPanel createControlPanel() {
	    JPanel ctrl =  new JPanel();
	    addBorder(ctrl,"Controls");
	    // TODO
	    GridLayout gLayout = new GridLayout(0,3);
	    ctrl.setLayout(gLayout);
	    
	    
	    JButton b = new JButton("<Back");
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	moveBack();
            	if (mPlaying){runOrPause();}
                System.out.println("Back button was clicked!");
            }
        });
        ctrl.add(b);
	    
        JButton p = new JButton("Play");
        p.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play button was clicked!");
                runOrPause();
            }
        });
        ctrl.add(p);
        mPlayButton = p;
	    
        JButton f = new JButton("Forward>");
        f.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	moveForward();
            	if (mPlaying){runOrPause();}
                System.out.println("Forward button was clicked!");
            }
        });
        ctrl.add(f);
        
	    return ctrl;
	}
	
    private World copyWorld(boolean useCloning)  {
    	   // TODO later
    		if (useCloning == false){
    			if (mWorld instanceof ArrayWorld){
    				return new ArrayWorld((ArrayWorld)mWorld);
    			}
    			else if (mWorld instanceof PackedWorld){
    				return new PackedWorld((PackedWorld)mWorld);
    			}
    		}
    		else{
    			try{
    				System.out.println("Used cloning");
    				return mWorld.clone();
    			}catch (CloneNotSupportedException e){
    				System.out.println(e.getMessage());
    			}
    		}
    	   return null;
    	}
    
    private void moveBack(){
    	if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");
        else {
        	if (mWorld.getGenerationCount()==0){
        		System.out.println("Gen0");
        	}
        	else{
        		mWorld=mCachedWorlds.get(mWorld.getGenerationCount() - 1);
        		//System.out.println("Decremented");
        	}
        	mGamePanel.display(mWorld);
        }
    }
    
    private void moveForward(){
    	if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");
        else {
        	if (mWorld.getGenerationCount()<mCachedWorlds.size()-1 ){
        		//System.out.println("using the cached version");
        		mWorld=mCachedWorlds.get(mWorld.getGenerationCount()+1);
        	}
        	else{
        		//System.out.println("creating new one");
        		mWorld = copyWorld(false);
        		mWorld.nextGeneration();
        		mCachedWorlds.add(mWorld);
        		
        	}
        	mGamePanel.display(mWorld);
        }
    	
    }
    
    private void runOrPause() {
        if (mPlaying) {
            mTimer.cancel();
            mPlaying=false;
            mPlayButton.setText("Play");
        }
        else {
            mPlaying=true;
            mPlayButton.setText("Stop");
            mTimer = new Timer(true);
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    moveForward();
                }
            }, 0, 500);
        }
    }
    
    
    
    public static void main(String[] args) throws IOException{
    	PatternStore ps = new PatternStore("http://www.cl.cam.ac.uk/teaching/1617/OOProg/ticks/life.txt");
        //PatternStore ps = new PatternStore("/home/cribcreaky/Documents/Computer Science/Ticks/Java/Tick5/bin/uk/ac/cam/cpu22/oop/tick5/Patsto.txt");
    	GUILife gui = new GUILife(ps);
        gui.setVisible(true);

    }
    
    

}