package mostafa.hafezypoor.ahmmad.panel.ui.main;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import mostafa.hafezypoor.ahmmad.panel.R;

public class FConnectionInternetError extends Fragment {
    private MaterialButton btnTryAgain;
    private Fragment fragment=null;
    public FConnectionInternetError(){

    }
    public FConnectionInternetError(Fragment fragment){
       this.fragment=fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= LayoutInflater.from(getContext()).inflate(R.layout.fconnection_internet_error,container,false);
       btnTryAgain=view.findViewById(R.id.btnTryAgain);
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fragment!=null){
            btnTryAgain.setVisibility(VISIBLE);
            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayout,fragment,"ffadf").commit();
                }
            });
        }
    }

}
