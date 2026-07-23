package mostafa.hafezypoor.ahmmad.panel.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetProfile;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FProfileRepository {
    private final MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){

        return handleError;
    }
    public LiveData<ModelGetProfile>getProfile(String key) {
        MutableLiveData<ModelGetProfile> mutableLiveData = new MutableLiveData<>();
        RetrofitInit.getInstance().getProfile(key).enqueue(new Callback<ModelGetProfile>() {
            @Override
            public void onResponse(Call<ModelGetProfile> call, Response<ModelGetProfile> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                    handleError.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ModelGetProfile> call, Throwable t) {
                handleError.setValue(t);
            }
        });
return mutableLiveData;
    }
    public LiveData<String>setProfile(String key,String name,String summerSkill,String instagram,String telegram){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().setProfile(key,name,summerSkill,instagram,telegram).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                    handleError.setValue(null);
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
