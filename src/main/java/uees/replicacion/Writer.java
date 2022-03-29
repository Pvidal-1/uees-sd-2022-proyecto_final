package uees.replicacion;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    
    private int counter;
    private String filename;

    public Writer(String filename) {
        this.counter = 0;
        this.filename = filename;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getFilename() {
        return filename;
    }
    
    public void writeToLog(String httpMethod, String message) {
        try {
            FileWriter myWriter = new FileWriter(this.getFilename(), true);
            myWriter.write("(" + this.getCounter() + ") " + httpMethod + ": " + message + "\n");
            myWriter.close();
            this.setCounter(this.getCounter() + 1);
            System.out.println("\nSuccessfully wrote to log.\n");
        } catch (IOException e) {
            System.out.println("An error occurred writing to log.");
            e.printStackTrace();
        }
    }
}
