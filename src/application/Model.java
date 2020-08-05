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
 	public void resultsSetup() {
  		for (Voter v: votes)
  			for(int j=0; j < v.ranking.size(); j++){
  				if(v.ranking.get(j)==1)
  					candidates.get(j).totVotes++;
  				System.out.println(candidates.get(j).name);
  				System.out.println(candidates.get(j).totVotes);
   			}
  		for(Voter w: votes)
  			System.out.println(w.ranking);
			//the above line helps us keep track of our input data!
  	}
  	/*
  	 * Below this line, I'm not sure is algorithmically correct,
  	 * however, it is for fringe cases of complicated ties in the data,
  	 * so I'm not particularly concerned about it.
  	 * It's included here for completeness.
  	 */
  	public int lowestCand(ArrayList<Candidate> candidates, int roundNum) {
  		int lowestCand=0;
  		for(int i=0; i < candidates.size(); i++)
  			if(candidates.get(i).totVotes < candidates.get(lowestCand).totVotes
  					&& candidates.get(i).eliminated == false)
  				lowestCand = i;
  		ArrayList<Candidate> lowtie = new ArrayList<Candidate>();
  		for(int j=0; j < candidates.size(); j++){
  			if(candidates.get(j).totVotes == candidates.get(lowestCand).totVotes
  					&& candidates.get(j).eliminated == false)
  				lowtie.add(candidates.get(j));
  		}
  		if(lowtie.size() > 1)
  			lowtie = lowtie(lowtie, lowestCand, roundNum);
  		else return lowestCand;
  		for(int k=0; k < candidates.size(); k++)
  			if(candidates.get(k).name.compareTo(lowtie.get(0).name) == 0)
  				return k;
  		return -2;
  	}
  	public ArrayList<Candidate> lowtie(ArrayList<Candidate> lowtie, int lowestCand, int roundNum){
  		ArrayList<Candidate> checked = new ArrayList<Candidate>();
		ArrayList<Candidate> checkeddupes = new ArrayList<Candidate>();
		tieBreaker(lowtie, roundNum);
			for(Candidate t: lowtie) {
				checked.add(t);
					for(Candidate u: checked){
						if(t.totVotes == u.totVotes && t.name.compareTo(u.name) != 0){
							checkeddupes.add(t);
							checkeddupes.add(u);
						}
					}
			}
			if(checkeddupes.isEmpty()){
		  		for(int i=0; i < checked.size(); i++){
		  			if(checked.get(i).totVotes < checked.get(lowestCand).totVotes
		  					&& checked.get(i).eliminated == false){
		  				ArrayList<Candidate> untied = new ArrayList<Candidate>();
		  				untied.add(checked.get(i));
		  				return untied;
		  			}
		  		}
			}
			else{
				int dupelow = 0;
				for(int dupe1=0; dupe1 < checkeddupes.size(); dupe1++)
					if(checkeddupes.get(dupe1).totVotes < checkeddupes.get(dupelow).totVotes)
						dupelow = dupe1;
				ArrayList<Candidate> dupelist = new ArrayList<Candidate>();
				dupelist.add(checkeddupes.get(dupelow));
				for(int dupe2=0; dupe2 < checkeddupes.size(); dupe2++)
					if(checkeddupes.get(dupe2).totVotes == checkeddupes.get(dupelow).totVotes)
						dupelist.add(checkeddupes.get(dupe2));
				lowtie(dupelist, lowestCand, roundNum);
			}
			return new ArrayList<Candidate>();
  	}
  	public void tieBreaker(ArrayList<Candidate> lowtie, int roundNum){
  		/**
  		 * for every candidate in lowtie,
  		 * for every voter whose vote has gone to them (?),
  		 * find their particular ranking of said candidate,
  		 * try to find
  		 * and increase their totVotes by 1.
  		 */
		for(Voter w: votes) {
  			for(int k=0; k < lowtie.size(); k++) {
				for(int r=roundNum+1; r < lowtie.size(); r++) {
					for(int s=0; s < w.ranking.size(); s++) {
						if(w.ranking.get(s) == r

							lowtie.get(s).totVotes++;
					}
				}
  			}
		}
  	}
}