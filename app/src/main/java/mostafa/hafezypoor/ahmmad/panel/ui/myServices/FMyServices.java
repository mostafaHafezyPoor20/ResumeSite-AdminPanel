package mostafa.hafezypoor.ahmmad.panel.ui.myServices;

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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMyServices;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FMyServices extends Fragment {
    private MyServiceViewModel myServiceViewModel;
    private RecyclerView list;
    private ExtendedFloatingActionButton fabAdd;
    private FragmentActivity fragmentActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View view=LayoutInflater.from(getContext()).inflate(R.layout.fmy_services,container,false);
    list=view.findViewById(R.id.list);
    fabAdd=view.findViewById(R.id.fabAdd);
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myServiceViewModel=new ViewModelProvider(getActivity()).get(MyServiceViewModel.class);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingMyServices(getContext()));
        myServiceViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof IOException){
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FMyServices()),"internet").commit();
                }
            }
        });
        myServiceViewModel.getServices(Constants.key).observe(getActivity(), new Observer<List<ModelMyServices>>() {
            @Override
            public void onChanged(List<ModelMyServices> modelMyServices) {
               list.setLayoutManager(new LinearLayoutManager(getActivity()));
               list.setAdapter(new AdapterMyServices(getContext(), modelMyServices, new AdapterMyServices.IEvent() {
                   @Override
                   public void removeService(String id) {
                      myServiceViewModel.removeService(Constants.key,id).observe(getActivity(), new Observer<String>() {
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
                startActivity(new Intent(getActivity(), AddMyServices.class));
                getActivity().finish();
            }
        });
    }
}
