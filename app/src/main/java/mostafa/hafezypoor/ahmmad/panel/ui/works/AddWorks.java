package mostafa.hafezypoor.ahmmad.panel.ui.works;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orbitalsonic.waterwave.WaterWaveView;

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

import kotlin.jvm.functions.Function2;
import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.ui.common.VibrationClass;
import mostafa.hafezypoor.ahmmad.panel.ui.main.MainActivity;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class AddWorks extends AppCompatActivity {
    ImageView image;
     Uri imageUri=null;
     MaterialButton btnSave;
     TextInputEditText title,description;
     TextView textError;
     ImageView imageBack;
     BottomSheetDialog dialog;
     WaterWaveView progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_works);
         dialog=new BottomSheetDialog(this,R.style.AppBottomSheetDialog);
        dialog.setContentView(R.layout.dialog_upload_work);
        dialog.setCancelable(false);
        progressBar=dialog.findViewById(R.id.progressBar);
        progressBar.setBehindWaveColor(getColor(R.color.orange));
        image=findViewById(R.id.image);
        imageBack=findViewById(R.id.imageBack);
        title=findViewById(R.id.title);
        textError=findViewById(R.id.textError);
        description=findViewById(R.id.description);
        btnSave=findViewById(R.id.btnSave);
        title.addTextChangedListener(listenerInput());
        description.addTextChangedListener(listenerInput());
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddWorks.this, MainActivity.class);
                intent.putExtra("section",R.id.works);
                startActivity(intent);
                finish();
            }
        });
        ActivityResultLauncher<String>pickImage=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                if (o!=null){
                 imageUri=o;
                 image.setImageURI(o);
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             pickImage.launch("image/*");
             textError.setVisibility(GONE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                addWork(uriToFile(imageUri));
                }
            }
        });
    }
    private void addWork(File image){
        dialog.show();
        UploadNotificationConfig uploadNotificationConfig=new UploadNotificationConfig("uploading",false
        ,new UploadNotificationStatusConfig("درحال آپلود تصویر نمونه کار","شروع آپلود",R.drawable.work_24px)
        ,new UploadNotificationStatusConfig("finish","upload finished",R.drawable.work_24px),
                new UploadNotificationStatusConfig("error","error during upload",R.drawable.work_24px),
                new UploadNotificationStatusConfig("cancel","upload canceled",R.drawable.work_24px));
        try {

            MultipartUploadRequest request=new MultipartUploadRequest(this, Constants.SERVER_URL+"works/addWork.php")
                    .addFileToUpload(image.getAbsolutePath(),"image")
                    .setNotificationConfig(new Function2<Context, String, UploadNotificationConfig>() {
                        @Override
                        public UploadNotificationConfig invoke(Context context, String s) {
                            return uploadNotificationConfig;
                        }
                    }).setMaxRetries(10)
                    .addParameter("key",Constants.key)
                    .addParameter("title",title.getText().toString().trim())
                    .addParameter("description",description.getText().toString().trim())
                    .addHeader("Content-Type","Multipart/form-data")
                    .addHeader("User-Agent","PostmanRuntime/7.32.2")
                    .setMethod("POST");
            request.startUpload();
            request.subscribe(this, this, new RequestObserverDelegate() {
                @Override
                public void onProgress(@NonNull Context context, @NonNull UploadInfo uploadInfo) {
                     progressBar.setProgress(uploadInfo.getProgressPercent());
                     if (uploadInfo.getProgressPercent()>=50){
                         progressBar.setTextColor(getColor(R.color.white));
                     }
                }

                @Override
                public void onSuccess(@NonNull Context context, @NonNull UploadInfo uploadInfo, @NonNull ServerResponse serverResponse) {
                    if (serverResponse.getBodyString().equals("200")){
                    dialog.dismiss();
                    Intent intent=new Intent(AddWorks.this, MainActivity.class);
                    intent.putExtra("section",R.id.works);
                    startActivity(intent);
                    finish();
               }
                }

                @Override
                public void onError(@NonNull Context context, @NonNull UploadInfo uploadInfo, @NonNull Throwable throwable) {
dialog.dismiss();
                    Toast.makeText(context, "اپلود با خطا مواجه شد!", Toast.LENGTH_SHORT).show();
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
        InputStream inputStream=getContentResolver().openInputStream(uri);
         File tempFile = File.createTempFile("upload",".jpg",getCacheDir());
        FileOutputStream fileOutputStream=new FileOutputStream(tempFile);
        byte[]buff=new byte[1024];
        int len;
        while((len=inputStream.read(buff))>0){
            fileOutputStream.write(buff,0,len);
        }
        fileOutputStream.close();
        inputStream.close();
        return tempFile;
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
private boolean checkInput(){
        boolean result=true;
        if (imageUri==null){
            textError.setText("تصویر نمونه کار را انتخاب نکرده ایید !");
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddWorks.this, MainActivity.class);
        intent.putExtra("section",R.id.works);
        startActivity(intent);
        finish();
    }
}
