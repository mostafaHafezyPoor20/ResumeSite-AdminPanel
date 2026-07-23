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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class AddWorkExperiences extends AppCompatActivity {
    private TextInputEditText title,date,description;
    private MaterialButton btnSave;
    private ImageView imageBack;
    private TextView textError;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_work_experiences);
        FWorkExperiencesViewModel fWorkExperiencesViewModel=new ViewModelProvider(this).get(FWorkExperiencesViewModel.class);
        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        description=findViewById(R.id.description);
        btnSave=findViewById(R.id.btnSave);
        imageBack=findViewById(R.id.imageBack);
        textError=findViewById(R.id.textError);
        title.addTextChangedListener(listenerInput());
        date.addTextChangedListener(listenerInput());
        description.addTextChangedListener(listenerInput());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    fWorkExperiencesViewModel.addWorkExperiences(Constants.key,new ModelExperiences(title.getText().toString().trim(),date.getText().toString().trim(),description.getText().toString().trim())).observe(AddWorkExperiences.this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("200")){
                                Intent intent=new Intent(AddWorkExperiences.this, MainActivity.class);
                                intent.putExtra("section",R.id.workExperiences);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });
         imageBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(AddWorkExperiences.this, MainActivity.class);
                 intent.putExtra("section",R.id.workExperiences);
                 startActivity(intent);
                 finish();
             }
         });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddWorkExperiences.this, MainActivity.class);
        intent.putExtra("section",R.id.workExperiences);
        startActivity(intent);
        finish();
    }
    private boolean checkInput(){
        boolean result =true;
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
