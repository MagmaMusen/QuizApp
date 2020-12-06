package SMAP.assignment.QuizAppProject.Views.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import SMAP.assignment.QuizAppProject.Database.Question;
import SMAP.assignment.QuizAppProject.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>{

    private Context context;
    private List<Question> questionList;
    private IQuestionItemClickedListener listener;

    public interface IQuestionItemClickedListener {
        void onQuestionClicked(Question question);
    }

    public QuestionAdapter(IQuestionItemClickedListener listener, Context context){
        this.listener = listener;
        this.context = context;
    }

    public void updateQuestionList(List<Question> list){
        questionList = list;
        //todo some image stuff?
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_question_item, parent, false);
        QuestionViewHolder vh = new QuestionViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question q = questionList.get(position);
        holder.txtThumbnail.setText(q.getQuestion());
        if(q.getImage() != null) {
            Glide.with(holder.imgThumbnail.getContext()).load(q.getImage()).into(holder.imgThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (questionList != null) {
            return questionList.size();
        } else {
            return 0;
        }
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgThumbnail;
        TextView txtThumbnail;

        IQuestionItemClickedListener listener;

        public QuestionViewHolder(@NonNull View itemView, IQuestionItemClickedListener questionItemClickedListener) {
            super(itemView);

            // Set views and listener.
            imgThumbnail = itemView.findViewById(R.id.imgQuestionThumbnail);
            txtThumbnail = itemView.findViewById(R.id.txtQuestionThumbnail);

            // This listener = might be unnecessary.
            listener = questionItemClickedListener;
            itemView.setOnClickListener(this);
        }

        // The listener interface function that returns the clicked index in the recyclerview.
        @Override
        public void onClick(View v) {
            listener.onQuestionClicked(questionList.get(getAdapterPosition()));
        }
    }
}
