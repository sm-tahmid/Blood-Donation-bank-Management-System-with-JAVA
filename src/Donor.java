
import java.text.SimpleDateFormat;
import java.util.Date;

public class Donor {
    private String name;
    private String bloodType;
    private Date lastDonationDate;
    private String address;
    private String contactNumber;
    private String email;
    private int age;  // Added age attribute


    // Constructor to initialize a new Donor.
    public Donor(String name, int age, String bloodType, String address, String contactNumber, String email) {
        this.name = name;
        this.age = age;  // Initialize age
        this.bloodType = bloodType;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        // By default, when a donor is registered, the last donation date is null.
        this.lastDonationDate = null;
    }


    // Getter and Setter methods for each attribute:

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {  // Getter for age
        return age;
    }

    public void setAge(int age) {  // Setter for age
        this.age = age;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Date getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(Date lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // Method to check if the donor is eligible for donation (not donated in the last 4 months)
    public boolean isEligibleForDonation() {
        if (lastDonationDate == null) {
            return true;
        }
        Date currentDate = new Date();
        long differenceInTime = currentDate.getTime() - lastDonationDate.getTime();
        long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24));
        return differenceInDays > 120; // 4 months * 30 days/month = 120 days
    }


    //ToString
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = lastDonationDate != null ? sdf.format(lastDonationDate) : "Never Donated";
        return "Donor: " + name + ", Age: " + age + ", Blood Type: " + bloodType + ", Last Donated: " + dateStr;  // Added age in toString
    }


}
