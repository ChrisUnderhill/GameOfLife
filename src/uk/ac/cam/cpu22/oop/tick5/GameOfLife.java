package uk.ac.cam.cpu22.oop.tick5;

//http://www.cl.cam.ac.uk/teaching/current/OOProg/ticks/life.txt
//glider = 166

import java.io.*;
import java.util.*;

public class GameOfLife {
	private World mWorld;
	private PatternStore mStore;
	private ArrayList<World> mCachedWorlds;
	
    public GameOfLife(PatternStore ps) {
        mStore = ps;
        mCachedWorlds = new ArrayList<World>();
    }

    
    public void print() {
    	
    	System.out.println("-" + mWorld.getGenerationCount()); 
    			for (int row = 0; row < mWorld.getHeight(); row++) {
    			  for (int col = 0; col < mWorld.getWidth(); col++) {
    				 System.out.print(mWorld.getCell(col, row) ? "#" : "_"); 
    			  }
    			  System.out.println(); 
    			} 
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

    public void play() throws IOException {
        
        String response="";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                
        System.out.println("Please select a pattern to play (l to list:");
        while (!response.equals("q")) {
                response = in.readLine();
                System.out.println(response);
                if (response.equals("f")) {
                    if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");
                    else {
                    	if (mWorld.getGenerationCount()<mCachedWorlds.size()-1 ){
                    		System.out.println("using the cached version");
                    		mWorld=mCachedWorlds.get(mWorld.getGenerationCount()+1);
                    	}
                    	else{
                    		System.out.println("creating new one");
                    		mWorld = copyWorld(false);
                    		mWorld.nextGeneration();
                    		mCachedWorlds.add(mWorld);
                    		
                    	}
                            print();
                    }
                }
                else if (response.equals("b")) {
	                if (mWorld==null) System.out.println("Please select a pattern to play (l to list):");
	                else {
	                	if (mWorld.getGenerationCount()==0){
	                		System.out.println("Gen0");
	                	}
	                	else{
	                		mWorld=mCachedWorlds.get(mWorld.getGenerationCount() - 1);
	                		System.out.println("Decremented");
	                	}
	                        print();
	                }
                }
                else if (response.equals("l")) {
                        List<Pattern> names = mStore.getPatternsNameSorted();
                        int i=0;
                        for (Pattern p : names) {
                                System.out.println(i+" "+p.getName()+"  ("+p.getAuthor()+")");
                                i++;
                        }
                }
                else if (response.startsWith("p")) {
                   List<Pattern> names = mStore.getPatternsNameSorted();
                   // TODO: Extract the integer after the p in response
                   // TODO: Get the associated pattern
                   // TODO: Initialise mWorld using PackedWorld or ArrayWorld based
                   //       on pattern world size
                   String[] splitted = response.split(" ");
                   int chosen = Integer.parseInt(splitted[1]);
                   Pattern chosenPat = names.get(chosen);
                   
                   try{
	                   if ( chosenPat.getHeight()*chosenPat.getWidth() <= 64 ){
	                	   mWorld = new PackedWorld( chosenPat );
	                   }else{
	                	   mWorld = new ArrayWorld( chosenPat );
	                   }
	                   System.out.println(mWorld);
	                   mCachedWorlds.add(mWorld);
                   } catch (PatternFormatException e){
                	   System.out.println("ERROR " + e.getMessage());
                   }
                   
                   print();
                }
                
        }
    }

    public void test(){
    	List<Pattern> names = mStore.getPatternsNameSorted();
        int chosen = 166;
        //Pattern chosenPat = names.get(chosen);
        try{
        	/*
        	mWorld = new PackedWorld(chosenPat);
        	print();
        	PackedWorld mWorld2 = new PackedWorld((PackedWorld)mWorld);
        	mWorld2.nextGeneration();
        	mWorld=mWorld2;
        	print();
        	*/
        	
        	Pattern chosenPat = new Pattern("test:rkh23:8:8:0:0:1111 1111 1111 1111");
        	mWorld = new ArrayWorld(chosenPat);
        	print();
        	ArrayWorld mWorld2 = (ArrayWorld)mWorld.clone();
        	print();
        	ArrayWorld mWorldOrig = (ArrayWorld)mWorld.clone();
        	mWorld2.setCell(7, 1, true);
        	mWorld=mWorld2;
        	print();
        	mWorld=mWorldOrig;
        	print();
        	System.out.println(mWorld.getCell(7, 1));
        	System.out.println(mWorld2.getCell(7, 1));
        	
        	
        }catch (PatternFormatException | CloneNotSupportedException e){
        	System.out.println(e.getMessage());
        }
    }
    
    public static void main(String args[]) throws IOException {
        
        if (args.length!=1) {
                System.out.println("Usage: java GameOfLife <path/url to store>");
                return;
        }
        
        try {
                PatternStore ps = new PatternStore(args[0]);
                GameOfLife gol = new GameOfLife(ps);    
                gol.play();
                //gol.test();
        }
        catch (IOException ioe) {
                System.out.println("Failed to load pattern store");
        }
        
        
    }
}
