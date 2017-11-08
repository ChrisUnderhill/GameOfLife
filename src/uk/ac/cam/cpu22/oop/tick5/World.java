package uk.ac.cam.cpu22.oop.tick5;


public abstract class World implements Cloneable{

	private int mGeneration;
	private Pattern mPattern;
	
	public World(String str){
		mGeneration = 0;
		try{
			mPattern = new Pattern(str);
		}catch (PatternFormatException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public World(Pattern pattern){
		mGeneration = 0;
		mPattern = pattern;
		
	}
	
	public World(World w){
		mGeneration = w.getGenerationCount();
		mPattern = w.getPattern();
	}
	
	@Override
	public World clone() throws CloneNotSupportedException{
			return (World)super.clone();
	}
	
    public int getWidth(){ return mPattern.getWidth(); }
    public int getHeight() { return mPattern.getHeight(); }
    
    public int getGenerationCount() { return mGeneration; }
    protected void incrementGenerationCount() { mGeneration++; }
    protected Pattern getPattern() { return  mPattern; } //private
    public abstract void nextGeneration();
    public abstract boolean getCell(int c, int r); //THIS MIGHT NEED TO BE A BOOLEAN
    public abstract void setCell(int col, int row, boolean val);
    
    protected int countNeighbours(int col, int row){
    	int num = 0;
		
		for (int i = -1; i<=1; i++) {
			for (int j = -1; j<=1; j++) {
				if (!(i==0 && j==0)){
					num = num + (getCell(col+i, row + j) ? 1 : 0);
				}
			}	
		}
		
		return num;
    }
    
    protected boolean computeCell( int col, int row) {
    	// liveCell is true if the cell at position (col,row) in world is live
 	   boolean liveCell = getCell( col, row);
 		
 	   // neighbours is the number of live neighbours to cell (col,row)
 	   int neighbours = countNeighbours(col, row);

 	   // we will return this value at the end of the method to indicate whether 
 	   // cell (col,row) should be live in the next generation
 	   boolean nextCell = false;
 		
 	   //A live cell with less than two neighbours dies (underpopulation)
 	   if (neighbours < 2) {
 		  nextCell = false;
 	   }
 	 
 	   //A live cell with two or three neighbours lives (a balanced population)
 	   //TODO: write a if statement to check neighbours and update nextCell
 	   if (neighbours == 3 || (neighbours == 2 && liveCell ) ) {
 		  nextCell = true;
 	   }
 	   
 	   //A live cell with with more than three neighbours dies (overcrowding)
 	   //TODO: write a if statement to check neighbours and update nextCell
 	   if (neighbours > 3) {
 		  nextCell = false;
 	   }
 	   
 	   return nextCell;
    }
}
