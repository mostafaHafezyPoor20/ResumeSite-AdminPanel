package mostafa.hafezypoor.ahmmad.panel.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FWorkExperiencesRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    private   MutableLiveData<List<ModelExperiences>>mutableLiveDataWorkExperiences=new MutableLiveData<>();
    public LiveData<List<ModelExperiences>>getExperiences(String key){

        RetrofitInit.getInstance().getExperiences(key).enqueue(new Callback<List<ModelExperiences>>() {
            @Override
            public void onResponse(Call<List<ModelExperiences>> call, Response<List<ModelExperiences>> response) {
                if (response.isSuccessful()){
                    mutableLiveDataWorkExperiences.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelExperiences>> call, Throwable t) {
handleError.setValue(t);
            }
        });
        return mutableLiveDataWorkExperiences;
    }

    public LiveData<String>addExperiences(String key,ModelExperiences modelExperiences){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().addExperiences(key, modelExperiences.getTitle(), modelExperiences.getDate(), modelExperiences.getDescription()).enqueue(new Callback<String>() {
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
    public LiveData<String>removeWorkExperiences(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().removeWorkExperiences(key,id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                     mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<ModelExperiences>getWorkExperience(String key,String id){
        MutableLiveData<ModelExperiences>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getWorkExperience(key,id).enqueue(new Callback<ModelExperiences>() {
            @Override
            public void onResponse(Call<ModelExperiences> call, Response<ModelExperiences> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelExperiences> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
    public LiveData<String>editWorkExperience(String key,String id,String title,String date,String description){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().editWorkExperience(key,id,title,date,description).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
