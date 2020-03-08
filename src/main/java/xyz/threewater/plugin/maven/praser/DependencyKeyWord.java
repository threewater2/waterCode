package xyz.threewater.plugin.maven.praser;

public enum DependencyKeyWord {

    GROUP_ID("groupId"),
    ARTIFACT_ID("artifactId"),
    VERSION("version"),
    CLASSIFIER("classifier");


    DependencyKeyWord(String word){
        this.word=word;
    }
    private String word;

    @Override
    public String toString() {
        return word;
    }
}