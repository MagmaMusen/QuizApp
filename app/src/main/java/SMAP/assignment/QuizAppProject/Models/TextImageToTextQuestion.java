package SMAP.assignment.QuizAppProject.Models;

import android.media.Image;
import android.view.View;

public class TextImageToTextQuestion implements Question {

    public View getView(){

        return null;
    }



    @Override
    public String getThumbnailText() {
        return null;
    }

    @Override
    public String getThumbnailImageUrl() {
        String tempImage = "https://upload.wikimedia.org/wikipedia/commons/7/74/GeorgeClooneyHWoFJan12_%28headshot%29.jpg";
        return tempImage;
    }


    @Override
    public boolean getAnswerStatus() {
        return false;
    }
}
