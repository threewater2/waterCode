package xyz.threewater.common.component;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import xyz.threewater.resources.ImageResources;
public class ImageTextButton extends HBox {
    private String imagePath;
    private String text;

    public ImageTextButton(){}

    public ImageTextButton(String imagePath,String text){
        this.imagePath=imagePath;
        this.text=text;
    }

    public void createImageTextButton(){
        ImageView imageView= ImageResources.getInstance().
                getImageViewFromRelativePath(imagePath,true);
        Label label=new Label(text);
        this.getChildren().add(imageView);
        this.getChildren().add(label);
    }



    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
