package com.game.moteur;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReaderDict {

    private final String fileName;

    public FileReaderDict(String fileName) {
        this.fileName = fileName;
    }

    public List<String> read() throws IOException {
        List<String> words = new ArrayList<>();
        // File path is passed as parameter
        File file = new File(fileName);

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Declaring a string variable
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null)
            words.add(st);
        return words;
    }
}
