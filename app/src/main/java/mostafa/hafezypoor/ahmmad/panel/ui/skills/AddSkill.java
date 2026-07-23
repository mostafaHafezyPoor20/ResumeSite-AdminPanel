package mostafa.hafezypoor.ahmmad.panel.ui.skills;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orbitalsonic.waterwave.WaterWaveView;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class AddSkill extends AppCompatActivity {
    TextInputEditText title;
    SkillsViewModel skillsViewModel;
    ImageView imageBack;
    MaterialButton btnSave;
    AppCompatSeekBar seekBar;
    WaterWaveView waterWaveView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_skill);
        title=findViewById(R.id.title);
        imageBack=findViewById(R.id.imageBack);
        btnSave=findViewById(R.id.btnSave);
        seekBar=findViewById(R.id.seekBar);
        waterWaveView=findViewById(R.id.waterWaveView);
        waterWaveView.setBehindWaveColor(getColor(R.color.orange));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                waterWaveView.setProgress(i);
                if (i>=50){
                    waterWaveView.setTextColor(getColor(R.color.white));
                }else{
                    waterWaveView.setTextColor(getColor(R.color.red));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        skillsViewModel=new ViewModelProvider(this).get(SkillsViewModel.class);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skillsViewModel.addSkill(Constants.key,title.getText().toString().trim(),seekBar.getProgress()+"").observe(AddSkill.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equals("200")){
                            Intent intent=new Intent(AddSkill.this, MainActivity.class);
                            intent.putExtra("section",R.id.skills);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddSkill.this, MainActivity.class);
                intent.putExtra("section",R.id.skills);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddSkill.this, MainActivity.class);
        intent.putExtra("section",R.id.skills);
        startActivity(intent);
        finish();
    }
}
