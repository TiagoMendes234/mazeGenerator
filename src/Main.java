
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
            Algorithm a = new Algorithm(100,100);
        try {
            a.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
