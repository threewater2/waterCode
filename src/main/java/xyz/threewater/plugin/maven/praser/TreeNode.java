package xyz.threewater.plugin.maven.praser;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
class TreeNode<T> implements Iterable<TreeNode<T>>{
    private TreeNode<T> parent;
    private List<TreeNode<T>> children=new LinkedList<>();
    private T value;

    public TreeNode(T value){
        children=new LinkedList<>();
    }

    public TreeNode(){
    }

    public void addChild(TreeNode<T> childNode){
        this.children.add(childNode);
    }


    @Override
    @Nonnull
    public Iterator<TreeNode<T>> iterator() {
        return children.iterator();
    }


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }
}
