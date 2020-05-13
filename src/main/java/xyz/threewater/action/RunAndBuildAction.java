package xyz.threewater.action;

import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.build.JavaProjectBuilder;
import xyz.threewater.run.JavaProjectRunner;

@Component
public class RunAndBuildAction {
    private final Logger logger= LoggerFactory.getLogger(RunAndBuildAction.class);
    private final JavaProjectBuilder javaProjectBuilder;
    private final JavaProjectRunner javaProjectRunner;
    public RunAndBuildAction(JavaProjectBuilder javaProjectBuilder, JavaProjectRunner javaProjectRunner) {
        this.javaProjectBuilder = javaProjectBuilder;
        this.javaProjectRunner = javaProjectRunner;
    }


    public void buildProject(){
        buildStarted();
        Thread thread=new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                javaProjectBuilder.buildProject();
                buildFinished();
                return null;
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void runAndBuildProject(String className){
        projectBuildAndRunStarted();
        Thread thread=new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                logger.debug("build started!");
                javaProjectBuilder.buildProject();
                logger.debug("run started: fullClassName is:{}",className);
                javaProjectRunner.runProject(className);
                buildAndRunFinished();
                return null;
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void projectBuildAndRunStarted(){

    }


    public void buildAndRunFinished(){

    }

    public void buildStarted(){

    }

    public void buildFinished(){

    }
}
