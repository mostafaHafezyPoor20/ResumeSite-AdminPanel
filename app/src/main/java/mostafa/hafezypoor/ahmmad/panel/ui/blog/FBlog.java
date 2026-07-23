package mostafa.hafezypoor.ahmmad.panel.ui.blog;

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
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelBlog;
import mostafa.hafezypoor.ahmmad.panel.ui.main.FConnectionInternetError;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class FBlog extends Fragment {
private BlogViewModel blogViewModel;
private RecyclerView list;
private ExtendedFloatingActionButton fabAdd;
private FragmentActivity fragmentActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
        blogViewModel=new ViewModelProvider(getActivity()).get(BlogViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fblog,container,false);
        list=view.findViewById(R.id.list);
        fabAdd=view.findViewById(R.id.fabAdd);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(new AdapterLoadingBlog(getContext()));
        blogViewModel.getBlogs(Constants.key).observe(getActivity(), new Observer<List<ModelBlog>>() {
            @Override
            public void onChanged(List<ModelBlog> modelBlogs) {
                list.setLayoutManager(new LinearLayoutManager(getActivity()));
                list.setAdapter(new AdapterBlog(getActivity(), modelBlogs, new AdapterBlog.IEvent() {
                    @Override
                    public void removeBlog(String id) {
                      blogViewModel.removeBlog(Constants.key,id).observe(getActivity(), new Observer<String>() {
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
             startActivity(new Intent(getActivity(),AddBlog.class));
             getActivity().finish();
            }
        });
        blogViewModel.handleError().observe(getActivity(), new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                if (throwable instanceof IOException){
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(new FBlog()),"internet").commit();
                }
            }
        });
    }
}
