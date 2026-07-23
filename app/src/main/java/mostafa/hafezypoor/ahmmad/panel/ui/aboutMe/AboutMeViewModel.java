package mostafa.hafezypoor.ahmmad.panel.ui.aboutMe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetAboutMe;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FAboutMeRepository;

public class AboutMeViewModel extends ViewModel {
    private final FAboutMeRepository fAboutMeRepository;
    public LiveData<Throwable>handleError(){
        return fAboutMeRepository.handleError();
    }
    public AboutMeViewModel() {
        fAboutMeRepository=new FAboutMeRepository();
    }
    public LiveData<ModelGetAboutMe>getAbout(String key){
        return fAboutMeRepository.getAboutMe(key);
    }
    public LiveData<String>setAbout(String titleAboutMe,String descriptionAboutMe,String email,String phoneNumber,String address){
        return fAboutMeRepository.setAboutMe(new ModelGetAboutMe(titleAboutMe,descriptionAboutMe,address,email,phoneNumber));
    }
}
