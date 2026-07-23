package mostafa.hafezypoor.ahmmad.panel.ui.blog;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelBlog;

public class AdapterBlog extends RecyclerView.Adapter<AdapterBlog.ViewHolder> {
    private Context context;
    private List<ModelBlog>list;

    public AdapterBlog(Context context, List<ModelBlog> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_blog,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Picasso.get().load(list.get(position).getImage()).into(holder.image);
      holder.title.setText(list.get(position).getTitle());
      holder.description.setText(list.get(position).getDescription());
      holder.date.setText(list.get(position).getDate());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              BottomSheetDialog dialog=new BottomSheetDialog(context,R.style.AppBottomSheetDialog);
              dialog.setContentView(R.layout.dialog_blog);
              dialog.show();
              ((ImageView)dialog.findViewById(R.id.image)).setImageDrawable(holder.image.getDrawable());
              ((TextView)dialog.findViewById(R.id.title)).setText(list.get(position).getTitle());
              ((MaterialButton)dialog.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent=new Intent(context,EditBlog.class);
                      intent.putExtra("id",list.get(position).getId());
                      context.startActivity(intent);
                  }
              });
              ((MaterialButton)dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      iEvent.removeBlog(list.get(position).getId());
                      list.remove(position);
                      notifyDataSetChanged();
                      dialog.dismiss();
                  }
              });
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
       ImageView image;
       TextView date,title,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            date=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
        }
    }
    private IEvent iEvent;
    interface IEvent{
        void removeBlog(String id);
    }
}
