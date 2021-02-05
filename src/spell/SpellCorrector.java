package spell;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {
    public Trie myDictionary;
    // private Set<String> possibleWords = new
    public Set<String> candidateWords = new TreeSet<>();
    //public Set<String> validWords = new TreeSet<>();
    public Set<String> secondStringWords = new TreeSet<>();

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
        candidateWords.clear();
        secondStringWords.clear();

        if (myDictionary.find(inputWord.toLowerCase()) != null) {
            return inputWord.toLowerCase();
        }
        else {
            // try making some candidate words (1st level)
            makeCandidateWords(inputWord, candidateWords);
            if (searchCandidateWords(candidateWords) == null) {
                //2nd level
                for(String s : candidateWords) {
                    makeCandidateWords(s, secondStringWords);
                }
                searchCandidateWords(secondStringWords);
                return searchCandidateWords(secondStringWords);
            }
            searchCandidateWords(candidateWords);
            return searchCandidateWords(candidateWords);
        }
    }

    public void makeCandidateWords(String inputWord, Set<String> candidateWords) {
        addDeletionEdit(inputWord, candidateWords);
        addTranspositionEdit(inputWord, candidateWords);
        addAlterationEdit(inputWord, candidateWords);
        addInsertionEdit(inputWord, candidateWords);
    }

    public String searchCandidateWords(Set<String> candidateWords) {
        Iterator<String> itr = candidateWords.iterator();
        String foundWord, validWord= null;
        Set<String> validWords = new TreeSet<>();

        int frequencyCount, currentFrequencyCount = 0;

        while(itr.hasNext()) {
            foundWord = itr.next();
            if (myDictionary.find(foundWord) != null) {
                validWords.add(foundWord);
            }
        }
        // Iterator<String> validItr = validWords.iterator();

        for (String possibleWord : validWords) {
            frequencyCount = myDictionary.find(possibleWord).getValue();
            if(frequencyCount > currentFrequencyCount) {
                validWord = possibleWord;
                currentFrequencyCount = frequencyCount;
            }
        }
        if (validWords.isEmpty()) {
            return null;
        }
        return validWord;
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
