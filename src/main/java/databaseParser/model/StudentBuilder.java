package databaseParser.model;

public class StudentBuilder {
    private String specialty;
    private String groupID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String city;

    public StudentBuilder setSpecialty(String specialty) {
        this.specialty = specialty;
        return this;
    }

    public StudentBuilder setGroupID(String groupID) {
        this.groupID = groupID;
        return this;
    }

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public StudentBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public StudentBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public Student build() {
        return new Student(specialty, groupID, name, surname, phoneNumber, city);
    }
}
