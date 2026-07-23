package mostafa.hafezypoor.ahmmad.panel.ui.skills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orbitalsonic.waterwave.WaterWaveView;

import mostafa.hafezypoor.ahmmad.panel.R;

public class AdapterLoadingSkills extends RecyclerView.Adapter<AdapterLoadingSkills.ViewHolder>{
    public AdapterLoadingSkills(Context context) {
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_loading_skills,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.waterWaveView.setBehindWaveColor(context.getColor(R.color.orange));
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        WaterWaveView waterWaveView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            waterWaveView=itemView.findViewById(R.id.waterWaveView);
        }
    }
}
