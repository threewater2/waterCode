package xyz.threewater.action;

import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.threewater.build.JavaProjectBuilder;
import xyz.threewater.event.FileSaver;
import xyz.threewater.run.JavaProjectRunner;

@Component
public class RunAndBuildAction {
    private final Logger logger= LoggerFactory.getLogger(RunAndBuildAction.class);
    private final JavaProjectBuilder javaProjectBuilder;
    private final JavaProjectRunner javaProjectRunner;
    private final FileSaver fileSaver;
    private BuildStatus buildStatus=BuildStatus.DIRTY;
    private Thread buildThread;
    public RunAndBuildAction(JavaProjectBuilder javaProjectBuilder, JavaProjectRunner javaProjectRunner, FileSaver fileSaver) {
        this.javaProjectBuilder = javaProjectBuilder;
        this.javaProjectRunner = javaProjectRunner;
        this.fileSaver = fileSaver;
    }


    public void buildProject(){
        buildStarted();
        buildStatus=BuildStatus.BUILDING;
        buildThread=new Thread(new Task<Void>() {
            @Override
            protected Void call() {
            javaProjectBuilder.buildProject();
            buildFinished();
            buildStatus=BuildStatus.CLEAN;
            return null;
            }
        });
        buildThread.setDaemon(true);
        buildThread.start();
    }

    /**
     * 运行当前项目
     */
    public void runProject(String className,String...jvmArg){
        //如果源码脏了，就需要重新build
        if(buildStatus==BuildStatus.DIRTY){
            logger.debug("project is dirty");
            runAndBuildProject(className, jvmArg);
        }else {
            logger.debug("project is clean");
            Thread thread=new Thread(new Task<Void>() {
                @Override
                protected Void call() throws InterruptedException {
                    //等待build线程运行结束
                    if(buildThread!=null&&buildThread.isAlive()){
                        buildThread.join();
                    }
                    logger.debug("run started: fullClassName is:{}",className);
                    javaProjectRunner.runProject(className,jvmArg);
                    buildAndRunFinished();
                    return null;
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void runAndBuildProject(String className,String...jvmArg){
        projectBuildAndRunStarted();
        Thread thread=new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                logger.debug("build started!");
                buildStatus=BuildStatus.BUILDING;
                javaProjectBuilder.buildProject();
                buildStatus=BuildStatus.CLEAN;
                logger.debug("run started: fullClassName is:{}",className);
                javaProjectRunner.runProject(className,jvmArg);
                buildAndRunFinished();
                return null;
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void projectBuildAndRunStarted(){
        fileSaver.saveFile();
    }


    public void buildAndRunFinished(){

    }

    public void buildStarted(){

    }

    public void buildFinished(){

    }

    enum BuildStatus{
        CLEAN,DIRTY, BUILDING
    }
}
