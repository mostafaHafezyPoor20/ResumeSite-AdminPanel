package mostafa.hafezypoor.ahmmad.panel;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;

import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.UploadServiceConfig;
import net.gotev.uploadservice.network.HttpRequest;
import net.gotev.uploadservice.network.HttpStack;
import net.gotev.uploadservice.okhttp.OkHttpStack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okio.Timeout;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("uploading","uploading", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        UploadServiceConfig.initialize(this,"uploading",true);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(10,TimeUnit.MINUTES)
                .writeTimeout(10,TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();
UploadServiceConfig.setHttpStack(new OkHttpStack(okHttpClient));
    }
}
