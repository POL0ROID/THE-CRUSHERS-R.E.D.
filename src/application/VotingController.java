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
import javafx.stage.Stage;


public class VotingController implements Initializable{
	@FXML
	ListView<String> Candidates;

	@FXML
	ListView<Integer> ranks;

	@FXML
	AnchorPane mainPane;

	//Lists holding the candidates and rankings
	ArrayList<Candidate> candidates = new ArrayList<Candidate>();
	ArrayList<Integer> ranking = new ArrayList<Integer>();
	ArrayList<Voter> votes = new ArrayList<Voter>();
	//candidate object
	Candidate cand;
	String password;
	String instructions;
	String after;
	public void initData(String password, ArrayList<Candidate> candidates, ArrayList<Voter> votes){
		this.password = password;
		this.candidates = candidates;
		this.votes = votes;
	}
	public void backData(String password, ArrayList<Candidate> candidates, ArrayList<Voter> votes, String instructions, String after){
		initData(password, candidates, votes);
		this.instructions = instructions;
		this.after = after;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			//variable to track the number of candidates
			int number = 0;
			//read in the file containing the candidates
			String filePath = new File("").getAbsolutePath();
			File file = new File(filePath + "/src/Candidates.txt");
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
				cand = new Candidate(name,number);
				//add it to the list of candidates (with ascending order)
				candidates.add(cand);
				//display the candidates and ranks
				items.add(name);
				numbers.add(number);
			}
			in.close();
			for(int j=0; j < candidates.size(); ++j){
				this.ranking.add(0);
			}
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
			int numRank = ranks.getSelectionModel().getSelectedItem();
			//set the candidates name
			cand.name = selectedCandidate;
			//add it to the list containing the voter's vote
			for(int i=0; i < candidates.size(); ++i)
				if(candidates.get(i).name == cand.name){
					this.ranking.get(i).equals(numRank);
					break;
				}
			//remove the candidate from the list view
			Candidates.getItems().remove(selectedCandidate);
			if(Candidates.getItems().isEmpty()){
				Voter vote = new Voter(candidates,ranking);
				votes.add(vote);
				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/password.fxml"));
					mainPane = loader.load();
					Scene scene = new Scene(mainPane);
					window.setScene(scene);
					passwordController pcon = loader.getController();
					pcon.initData(password, candidates, instructions, after, votes);
					window.show();

				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception e) {
			System.out.println("You must select a Candidate and rank to submit your vote.");
			e.printStackTrace();
			}

	}

}
