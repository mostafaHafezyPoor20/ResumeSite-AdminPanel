package mostafa.hafezypoor.ahmmad.panel.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.invoke.MutableCallSite;
import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMyServices;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FMyServicesRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<List<ModelMyServices>>getMyServices(String key){
        MutableLiveData<List<ModelMyServices>> mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getMyServices(key).enqueue(new Callback<List<ModelMyServices>>() {
            @Override
            public void onResponse(Call<List<ModelMyServices>> call, Response<List<ModelMyServices>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelMyServices>> call, Throwable t) {
                 handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<ModelMyServices>getService(String key,String id){
        MutableLiveData<ModelMyServices>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getService(key,id).enqueue(new Callback<ModelMyServices>() {
            @Override
            public void onResponse(Call<ModelMyServices> call, Response<ModelMyServices> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelMyServices> call, Throwable t) {
                handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>editService(String key,String id,String icon,String title,String description){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().editService(key,id,icon,title,description).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>addService(String key,String icon,String title,String description){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().addService(key,icon,title,description).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>removeService(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().removeService(key,id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
             handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
}
