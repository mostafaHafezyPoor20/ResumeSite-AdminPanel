package mostafa.hafezypoor.ahmmad.panel.ui.aboutMe;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetAboutMe;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.ProfileViewModel;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FAboutMe extends Fragment {
    private AboutMeViewModel aboutMeViewModel;
    private MaterialButton btnSave;
    private TextInputEditText title,descriptionAboutMe,phoneNumber,email,address;
    private TextView textError;
    private NestedScrollView mainView;
    private ShimmerFrameLayout loading;
    private FragmentActivity fragmentActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fabout_me,container,false);
        title=view.findViewById(R.id.title);
        descriptionAboutMe=view.findViewById(R.id.descriptionAboutMe);
        phoneNumber=view.findViewById(R.id.phoneNumber);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        btnSave=view.findViewById(R.id.btnSave);
        textError=view.findViewById(R.id.textError);
        mainView=view.findViewById(R.id.mainView);
        loading=view.findViewById(R.id.loading);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          title.addTextChangedListener(listenerInput());
          descriptionAboutMe.addTextChangedListener(listenerInput());
          phoneNumber.addTextChangedListener(listenerInput());
          email.addTextChangedListener(listenerInput());
          address.addTextChangedListener(listenerInput());
          aboutMeViewModel=new ViewModelProvider(getActivity()).get(AboutMeViewModel.class);
          aboutMeViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
              @Override
              public void onChanged(Throwable throwable) {
                  if (throwable instanceof IOException){
                      fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(),"internet").commit();
                  }
              }
          });
          aboutMeViewModel.getAbout(Constants.key).observe(getActivity(), new Observer<ModelGetAboutMe>() {
              @Override
              public void onChanged(ModelGetAboutMe modelGetAboutMe) {
                  mainView.setVisibility(VISIBLE);
                  loading.stopShimmer();
                  loading.setVisibility(GONE);
                  title.setText(modelGetAboutMe.getTitleAboutMe());
                  descriptionAboutMe.setText(modelGetAboutMe.getDescriptionAboutMe());
                  phoneNumber.setText(modelGetAboutMe.getPhoneNumber());
                  email.setText(modelGetAboutMe.getEmail());
                  address.setText(modelGetAboutMe.getAddress());
              }
          });
          btnSave.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (checkInput()){
                      aboutMeViewModel.setAbout(title.getText().toString().trim(),descriptionAboutMe.getText().toString().trim(),email.getText().toString().trim(),phoneNumber.getText().toString().trim(),address.getText().toString().trim()).observe(getActivity(), new Observer<String>() {
                          @Override
                          public void onChanged(String s) {

                          }
                      });

                  }
              }
          });
        }
        private boolean checkInput(){
        boolean result=true;
        if (title.getText().toString().trim().isEmpty()){
            textError.setText("عنوان نمیتواند خالی باشد !");
            result=false;
        }else if (descriptionAboutMe.getText().toString().isEmpty()){
            textError.setText("متن درباره من نمیتواند خالی باشد !");
            result=false;
        }else if (phoneNumber.getText().toString().trim().isEmpty()){
            textError.setText("تلفن تماس نمیتواند خالی باشد !");
            result=false;
        }else if (email.getText().toString().trim().isEmpty()){
            textError.setText("ایمیل نمیتواند خالی باشد !");
            result=false;
        }else if (address.getText().toString().trim().isEmpty()){
            textError.setText("آدرس نمیتواند خالی باشد !");
            result=false;
        }
        if (!result){
            textError.setVisibility(VISIBLE);
            VibrationClass.vibration(getContext(),400);
        }
        return result;
        }
        private TextWatcher listenerInput(){
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                textError.setVisibility(GONE);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        };
        }
}
