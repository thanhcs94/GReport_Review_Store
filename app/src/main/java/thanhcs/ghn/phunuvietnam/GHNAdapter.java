package thanhcs.ghn.phunuvietnam;

/**
 * Created by ThanhCS94 on 10/18/17.
 * I'm HIDING. Don't waste your time to find me.
 */
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class GHNAdapter  extends RecyclerView.Adapter<GHNAdapter.MyViewHolder> {

    private List<Grating> moviesList;
    Activity activity;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textScore, textID, textComplain;
        public MyViewHolder(View view) {
            super(view);
            textScore = (TextView) view.findViewById(R.id.text_score);
            textID = (TextView) view.findViewById(R.id.text_id);
            textComplain = (TextView) view.findViewById(R.id.text_complain);
        }
    }


    public GHNAdapter(List<Grating> moviesList, Activity activity) {
        this.moviesList = moviesList;
        this.activity = activity;
    }

    public void replaceNew(List<Grating> lisRating){
        this.moviesList = lisRating;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rows, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Grating movie = moviesList.get(position);
        holder.textScore.setText(movie.getPoint()+"");
        int color = getColorRating(movie.getPoint());
        holder.textScore.setTextColor(color);
        if(!movie.getNote().isEmpty())
        holder.textComplain.setText(movie.getNote());
        else
            holder.textComplain.setText("No comment");

        String date =  movie.getCreatedDate();
        String date2[] = date.split("\\s+");
        holder.textID.setText("" + movie.getClientId() +" ["+date2[1]+" "+date2[2]+" "+date2[4]+"]");
    }

    private int getColorRating(int point) {
        int color = 0;
        switch (point){
            case 1:
                color = activity.getResources().getColor(R.color.one);
                break;
            case 2:
                color = activity.getResources().getColor(R.color.two);
                break;
            case 3:
                color = activity.getResources().getColor(R.color.three);
                break;
            case 4:
                color = activity.getResources().getColor(R.color.four);
                break;
            case 5:
                color = activity.getResources().getColor(R.color.five);
                break;
            default: return activity.getResources().getColor(R.color.one);
        }

        return color;
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}