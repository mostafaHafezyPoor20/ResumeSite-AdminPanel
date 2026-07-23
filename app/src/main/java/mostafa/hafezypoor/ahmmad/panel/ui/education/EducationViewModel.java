package mostafa.hafezypoor.ahmmad.panel.ui.education;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelEducation;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FEducationRepository;

public class EducationViewModel extends ViewModel {
    private final FEducationRepository fEducationRepository;

    public EducationViewModel() {
        fEducationRepository=new FEducationRepository();
    }
    public LiveData<List<ModelEducation>>getEducations(String key){
      return  fEducationRepository.getEducations(key);
    }
    public LiveData<String>addEducation(String key,String title,String date,String description){
        return fEducationRepository.addEducation(key,title,date,description);
    }
    public LiveData<String>removeEducation(String key,String id){
        return fEducationRepository.removeEducation(key, id);
    }
    public LiveData<ModelEducation>getEducation(String key,String id){
        return fEducationRepository.getEducation(key,id);
    }
    public LiveData<String>editEducation(String key,ModelEducation modelEducation){
        return fEducationRepository.editEducation(key,modelEducation);
    }
    public LiveData<Throwable>handleError(){
        return fEducationRepository.handleError();
    }
}
