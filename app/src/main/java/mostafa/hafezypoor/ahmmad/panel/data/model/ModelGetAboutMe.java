package mostafa.hafezypoor.ahmmad.panel.data.model;

public class ModelGetAboutMe {
    private String titleAboutMe;
    private String descriptionAboutMe;
    private String address;
    private String email;

    public ModelGetAboutMe(String titleAboutMe, String descriptionAboutMe, String address, String email, String phoneNumber) {
        this.titleAboutMe = titleAboutMe;
        this.descriptionAboutMe = descriptionAboutMe;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getDescriptionAboutMe() {
        return descriptionAboutMe;
    }

    public String getTitleAboutMe() {
        return titleAboutMe;
    }

    private String phoneNumber;
}
