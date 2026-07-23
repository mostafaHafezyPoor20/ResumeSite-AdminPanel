package mostafa.hafezypoor.ahmmad.panel.ui.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMessage;
import mostafa.hafezypoor.ahmmad.panel.ui.blog.AdapterBlog;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FMessages extends Fragment {
     MessageViewModel messageViewModel;
     private FragmentActivity fragmentActivity;
    private RecyclerView list;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
        messageViewModel=new ViewModelProvider(getActivity()).get(MessageViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fmessages,container,false);
        list=view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingMessages(getContext()));
      messageViewModel.getMessages(Constants.key).observe(getActivity(), new Observer<List<ModelMessage>>() {
          @Override
          public void onChanged(List<ModelMessage> modelMessages) {
              list.setLayoutManager(new LinearLayoutManager(getActivity()));
              list.setAdapter(new AdapterMessages(getActivity(),modelMessages));
          }
      });
      messageViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
          @Override
          public void onChanged(Throwable throwable) {
              if (throwable instanceof IOException){
                  fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FMessages()),"internet").commit();
              }
          }
      });
    }
}
