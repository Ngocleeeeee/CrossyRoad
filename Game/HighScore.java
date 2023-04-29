package Game;
import java.io.*;


public class HighScore {

    public void updateScores(int score) {
        int fileScore = readScore();

        if (score > fileScore) {
            fileScore = score;
        }

        try {

            FileOutputStream fstream = new FileOutputStream("/HScore.dat");
            DataOutputStream outputFile = new DataOutputStream(fstream);

            outputFile.writeInt(score);
            outputFile.writeInt(fileScore);
            outputFile.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public int readScore() {

        int fileScore = 0;

        boolean endOfFile = false;

        try {

            FileInputStream fstream = new FileInputStream("/HScore.dat");
            DataInputStream inputFile = new DataInputStream(fstream);

            while (!endOfFile) {
                try {

                    fileScore = inputFile.readInt();

                } catch (EOFException e) {

                    endOfFile = true;
                }
            }

            inputFile.close();

        } catch (IOException e) {
            try {

                FileOutputStream fstream = new FileOutputStream("HScore.dat");
                DataOutputStream outputFile = new DataOutputStream(fstream);

                outputFile.writeInt(0);

                outputFile.close();

            } catch (IOException ex) {

                e.printStackTrace();
            }
        }

        return fileScore;
    }

}
