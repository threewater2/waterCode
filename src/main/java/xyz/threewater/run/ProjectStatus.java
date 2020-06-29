package xyz.threewater.run;


import org.springframework.stereotype.Component;

@Component
public class ProjectStatus {
    private boolean isChangeBeforeLastBuild=true;

    public boolean isChangeBeforeLastBuild() {
        return isChangeBeforeLastBuild;
    }

    public void setChangeBeforeLastBuild(boolean changeBeforeLastBuild) {
        isChangeBeforeLastBuild = changeBeforeLastBuild;
    }
}
