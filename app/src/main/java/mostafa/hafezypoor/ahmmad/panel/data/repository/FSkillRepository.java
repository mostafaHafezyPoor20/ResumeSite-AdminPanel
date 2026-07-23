package mostafa.hafezypoor.ahmmad.panel.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelSkill;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;

public class FSkillRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<List<ModelSkill>>getSkills(@Field("key")String key){
        MutableLiveData<List<ModelSkill>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getSkills(key).enqueue(new Callback<List<ModelSkill>>() {
            @Override
            public void onResponse(Call<List<ModelSkill>> call, Response<List<ModelSkill>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelSkill>> call, Throwable t) {
                handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>addSkill(String key,String title,String percent){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().addSkill(key,title,percent).enqueue(new Callback<String>() {
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
    public LiveData<String>removeSkill(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().removeSkill(key,id).enqueue(new Callback<String>() {
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
    public LiveData<String>editSkill(String key,String id,String percent,String title){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().editSkill(key,id,percent,title).enqueue(new Callback<String>() {
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
    public LiveData<ModelSkill>getSkill(String key,String id){
        MutableLiveData<ModelSkill>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getSkill(key,id).enqueue(new Callback<ModelSkill>() {
            @Override
            public void onResponse(Call<ModelSkill> call, Response<ModelSkill> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelSkill> call, Throwable t) {
              handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
}
