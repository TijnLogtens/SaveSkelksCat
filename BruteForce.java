import java.util.ArrayList;
public class BruteForce{

	private final boolean DEBUG = false; //set this true if you want to see all the possibilities being printed out.
	private final boolean SEE_THE_CONFIGURATION = true; // check this true if, when the chromatic number is found you want to see the colors assigned.

	public void search(int n, ArrayList<Integer> colours, int k, ColEdge[] e){
		if(colours.size() == 0){
			for(int i = 0; i < k; i++){
				colours.add(i);		// add a colour to colours
				if(DEBUG)System.out.println(colours + " 1 " + i + "\n");
				search(n, colours, k, e);    //recalls for colours.lenght != 0.
				colours.remove(colours.size()-1);  //removes last colour
			}
			for(int i = 0; i < colours.size(); i++){ // this loop will be executed only if the chromatic number is not k.
				colours.remove(i);
			}
			search(n, colours, k+1, e); // repeat the opration for k+1.
		}
				//i think here is where you would change the code in case you want to optimize the program and check if it's legal even before the array of colour is as long as the number of vrtices.
		else if(colours.size() < n){  // in case the array colour is not as long as the number of vertices add more colours
			for(int i=0; i<k; i++){
				colours.add(i);
				if(DEBUG)System.out.println(colours +" "+ i+"\n");
				search(n, colours, k, e);
				colours.remove(colours.size()-1); // remove the previous colour before repeting the loop.

			}
		}
		else{// this will be executed if the number of colours is equal to the number of vertices
			if(check(colours, e)){// check if this configuration is legal
				if(SEE_THE_CONFIGURATION)System.out.println("\n colours configuration:\n\n" + colours + "\n");
				System.out.println("Lowest chromatic number: " + k);
				System.exit(0);
			}
		}
	}

	public boolean check(ArrayList<Integer> colours, ColEdge[] e){// method to check if the configuration colors-edges is legal.
		for(int i = 0; i < e.length; i++){
			if(colours.get((e[i].v)-1) == colours.get((e[i].u)-1)){
				if(DEBUG)System.out.println("checked!");
				return false;
			}
		}
		if(DEBUG)System.out.println("found!.........................................................................................");
		return true;
	}
}
