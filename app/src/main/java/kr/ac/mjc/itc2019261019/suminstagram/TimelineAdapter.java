package kr.ac.mjc.itc2019261019.suminstagram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    private List<Post> mPostList;

    public TimelineAdapter(List<Post> postList) {
        this.mPostList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTv;
        TextView textTv;
        ImageView imageIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTv = itemView.findViewById(R.id.username_tv);
            textTv = itemView.findViewById(R.id.text_tv);
            imageIv = itemView.findViewById(R.id.image_iv);
        }

        public void bind(Post post) {
            usernameTv.setText(post.getUsername());
            textTv.setText(post.getText());
            Glide.with(itemView.getContext()).load(post.getImageUri()).into(imageIv);
        }
    }
}
