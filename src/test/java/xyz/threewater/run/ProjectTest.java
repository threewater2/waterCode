package xyz.threewater.run;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
public class ProjectTest {

    @Resource(name = "MavenProject")
    private JavaProject project;

    @Value("${project.compilePath}")
    private String outPath;


    @Test
    public void test(){
        new File(outPath).delete();
        project.build();
        assertTrue(new File(outPath+"/xyz/threewater/WaterCode.class").exists());
        assertTrue(new File(outPath+"/application.properties").exists());
    }

    @Test
    public void copyResourceTest(){
        project.copyResource();
    }

}
