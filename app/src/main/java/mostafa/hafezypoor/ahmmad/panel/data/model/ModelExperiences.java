package mostafa.hafezypoor.ahmmad.panel.data.model;

public class ModelExperiences {
    private String id;
    private String title;

    public ModelExperiences(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public ModelExperiences(String id, String title, String date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    private String date;
    private String description;

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
