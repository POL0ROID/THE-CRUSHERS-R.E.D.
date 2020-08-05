package application;
import java.util.ArrayList;

import javafx.fxml.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.event.*;
import javafx.collections.*;

/* ResultsController populates and displays the results graph,
 * iterates through rounds,
 * and populates and displays again.
 */
public class ResultsController {
		//the axis holding the names of the candidates
		@FXML
		CategoryAxis Candidates = new CategoryAxis();

		//the axis holding the number of votes
		@FXML
		NumberAxis numVotes = new NumberAxis();

		@FXML
		BarChart<String,Number> bc = new BarChart<String,Number>(Candidates, numVotes);
		//dataset for barchart (fxml annotation not necessary?)
		@FXML
        XYChart.Series<String, Number> xychart = new XYChart.Series<String, Number>();

		ArrayList<Integer> totVotes;

		static int roundNum = 1;

		public void round() {
	        bc.setTitle("Round " + roundNum);
	        bc.setAnimated(false);
	        for(Candidate c : Main.model.getCandidates())
	        Candidates.getCategories().add(c.name);
	        resultsSetup();
		    for(Candidate c : Main.model.getCandidates()){
		        	xychart.getData().add(new XYChart.Data<String, Number>
		        	(c.name, c.totVotes));
		    }

		    bc.getData().add(xychart);
		}
		public void resultsSetup() {
	  		for (Voter v: Main.model.getVotes())
	  			for(int j=0; j < v.ranking.size(); j++){
	  				if(v.ranking.get(j)==1)
	  					Main.model.getCandidates().get(j).totVotes++;
	  				System.out.println(Main.model.getCandidates().get(j).name +
	  						Main.model.getCandidates().get(j).totVotes);
	   			}
	  		for(Voter w: Main.model.getVotes())
	  			System.out.println(w.ranking);
				//the above line helps us keep track of our input data!
	  	}

	  	/* As of v0.9.9 (Final),
	  	 * Below this line is a very inaccurate and incomplete vote tabulation algorithm.
	  	 * It was supposed to eliminate the candidate with the least number of votes,
	  	 * sequentially, using the ranked choice algorithm,
	  	 * until only one candidate is left.
	  	 * It does not function, and as such is not fully annotated.
	  	 * It is being left in to demonstrate the concept.
	  	 */

	  	//The below function should be called when an administrator hits the next round button.
  	public void resultsTabulate(ActionEvent event) {
  		++roundNum;
  		for(Voter w: Main.model.getVotes()) {
  			int lowestCand = 0;
  			// lowestCand = Main.model.lowestCand(Main.model.getCandidates(), roundNum);
  			Main.model.getCandidates().get(lowestCand).eliminated = true;
  			for(int k=0; k < Main.model.getCandidates().size(); k++) {
  				if(Main.model.getCandidates().get(k).eliminated == true) {
  						for(int r=roundNum; r < Main.model.getCandidates().size(); r++) {
  							boolean brake = false;
  							for(int s=0; s < Main.model.getCandidates().size(); s++) {
  								if(w.ranking.get(s) == r
  										&& Main.model.getCandidates().get(s).eliminated == false) {
  									brake = true;
  									Main.model.getCandidates().get(s).totVotes++;
  									break;
  								}
  							}
  							if(brake)
  								break;
  						}
  					Main.model.getCandidates().get(k).totVotes=-1;
  				}
  			}
  		}
  		 // very important! this print is the only way rounds 2+ are displayed currently.

  		for(Candidate c: Main.model.getCandidates())
  			System.out.println(c.totVotes);
  		bc.getData().clear();
	    for(int i=0; i < Main.model.getCandidates().size(); i++){
	        	xychart.getData().add(new XYChart.Data<String, Number>
	        	(Main.model.getCandidates().get(i).name,
	        			Main.model.getCandidates().get(i).totVotes));
	    }
  		bc.getData().add( xychart );
        bc.setTitle("Round " + roundNum);
  		bc.layout();
  	}
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
  		/*
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
						if(w.ranking.get(s) == r)

							lowtie.get(s).totVotes++;
					}
				}
  			}
		}
  	}
}
