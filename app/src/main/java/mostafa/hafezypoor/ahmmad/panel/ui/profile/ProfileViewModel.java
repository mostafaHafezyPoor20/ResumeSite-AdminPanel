package mostafa.hafezypoor.ahmmad.panel.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetProfile;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FProfileRepository;

public class ProfileViewModel extends ViewModel {
    private final FProfileRepository fProfileRepository;
    public ProfileViewModel(){
        fProfileRepository=new FProfileRepository();
    }
    public LiveData<ModelGetProfile>getProfile(String key){
        return fProfileRepository.getProfile(key);
    }
    public LiveData<String>setProfile(String key,String name,String summerSkill,String instagram,String telegram){
        return fProfileRepository.setProfile(key,name,summerSkill,instagram,telegram);
    }
    public LiveData<Throwable>handleError(){
        return fProfileRepository.handleError();
    }

}
