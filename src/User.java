public class User {
  private String firstName;
  private String lastName;
  private String phoneNumber;

  public User(String firstName, String lastName, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  @Override
  public String toString() {
    return "Имя: " + firstName + ", Фамилия: " + lastName + ", Номер: " + phoneNumber;
  }
}
