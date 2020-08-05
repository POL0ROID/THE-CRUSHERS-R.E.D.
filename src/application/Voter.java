package application;
import java.util.*;
/*
 * The Voter class is passed
 * 1) the list of candidates in order
 * 2) how they each were ranked
 * by [something else].
 * Our fundamental data set is an ArrayList of these.
 * In tabulation,  we go down the ArrayList<Voter>, and for each time the rank of Candidate 1 was
 * listed as "1" by voters,
 * totVotes is incremented by 1.
 * On each loop,
 * the candidate with the lowest totVotes is eliminated,
 * (setting "eliminated" to "true"),
 * we go down the ArrayList<voter> to find people whose nth preferences have all been eliminated,
 * and increment their n+1th preferences' totVotes by 1.
 */
public class Voter {
	public ArrayList<Integer> ranking = new ArrayList<Integer>();
	Voter(ArrayList<Integer> ranking){
		this.ranking = ranking;
	}
}