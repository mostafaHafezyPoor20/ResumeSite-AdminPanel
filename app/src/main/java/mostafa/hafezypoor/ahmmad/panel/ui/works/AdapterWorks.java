package mostafa.hafezypoor.ahmmad.panel.ui.works;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelWork;

public class AdapterWorks extends RecyclerView.Adapter<AdapterWorks.ViewHolder> {
    private Context context;
    private List<ModelWork>list;

    public AdapterWorks(Context context, List<ModelWork> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_works,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.title.setText(list.get(position).getTitle());
      holder.description.setText(list.get(position).getDescription());
     Picasso.get().load(list.get(position).getImage()).error(R.drawable.icon).into(holder.image);
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             BottomSheetDialog dialog = new BottomSheetDialog(context,R.style.AppBottomSheetDialog);
             dialog.setContentView(R.layout.dialog_works);
             ((ImageView)dialog.findViewById(R.id.image)).setImageDrawable(holder.image.getDrawable());
             ((TextView)dialog.findViewById(R.id.title)).setText(list.get(position).getTitle());
             dialog.show();
             ((MaterialButton)dialog.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     dialog.dismiss();
                     Intent intent=new Intent(context,EditWork.class);
                     intent.putExtra("id",list.get(position).getId());
                     context.startActivity(intent);
                 }
             });
             ((MaterialButton)dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     iEvent.removeWork(list.get(position).getId());
                     dialog.dismiss();
                     list.remove(position);
                     notifyDataSetChanged();
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
        TextView title,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
        }
    }
    private IEvent iEvent;
    interface IEvent{
        void removeWork(String id);
    }
}
