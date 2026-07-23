package mostafa.hafezypoor.ahmmad.panel.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetAboutMe;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAboutMeRepository {
    private final MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<ModelGetAboutMe>getAboutMe(String key){
        MutableLiveData<ModelGetAboutMe>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getAboutMe(key).enqueue(new Callback<ModelGetAboutMe>() {
            @Override
            public void onResponse(Call<ModelGetAboutMe> call, Response<ModelGetAboutMe> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelGetAboutMe> call, Throwable t) {
                handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>setAboutMe(ModelGetAboutMe modelGetAboutMe){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
      RetrofitInit.getInstance().setAboutMe(Constants.key,modelGetAboutMe.getTitleAboutMe(),modelGetAboutMe.getDescriptionAboutMe(),modelGetAboutMe.getEmail(),modelGetAboutMe.getPhoneNumber(),modelGetAboutMe.getAddress()).enqueue(new Callback<String>() {
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
