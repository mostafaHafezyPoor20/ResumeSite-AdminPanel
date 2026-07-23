package mostafa.hafezypoor.ahmmad.panel.ui.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FMessagesRepository;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class ShowMessage extends AppCompatActivity {

    MessageViewModel messageViewModel;
     TextInputEditText name,phoneNumber;
    TextView message;
    ImageView imageBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_message);
        messageViewModel=new ViewModelProvider(this).get(MessageViewModel.class);
        name=findViewById(R.id.name);
        phoneNumber=findViewById(R.id.phoneNumber);
        message=findViewById(R.id.message);
        imageBack=findViewById(R.id.imageBack);
        name.setText(getIntent().getExtras().getString("name"));
        phoneNumber.setText(getIntent().getExtras().getString("phoneNumber"));
        message.setText(getIntent().getExtras().getString("message"));
        messageViewModel.visitedMessage(Constants.key,getIntent().getExtras().getString("id")).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShowMessage.this, MainActivity.class);
                intent.putExtra("section",R.id.messages);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ShowMessage.this, MainActivity.class);
        intent.putExtra("section",R.id.messages);
        startActivity(intent);
        finish();
    }
}
