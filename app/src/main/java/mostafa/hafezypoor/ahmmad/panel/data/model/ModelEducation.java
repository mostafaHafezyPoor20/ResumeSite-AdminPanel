package mostafa.hafezypoor.ahmmad.panel.data.model;

public class ModelEducation {
    private String id;
    private String title;
    private String date;

    public String getId() {
        return id;
    }

    public ModelEducation(String id, String title, String date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    private String description;
}
