package SMAP.assignment.QuizAppProject.Database;

import com.google.firebase.firestore.Exclude;

public interface IEntity {
    @Exclude
    String getEntityKey();
    @Exclude
    String getCollectionName();
}
