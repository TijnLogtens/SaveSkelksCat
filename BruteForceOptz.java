import java.util.ArrayList;
import java.util.Date;
public class BruteForceOptz{
	
	final private boolean DEBUG = false; //set this true if you want to see all the possibilities being printed out.
	final private boolean SEE_THE_CONFIGURATION = true; // check this true if, when the chromatic number is found you want to see the colors assigned.
	
	public void search(int n, ArrayList<Integer> p, int k, ColEdge[] e){
		if(p.size()==0){//if it's the first time the method is called and the array of colour is empty .
			for(int i=0; i<k; i++){//loop as many times as the number of colours you want to check.
				p.add(i);		// add a colour
				if(DEBUG)System.out.println(p+" 1 "+ i+"\n");
				search(n, p, k, e);    // recall the method with p.lenght != 0.
				p.remove(p.size()-1);  // before restarting the loop you want to make sure the previous colour is deleted from the array
			}
			for(int i=0; i<p.size(); i++){ // this loop will be executed only if the chromatic number is not k.
				p.remove(i);  // reset the color array
			}
			search(n, p, k+1, e); // repeat the opration for k+1.
		}
				//i think here is where you would change the code in case you want to optimize the program and check if it's legal even before the array of colour is as long as the number of vrtices.
		else if(p.size()<n && check(p, e)){  // in case the array colour is not as long as the number of vertices add more colours
			for(int i=0; i<k; i++){
				p.add(i); // add a colour
				if(DEBUG)System.out.println(p+" "+ i+"\n");
				search(n, p, k, e);
				p.remove(p.size()-1); // remove the previous colour before repeting the loop.
				
			}
		}
		else{// this will be executed if the number of colours is equal to the number of vertices
			if(check(p, e)){// check if this configuration is legal
				if(SEE_THE_CONFIGURATION)System.out.println("\n colours configuration:\n\n"+p+"\n");
				System.out.println("Lowest chromatic number: "+k);//if it is print out the chromatic number
				System.exit(0);
			}
		}	
	}
	
	public boolean check(ArrayList<Integer> p, ColEdge[] e){// method to check if the configuration colors-edges is legal.
		/*if(p.size()==e.length){
			for(int i=0; i<e.length; i++){
			if(p.get((e[i].v)-1)==p.get((e[i].u)-1)){
				if(DEBUG)System.out.println("checked!");
				return false;
			}
		}
		
		if(DEBUG)System.out.println("found!.........................................................................................");
		return true;
		}
		else{*/
			for(int i=0; i<e.length; i++){
				if(e[i].v<=p.size()&&e[i].u<=p.size()){
					if(p.get((e[i].v)-1)==p.get((e[i].u)-1)){
						if(DEBUG)System.out.println("illegal");
					return false;
					}
				}
			}
	//	}
		return true;
		}
		
}
