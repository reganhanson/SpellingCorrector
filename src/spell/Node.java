package spell;

public class Node implements INode{
    public int wordCount;
    public int wordFrequency;
    public boolean EOW;
    public char letter;
    public Node[] children;
    // Node constructor
    public Node() {
        this.wordCount = 0;
        this.letter = 0;
        this.EOW = false;
        children = new Node[26];
    }
    @Override
    public int getValue() {
        return wordFrequency;
    }

    @Override
    public void incrementValue() {
        wordFrequency++;
    }

    @Override
    public INode[] getChildren() {
        return new INode[0];
    }
}
