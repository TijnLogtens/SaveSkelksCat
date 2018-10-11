import java.util.ArrayList;
public class BruteForce{

	private final boolean DEBUG = false; //set this true if you want to see all the possibilities being printed out.
	private final boolean SEE_THE_CONFIGURATION = true; // check this true if, when the chromatic number is found you want to see the colors assigned.

	public void search(int n, ArrayList<Integer> colours, int k, ColEdge[] e){
		if(colours.size() == 0){ //base case
			for(int i = 0; i < k; i++){
				colours.add(i);
				if(DEBUG)System.out.println(colours + " 1 " + i + "\n");
				search(n, colours, k, e);    //recalls for colours.lenght != 0.
				colours.remove(colours.size()-1);
			}
			for(int i = 0; i < colours.size(); i++){ //if k != chromatic number
				colours.remove(i);
			}
			search(n, colours, k+1, e); // repeat for k+1.
		}
		else if(colours.size() < n && check(colours, e)){
			for(int i=0; i<k; i++){
				colours.add(i);
				if(DEBUG)System.out.println(colours +" "+ i+"\n");
				search(n, colours, k, e);
				colours.remove(colours.size()-1); // remove the previous colour before repeting the loop.
			}
		}
		else{// if number of colours = vertices
			if(check(colours, e)){
				if(SEE_THE_CONFIGURATION)System.out.println("\n colours configuration:\n\n" + colours + "\n");
				System.out.println("Lowest chromatic number: " + k);
				System.exit(0);
			}
		}
	}

	public boolean check(ArrayList<Integer> colours, ColEdge[] e){// method to check if the configuration colors-edges is legal.
		for(int i=0; i < e.length; i++){
				if(e[i].v <= colours.size() && e[i].u <= colours.size()){
					if(colours.get((e[i].v)-1) == colours.get((e[i].u)-1)){
						if(DEBUG)System.out.println("illegal");
					return false;
					}
				}
			}
		return true;
	}
}
