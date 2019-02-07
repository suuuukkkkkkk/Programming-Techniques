package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadFile {

    // used to hold each records
    public ArrayList<String> records;

    /**
     * Constructor
     */
    public ReadFile(){
        records = new ArrayList<>();
    }

    /**
     * To read each line of a text-based file
     */
    public ArrayList<String> readRecords(String file) {
        BufferedReader br = null;

        try {

            String currentLine;

            br = new BufferedReader(new FileReader(file));

            while ((currentLine = br.readLine()) != null) {
                records.add(currentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return this.records;
    }
}
