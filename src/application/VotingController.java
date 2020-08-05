package application;
import java.io.*;

import java.net.URL;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/* VotingController gathers input from the view,
 * and forwards it to the rest of the program.
 */
public class VotingController implements Initializable{
	/* these types of annotated statements serve two purposes:
	 * to introduce JavaFX elements to Java so that it can program them,
	 * and to show JavaFX what programming to look for via the assigned object names.
	 */
	@FXML
	ListView<String> Candidates;

	@FXML
	ListView<Integer> ranks;

	@FXML
	AnchorPane mainPane;

	@FXML
	Text t = new Text();


	//Lists holding the candidates and rankings
	public static ArrayList<Candidate> candidates = new ArrayList<Candidate>();
	public static ArrayList<Voter> votes = new ArrayList<Voter>();
	public static ArrayList<Integer> ranking = new ArrayList<Integer>();
	String password;
	String instructions;
	String after;
	//this method takes in data from elsewhere
	public void initData(String password, String instructions, String after){
		this.password = password;
		this.instructions = instructions;
		this.after = after;
		t.setText(instructions);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			boolean populate = false;
			//variable to track the number of candidates
			int number = 0;
			//read in the file containing the candidates
			File file = new File(Main.model.filePath + "/src/Candidates.txt"); //safe pathing
			Scanner in = new Scanner(file);
			//use this to display candidates in ListView
			ObservableList<String> items = FXCollections.observableArrayList();
			Candidates.setItems(items);
			//use this to display ranks in ListView
			ObservableList<Integer> numbers = FXCollections.observableArrayList();
			ranks.setItems(numbers);
			//saves the candidates to the Array list
			while(in.hasNextLine()) {
				String name = in.nextLine();
				number = number + 1;
				//declare the candidate
				if(Main.model.isEmpty())
					populate = true;
				if(name != null && populate){
					Candidate cand = new Candidate(name);
					//add it to the list of candidates (with ascending order)
					candidates.add(cand);
				}
				//display the candidates and ranks
				items.add(name);
				numbers.add(number);
			}
			in.close();
			Main.model.populateCandidates(candidates); //adds the gathered Candidate objects
			if(ranking.isEmpty())
				for(int j=0; j < candidates.size(); ++j){
					ranking.add(0);
				}
			/*This just gets ranking to an operable and empty size,
			 * since assigning votes/ranks to candidates is order-based.
			 */
			populate = false;
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("There is something wrong :(");
		}
	}

	/* Method: submit
	 * use: goes to the password scene and submits the ranking for the particular candidate
	 */
	public void submit(ActionEvent event) throws IOException{
		try {
			//get the selected candidate and rank from list view
			String selectedCandidate = Candidates.getSelectionModel().getSelectedItem();
			int selectedRank = ranks.getSelectionModel().getSelectedItem();
			System.out.println(candidates.size());
			//set the candidates name
			//add it to the list containing the voter's vote
			for(int i=0; i < candidates.size(); ++i){
				if(candidates.get(i).name.compareTo(selectedCandidate) == 0){
					System.out.println(i + " " + selectedRank);
					ranking.set(i, selectedRank);
					break;
				}
			}
			System.out.println(ranking.size());
			//remove the candidate from the list view
			Candidates.getItems().remove(selectedCandidate);
			if(Candidates.getItems().isEmpty()){
				try{
					ArrayList<Integer> ranking2 = new ArrayList<Integer>();
					for(int q=0; q < ranking.size(); ++q)
						ranking2.add(ranking.get(q));
					//gets a non-static list from the static list apparently
					Main.model.addVotes(new Voter(ranking2));//adds the finalized vote to the arraylist
					Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
					FXMLLoader loader = new FXMLLoader();
					FileInputStream stream =
							new FileInputStream(new File("src/resources/password.fxml"));
					mainPane = loader.load(stream);
					Scene scene = new Scene(mainPane);
					window.setScene(scene);
					passwordController pcon = loader.getController();
					pcon.initData(password, instructions, after, ranking);
					window.show(); //same as in main, essentially
				}
				catch(Exception e){
					System.out.println("Something's up with password.fxml.");
					e.printStackTrace();
				}
			}
		}
		catch(Exception e) {
			System.out.println("You must select a Candidate and rank to submit your vote.");
		}

	}
}