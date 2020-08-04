package application;
/**
 * The Candidate class contains the name, elimination status, and number of votes of a candidate,
 * as well as a reference to how many candidates there are.
 * It is placed in an ordered ArrayList, and accessed by its order in it.
 * totVotes is changed by
 * @author William Paul Baker
 */
public class Candidate {
	public String name;
	public boolean eliminated=false;
	public int numCandidates;
	public int totVotes;
	Candidate(String s, int numCandidates){
		this.name = s;
		this.numCandidates = numCandidates;
	}
}