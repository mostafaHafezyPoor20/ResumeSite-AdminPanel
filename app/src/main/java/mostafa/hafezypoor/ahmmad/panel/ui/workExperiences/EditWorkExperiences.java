package mostafa.hafezypoor.ahmmad.panel.ui.workExperiences;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class EditWorkExperiences extends AppCompatActivity {
    private FWorkExperiencesViewModel fWorkExperiencesViewModel;
    private TextInputEditText title,date,description;
   private TextView titleActivity,textError;
   private MaterialButton btnSave;
   private ImageView imageBack;
   private NestedScrollView mainView;
   private ShimmerFrameLayout loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_work_experiences);
        title=findViewById(R.id.title);
        mainView=findViewById(R.id.mainView);
        btnSave=findViewById(R.id.btnSave);
        date=findViewById(R.id.date);
        description=findViewById(R.id.description);
        titleActivity=findViewById(R.id.titleActivity);
        imageBack=findViewById(R.id.imageBack);
        textError=findViewById(R.id.textError);
        loading=findViewById(R.id.loading);
        title.addTextChangedListener(listenerInput());
        date.addTextChangedListener(listenerInput());
        description.addTextChangedListener(listenerInput());
        fWorkExperiencesViewModel=new ViewModelProvider(this).get(FWorkExperiencesViewModel.class);
        if (getIntent().getExtras()!=null){
            String workExperiencesID=getIntent().getExtras().getString("workExperiencesID");
            fWorkExperiencesViewModel.getWorkExperience(Constants.key,workExperiencesID).observe(this, new Observer<ModelExperiences>() {
                @Override
                public void onChanged(ModelExperiences modelExperiences) {
                    loading.stopShimmer();
                    loading.setVisibility(GONE);
                    mainView.setVisibility(VISIBLE);
                    title.setText(modelExperiences.getTitle());
                    date.setText(modelExperiences.getDate());
                    description.setText(modelExperiences.getDescription());
                    titleActivity.setText("شما درحال ویرایش "+modelExperiences.getTitle()+" هستید! ");
                }
            });
          btnSave.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (checkInput()){
                      fWorkExperiencesViewModel.editWorkExperience(Constants.key,workExperiencesID,title.getText().toString().trim(),date.getText().toString().trim(),description.getText().toString().trim()).observe(EditWorkExperiences.this, new Observer<String>() {
                          @Override
                          public void onChanged(String s) {
                              if (s.equals("200")){
                                  Intent intent=new Intent(EditWorkExperiences.this, MainActivity.class);
                                  intent.putExtra("section",R.id.workExperiences);
                                  startActivity(intent);
                                  finish();
                              }
                          }
                      });
                  }
              }
          });
        }
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditWorkExperiences.this, MainActivity.class);
                intent.putExtra("section",R.id.workExperiences);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditWorkExperiences.this, MainActivity.class);
        intent.putExtra("section",R.id.workExperiences);
        startActivity(intent);
        finish();
    }
  private boolean checkInput(){
        boolean result=true;
        if (title.getText().toString().trim().isEmpty()){
            textError.setText("عنوان نمیتواند خالی باشد !");
            result=false;
        }else if (date.getText().toString().trim().isEmpty()){
            textError.setText("زمان نمیتواند خالی باشد !");
            result=false;
        }else if (description.getText().toString().trim().isEmpty()){
            textError.setText("توضیحات نمیتواند خالی باشد !");
            result=false;
        }
        if (!result){
            textError.setVisibility(VISIBLE);
            VibrationClass.vibration(this,400);
        }
        return result;
  }
  private TextWatcher listenerInput(){
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              textError.setVisibility(GONE);
            }
        };
  }
}
