package mostafa.hafezypoor.ahmmad.panel.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelEducation;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class FEducationRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<List<ModelEducation>>getEducations(String key){
        MutableLiveData<List<ModelEducation>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getEducations(key).enqueue(new Callback<List<ModelEducation>>() {
            @Override
            public void onResponse(Call<List<ModelEducation>> call, Response<List<ModelEducation>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelEducation>> call, Throwable t) {
handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>addEducation(String key,String title,String date,String description){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().addEducation(key,title,date,description).enqueue(new Callback<String>() {
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
    public LiveData<String>removeEducation(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().removeEducation(key,id).enqueue(new Callback<String>() {
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
    public LiveData<ModelEducation>getEducation(String key,String id){
        MutableLiveData<ModelEducation>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getEducation(key,id).enqueue(new Callback<ModelEducation>() {
            @Override
            public void onResponse(Call<ModelEducation> call, Response<ModelEducation> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelEducation> call, Throwable t) {
handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>editEducation(String key,ModelEducation modelEducation){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().editEducation(key,modelEducation.getId(),modelEducation.getTitle(),modelEducation.getDate(),modelEducation.getDescription()).enqueue(new Callback<String>() {
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
