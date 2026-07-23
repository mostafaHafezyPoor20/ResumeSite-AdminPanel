package mostafa.hafezypoor.ahmmad.panel.ui.skills;

import androidx.core.text.StringKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Struct;
import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelSkill;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FSkillRepository;
import retrofit2.http.Field;

public class SkillsViewModel extends ViewModel {
    private final FSkillRepository fSkillRepository;

    public SkillsViewModel() {
        fSkillRepository=new FSkillRepository();
    }
    public LiveData<List<ModelSkill>>getSkills(String key){
        return fSkillRepository.getSkills(key);
    }
    public LiveData<String>addSkill(String key,String title,String percent){
        return fSkillRepository.addSkill(key, title, percent);
    }
    public LiveData<String>removeSkill(String key,String id){
        return fSkillRepository.removeSkill(key,id);
    }
    public LiveData<String>editSkill(String key,String id,String percent,String title){
        return fSkillRepository.editSkill(key,id,percent,title);
    }
    public LiveData<ModelSkill>getSkill(String key,String id){
        return fSkillRepository.getSkill(key,id);
    }
    public LiveData<Throwable>handleError(){
        return fSkillRepository.handleError();
    }
}
