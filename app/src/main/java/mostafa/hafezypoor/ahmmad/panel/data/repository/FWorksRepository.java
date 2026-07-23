package mostafa.hafezypoor.ahmmad.panel.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelWork;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FWorksRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<List<ModelWork>>getWorks(String id){
        MutableLiveData<List<ModelWork>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getWorks(id).enqueue(new Callback<List<ModelWork>>() {
            @Override
            public void onResponse(Call<List<ModelWork>> call, Response<List<ModelWork>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelWork>> call, Throwable t) {
                handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>removeWork(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().removeWork(key,id).enqueue(new Callback<String>() {
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
    public LiveData<ModelWork>getWork(String key,String id){
        MutableLiveData<ModelWork>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getWork(key,id).enqueue(new Callback<ModelWork>() {
            @Override
            public void onResponse(Call<ModelWork> call, Response<ModelWork> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelWork> call, Throwable t) {
                 handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>editWork(String key,String id,String title,String description){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().editWork(key,id,title,description).enqueue(new Callback<String>() {
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
