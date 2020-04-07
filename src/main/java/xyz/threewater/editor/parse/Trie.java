package xyz.threewater.editor.parse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie<V> {

    private Node<V> root=new Node<>();

    public V get(String key){
        Node<V> node = findNode(root, key);
        if(node!=null){
            return node.getValue();
        }
        return null;
    }

    public List<String> startWith(String prefix){
        List<String> list=new ArrayList<>();
        Node<V> prefixNode = findNode(root, prefix);
        if(prefixNode==null) return list;
        findSubNodeStr(prefix,list,prefixNode);
        return list;
    }

    private Node<V> findNode(Node<V> node, String prefix){
        HashMap<Character, Node<V>> children = node.getChildren();
        for(char c:children.keySet()){
            if(prefix.charAt(0)==c){
                Node<V> next = children.get(c);
                if(prefix.length()==1)
                    return next;
                else
                    return findNode(next,prefix.substring(1));
            }
        }
        return null;
    }

    private void findSubNodeStr(String subString,List<String> prefixList,Node<V> prefixNode){
        HashMap<Character, Node<V>> children = prefixNode.getChildren();
        for(char c: children.keySet()){
            //不破坏上层的栈结构，让字符串变成值传递
            String next= subString + c;
            Node<V> nextNode=children.get(c);
            if(nextNode.isWord()){
                prefixList.add(next);
            }
            findSubNodeStr(next,prefixList,nextNode);
        }
    }


    public void add(String key,V value){
        addNode(root,key,value);
    }

    private void addNode(Node<V> root,String key,V value){
        HashMap<Character, Node<V>> children = root.getChildren();
        Node<V> node;
        char c=key.charAt(0);
        if (children.containsKey(c)) {
            node=children.get(c);
            //repeat
            if(key.length()==1){
                return;
            }
        }else {
            node=new Node<>();
            children.put(c,node);
            //not repeat but end
            if(key.length()==1){
                node.setValue(value);
                node.setWord(true);
                return;
            }
        }
        addNode(node,key.substring(1),value);
    }

    public void remove(String key){
        Node<V> node = findNode(root, key);
        if(node!=null&&node.isWord()){
            node.setWord(false);
            node.setValue(null);
        }
    }

    private static class Node<V> {
        private V value=null;
        private boolean isWord=false;
        private HashMap<Character,Node<V>> children=new HashMap<>();

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean word) {
            isWord = word;
        }

        public HashMap<Character, Node<V>> getChildren() {
            return children;
        }

    }
}
