package Game;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Score {
	public void Scores(String name,int score) {

        try {

            FileOutputStream fstream = new FileOutputStream("/Score.txt");
            DataOutputStream outputFile = new DataOutputStream(fstream);
            
            outputFile.writeUTF("Name:"+name);
            outputFile.writeUTF("Score:"+score);

            outputFile.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
