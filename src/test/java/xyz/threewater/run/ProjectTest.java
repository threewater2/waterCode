package xyz.threewater.run;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ProjectTest {

    @Resource(name = "mavenProject")
    private Project project;


    @Test
    public void test(){
        project.build();
        project.run("xyz.threewater.TestRun");
    }
}
