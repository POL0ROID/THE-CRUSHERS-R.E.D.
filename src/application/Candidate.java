package application;
/*
 * The Candidate class contains the name, elimination status, and number of votes of a candidate.
 * It is placed in an ordered ArrayList, and accessed by its order in it.
 */
public class Candidate {
	public String name;
	public boolean eliminated=false;
	public int totVotes;
	Candidate(String s){
		this.name = s;
		this.totVotes = 0;
	}
}