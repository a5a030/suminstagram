package kr.ac.mjc.itc2019261019.suminstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.ac.mjc.itc2019261019.suminstagram.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    private FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final  User user = mUsers.get(position);

        holder.usernameTv.setText(user.getUsername());
        holder.fullNameTv.setText(user.getFullName());
        Glide.with(mContext).load(user.getImageUrl()).into(holder.profileIv);
        //isFollowing(user.getId(), holder.followBtn);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTv;
        TextView fullNameTv;
        CircleImageView profileIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTv = itemView.findViewById(R.id.username_tv);
            fullNameTv = itemView.findViewById(R.id.fullNameTv);
            profileIv = itemView.findViewById(R.id.propileIv);
        }
    }
}

