package SMAP.assignment.QuizAppProject.Database;

import com.google.firebase.firestore.Exclude;

public interface IEntity<TKey> {
    @Exclude
    TKey getEntityKey();
}
