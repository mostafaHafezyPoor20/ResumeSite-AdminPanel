package mostafa.hafezypoor.ahmmad.panel.data.netwrok;


import java.util.List;

import mostafa.hafezypoor.ahmmad.panel.data.model.ModelBlog;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelEducation;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelExperiences;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetAboutMe;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelGetProfile;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMessage;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelMyServices;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelSkill;
import mostafa.hafezypoor.ahmmad.panel.data.model.ModelWork;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IConnection {
    @FormUrlEncoded
    @POST("profile/getProfile.php")
    Call<ModelGetProfile> getProfile(@Field("key")String key);

    @FormUrlEncoded
    @POST("profile/setProfile.php")
    Call<String>setProfile(@Field("key")String key,@Field("name")String name,@Field("summerSkill")String summerSkill,@Field("instagram")String Instagram,@Field("telegram")String telegram);

    @FormUrlEncoded
    @POST("aboutME/getAboutMe.php")
    Call<ModelGetAboutMe>getAboutMe(@Field("key")String key);

    @FormUrlEncoded
    @POST("aboutME/setAboutMe.php")
    Call<String>setAboutMe(@Field("key")String key,@Field("titleAboutMe")String titleAboutMe,@Field("descriptionAboutMe")String descriptionAboutMe,@Field("email")String email,@Field("phoneNumber")String phoneNumber,@Field("address")String address);

    @FormUrlEncoded
    @POST("experiences/getExperiences.php")
    Call<List<ModelExperiences>>getExperiences(@Field("key")String key);

    @FormUrlEncoded
    @POST("experiences/addExperiences.php")
    Call<String>addExperiences(@Field("key")String key,@Field("title")String title,@Field("date")String date,@Field("description")String description);

    @FormUrlEncoded
    @POST("experiences/removeWorkExperiences.php")
    Call<String>removeWorkExperiences(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("experiences/getWorkExperience.php")
    Call<ModelExperiences>getWorkExperience(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("experiences/editExperiences.php")
    Call<String>editWorkExperience(@Field("key")String key,@Field("id")String id,@Field("title")String title,@Field("date")String date,@Field("description")String description);

    @FormUrlEncoded
    @POST("education/getEducations.php")
    Call<List<ModelEducation>>getEducations(@Field("key")String key);

    @FormUrlEncoded
    @POST("education/addEducation.php")
    Call<String>addEducation(@Field("key")String key,@Field("title")String title,@Field("date")String date,@Field("description")String description);

    @FormUrlEncoded
    @POST("education/removeEducation.php")
    Call<String>removeEducation(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("education/getEducation.php")
    Call<ModelEducation>getEducation(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("education/editEducation.php")
    Call<String>editEducation(@Field("key")String key,@Field("id")String id,@Field("title")String title,@Field("date")String date,@Field("description")String description);

    @FormUrlEncoded
    @POST("services/getServices.php")
    Call<List<ModelMyServices>>getMyServices(@Field("key")String key);

    @FormUrlEncoded
    @POST("services/getService.php")
    Call<ModelMyServices>getService(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("services/editService.php")
    Call<String>editService(@Field("key")String key,@Field("id")String id,@Field("icon")String icon,@Field("title")String title,@Field("description")String description);

    @FormUrlEncoded
    @POST("services/addService.php")
    Call<String>addService(@Field("key")String key,@Field("icon")String icon,@Field("title")String title,@Field("description")String description);

    @FormUrlEncoded
    @POST("services/removeService.php")
    Call<String>removeService(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("skills/getSkills.php")
    Call<List<ModelSkill>>getSkills(@Field("key")String key);

    @FormUrlEncoded
    @POST("skills/addSkill.php")
    Call<String>addSkill(@Field("key")String key,@Field("title")String title,@Field("percent")String percent);

    @FormUrlEncoded
    @POST("skills/removeSkill.php")
    Call<String>removeSkill(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("skills/editSkill.php")
    Call<String>editSkill(@Field("key")String key,@Field("id")String id,@Field("percent")String percent,@Field("title")String title);

    @FormUrlEncoded
    @POST("skills/getSkill.php")
    Call<ModelSkill>getSkill(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("works/getWorks.php")
    Call<List<ModelWork>>getWorks(@Field("key")String key);

    @FormUrlEncoded
    @POST("works/removeWork.php")
    Call<String>removeWork(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("works/getWork.php")
    Call<ModelWork>getWork(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("works/editWork.php")
    Call<String>editWork(@Field("key")String key,@Field("id")String id,@Field("title")String title,@Field("description")String description);

    @FormUrlEncoded
    @POST("blog/getBlogs.php")
    Call<List<ModelBlog>> getBlogs(@Field("key")String key);

    @FormUrlEncoded
    @POST("blog/removeBlog.php")
    Call<String>removeBlog(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("blog/getBlog.php")
    Call<ModelBlog> getBlog(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("blog/editBlog.php")
    Call<String>editBlog(@Field("key")String key,@Field("id")String id,@Field("title")String title,@Field("date")String date,@Field("text")String text);

    @FormUrlEncoded
    @POST("messages/getMessages.php")
    Call<List<ModelMessage>>getMessages(@Field("key")String key);

    @FormUrlEncoded
    @POST("messages/visitedMessage.php")
    Call<String>visitedMessage(@Field("key")String key,@Field("id")String id);

    @FormUrlEncoded
    @POST("notification/checkToken.php")
    Call<String>checkToken(@Field("key")String key,@Field("token")String token);
}
