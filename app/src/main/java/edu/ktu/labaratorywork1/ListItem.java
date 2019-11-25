package edu.ktu.labaratorywork1;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

public class ListItem implements Serializable, Comparable<ListItem>{
    private String title;
    private int imagedId;
    private String description;
    private String[] dances;
    private String YoutubeLink;

    public ListItem(){

    }

    public ListItem(String title, int imagedId, String description){
        this.title = title;
        this.imagedId = imagedId;
        this.description = description;
    }

    public ListItem(String title, int imagedId, String description, String youtubeLink){
        this.title = title;
        this.imagedId = imagedId;
        this.description = description;
        this.YoutubeLink = youtubeLink;
    }

    public String getYoutubeLink() {
        return YoutubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        YoutubeLink = youtubeLink;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getImagedId(){
        return this.imagedId;
    }

    public void setImagedId(int imageId){
        this.imagedId = imageId;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String[] getListOfDances(){
        dances = description.split(", ");
        return dances;
    }

    @Override
    public int compareTo(ListItem o) {
        int comparison = this.title.compareTo(o.title);
        if(comparison > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
