package uk.ac.cam.cpu22.oop.tick5;

import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
	
	private List<Pattern> mPatterns = new LinkedList<>();
	private Map<String,List<Pattern>> mMapAuths = new HashMap<>();
	private Map<String,Pattern> mMapName = new HashMap<>();

   public PatternStore(String source) throws IOException {
       if (source.startsWith("http://")) {
          loadFromURL(source);
       }
       else {
          loadFromDisk(source);
    }
   }
    
   public PatternStore(Reader source) throws IOException {
      load(source);
   }
    
   private void load(Reader r) throws IOException {
      // TODO: read each line from the reader and print it to the screen   
	   BufferedReader b = new BufferedReader(r);
	   String line = b.readLine();
	   while (line!=null){
		   try{
			   Pattern pat = new Pattern(line);
			   mPatterns.add(pat);
			   if (mMapAuths.get(pat.getAuthor()) == null) {
				   mMapAuths.put(pat.getAuthor(), new LinkedList<Pattern> () );
				   }
			   mMapAuths.get(pat.getAuthor()).add(pat);
			   mMapName.put(pat.getName(), pat);
			   line=b.readLine();
		   } catch (PatternFormatException e){
			   System.out.println(line);
			   line = b.readLine();
		   }
	   }
	   
   }
    
    
   private void loadFromURL(String url) throws IOException {
    // TODO: Create a Reader for the URL and then call load on it
	   URL destination = new URL(url);
	   URLConnection conn = destination.openConnection();
	   Reader r = new java.io.InputStreamReader(conn.getInputStream());
	   load(r);
   }

   private void loadFromDisk(String filename) throws IOException {
    // TODO: Create a Reader for the file and then call load on it
	   Reader r = new FileReader(filename);
	   load(r);
   }
   
   public List<Pattern> getPatternsNameSorted() {
	   // TODO: Get a list of all patterns sorted by name
	   Collections.sort(mPatterns);
	   return new LinkedList<Pattern>(mPatterns);
	}

	public List<Pattern> getPatternsAuthorSorted() {
		Collections.sort(mPatterns, new Comparator<Pattern>() {
			   public int compare(Pattern p1, Pattern p2) {
			      if (p1.getAuthor().compareTo(p2.getAuthor()) == 0){
			          return p1.getName().compareTo(p2.getName());
			      }else{
			          return p1.getAuthor().compareTo(p2.getAuthor());
			      }
			   }
			 });
		return new LinkedList<Pattern>(mPatterns);
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
	   // TODO:  return a list of patterns from a particular author sorted by name
		if (mMapAuths.get(author)==null){
			throw new PatternNotFound("Author "+ author + " does not exist in map");
		}
		LinkedList<Pattern> list= new LinkedList<Pattern>(mMapAuths.get(author));
		Collections.sort(list);
		return list;
		
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
	   // TODO: Get a particular pattern by name
		if (mMapName.get(name)==null){
			throw new PatternNotFound("Name "+ name + " does not exist in map");
		}
		return mMapName.get(name);
	}

	public List<String> getPatternAuthors() {
	   // TODO: Get a sorted list of all pattern authors in the store
		LinkedList<String> list = new LinkedList<String>(mMapAuths.keySet());
		Collections.sort(list);
		return list;
	}

	public List<String> getPatternNames() {
	   // TODO: Get a list of all pattern names in the store,
	   // sorted by name
		LinkedList<String> list = new LinkedList<String>(mMapName.keySet());
		Collections.sort(list);
		return list;
	}

   public static void main(String args[]) {
	   try{
	      PatternStore p =
	       new PatternStore(args[0]);
	      System.out.println(p.getPatternAuthors());
	      System.out.println(p.getPatternByName("glider"));
	   } catch (IOException | PatternNotFound e) {
		   System.out.println("ERROR " + e.getMessage());
	   }
   }
}
