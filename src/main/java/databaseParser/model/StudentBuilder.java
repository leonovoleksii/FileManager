package databaseParser.model;

public class StudentBuilder {
    private String specialty;
    private String groupID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String city;

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Student build() {
        return new Student(specialty, groupID, name, surname, phoneNumber, city);
    }
}
