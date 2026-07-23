package mostafa.hafezypoor.ahmmad.panel.ui.skills;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orbitalsonic.waterwave.WaterWaveView;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelSkill;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class EditSkill extends AppCompatActivity {
    TextView titleActivity;
    TextInputEditText seekBar_percent,title;
    SkillsViewModel skillsViewModel;
    MaterialButton btnSave;
    ImageView imageBack;
    AppCompatSeekBar seekBar;
    WaterWaveView waterWaveView,loadingWaterWaveView;
    NestedScrollView mainView;
    ShimmerFrameLayout loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_skill);
        titleActivity=findViewById(R.id.titleActivity);
        loadingWaterWaveView=findViewById(R.id.loadingWaterWaveView);
        mainView=findViewById(R.id.mainView);
        loading=findViewById(R.id.loading);
        seekBar_percent=findViewById(R.id.seekBar_percent);
        title=findViewById(R.id.title);
        btnSave=findViewById(R.id.btnSave);
        imageBack=findViewById(R.id.imageBack);
        seekBar=findViewById(R.id.seekBar);
        waterWaveView=findViewById(R.id.waterWaveView);
        waterWaveView.setBehindWaveColor(getColor(R.color.orange));
        loadingWaterWaveView.setBehindWaveColor(getColor(R.color.orange));
        skillsViewModel=new ViewModelProvider(this).get(SkillsViewModel.class);
        if (getIntent().getExtras()!=null){
            String id=getIntent().getExtras().getString("id");
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
            skillsViewModel.getSkill(Constants.key,id).observe(this, new Observer<ModelSkill>() {
                @Override
                public void onChanged(ModelSkill modelSkill) {
                    loading.stopShimmer();
                    loading.setVisibility(GONE);
                    mainView.setVisibility(VISIBLE);
                    titleActivity.setText(modelSkill.getTitle());
                    seekBar.setProgress(Integer.valueOf(modelSkill.getPercent()));
                    waterWaveView.setProgress(Integer.valueOf(modelSkill.getPercent()));
                    title.setText(modelSkill.getTitle());
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    skillsViewModel.editSkill(Constants.key,id,seekBar.getProgress()+"",title.getText().toString().trim()).observe(EditSkill.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("200")){
                                Intent intent=new Intent(EditSkill.this, MainActivity.class);
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
                  Intent intent=new Intent(EditSkill.this, MainActivity.class);
                  intent.putExtra("section",R.id.skills);
                  startActivity(intent);
                  finish();
              }
          });
        }

    }
}
