package spell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class SpellCorrector implements ISpellCorrector{

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        // Make the dictionary/trie object
        Trie Dictionary = new Trie();
        String word = "";
        // Open file used for dictionary
        //InputStream in = Files.newInputStream(Paths.get());
        BufferedReader readFile = new BufferedReader(new FileReader(dictionaryFileName));
        String currentLine = readFile.readLine();
        while (currentLine != null) {
            for (int i = 0; i < currentLine.length(); i++) {
                if (currentLine.charAt(i) == ' ') {
                    //word is over
                    //System.out.print(word);
                    Dictionary.add(word);
                    word = "";
                } else {
                    word = word + currentLine.charAt(i);
                }
            }
            currentLine = readFile.readLine();
        }

        // parse the words from the txt file and add them
        // to the dictionary via the Trie add method
        // until there are no more words in the text file.

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
