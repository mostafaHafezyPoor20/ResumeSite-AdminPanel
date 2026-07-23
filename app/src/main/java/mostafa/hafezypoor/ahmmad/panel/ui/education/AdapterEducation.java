package mostafa.hafezypoor.ahmmad.panel.ui.education;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelEducation;

public class AdapterEducation extends RecyclerView.Adapter<AdapterEducation.ViewHolder> {
    private Context context;
    private List<ModelEducation>list;

    public AdapterEducation(Context context, List<ModelEducation> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_education,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDate());
        holder.description.setText(list.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(context,R.style.AppBottomSheetDialog);
                dialog.setContentView(R.layout.dialog_education);
                dialog.show();
                ((TextView)dialog.findViewById(R.id.title)).setText(" درحال ویرایش "+list.get(position).getTitle()+" هستید ! ");
                ((MaterialButton)dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iEvent.removeEducation(list.get(position).getId());
                        list.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                ((MaterialButton)dialog.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent=new Intent(context, EditEducation.class);
                        intent.putExtra("id",list.get(position).getId());
                        context.startActivity(intent);
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
        TextView title,date,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            description=itemView.findViewById(R.id.description);
        }
    }
    private IEvent iEvent;
    interface IEvent{
         void removeEducation(String id);
    }
}
