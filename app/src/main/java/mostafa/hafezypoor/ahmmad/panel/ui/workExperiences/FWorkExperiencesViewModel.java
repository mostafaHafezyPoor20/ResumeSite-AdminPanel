package mostafa.hafezypoor.ahmmad.panel.ui.workExperiences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FWorkExperiencesRepository;

public class FWorkExperiencesViewModel extends ViewModel {
    private  final FWorkExperiencesRepository fWorkExperiencesRepository;
    public FWorkExperiencesViewModel(){
        fWorkExperiencesRepository=new FWorkExperiencesRepository();
    }
    public LiveData<List<ModelExperiences>>getWorkExperiences(String key){
        return fWorkExperiencesRepository.getExperiences(key);
    }
    public LiveData<String>addWorkExperiences(String key,ModelExperiences modelExperiences){
        return fWorkExperiencesRepository.addExperiences(key,modelExperiences);
    }
    public LiveData<String>removeWorkExperiences(String key,String id){
        return fWorkExperiencesRepository.removeWorkExperiences(key,id);
    }
    public LiveData<ModelExperiences>getWorkExperience(String key,String id){
        return fWorkExperiencesRepository.getWorkExperience(key,id);
    }
    public LiveData<String>editWorkExperience(String key,String id,String title,String date,String description){
        return fWorkExperiencesRepository.editWorkExperience(key,id,title,date,description);
    }
    public LiveData<Throwable>handleError(){
        return fWorkExperiencesRepository.handleError();
    }
}
