package mostafa.hafezypoor.ahmmad.panel.ui.works;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelWork;
import mostafa.hafezypoor.ahmmad.panel.data.netwrok.RetrofitInit;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FWorksRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorksViewModel extends ViewModel {
    private final FWorksRepository fWorksRepository;
    public WorksViewModel(){
        fWorksRepository=new FWorksRepository();
    }
  public LiveData<List<ModelWork>>getWorks(String key){
        return fWorksRepository.getWorks(key);
  }
  public LiveData<String>removeWork(String key,String id){
        return fWorksRepository.removeWork(key,id);
  }
  public LiveData<ModelWork>getWork(String key,String id){
        return fWorksRepository.getWork(key,id);
  }
  public LiveData<String>editWork(String key,String id,String title,String description){
        return fWorksRepository.editWork(key, id, title, description);
  }
  public LiveData<Throwable>handleError(){
        return fWorksRepository.handleError();
  }
}
