package uk.ac.cam.cpu22.oop.tick5;

public class PackedWorld extends World implements Cloneable{

	private long mWorld;
	private long mWorldTmp;
	
	public PackedWorld(String str) throws PatternFormatException {
		super(str);
    	mWorld=0;
	    getPattern().initialise(this);
	    switchWorlds();
	    
	    if (getPattern().getWidth()*getPattern().getHeight()>64 ){
	    	throw new PatternFormatException("Requested Pattern cannot be represented as a PackedLong! ");
	    	
	    }
	}
	
	
	public PackedWorld(Pattern pat) throws PatternFormatException {
    	super(pat);
    	mWorld=0;
	    getPattern().initialise(this);
	    switchWorlds();
	    
	    if (getPattern().getWidth()*getPattern().getHeight()>64 ){
	    	throw new PatternFormatException("Requested Pattern cannot be represented as a PackedLong! ");
	    	
	    }
    }
	
	public PackedWorld( PackedWorld w ){
    	super(w);
    	//mWorld = 0L;
    	for (int i = 0; i<=w.getWidth(); i++){
    		for (int j = 0; j<=w.getHeight(); j++){
        		setCell(i,j, w.getCell(i,j));
        	}
    	}
    	switchWorlds();
    }
	
	@Override
	public PackedWorld clone() throws CloneNotSupportedException{
		return (PackedWorld)super.clone();
	}
	
	
	public boolean get(long packed, int position) {
		   // set "check" to equal 1 if the "position" bit in "packed" is set to 1
		   // you should use bitwise operators (not % or similar)
		   long check = (packed>>(position))&(1); //TODO: complete this statement

		   return (check == 1);
		   }

   /*
    * Set the nth bit in the packed number to the value given
    * and return the new packed number
    */
   public long set(long packed, int position, boolean value) {
      if (value) {
		  packed = packed | (1L<<(position));
         // TODO: complete this using bitwise operators
         // update the value "packed" with the bit at "position" set to 1
      }
      else {
		  packed = packed & (~(1L<<(position)));
         // TODO: complete this using bitwise operators
         // update the value "packed" with the bit a "position" set to 0 
      }
      return packed;
   }
	
	
	@Override
	public void nextGeneration() {
		for (int i =0; i<getPattern().getHeight(); i++){
			for (int j = 0; j<getPattern().getWidth(); j++){
				setCell( j, i, computeCell(j,i) );
			}
		}
		incrementGenerationCount();
		switchWorlds();
	}

	@Override
	public boolean getCell(int col, int row) {
		   if (!(col<=getPattern().getHeight()-1 && row<=getPattern().getWidth()-1 && col>=0 && row>=0)){
			   //System.out.println("FAIL");
			   return false;
		   }
		   
		   int pos = row*getPattern().getWidth() + col; 
		   //System.out.print(pos);
		   
		   return get(mWorld, pos);
	}

	@Override
	public void setCell(int col, int row, boolean val) {
		if ((col<=getPattern().getHeight()-1 && row<=getPattern().getWidth()-1 && col>=0 && row>=0)){
				mWorldTmp = set(mWorldTmp, row*getPattern().getWidth()+col, val);
				//System.out.println(row*getPattern().getWidth()+col);
		    }
		   
	}
	
	private void switchWorlds(){
		mWorld=mWorldTmp;
	}
}
