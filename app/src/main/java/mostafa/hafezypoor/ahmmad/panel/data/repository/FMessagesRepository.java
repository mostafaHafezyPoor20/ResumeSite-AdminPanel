package mostafa.hafezypoor.ahmmad.panel.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMessage;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FMessagesRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<List<ModelMessage>>getMessages(String key){
        MutableLiveData<List<ModelMessage>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getMessages(key).enqueue(new Callback<List<ModelMessage>>() {
            @Override
            public void onResponse(Call<List<ModelMessage>> call, Response<List<ModelMessage>> response) {
                if (response.isSuccessful()){
                   mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelMessage>> call, Throwable t) {
               handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>visitedMessage(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().visitedMessage(Constants.key,id).enqueue(new Callback<String>() {
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
