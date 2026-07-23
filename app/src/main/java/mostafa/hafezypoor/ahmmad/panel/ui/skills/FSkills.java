package mostafa.hafezypoor.ahmmad.panel.ui.skills;

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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelSkill;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FSkills extends Fragment {
    private RecyclerView list;
    private SkillsViewModel skillsViewModel;
    private FragmentActivity fragmentActivity;
    private ExtendedFloatingActionButton fabAdd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
        skillsViewModel=new ViewModelProvider(getActivity()).get(SkillsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fskills,container,false);
        fabAdd=view.findViewById(R.id.fabAdd);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingSkills(getContext()));
        skillsViewModel.getSkills(Constants.key).observe(getActivity(), new Observer<List<ModelSkill>>() {
            @Override
            public void onChanged(List<ModelSkill> modelSkills) {
                list.setLayoutManager(new LinearLayoutManager(getContext()));
                list.setAdapter(new AdapterSkills(getContext(), modelSkills, new AdapterSkills.IEvent() {
                    @Override
                    public void removeSkill(String id) {
                        skillsViewModel.removeSkill(Constants.key,id).observe(getActivity(), new Observer<String>() {
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
               startActivity(new Intent(getActivity(),AddSkill.class));
           }
       });
        skillsViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof IOException){
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FSkills()),"internet").commit();
                }
            }
        });
    }
}
