package mostafa.hafezypoor.ahmmad.panel.ui.education;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.IOException;
import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelEducation;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FEducation extends Fragment {
    private RecyclerView list;
    private ExtendedFloatingActionButton fabAdd;
    private FragmentActivity fragmentActivity;
    private EducationViewModel educationViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.feducation,container,false);
         list=view.findViewById(R.id.list);
         fabAdd=view.findViewById(R.id.fabAdd);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        educationViewModel=new ViewModelProvider(getActivity()).get(EducationViewModel.class);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingEducation(getContext()));
        educationViewModel.getEducations(Constants.key).observe(getActivity(), new Observer<List<ModelEducation>>() {
            @Override
            public void onChanged(List<ModelEducation> modelEducations) {
                list.setLayoutManager(new LinearLayoutManager(getContext()));
                list.setAdapter(new AdapterEducation(getActivity(), modelEducations, new AdapterEducation.IEvent() {
                    @Override
                    public void removeEducation(String id) {
                      educationViewModel.removeEducation(Constants.key,id).observe(getActivity(), new Observer<String>() {
                          @Override
                          public void onChanged(String s) {
                              if (s.equals("200")){

                              }
                          }
                      });
                    }
                }));
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getActivity(), AddEducation.class));
            }
        });
        educationViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof IOException){
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FEducation()),"internet").commit();
                }
            }
        });
    }
}
