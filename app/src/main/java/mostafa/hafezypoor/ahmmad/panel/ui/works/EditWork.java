package mostafa.hafezypoor.ahmmad.panel.ui.works;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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
import java.lang.reflect.Field;

import kotlin.jvm.functions.Function2;
import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelWork;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;
import retrofit2.Call;

public class EditWork extends AppCompatActivity {
    ImageView image;
    TextInputEditText title,description;
    WorksViewModel worksViewModel;
    TextView titleActivity,textError;
    ImageView imageBack;
    MaterialButton btnSave;
    NestedScrollView mainView;
    ShimmerFrameLayout loading;
    BottomSheetDialog dialog;
    WaterWaveView progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_work);
        dialog=new BottomSheetDialog(this,R.style.AppBottomSheetDialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_upload_work);
        progressBar=dialog.findViewById(R.id.progressBar);
        progressBar.setBehindWaveColor(getColor(R.color.orange));
        worksViewModel=new ViewModelProvider(this).get(WorksViewModel.class);
        image=findViewById(R.id.image);
        mainView=findViewById(R.id.mainView);
        loading=findViewById(R.id.loading);
        btnSave=findViewById(R.id.btnSave);
        textError=findViewById(R.id.textError);
        imageBack=findViewById(R.id.imageBack);
        titleActivity=findViewById(R.id.titleActivity);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        if (getIntent().getExtras()!=null){
            String id=getIntent().getExtras().getString("id");
            worksViewModel.getWork(Constants.key,id).observe(this, new Observer<ModelWork>() {
                @Override
                public void onChanged(ModelWork modelWork) {
                    loading.stopShimmer();
                    loading.setVisibility(GONE);
                    mainView.setVisibility(VISIBLE);
                    titleActivity.setText(modelWork.getTitle());
                    Picasso.get().load(modelWork.getImage()).into(image);
                    title.setText(modelWork.getTitle());
                    description.setText(modelWork.getDescription());
                }
            });
            ActivityResultLauncher<String>pickImage=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                   if (o!=null){
                       uploadImageWork(uriToFile(o),id);
                       image.setImageURI(o);
                   }
                }
            });
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   pickImage.launch("image/*");
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkInput()){
                       worksViewModel.editWork(Constants.key,id,title.getText().toString().trim(),description.getText().toString().trim()).observe(EditWork.this, new Observer<String>() {
                           @Override
                           public void onChanged(String s) {
                               if (s.equals("200")){
                                   Intent intent=new Intent(EditWork.this, MainActivity.class);
                                   intent.putExtra("section",R.id.works);
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
            Intent intent=new Intent(EditWork.this, MainActivity.class);
            intent.putExtra("section",R.id.works);
            startActivity(intent);
            finish();
        }
    });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditWork.this, MainActivity.class);
        intent.putExtra("section",R.id.works);
        startActivity(intent);
        finish();
    }
    private void uploadImageWork(File file,String id){
        dialog.show();
        UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig("uploading",false
                ,new UploadNotificationStatusConfig("درحال آپلود تصویر نمونه کار","شروع آپلود",R.drawable.work_24px)
                ,new UploadNotificationStatusConfig("finish","upload finished",R.drawable.work_24px),
                new UploadNotificationStatusConfig("error","error during upload",R.drawable.work_24px),
                new UploadNotificationStatusConfig("cancel","upload canceled",R.drawable.work_24px));
        try {
            MultipartUploadRequest request=new MultipartUploadRequest(this,Constants.SERVER_URL+"works/changeImageWork.php")
                    .addFileToUpload(file.getAbsolutePath(),"image")
                    .setNotificationConfig(new Function2<Context, String, UploadNotificationConfig>() {
                        @Override
                        public UploadNotificationConfig invoke(Context context, String s) {
                            return uploadNotificationConfig;
                        }
                    })
                    .addParameter("key",Constants.key)
                    .addParameter("id",id)
                    .addHeader("Content-Type","Multipart/form-data")
                    .addHeader("User-Agent","PostmanRuntime/7.32.2")
                    .setMaxRetries(10)
                    .setMethod("POST");
            request.startUpload();
            request.subscribe(this, this, new RequestObserverDelegate() {
                @Override
                public void onProgress(@NonNull Context context, @NonNull UploadInfo uploadInfo) {
                     progressBar.setProgress(uploadInfo.getProgressPercent());
                }

                @Override
                public void onSuccess(@NonNull Context context, @NonNull UploadInfo uploadInfo, @NonNull ServerResponse serverResponse) {
                   dialog.dismiss();
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
        }catch (FileNotFoundException fileNotFoundException){
            fileNotFoundException.printStackTrace();
        }
    }
    private File uriToFile(Uri uri){
        try{
            InputStream inputStream=getContentResolver().openInputStream(uri);
            File tempFile=File.createTempFile("upload",".jpg",getCacheDir());
            FileOutputStream fileOutputStream=new FileOutputStream(tempFile);
            byte[]buff=new byte[1024];
            int len;
            while ((len=inputStream.read(buff))>0){
                fileOutputStream.write(buff,0,len);
            }
            fileOutputStream.close();
            inputStream.close();
            return tempFile;
        }catch (IOException ioException){
          throw new RuntimeException(ioException);
        }
    }
    private boolean checkInput(){
        boolean result=true;
        if (title.getText().toString().trim().isEmpty()){
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
}
