package spell;

import javax.print.DocFlavor;
import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {
    public Trie myDictionary;
    // private Set<String> possibleWords = new
    public Set<String> candidateWords = new TreeSet<String>();

    public SpellCorrector() {
        myDictionary = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File txtFile = new File(dictionaryFileName);    // will give pointer to file object
        Scanner scanFile = new Scanner(txtFile);        // Send the txt file to the scanner file
        while (scanFile.hasNext()) {                    // scans until end of file
            myDictionary.add(scanFile.next());          // send the scanned word (goes until white space) to the dictionary
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if (myDictionary.find(inputWord.toLowerCase()) != null) {
            return inputWord.toLowerCase();
        }
        else {
            return null;
        }
    }

    public void makeCandidateWords(String inputWord, Set<String> candidateWords) {
        /*addDeletionEdit(inputWord);
        addTranspositionEdit(inputWord);
        addAlterationEdit(inputWord);
        addInsertionEdit(inputWord);*/
    }

    public String searchCandidateWords() {
        return null;
    }

    public void addDeletionEdit(String inputWord, Set<String> addWords) {
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder word = new StringBuilder(inputWord);
            addWords.add(word.deleteCharAt(i).toString());
        }
    }

    public void addTranspositionEdit(String inputWord, Set<String> addWords) {
        for (int i = 0; i < inputWord.length() - 1; i++) {
            StringBuilder word = new StringBuilder(inputWord);
            char first = inputWord.charAt(i);
            char second = inputWord.charAt(i+1);
            word.setCharAt(i, second);
            word.setCharAt((i+1), first);
            addWords.add(word.toString());
        }
    }

    public void addInsertionEdit(String inputWord, Set<String> addWords) {
        char a;
        for (int i = 0; i < inputWord.length() + 1; i++) {
            for (int j = 97; j < 123; j++) {
                StringBuilder word = new StringBuilder(inputWord);
                a = (char)j;
                word.insert(i, a);
                addWords.add(word.toString());
            }
        }
    }

    public void addAlterationEdit(String inputWord, Set<String> addWords) {
        char a;
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder word = new StringBuilder(inputWord);
            for (int j = 97; j < 123; j++) {
                a = (char)j;
                word.setCharAt(i, a);
                addWords.add(word.toString());

            }
        }
    }

}
