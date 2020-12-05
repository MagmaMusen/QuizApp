package SMAP.assignment.QuizAppProject.Views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Quiz;
import SMAP.assignment.QuizAppProject.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    public interface IListItemClickedListener {
        void onListClicked(int index);
    }

    private IListItemClickedListener listener;

    //Data in adapter
    private List<Quiz> quizList;

    //Constructor
    public ListAdapter(IListItemClickedListener listener) {
        this.listener = listener;
    }

    //method for updating the quiz list
    public void updateQuizList(List<Quiz> lists) {
        quizList = lists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quizzes, parent, false);
        ListViewHolder vh = new ListViewHolder(v, listener);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        //sets the name of the quiz
        holder.txtQuizName.setText(quizList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (quizList == null) {
            return 0;
        }
        return quizList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Viewhodler UI widget references
        TextView txtQuizName;

        ListAdapter.IListItemClickedListener listener;

        public ListViewHolder(@NonNull View itemView, ListAdapter.IListItemClickedListener listItemClickedListener) {
            super(itemView);

            txtQuizName = itemView.findViewById(R.id.txtQuizName);
            listener = listItemClickedListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onListClicked(getAdapterPosition());
        }
    }
}

