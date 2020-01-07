module xyz.threewater {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.commons.io;


    opens xyz.threewater to javafx.fxml;
    opens xyz.threewater.controller to javafx.fxml;
    exports xyz.threewater;
    exports xyz.threewater.dir;
    exports xyz.threewater.plugin.maven;
    exports xyz.threewater.console;
    exports xyz.threewater.editor;
    exports xyz.threewater.bar;
    exports xyz.threewater.controller;
}