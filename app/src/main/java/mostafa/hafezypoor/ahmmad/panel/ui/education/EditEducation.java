package mostafa.hafezypoor.ahmmad.panel.ui.education;

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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelEducation;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class EditEducation extends AppCompatActivity {
    private EducationViewModel educationViewModel;
    private TextInputEditText title,date,description;
    private TextView titleActivity,textError;
    private MaterialButton btnSave;
    private ImageView imageBack;
    private NestedScrollView mainView;
    private ShimmerFrameLayout loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_education);
        title=findViewById(R.id.title);
        mainView=findViewById(R.id.mainView);
        loading=findViewById(R.id.loading);
        date=findViewById(R.id.date);
        description=findViewById(R.id.description);
        titleActivity=findViewById(R.id.titleActivity);
        btnSave=findViewById(R.id.btnSave);
        imageBack=findViewById(R.id.imageBack);
        textError=findViewById(R.id.textError);
        educationViewModel=new ViewModelProvider(this).get(EducationViewModel.class);
        if (getIntent().getExtras()!=null) {
            String id = getIntent().getExtras().getString("id");
            title.addTextChangedListener(listenerInputs());
            date.addTextChangedListener(listenerInputs());
            description.addTextChangedListener(listenerInputs());
            educationViewModel.getEducation(Constants.key, id).observe(this, new Observer<ModelEducation>() {
                @Override
                public void onChanged(ModelEducation modelEducation) {
                    loading.stopShimmer();
                    loading.setVisibility(GONE);
                    mainView.setVisibility(VISIBLE);
                    titleActivity.setText(" شما درحال ویرایش  " + modelEducation.getTitle() + " هستید ! ");
                    title.setText(modelEducation.getTitle());
                    date.setText(modelEducation.getDate());
                    description.setText(modelEducation.getDescription());
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkInputs()){
                        educationViewModel.editEducation(Constants.key, new ModelEducation(id, title.getText().toString().trim(), date.getText().toString().trim(), description.getText().toString().trim())).observe(EditEducation.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                if (s.equals("200")) {
                                    Intent intent = new Intent(EditEducation.this, MainActivity.class);
                                    intent.putExtra("section", R.id.education);
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
                Intent intent = new Intent(EditEducation.this, MainActivity.class);
                intent.putExtra("section", R.id.education);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean checkInputs(){
        boolean result=true;
        if (title.getText().toString().trim().isEmpty()){
            textError.setText("عنوان نمیتواند خالی باشد !");
            result = false;
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
    private TextWatcher listenerInputs(){
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
