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
        word = word.toLowerCase();
        // We always want to start at the root node
        Node currentNode = root;
        if (currentNode == null) {
            return null;
        }
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
