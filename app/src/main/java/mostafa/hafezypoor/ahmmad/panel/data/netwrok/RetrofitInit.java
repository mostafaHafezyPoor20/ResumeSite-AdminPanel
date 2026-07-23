package mostafa.hafezypoor.ahmmad.panel.data.netwrok;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import mostafa.hafezypoor.ahmmad.panel.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitInit {
private static Retrofit retrofit;
public static IConnection getInstance(){
    if (retrofit==null){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .build();
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();
        retrofit=new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    return retrofit.create(IConnection.class);
}
}
