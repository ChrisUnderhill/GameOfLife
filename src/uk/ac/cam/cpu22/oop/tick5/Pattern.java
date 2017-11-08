package uk.ac.cam.cpu22.oop.tick5;

public class Pattern implements Comparable<Pattern> {

    private String mName;
    private String mAuthor;
    private int mWidth;
    private int mHeight;
    private int mStartCol;
    private int mStartRow;
    private String mCells;
    
    @Override
    public int compareTo(Pattern o) {
       return mName.compareTo(o.getName());
    }
    
    @Override
    public String toString(){
    	return mName;
    }
    
    //TODO: write public 'get' methods for ALL of the fields above;
    //      for instance 'getName' should be written as:
    public String getName() {
        return mName;
     }
    public String getAuthor() {
        return mAuthor;
     }
    public int getWidth() {
        return mWidth;
     }
    public int getHeight() {
        return mHeight;
     }
    public int getStartCol() {
        return mStartCol;
     }
    public int getStartRow() {
        return mStartRow;
     }
    public String getCells() {
        return mCells;
     }

    public Pattern(String format) throws PatternFormatException {
       //TODO: initialise all fields of this class using contents of 
       //      'format' to determine the correct values (this code
       //      is similar tothat you used in the new ArrayLife constructor
    		String[] splitString = format.split(":");
    		if (splitString.length !=7) throw new PatternFormatException("Incorrect number of arguments!");
    		
	    	mName = splitString[0];
	    	mAuthor = splitString[1];
	    	
	    	try{
		    	mWidth = Integer.parseInt(splitString[2]);
		    	mHeight = Integer.parseInt(splitString[3]);
		    	mStartCol = Integer.parseInt(splitString[4]);
		    	mStartRow = Integer.parseInt(splitString[5]);
	    	}
	    	catch (NumberFormatException e) {
	    		throw new PatternFormatException("Integer arguments not passed as integers! ");
	    	}
	    	mCells = splitString[6];

	    	
    }

    
    
    public static void main(String[] args) throws Exception
    {
    	try{
	    	Pattern pat = new Pattern(args[0]);
	    	System.out.println("Name: " + pat.getName() );
	    	System.out.println("Author: " + pat.getAuthor() );
	    	System.out.println("Width: " + pat.getWidth() );
	    	System.out.println("Height: " + pat.getHeight() );
	    	System.out.println("StartCol: " + pat.getStartCol() );
	    	System.out.println("StartRow: " + pat.getStartRow() );
	    	System.out.println("Pattern: " + pat.getCells() );
    	}
    	catch (PatternFormatException e){
    		System.out.println(e.getMessage() );
    	}
    }

    public void initialise(World world) throws PatternFormatException {
       //TODO: update the values in the 2D array representing the state of
       //      'world' as expressed by the contents of the field 'mCells'.
	    
	        //TODO: Using loops, update the appropriate cells of 'world'
	        //      to 'true'
	        // ...
	    	String[] cellsString = mCells.split(" ");
	    	
	    	
	    	for (int i = 0; i < cellsString.length; ++i ){
	    		char[] cellsChars = cellsString[i].toCharArray();
	    		
	    		for (int j = 0; j < cellsChars.length; ++j ){
	    			
	    			try {
	    				if (Character.getNumericValue(cellsChars[j]) != 0 && Character.getNumericValue(cellsChars[j]) != 1 ){
	    					throw new PatternFormatException("Cells were not exclusively either 0 or 1! ");
	    				}
	    			}
	    			catch (NumberFormatException e){
	    				throw new PatternFormatException("Wrong format used for cells! ");
	    			}
	    			
	    			if (cellsChars[j] ==  '1' ){
	    				world.setCell( mStartCol + j, mStartRow + i, true);
	    				//WARNING: THIS HERE MIGHT BE THE WRONG WAY ROUND
	    			}
	    		}
	    		
	    	}
    
    }
    
}