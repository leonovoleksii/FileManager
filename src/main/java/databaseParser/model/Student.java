package databaseParser.model;

public class Student {
    private String specialty;
    private String groupID;
    private String name;
    private String surname;
    private String phoneNumber;
    private String city;

    public Student(String specialty, String groupID, String name, String surname,
                   String phoneNumber, String city) {
        this.specialty = specialty;
        this.groupID = groupID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public boolean similarTo(Student other) {
        return (other.specialty == null || other.specialty.equals(this.specialty)) &&
                (other.groupID == null || other.groupID.equals(this.groupID)) &&
                (other.name == null || other.name.equals(this.name)) &&
                (other.surname == null || other.surname.equals(this.surname)) &&
                (other.phoneNumber == null || other.phoneNumber.equals(this.phoneNumber)) &&
                (other.city == null || other.city.equals(this.city));
    }

    public String toString() {
        return "Specialty: " +
                specialty +
                "\n" +
                "GroupID: " +
                groupID +
                "\n" +
                "Name: " +
                name +
                "\n" +
                "Surname: " +
                surname +
                "\n" +
                "PhoneNumber: " +
                phoneNumber +
                "\n" +
                "City: " +
                city +
                "\n";
    }

}
