package spell;

public class Trie implements ITrie {
    private final Node root;
    private int wordCount;
    private int nodeCount;
    private boolean stay = true;
    private int codeNumber = 0;

    public Trie() {
        root = new Node();
        nodeCount = 1;
        wordCount = 0;
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase();
        if (word.isEmpty()) {
            this.root.EOW = true;
            return;
        }
        Node currentNode = root;
        //root.EOW = false;
        for (int i = 0; i < word.length(); i++) {
            if (currentNode.children[word.charAt(i) - 'a'] == null) {
                // Create the node
                currentNode.children[word.charAt(i) - 'a'] = new Node();
                nodeCount++;
                // make that node the current Node
                // move along
            }
            currentNode = currentNode.children[word.charAt(i) - 'a'];
        }
        currentNode.incrementValue();
        if (currentNode.wordFrequency <= 1) {
            wordCount++;
        }
    }

    @Override
    public INode find(String word) {
        /*word = word.toLowerCase();
        // We always want to start at the root node
        Node currentNode = root;
        // If the root is null, the trie hasn't been created...
        /*if (currentNode == null) {
            return null;
        }
        //if the next letter is there move down in the trie and look for the next letter
        for (int i = 0; i < word.length(); i++) {
            if (currentNode.children[(int) (word.charAt(i) - 'a')] == null) {
                return null;
            } else {
                currentNode = currentNode.children[(int) (word.charAt(i) - 'a')];
            }
        }
        // If it hasn't returned null already, then either the word is there
        // I.e. current node value is 1 or greater
        // or that word isn't there but a word continuing from there is'
        //return currentNode;
        if (currentNode != null && currentNode.getValue() >= 1) {
            return currentNode;
        }
        return null;*/
        Node currentNode = root;
        if (word == null) {
            return null;
        }
        for (int i = 0; i < word.length(); i++) {
            if (currentNode.children[word.charAt(i) - 'a'] == null) {
                return null;
            }
            else {
                currentNode = currentNode.children[word.charAt(i) - 'a'];
            }
        }
        if (currentNode != null && currentNode.getValue() > 0) {
            return currentNode;
        }

        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        // traverse through all the words in the trie

        StringBuilder word = new StringBuilder();
        StringBuilder printWord = new StringBuilder();

        toStringHelper(root, word, printWord);


        return printWord.toString();
    }

    @Override
    public int hashCode() {
        recursiveHash(root);
        //codeNumber = codeNumber - nodeCount;
        int codeNumber2;
        codeNumber2 = codeNumber - (2 * wordCount) + nodeCount;
        codeNumber = 0;
        return codeNumber2;
    }

    private void recursiveHash(Node currentNode) {
        for (int i = 0; i < 26; i++) {
            if (currentNode.children[i] != null) {
                if (currentNode == root) {
                    codeNumber = codeNumber + (17 * i);
                }
                codeNumber = codeNumber + (31 * i);
                //recursiveHash(currentNode.children[i]);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        // Error checking to make sure that o is a Trie
        if (o == null) {
            return false;
        }
        if (!(o instanceof Trie)) {
            return false;
        }
        if (this.wordCount != ((Trie) o).wordCount) {
            return false;
        }
        if (this.nodeCount != ((Trie) o).nodeCount) {
            return false;
        }
        // if none of these are false, then recursively search the trees for similarities.
        Node compareNode = ((Trie) o).root;
        equalsHelper(this.root, compareNode);
        return stay;
    }

    private void equalsHelper(Node currentNode, Node compareNode) {
        if (stay) {
            if (currentNode.getValue() != compareNode.getValue()) {
                stay = false;
            }
            for (int i = 0; i < 26; i++) {
                if (currentNode.children[i] != null && compareNode.children[i] != null) {
                    if (currentNode.children[i].getValue() == compareNode.children[i].getValue()) {
                        equalsHelper(currentNode.children[i], compareNode.children[i]);
                    } else {
                        stay = false;
                    }
                } else if (currentNode.children[i] != null && compareNode.children[i] == null) {
                    stay = false;
                } else if (currentNode.children[i] == null && compareNode.children[i] != null) {
                    stay = false;
                }
            }
        }
    }

    private void toStringHelper(Node currentNode, StringBuilder word, StringBuilder printWord) {
        char letter;
        if (currentNode == null) {
            return;
        }
        if (currentNode.getValue() > 0) {
            printWord.append(word);
                    printWord.append("\n");
        }

        for (int i = 0; i < 26; i++) {
            if (currentNode.children[i] != null) {
                // add letter to word
                letter = (char) (i + 'a');
                word.append(letter);
                toStringHelper(currentNode.children[i], word, printWord);
                word.deleteCharAt(word.length() - 1);
            }
        }
    }
}
