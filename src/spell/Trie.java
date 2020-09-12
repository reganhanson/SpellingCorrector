package spell;

public class Trie implements ITrie {
    Node root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        root = new Node();
        nodeCount = 1;
    }

    @Override
    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
     *
     * @param word the word being added to the trie
     */
    public void add(String word) {
        if (word.isEmpty()) {
            this.root.EOW = true;
            return;
        }
        Node currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            if (currentNode.children[word.charAt(i) - 'a'] == null) {
                // Create the node
                currentNode.children[word.charAt(i) - 'a'] = new Node();
                nodeCount++;
                // make that node the current Node
                currentNode = currentNode.children[word.charAt(i) - 'a'];
                // move along
            }
            else {
                currentNode = currentNode.children[word.charAt(i) - 'a'];
            }
        }
        currentNode.incrementValue();
        wordCount++;
    }

    @Override
    public INode find(String word) {
        // We always want to start at the root node
        Node currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            if (currentNode.children[word.charAt(i) - 'a'] == null) {
                return null;
            } else {
                currentNode = currentNode.children[word.charAt(i) - 'a'];
            }
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }
}
