package xyz.threewater.enviroment;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 用来保存可以运行的java类
 * 存储新形式为全限定类名，绝对路径
 */
@Component
public class MainClassList {
    private final Logger logger= LoggerFactory.getLogger(MainClassList.class);

    private final JavaFxComponent javaFxComponent;

    //全限定类名作为键，文件名作为值
    private final Map<String,String> classNameMap=new HashMap<>();
    private Button mainClassButton;
    private VBox mainClassGroup;

    public MainClassList(JavaFxComponent javaFxComponent) {
        this.javaFxComponent = javaFxComponent;
    }

    public void initial(){
        this.mainClassButton=javaFxComponent.get("mainClassButton",Button.class);
        this.mainClassGroup =javaFxComponent.get("mainClassGroup",VBox.class);
        initialEvent();
    }
    public void addMainClass(String fileName,String fullClassName){
        this.classNameMap.put(fileName,fullClassName);
        mainClassButton.setText(fileName);
        Label label = new Label(fileName);
        Pane pane=new Pane();
        HBox.setHgrow(pane,Priority.ALWAYS);
        HBox hBox=new HBox(label,pane);
        hBox.getStyleClass().add("main-class-item");
        addItemClickEvent(hBox,fileName);
        mainClassGroup.getChildren().add(hBox);
    }

    /**
     * 点击按钮弹出下拉框，再次点击隐藏
     */
    private void initialEvent(){
        mainClassButton.setOnMouseClicked(event -> {
            if(mainClassGroup.isVisible()){
                mainClassGroup.setVisible(false);
                return;
            }
            double layoutX=mainClassButton.getLayoutX();
            double layoutY=mainClassButton.getLayoutY()+mainClassButton.getHeight();
            mainClassGroup.setLayoutX(layoutX);
            mainClassGroup.setLayoutY(layoutY);
            mainClassGroup.setVisible(true);
        });
    }


    /**
     * 当运行类候选列表被点击时，触发此事件
     */
    private void addItemClickEvent(HBox hBox,String fileName){
        hBox.setOnMouseClicked(e->{
            mainClassButton.setText(fileName);
            logger.debug("class item clicked !,{}",fileName);
            mainClassGroup.setVisible(false);
        });
    }

    /**
     * 返回当前可运行的java类
     */
    public Optional<String> getCurrentRunnableClass(){
        String fileName = mainClassButton.getText();
        if(fileName.equals("no runnable class")){
            return Optional.empty();
        }
        return Optional.of(classNameMap.get(fileName));
    }

}
