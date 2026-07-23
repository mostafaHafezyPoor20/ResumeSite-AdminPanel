package mostafa.hafezypoor.ahmmad.panel.ui.myServices;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMyServices;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FMyServicesRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyServiceViewModel extends ViewModel {
    private final FMyServicesRepository fMyServicesRepository;
    public MyServiceViewModel(){
        fMyServicesRepository=new FMyServicesRepository();
    }
    public LiveData<Throwable>handleError(){
        return fMyServicesRepository.handleError();
    }
    public LiveData<List<ModelMyServices>>getServices(String key){
        return fMyServicesRepository.getMyServices(key);
    }
    public LiveData<ModelMyServices>getService(String key,String id){
        return fMyServicesRepository.getService(key,id);
    }
   public LiveData<String>editService(String key,String id,String icon,String title,String description){
        return fMyServicesRepository.editService(key, id, icon, title, description);
   }
   public LiveData<String>addService(String key,String icon,String title,String description){
        return fMyServicesRepository.addService(key,icon,title,description);
   }
   public LiveData<String>removeService(String key,String id){
        return fMyServicesRepository.removeService(key, id);
   }
}
