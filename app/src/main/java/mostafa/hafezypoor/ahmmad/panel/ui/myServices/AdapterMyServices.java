package mostafa.hafezypoor.ahmmad.panel.ui.myServices;

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

import org.w3c.dom.Text;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMyServices;

public class AdapterMyServices extends RecyclerView.Adapter<AdapterMyServices.ViewHolder >{
    private Context context;
    private List<ModelMyServices>list;

    public AdapterMyServices(Context context, List<ModelMyServices> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_my_services,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.icon.setText(list.get(position).getIcon());
         holder.title.setText(list.get(position).getTitle());
         holder.description.setText(list.get(position).getDescription());
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 BottomSheetDialog dialog = new BottomSheetDialog(context,R.style.AppBottomSheetDialog);
                 dialog.setContentView(R.layout.dialog_my_services);
                 dialog.show();
                 ((TextView)dialog.findViewById(R.id.title)).setText(list.get(position).getTitle());
                 ((MaterialButton)dialog.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();
                         Intent intent=new Intent(context, EditMyServices.class);
                         intent.putExtra("id",list.get(position).getId());
                         context.startActivity(intent);
                     }
                 });
                 ((MaterialButton)dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         iEvent.removeService(list.get(position).getId());
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
         TextView icon,title,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
        }
    }
    private IEvent iEvent;
    interface IEvent{
        void removeService(String id);
    }
}
