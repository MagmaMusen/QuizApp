package SMAP.assignment.QuizAppProject.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public interface ISearchItemClickedListener{
        void onSearchClicked(int index);
        void addQuiz(Quiz quiz);
    }

    private ISearchItemClickedListener listener;

    //Data in adapter
    private List<Quiz> quizList;

    public SearchAdapter(ISearchItemClickedListener listener){
        this.listener = listener;
    }

    public void updateQuizList(List<Quiz> lists){
        quizList = lists;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quizzes_search, parent, false);
        SearchViewHolder vh = new SearchViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.txtQuizName.setText(quizList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtQuizName;
        Button btnAdd;

        ISearchItemClickedListener listener;

        //Constructor
        public SearchViewHolder(@NonNull View itemView, final ISearchItemClickedListener searchItemClickedListener){
            super(itemView);

            txtQuizName = itemView.findViewById(R.id.txtQuizName);
            btnAdd = itemView.findViewById(R.id.btnQuizSearchAdd);
            listener = searchItemClickedListener;

            itemView.setOnClickListener(this);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.addQuiz(quizList.get(getAdapterPosition()));
                }
            });
        }

        @Override
        public void onClick(View v) {
            listener.onSearchClicked(getAdapterPosition());
        }
    }
}
