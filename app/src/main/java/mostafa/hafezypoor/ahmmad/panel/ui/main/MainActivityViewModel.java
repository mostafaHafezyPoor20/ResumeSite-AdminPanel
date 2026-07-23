package mostafa.hafezypoor.ahmmad.panel.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import mostafa.hafezypoor.ahmmad.panel.data.repository.MainActivityRepository;

public class MainActivityViewModel extends ViewModel {
    private final MainActivityRepository mainActivityRepository;

    public MainActivityViewModel() {
        this.mainActivityRepository = new MainActivityRepository();
    }
    public LiveData<String>checkToken(String key,String token){
      return   mainActivityRepository.checkToken(key,token);
    }
}
