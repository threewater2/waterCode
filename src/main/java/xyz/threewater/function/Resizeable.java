package xyz.threewater.function;

import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
/**
 * 可以使控件拖拽调整大小
 */
public interface Resizeable {

    Pair<Boolean,Boolean> edgeClickStatus=new Pair<>(false,false);

    default void resizeableWidth(Control control,double edgeWidth){
        changeCursorStyleAtEdge(control,edgeWidth,true);
        changeEdgeClickStatus(control,edgeWidth,true);
        resize(control,true);
    }

    default void resizeableHeight(Control control,double edgeHeight){
        changeCursorStyleAtEdge(control,edgeHeight,false);
        changeEdgeClickStatus(control,edgeHeight,false);
        resize(control,false);
    }


    private void changeCursorStyleAtEdge(Control control,double scrollWeight,boolean isX){
        control.setOnMouseMoved(mouseEvent -> {
            double controlPos = getControlPos(control, isX);
            double mousePos = getMousePos(mouseEvent, isX);
            if(isMouseAtEdge(controlPos,mousePos,scrollWeight)){
                Cursor cursor=isX?Cursor.E_RESIZE:Cursor.N_RESIZE;
                control.setCursor(cursor);
            }else {
                control.setCursor(Cursor.DEFAULT);
            }
        });
    }

    private void changeEdgeClickStatus(Control control,double scrollWeight,boolean isX){
        control.setOnMousePressed(mouseEvent -> {
            double controlPos = getControlPos(control, isX);
            double mousePos = getMousePos(mouseEvent, isX);
            if(isMouseAtEdge(controlPos,mousePos,scrollWeight)){
                if(isX){
                    edgeClickStatus.setKey(true);
                }else {
                    edgeClickStatus.setValue(true);
                }
            }
        });
        control.setOnMouseReleased(mouseEvent -> {
            if(isX){
                edgeClickStatus.setKey(false);
            }else {
                edgeClickStatus.setValue(false);
            }
        });
    }

    private double getControlPos(Control control,boolean isX){
        return isX?control.getLayoutBounds().getWidth():
                control.getScene().getHeight()-control.getLayoutY();
    }

    private double getMousePos(MouseEvent mouseEvent,boolean isX){
        Control control=(Control)mouseEvent.getSource();
        return isX?mouseEvent.getX():
                control.getScene().getHeight()-mouseEvent.getSceneY();
    }

    private void resize(Control control,boolean isX){
        control.setOnMouseDragged(mouseEvent -> {
            boolean clickStatus=isX?edgeClickStatus.getKey():edgeClickStatus.getValue();
            if(!clickStatus) return;
            if(isX){
                control.setPrefWidth(mouseEvent.getX());
            }else {
                double newHeight=control.getScene().getHeight()-mouseEvent.getSceneY();
                control.setPrefHeight(newHeight);
            }
        });
    }

    private boolean isMouseAtEdge(double controlPos,double cursorPos,double scrollWeight) {
        return cursorPos <= controlPos && cursorPos > (controlPos - scrollWeight);
    }

}
