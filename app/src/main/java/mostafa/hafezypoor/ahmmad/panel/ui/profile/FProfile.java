package mostafa.hafezypoor.ahmmad.panel.ui.profile;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InputEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orbitalsonic.waterwave.WaterWaveView;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.data.UploadInfo;
import net.gotev.uploadservice.data.UploadNotificationConfig;
import net.gotev.uploadservice.data.UploadNotificationStatusConfig;
import net.gotev.uploadservice.network.ServerResponse;
import net.gotev.uploadservice.observer.request.RequestObserverDelegate;
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import kotlin.jvm.functions.Function2;
import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetProfile;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FProfile extends Fragment {
    private ProfileViewModel profileViewModel;
    private TextInputEditText name,summerSkill,instagram,telegram;
    private ImageView imageProfile;
    private MaterialButton btnSave;
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
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fprofile,container,false);
        name=view.findViewById(R.id.name);
        summerSkill=view.findViewById(R.id.summerSkills);
        instagram=view.findViewById(R.id.instagram);
        telegram=view.findViewById(R.id.telegram);
        imageProfile=view.findViewById(R.id.imageProfile);
        btnSave=view.findViewById(R.id.btnSave);
        textError=view.findViewById(R.id.textError);
        mainView=view.findViewById(R.id.mainView);
        loading=view.findViewById(R.id.loading);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name.addTextChangedListener(listenerInput());
        summerSkill.addTextChangedListener(listenerInput());
        instagram.addTextChangedListener(listenerInput());
        telegram.addTextChangedListener(listenerInput());
        profileViewModel=new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        profileViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof IOException){
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FProfile()),"internet").commit();
                }else if (throwable instanceof UnknownHostException||throwable!=null){
                  fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(),"internet").commit();
                }
            }
        });
      profileViewModel.getProfile(Constants.key).observe(getActivity(), new Observer<ModelGetProfile>() {
          @Override
          public void onChanged(ModelGetProfile modelGetProfile) {
              mainView.setVisibility(VISIBLE);
              loading.stopShimmer();
              loading.setVisibility(GONE);
              name.setText(modelGetProfile.getName());
              summerSkill.setText(modelGetProfile.getSummerSkill());
              instagram.setText(modelGetProfile.getInstagram());
              telegram.setText(modelGetProfile.getTelegram());
              Picasso.get().load(modelGetProfile.getImageProfile()).error(R.drawable.icon).into(imageProfile);
          }
      });
        ActivityResultLauncher<String>pickImage=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                if (o!=null){
                    uploadMainImage(uriToFile(o));
                    imageProfile.setImageURI(o);
                }
            }
        });
      imageProfile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
                 pickImage.launch("image/*");
          }
      });
      btnSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (checkInput()){
                  profileViewModel.setProfile(Constants.key,name.getText().toString().trim(),summerSkill.getText().toString().trim(),instagram.getText().toString().trim(),telegram.getText().toString()).observe(getActivity(), new Observer<String>() {
                      @Override
                      public void onChanged(String s) {

                      }
                  });
              }
          }
      });
    }
private void uploadMainImage(File image){
    BottomSheetDialog dialogUploading=new BottomSheetDialog(getActivity(),R.style.AppBottomSheetDialog);
    dialogUploading.setContentView(R.layout.dialog_about_me_upload_image_profile);
    dialogUploading.show();
    dialogUploading.setCancelable(false);
    WaterWaveView progressBar=dialogUploading.findViewById(R.id.progressBar);
    progressBar.setBehindWaveColor(getContext().getColor(R.color.orange));
    UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig("uploading",false,
    new UploadNotificationStatusConfig("اپلود تصویر پروفایل","شروع آپلود",R.drawable.account_circle_24px),
    new UploadNotificationStatusConfig("finish","upload icon profile finished",R.drawable.account_circle_24px),
    new UploadNotificationStatusConfig("error","upload error ):",R.drawable.account_circle_24px),
    new UploadNotificationStatusConfig("cancel","upload canceled"));

    try {
        MultipartUploadRequest request = new MultipartUploadRequest(getActivity(),Constants.SERVER_URL+"profile/uploadImageProfile.php")
                .addFileToUpload(image.getAbsolutePath(),"image")
                .setNotificationConfig(new Function2<Context, String, UploadNotificationConfig>() {
                    @Override
                    public UploadNotificationConfig invoke(Context context, String s) {
                        return uploadNotificationConfig;
                    }
                })
                .setMaxRetries(10)
                .addParameter("key",Constants.key)
                .addHeader("Content-Type","Multipart/form-data")
                .addHeader("User-Agent","PostmanRuntime/7.32.2")
                .setMethod("POST");
        request.startUpload();
        request.subscribe(getActivity(), getActivity(), new RequestObserverDelegate() {
            @Override
            public void onProgress(@NonNull Context context, @NonNull UploadInfo uploadInfo) {
                if (uploadInfo.getProgressPercent()>=50){
                    progressBar.setTextColor(getContext().getColor(R.color.white));
                }
               progressBar.setProgress(uploadInfo.getProgressPercent());
            }

            @Override
            public void onSuccess(@NonNull Context context, @NonNull UploadInfo uploadInfo, @NonNull ServerResponse serverResponse) {
            dialogUploading.dismiss();
            }

            @Override
            public void onError(@NonNull Context context, @NonNull UploadInfo uploadInfo, @NonNull Throwable throwable) {

            }

            @Override
            public void onCompleted(@NonNull Context context, @NonNull UploadInfo uploadInfo) {

            }

            @Override
            public void onCompletedWhileNotObserving() {

            }
        });
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
}
private File uriToFile(Uri uri){
    try {
        InputStream inputStream=getContext().getContentResolver().openInputStream(uri);
        File tempFile=File.createTempFile("upload",".jpg",getContext().getCacheDir());
        FileOutputStream out = new FileOutputStream(tempFile);
        byte[]buf=new byte[1024];
        int len;
        while((len=inputStream.read(buf))>0){
            out.write(buf,0,len);
        }
        out.close();
        inputStream.close();
        return tempFile;
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
private boolean checkInput(){
  boolean result=true;
  if (name.getText().toString().trim().isEmpty()){
      textError.setText("نام نمیتواند خالی باشد !");
      result=false;
  }else if (summerSkill.getText().toString().trim().isEmpty()){
      textError.setText("خلاصه مهارت ها نمیتواند خالی باشد !");
      result=false;
  }else if (instagram.getText().toString().trim().isEmpty()){
      textError.setText("آدرس اینستاگرام نمیتواند خالی باشد !");
      result=false;
  }else if (telegram.getText().toString().trim().isEmpty()){
      textError.setText("آدرس تلگرام نمیتواند خالی باشد !");
      result=false;
  }
  if (!result){
      textError.setVisibility(VISIBLE);
      VibrationClass.vibration(getContext(),400);
  }
  return result;
}
private TextWatcher listenerInput(){
        return  new TextWatcher() {
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
