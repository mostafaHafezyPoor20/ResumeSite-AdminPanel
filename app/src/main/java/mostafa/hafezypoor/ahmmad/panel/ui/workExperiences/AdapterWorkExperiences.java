package mostafa.hafezypoor.ahmmad.panel.ui.workExperiences;

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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;

public class AdapterWorkExperiences extends RecyclerView.Adapter<AdapterWorkExperiences.ViewHolder> {
    private Context context;
    private List<ModelExperiences>list;

    public AdapterWorkExperiences(Context context, List<ModelExperiences> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_work_experiences,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.title.setText(list.get(position).getTitle());
       holder.date.setText(list.get(position).getDate());
       holder.description.setText(list.get(position).getDescription());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context,R.style.AppBottomSheetDialog);
               bottomSheetDialog.setContentView(R.layout.dialog_work_experiences);
               bottomSheetDialog.show();
               ((TextView)bottomSheetDialog.findViewById(R.id.title)).setText(list.get(position).getTitle());
               ((MaterialButton)bottomSheetDialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       bottomSheetDialog.dismiss();
                       iEvent.removeWorkExperiences(list.get(position).getId());
                       list.remove(position);
                       notifyDataSetChanged();
                   }
               });
               ((MaterialButton)bottomSheetDialog.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       bottomSheetDialog.dismiss();
                       iEvent.editWorkExperiences(list.get(position).getId());
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
         void removeWorkExperiences(String id);
         void editWorkExperiences(String id);
    }
}
