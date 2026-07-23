package mostafa.hafezypoor.ahmmad.panel.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelBlog;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FBlogRepository {
    private MutableLiveData<Throwable>handleError=new MutableLiveData<>();
    public LiveData<Throwable>handleError(){
        return handleError;
    }
    public LiveData<List<ModelBlog>> getBlogs(String key){
        MutableLiveData<List<ModelBlog>>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getBlogs(key).enqueue(new Callback<List<ModelBlog>>() {
            @Override
            public void onResponse(Call<List<ModelBlog>> call, Response<List<ModelBlog>> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ModelBlog>> call, Throwable t) {
            handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>removeBlog(String key,String id){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().removeBlog(key,id).enqueue(new Callback<String>() {
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
    public LiveData<ModelBlog>getBlog(String key,String id){
        MutableLiveData<ModelBlog>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().getBlog(key,id).enqueue(new Callback<ModelBlog>() {
            @Override
            public void onResponse(Call<ModelBlog> call, Response<ModelBlog> response) {
                if (response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelBlog> call, Throwable t) {
               handleError.setValue(t);
            }
        });
        return mutableLiveData;
    }
    public LiveData<String>editBlog(String key,String id,String title,String date,String text){
        MutableLiveData<String>mutableLiveData=new MutableLiveData<>();
        RetrofitInit.getInstance().editBlog(key, id, title, date, text).enqueue(new Callback<String>() {
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
    return mutableLiveData;}
}
