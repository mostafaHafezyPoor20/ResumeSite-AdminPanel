package mostafa.hafezypoor.ahmmad.panel.ui.myServices;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.net.Uri;
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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMyServices;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class EditMyServices extends AppCompatActivity {
    MyServiceViewModel myServiceViewModel;
    TextInputEditText icon,title,description;
    MaterialButton btnSave,btnIcons;
    TextView textError,titleActivity;
    ImageView imageBack;
    NestedScrollView mainView;
    ShimmerFrameLayout loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_services);
        icon=findViewById(R.id.icon);
        btnIcons=findViewById(R.id.btnIcons);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        btnSave=findViewById(R.id.btnSave);
        textError=findViewById(R.id.textError);
        titleActivity=findViewById(R.id.titleActivity);
        imageBack=findViewById(R.id.imageBack);
        mainView=findViewById(R.id.mainView);
        loading=findViewById(R.id.loading);
        icon.addTextChangedListener(listenerInput());
        title.addTextChangedListener(listenerInput());
        description.addTextChangedListener(listenerInput());
        myServiceViewModel=new ViewModelProvider(this).get(MyServiceViewModel.class);
        if (getIntent().getExtras()!=null){
            String id=getIntent().getExtras().getString("id");
            myServiceViewModel.getService(Constants.key,id).observe(this, new Observer<ModelMyServices>() {
                @Override
                public void onChanged(ModelMyServices modelMyServices) {
                    loading.stopShimmer();
                    loading.setVisibility(GONE);
                    mainView.setVisibility(VISIBLE);
                     icon.setText(modelMyServices.getIcon());
                     title.setText(modelMyServices.getTitle());
                     description.setText(modelMyServices.getDescription());
                     titleActivity.setText(modelMyServices.getTitle());
                }
            });
            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(EditMyServices.this, MainActivity.class);
                    intent.putExtra("section",R.id.myServices);
                    startActivity(intent);
                    finish();
                }
            });
            btnIcons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://icons.getbootstrap.com/")));
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if (checkInput()){
                      myServiceViewModel.editService(Constants.key,id,icon.getText().toString().trim(),title.getText().toString().trim(),description.getText().toString().trim()).observe(EditMyServices.this, new Observer<String>() {
                          @Override
                          public void onChanged(String s) {
                              if (s.equals("200")){
                                  Intent intent=new Intent(EditMyServices.this, MainActivity.class);
                                  intent.putExtra("section",R.id.myServices);
                                  startActivity(intent);
                                  finish();
                              }
                          }
                      });
                  }
                }
            });
        }
    }
private boolean checkInput(){
   boolean result=true;
   if (icon.getText().toString().trim().isEmpty()){
       textError.setText("کد ایکن نمیتواند خالی باشد !");
       result=false;
   }else if (title.getText().toString().trim().isEmpty()){
       textError.setText("عنوان نمیتواند خالی باشد !");
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditMyServices.this, MainActivity.class);
        intent.putExtra("section",R.id.myServices);
        startActivity(intent);
        finish();
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
