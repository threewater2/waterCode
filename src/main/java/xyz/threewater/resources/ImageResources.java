package xyz.threewater.resources;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;
import xyz.threewater.utils.FileUtil;
import xyz.threewater.utils.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageResources {

    private static ImageResources imageResources =new ImageResources();
    private Map<String, ImageView> svgPathMap=new HashMap<>();

    private ImageResources(){}
    public static ImageResources getInstance(){
        return imageResources;
    }

    public ImageView getImageView(String imagePath,boolean isNew){
        if(svgPathMap.get(imagePath)==null||isNew){
            try {
                InputStream inputStream = new FileInputStream(new File(imagePath));
                Image image=new Image(inputStream);
                ImageView imageView=new ImageView(image);
                svgPathMap.put(imagePath,imageView);
                return imageView;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return svgPathMap.get(imagePath);
    }

    public ImageView getImageViewFromRelativePath(String relativePath, boolean isNew){
        String absolutePath = PathUtil.getResourceFromClassPath(relativePath);
        return getImageView(absolutePath,isNew);
    }
}
