package xyz.threewater.action;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.springframework.stereotype.Component;

/**
 * 当需要focus某个窗口的时候，就用则个触发
 */
@Component
public class FocusAction {

    private TabPane bottomTabPane;
    public void initial(TabPane bottomTabPane){
        this.bottomTabPane=bottomTabPane;
    }

    public void outPutWindowsFocus(){
        SingleSelectionModel<Tab> selectionModel = bottomTabPane.getSelectionModel();
        selectionModel.select(1);
    }
}
