package uk.ac.cam.cpu22.oop.tick5;


public class ArrayWorld extends World implements Cloneable{

	protected boolean[][] mWorld;
	private boolean[] mDeadRow;
    
	
	public ArrayWorld(String str) throws PatternFormatException{
		super(str);
		
		mWorld = new boolean[getHeight()][getWidth()];
	    getPattern().initialise(this);
	    mDeadRow = new boolean[getWidth()];
	    
	    boolean allDead;
	    for (int i = 0; i<getHeight(); i++){
	    	allDead = true;
	    	for (int j = 0; j<getWidth(); j++){
		    	if (mWorld[i][j] == true){
		    		allDead=false;
		    	}
		    }
	    	if (allDead == true){
	    		mWorld[i] = mDeadRow;
	    		//System.out.println("All dead row");
	    	}
	    }
		
	}
	
    public ArrayWorld(Pattern pat) throws PatternFormatException {
    	super(pat);
	    // TODO: initialise mWorld
    	mWorld = new boolean[getWidth()][getHeight()];
	    getPattern().initialise(this);
	    mDeadRow = new boolean[getHeight()];
	    
	    boolean allDead;
	    for (int i = 0; i<getWidth(); i++){
	    	allDead = true;
	    	for (int j = 0; j<getHeight(); j++){
		    	if (mWorld[i][j] == true){
		    		allDead=false;
		    	}
		    }
	    	if (allDead == true){
	    		mWorld[i] = mDeadRow;
	    		//System.out.println("All dead row");
	    	}
	    }
    }

    public ArrayWorld( ArrayWorld w ){
    	super(w);
    	
    	mWorld = new boolean[w.getWidth()][w.getHeight()];
    	for (int i = 0; i<w.getWidth(); i++){
    		for (int j = 0; j<w.getHeight(); j++){
        		setCell(i,j, w.getCell(i,j));
        	}
    	}
    	mDeadRow = w.getDeadRow();
    	
    	boolean allDead;
	    for (int i = 0; i<getWidth(); i++){
	    	allDead = true;
	    	for (int j = 0; j<getHeight(); j++){
		    	if (mWorld[i][j] == true){
		    		allDead=false;
		    	}
		    }
	    	if (allDead == true){
	    		mWorld[i] = mDeadRow;
	    		//System.out.println("All dead row");
	    	}
	    }
    }
    
    @Override
    public ArrayWorld clone() throws CloneNotSupportedException{
    	ArrayWorld w = (ArrayWorld)super.clone();
    	boolean[][] a = new boolean[getHeight()][getWidth()];
    	
    	for (int i = 0; i<w.getWidth(); i++){
    		for (int j = 0; j<w.getHeight(); j++){
        		a[i][j] = mWorld[i][j];
        	}
    	}
    	
    	boolean allDead;
	    for (int i = 0; i<getWidth(); i++){
	    	allDead = true;
	    	for (int j = 0; j<getHeight(); j++){
		    	if (mWorld[i][j] == true){
		    		allDead=false;
		    	}
		    }
	    	if (allDead == true){
	    		mWorld[i] = mDeadRow;
	    		//System.out.println("All dead row");
	    	}
	    }
	    w.mWorld = a;
		return w;
    }
    
	@Override
	public void nextGeneration() {
		boolean[][] nextGeneration = new boolean[mWorld.length][];
        for (int y = 0; y < getWidth(); ++y) {
            nextGeneration[y] = new boolean[mWorld[y].length];
            for (int x = 0; x < getHeight(); ++x) {
                boolean nextCell = computeCell(y, x);
                nextGeneration[y][x]=nextCell;
            }
        }
        mWorld = nextGeneration;
        incrementGenerationCount();
	}


	@Override
	public boolean getCell(int col, int row) {
		if (row < 0 || row >= getHeight() ) return false;
	    if (col < 0 || col >= getWidth() ) return false;

		return mWorld[col][row];
	}


	@Override
	public void setCell(int col, int row, boolean val) {
		if (row < 0 || row > getHeight() - 1) return;
		if (col < 0 || col > getWidth() - 1) return;
		
		
		mWorld[col][row] = val;

	}
	
	public boolean[] getDeadRow(){
		return mDeadRow;
	}
	

}
