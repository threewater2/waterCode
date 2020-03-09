module xyz.threewater {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.apache.commons.io;
    requires richtextfx;
    requires reactfx;
    requires java.xml;
    requires fastjson;
    requires jsr305;
    requires jediterm.pty;
    requires pty4j;

    opens xyz.threewater to javafx.fxml;
    opens xyz.threewater.controller to javafx.fxml;
    opens xyz.threewater.common.component to javafx.fxml;
    exports xyz.threewater;
    exports xyz.threewater.dir;
    exports xyz.threewater.plugin.maven;
    exports xyz.threewater.console;
    exports xyz.threewater.editor;
    exports xyz.threewater.bar;
    exports xyz.threewater.controller;
}