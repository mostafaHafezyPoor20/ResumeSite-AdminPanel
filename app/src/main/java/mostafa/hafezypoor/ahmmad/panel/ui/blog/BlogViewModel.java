package mostafa.hafezypoor.ahmmad.panel.ui.blog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelBlog;
import mostafa.hafezypoor.ahmmad.panel.data.repository.FBlogRepository;

public class BlogViewModel extends ViewModel {
    private final FBlogRepository fBlogRepository;
    public BlogViewModel(){
        fBlogRepository=new FBlogRepository();
    }
    public LiveData<List<ModelBlog>> getBlogs(String key){
        return fBlogRepository.getBlogs(key);
    }
    public LiveData<String>removeBlog(String key,String id){
        return fBlogRepository.removeBlog(key,id);
    }
    public LiveData<ModelBlog>getBlog(String key,String id){
        return fBlogRepository.getBlog(key,id);
    }
    public LiveData<String>editBlog(String key,String id,String title,String date,String text){
        return fBlogRepository.editBlog(key,id,title,date,text);
    }
    public LiveData<Throwable>handleError(){
        return fBlogRepository.handleError();
    }
}
