package mostafa.hafezypoor.ahmmad.panel.ui.messages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMessage;

public class AdapterMessages extends RecyclerView.Adapter<AdapterMessages.ViewHolder >{
    private Context context;
    private List<ModelMessage>list;

    public AdapterMessages(Context context, List<ModelMessage> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_messages,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.message.setText(list.get(position).getMessage());
        holder.textViewCard.setText(list.get(position).getName().substring(0,1).toUpperCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShowMessage.class);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("phoneNumber",list.get(position).getPhoneNumber());
                intent.putExtra("message",list.get(position).getMessage());
                context.startActivity(intent);
            }
        });
        if (list.get(position).getVisited().equals("false")){
            holder.cardImageName.setCardBackgroundColor(context.getColor(R.color.red));
            holder.rootCardView.setStrokeColor(context.getColor(R.color.red));
        }else{
            holder.cardImageName.setCardBackgroundColor(context.getColor(R.color.orange));
            holder.rootCardView.setStrokeColor(context.getColor(R.color.orange));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,message,textViewCard;
        MaterialCardView rootCardView,cardImageName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            message=itemView.findViewById(R.id.message);
            textViewCard=itemView.findViewById(R.id.textViewCard);
            rootCardView=itemView.findViewById(R.id.rootCarView);
            cardImageName=itemView.findViewById(R.id.cardImageName);
        }
    }
}
