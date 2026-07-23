package mostafa.hafezypoor.ahmmad.panel.ui.messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMessage;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FMessagesRepository;

public class MessageViewModel extends ViewModel {
    private final FMessagesRepository fMessagesRepository;

    public MessageViewModel() {
        fMessagesRepository=new FMessagesRepository();
    }
    public LiveData<List<ModelMessage>>getMessages(String key){
        return fMessagesRepository.getMessages(key);
    }
    public LiveData<String>visitedMessage(String key,String id){
        return fMessagesRepository.visitedMessage(key,id);
    }
    public LiveData<Throwable>handleError(){
        return fMessagesRepository.handleError();
    }

}
