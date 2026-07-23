package mostafa.hafezypoor.ahmmad.panel.ui.main;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import mostafa.hafezypoor.ahmmad.panel.R;
import mostafa.hafezypoor.ahmmad.panel.ui.aboutMe.FAboutMe;
import mostafa.hafezypoor.ahmmad.panel.ui.blog.FBlog;
import mostafa.hafezypoor.ahmmad.panel.ui.education.FEducation;
import mostafa.hafezypoor.ahmmad.panel.ui.messages.FMessages;
import mostafa.hafezypoor.ahmmad.panel.ui.myServices.FMyServices;
import mostafa.hafezypoor.ahmmad.panel.ui.profile.FProfile;
import mostafa.hafezypoor.ahmmad.panel.ui.skills.FSkills;
import mostafa.hafezypoor.ahmmad.panel.ui.workExperiences.FWorkExperiences;
import mostafa.hafezypoor.ahmmad.panel.ui.works.FWorks;
import mostafa.hafezypoor.ahmmad.panel.utils.Constants;

public class MainActivity extends AppCompatActivity {
    Handler handler=new Handler(Looper.getMainLooper());
    ConnectivityManager connectivityManager;
    ConnectivityManager.NetworkCallback networkCallback;
    RelativeLayout toolbar;
    Fragment fragment;
    private MainActivityViewModel mainActivityViewModel;
TextView title;
DrawerLayout drawerLayout;
ImageView openMenu;
NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel=new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);
        checkToken();
        checkPermissionNotification();
        checkInternetConnection();
        connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        title=findViewById(R.id.title);
        openMenu=findViewById(R.id.openMenu);
        navigationView=findViewById(R.id.menu);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawerLayoutMain);
        if (getIntent().getExtras()!=null){
          navigationView.setCheckedItem(getIntent().getExtras().getInt("section"));
          int section=getIntent().getExtras().getInt("section");
         if (section==R.id.workExperiences){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FWorkExperiences(),"fworkExperiences").commit();
             title.setText("تجربه کاری من");
         } else if (section==R.id.education) {
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FEducation(),"feducation").commit();
             title.setText("تحصیلات من");
         }else if (section==R.id.myServices){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FMyServices(),"fmyServices").commit();
             title.setText("سرویس های من");
         }else if (section==R.id.skills){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FSkills(),"fskills").commit();
             title.setText("مهارت های من");
         }else if (section==R.id.works){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FWorks(),"fskills").commit();
             title.setText("نمونه کار های من");
         }else if (section==R.id.blog){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FBlog(),"fblog").commit();
             title.setText("وبلاگ  من");
         }else if (section==R.id.messages){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FMessages(),"fmessages").commit();
             title.setText("پیام های من");
         }else{
             getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FProfile(),"feducation").commit();
             title.setText("پروفایل");
             navigationView.setCheckedItem(R.id.profile);
         }
        }else{
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FProfile(),"feducation").commit();
            title.setText("پروفایل");
            navigationView.setCheckedItem(R.id.profile);
        }


        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 drawerLayout.close();
                 if (menuItem.getItemId()==R.id.profile){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FProfile(),"profile").commit();
                     title.setText("پروفایل");
                 }else if (menuItem.getItemId()==R.id.aboutMe){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FAboutMe(),"faboutMe").commit();
                     title.setText("درباره من");
                 }else if (menuItem.getItemId()==R.id.workExperiences){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FWorkExperiences(),"fworkExperiences").commit();
                     title.setText("تجربه کاری من");
                 }else if (menuItem.getItemId()==R.id.education){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FEducation(),"feducation").commit();
                     title.setText("تحصیلات من");
                 }else if (menuItem.getItemId()==R.id.myServices){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FMyServices(),"fmyServices").commit();
                     title.setText("سرویس های من");
                 }else if (menuItem.getItemId()==R.id.skills){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FSkills(),"fskills").commit();
                     title.setText("مهارت های من");
                 }else if (menuItem.getItemId()==R.id.works){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FWorks(),"fskills").commit();
                     title.setText("نمونه کار های من");
                 }else if (menuItem.getItemId()==R.id.blog){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FBlog(),"fblog").commit();
                     title.setText("وبلاگ  من");
                 }else if (menuItem.getItemId()==R.id.messages){
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FMessages(),"fmessages").commit();
                     title.setText("پیام های من");
                 }
                 return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    private ConnectivityManager.NetworkCallback checkInternetConnection(){
        networkCallback=new ConnectivityManager.NetworkCallback(){
            @Override
            public void onBlockedStatusChanged(@NonNull Network network, boolean blocked) {
                super.onBlockedStatusChanged(network, blocked);
            }

            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                if (getSupportFragmentManager().findFragmentById(R.id.mainActivityFrameLayout) instanceof FConnectionInternetError){
                    int id=R.id.profile;
                    if (fragment instanceof FProfile){
                        id=R.id.profile;
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FProfile(),"profile").commit();
                    }else if (fragment instanceof FAboutMe){
                        id=R.id.aboutMe;
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FAboutMe(),"faboutME").commit();
                    }else if (fragment instanceof FWorkExperiences){
                        id=R.id.workExperiences;
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FWorkExperiences(),"fworkExperiences").commit();
                    }else if (fragment instanceof FEducation){
                        id=R.id.education;
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FEducation(),"feducation").commit();
                    }else if (fragment instanceof FMyServices){
                     id=R.id.myServices;
                     getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FMyServices(),"fmyServices").commit();
                    }else if (fragment instanceof FSkills){
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FSkills(),"fskills").commit();
                        title.setText("مهارت های من");
                    } else if (fragment instanceof FWorks){
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FWorks(),"fworks").commit();
                        title.setText("نمونه کار های من");
                    }else if (fragment instanceof FBlog){
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FBlog(),"fblog").commit();
                        title.setText("وبلاگ  من");
                    }else if (fragment instanceof FMessages){
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FMessages(),"fmessages").commit();
                        title.setText("پیام های من");
                    }else {
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FProfile(),"feducation").commit();
                        title.setText("پروفایل");
                    }
                    int finalId = id;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (finalId==R.id.profile){
                                title.setText("پروفایل");
                            }else if (finalId==R.id.aboutMe){
                                title.setText("درباره من");
                            }else if (finalId==R.id.workExperiences){
                                title.setText("تجربه کاری من");
                            }else if (finalId==R.id.education){
                                title.setText("تحصیلات من");
                            }else if (finalId==R.id.myServices){
                                title.setText("سرویس های من");
                            }else if (finalId==R.id.skills){
                                title.setText("مهارت های من");
                            }else if (finalId==R.id.works){
                                title.setText("نمونه کار های من");
                            }else if (finalId==R.id.blog){
                                title.setText("وبلاگ  من");
                            }else if (finalId==R.id.messages){
                                title.setText("پیام های من");
                            }
                            navigationView.setCheckedItem(finalId);
                            toolbar.setVisibility(VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
               fragment=getSupportFragmentManager().findFragmentById(R.id.mainActivityFrameLayout);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.mainActivityFrameLayout,new FConnectionInternetError(),"feducation").commit();
                toolbar.post(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setVisibility(GONE);

                    }
                });

            }
        };
        return networkCallback;
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }
    private void checkPermissionNotification(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                BottomSheetDialog dialog=new BottomSheetDialog(this,R.style.AppBottomSheetDialog);
                dialog.setContentView(R.layout.dcheck_permission_notification);
                dialog.show();
                ((MaterialButton)dialog.findViewById(R.id.btnDismiss)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                ((MaterialButton)dialog.findViewById(R.id.enableNotification)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},1001);
                        dialog.dismiss();
                    }
                });
            }
        }
    }
    private void checkToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                 mainActivityViewModel.checkToken(Constants.key,task.getResult()).observe(MainActivity.this, new Observer<String>() {
                     @Override
                     public void onChanged(String s) {

                     }
                 });
            }
        });
    }
}