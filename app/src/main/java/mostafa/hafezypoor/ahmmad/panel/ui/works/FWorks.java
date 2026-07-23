package mostafa.hafezypoor.ahmmad.panel.ui.works;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.IOException;
import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelWork;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FWorks extends Fragment {
    private WorksViewModel worksViewModel;
    private RecyclerView list;
    private ExtendedFloatingActionButton fabAdd;
    private FragmentActivity fragmentActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
        worksViewModel=new ViewModelProvider(getActivity()).get(WorksViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=LayoutInflater.from(getContext()).inflate(R.layout.fworks,container,false);
       list=view.findViewById(R.id.list);
       fabAdd=view.findViewById(R.id.fabAdd);
      return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingWorks(getContext()));
        worksViewModel.getWorks(Constants.key).observe(getActivity(), new Observer<List<ModelWork>>() {
            @Override
            public void onChanged(List<ModelWork> modelWorks) {
               list.setLayoutManager(new LinearLayoutManager(getActivity()));
               list.setAdapter(new AdapterWorks(getContext(), modelWorks, new AdapterWorks.IEvent() {
                   @Override
                   public void removeWork(String id) {
                       worksViewModel.removeWork(Constants.key,id).observe(getActivity(), new Observer<String>() {
                           @Override
                           public void onChanged(String s) {

                           }
                       });
                   }
               }));
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddWorks.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        worksViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                 if (throwable instanceof IOException){
                     fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FWorks()),"internet").commit();
                 }
            }
        });
    }
}
