package mostafa.hafezypoor.ahmmad.panel.ui.workExperiences;

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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FWorkExperiences extends Fragment {
    private RecyclerView list;
    ExtendedFloatingActionButton addFab;
    private FWorkExperiencesViewModel fWorkExperiencesViewModel;
    private FragmentActivity fragmentActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
        fWorkExperiencesViewModel=new ViewModelProvider(getActivity()).get(FWorkExperiencesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=LayoutInflater.from(getContext()).inflate(R.layout.fwork_experiences,container,false);
       list=view.findViewById(R.id.list);
       addFab=view.findViewById(R.id.addFab);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingWorkExperiences(getContext()));
        fWorkExperiencesViewModel.getWorkExperiences(Constants.key).observe(getActivity(), new Observer<List<ModelExperiences>>() {
            @Override
            public void onChanged(List<ModelExperiences> modelExperiences) {
             list.setLayoutManager(new LinearLayoutManager(getContext()));
             list.setAdapter(new AdapterWorkExperiences(getContext(), modelExperiences, new AdapterWorkExperiences.IEvent() {
                 @Override
                 public void removeWorkExperiences(String id) {
                     fWorkExperiencesViewModel.removeWorkExperiences(Constants.key,id).observe(getActivity(), new Observer<String>() {
                         @Override
                         public void onChanged(String s) {
                             if (s.equals("200")){
                             }
                         }
                     });
                 }

                 @Override
                 public void editWorkExperiences(String id) {
                     Intent intent=new Intent(getContext(), EditWorkExperiences.class);
                     intent.putExtra("workExperiencesID",id);
                     getContext().startActivity(intent);
                     getActivity().finish();
                 }
             }));
            }
        });
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getActivity(), AddWorkExperiences.class));
              getActivity().finish();
            }
        });
        fWorkExperiencesViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof IOException){
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FWorkExperiences()),"internet").commit();
                }
            }
        });
    }
}
