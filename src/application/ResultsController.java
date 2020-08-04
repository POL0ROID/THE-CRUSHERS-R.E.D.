package application;
import java.util.ArrayList;

import javafx.fxml.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.event.*;

public class ResultsController {
		//the axis holding the names of the candidates
		@FXML
		CategoryAxis Candidates = new CategoryAxis();

		//the axis holding the number of votes
		@FXML
		NumberAxis numVotes = new NumberAxis();

		@FXML
		BarChart<String,Number> bc = new BarChart<String,Number>(Candidates, numVotes);

		@FXML
        XYChart.Series<String, Number> xychart = new XYChart.Series<String, Number>();

		ArrayList<Voter> votes;
		ArrayList<Candidate> candidates;
		ArrayList<Integer> totVotes;

		static int roundNum = 1;

		public void initData(ArrayList<Candidate> candidates, ArrayList<Voter> votes){
			this.candidates = candidates;
			this.votes = votes;
			round();
		}
		public void round() {
	        bc.setTitle("Round 1");
	        resultsSetup(candidates, votes);
		    for(int i=0; i < candidates.size(); i++){
		        	xychart.getData().add(new XYChart.Data<String, Number>(candidates.get(i).name,
		        			candidates.get(i).totVotes));
		    }
		    bc.getData().add(xychart);
		}
  	public void resultsSetup(ArrayList<Candidate> candidates, ArrayList<Voter> votes) {
  		for (Voter v: votes)
  			for(int j=0; j < candidates.size(); j++){
  				if(v.ranking.get(j)==1)
  					candidates.get(j).totVotes++;
  				System.out.println(candidates.get(j).name);
  				System.out.println(candidates.get(j).totVotes);
   			}
  		for(Voter w: votes)
  			System.out.println(w.ranking);
			//the above line helps us keep track of our input data!
  	}
  	//The below function should be called when an administrator hits the next round button.
  	public void resultsTabulate(ActionEvent event) {
  		++roundNum;
  		for(Voter w: votes) {
  			int lowestCand = lowestCand(candidates);
  			candidates.get(lowestCand).eliminated = true;
  			for(int k=0; k < candidates.size(); k++) {
  				if(candidates.get(k).eliminated == true) {
  						for(int r=roundNum; r < candidates.size(); r++) {
  							boolean brake = false;
  							for(int s=0; s < candidates.size(); s++) {
  								if(w.ranking.get(s) == r
  										&& candidates.get(s).eliminated == false) {
  									brake = true;
  									candidates.get(s).totVotes++;
  									break;
  								}
  							}
  							if(brake)
  								break;
  						}
  					candidates.get(k).totVotes=-1;
  				}
  			}
  		}
  		/**
  		 * very important! this print is the only way rounds 2+ are displayed currently.
  		 */
  		for(Candidate c: candidates)
  			System.out.println(c.totVotes);
  		// bc.getData().clear();
  		// bc.layout();
  		// bc.getData().addAll( xychart );
  	}
  	/**
  	 * Below this line, I'm not sure is algorithmically correct,
  	 * however, it is for fringe cases of complicated ties in the data,
  	 * so I'm not particularly concerned about it.
  	 * It's included here for completeness.
  	 */
  	public int lowestCand(ArrayList<Candidate> candidates) {
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
  			lowtie = lowtie(lowtie, lowestCand);
  		else return lowestCand;
  		for(int k=0; k < candidates.size(); k++)
  			if(candidates.get(k).name.compareTo(lowtie.get(0).name) == 0)
  				return k;
  		return -2;
  	}
  	public ArrayList<Candidate> lowtie(ArrayList<Candidate> lowtie, int lowestCand){
  		ArrayList<Candidate> checked = new ArrayList<Candidate>();
		ArrayList<Candidate> checkeddupes = new ArrayList<Candidate>();
		tieBreaker(lowtie);
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
				lowtie(dupelist, lowestCand);
			}
			return new ArrayList<Candidate>();
  	}
  	public void tieBreaker(ArrayList<Candidate> lowtie){
  		for(Voter w: votes) {
	  			for(int k=0; k < lowtie.size(); k++) {
	  						for(int r=roundNum+1; r < lowtie.size(); r++) {
	  							for(int s=0; s < lowtie.size(); s++) {
	  								if(w.ranking.get(s) == r
	  										&& lowtie.get(s).eliminated == false)
	  									lowtie.get(s).totVotes++;
	  							}
	  						}
	  			}
			}

  	}
}
