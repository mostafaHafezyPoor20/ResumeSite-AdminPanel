package mostafa.hafezypoor.ahmmad.panel.ui.skills;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.orbitalsonic.waterwave.WaterWaveView;

import mostafa.hafezypoor.ahmmad.panel.R;
import java.util.List;
import java.util.Map;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelSkill;

class AdapterSkills extends RecyclerView.Adapter<AdapterSkills.ViewHolder> {
        private Context context;
        private List<ModelSkill>list;

    public AdapterSkills(Context context, List<ModelSkill> list,IEvent iEvent) {
        this.context = context;
        this.list = list;
        this.iEvent=iEvent;
    }

    @NonNull
        @Override
        public AdapterSkills.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdapterSkills.ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_skills,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdapterSkills.ViewHolder holder, int position) {
              holder.seekBar_precent.setWaveStrong(50);
              holder.seekBar_precent.setProgress(Integer.valueOf(list.get(position).getPercent()));
              holder.seekBar_precent.setBehindWaveColor(context.getColor(R.color.orange));
              holder.seekBar_precent.startAnimation();
              if (Integer.valueOf(list.get(position).getPercent())>=50){
                  holder.seekBar_precent.setTextColor(context.getColor(R.color.white));
              }
              holder.title.setText(list.get(position).getTitle());
              holder.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      BottomSheetDialog dialog=new BottomSheetDialog(context,R.style.AppBottomSheetDialog);
                      dialog.setContentView(R.layout.dialog_skill);
                      dialog.show();
                      ((TextView)dialog.findViewById(R.id.title)).setText(list.get(position).getTitle());
                      ((MaterialButton)dialog.findViewById(R.id.btnEdit)).setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent intent=new Intent(context, EditSkill.class);
                              intent.putExtra("id",list.get(position).getId());
                              context.startActivity(intent);
                              dialog.dismiss();
                          }
                      });
                      ((MaterialButton)dialog.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              iEvent.removeSkill(list.get(position).getId());
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
        WaterWaveView seekBar_precent;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seekBar_precent=itemView.findViewById(R.id.seekBar_percent);
            title=itemView.findViewById(R.id.title);
        }

    }
    private IEvent iEvent;
    interface IEvent{
        void removeSkill(String id);
    }
    }

