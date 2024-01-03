package ba.sum.fpmoz.pma;

public class ReadWriteUserDetails {
    public String doB, gender, mobile;

    //Constructor
    public ReadWriteUserDetails() {};
    public ReadWriteUserDetails( String textdoB, String textGender, String textMobile) {
        this.doB = textdoB;
        this.gender = textGender;
        this.mobile = textMobile;
    }
}
