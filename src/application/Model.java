package application;
import java.io.File;
import java.util.*;
/*
 * The Model class contains the ArrayList of Voters and the ArrayList of Candidates.
 * The controller classes are passed the model object from Main,
 * and change the contents of these arraylists based on View inputs.
 */
public class Model {
	private ArrayList<Voter> votes;
	private ArrayList<Candidate> candidates;
	String filePath = new File("").getAbsolutePath();
	public Model(){
		votes = new ArrayList<Voter>();
		candidates = new ArrayList<Candidate>();
	}
	public void populateCandidates(ArrayList<Candidate> candidates){
		if(this.candidates.isEmpty())
			this.candidates = candidates;
	}
	public void addVotes(Voter vote){
		votes.add(vote);
	}
	public boolean isEmpty(){
		return candidates.isEmpty();
	}
	public ArrayList<Candidate> getCandidates(){
		return candidates;
	}
	public ArrayList<Voter> getVotes(){
		return votes;
	}
}