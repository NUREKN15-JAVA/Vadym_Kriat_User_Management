package ua.nure.kn156.kriat;

import java.util.Calendar;
import java.util.Date;

/**
 * The {@code User} class represents user data.
 *
 * @author Vadym Kriat
 */
public class User {
    /**
     * The id of user
     */
    private Long id;
    /**
     * The last name of user
     */
    private String lastName;
    /**
     * The first name of user
     */
    private String firstName;
    /**
     * The date of Birth of user
     */
    private Date date;

    public User() {

    }

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.date = user.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the {@code long} value at the
     * specified of this user for current time.
     *
     * @return the {@code long} value at the specified of this user for current time.
     */
    public long getAge() {
        Calendar calendar = Calendar.getInstance();
        long currYear = calendar.get(Calendar.YEAR);
        long currDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date);
        long yearOfBirth = calendar.get(Calendar.YEAR);
        long dayOfYearOfBirth = calendar.get(Calendar.DAY_OF_YEAR);
        long age = currYear - yearOfBirth;
        if (currDayOfYear < dayOfYearOfBirth) {
            return --age;
        }
        return age;
    }

    /**
     * Returns the {@code String} first and last name
     * separated by commas.
     *
     * @return the {@code long} value at the
     * specified of this user for current time.
     * @throws IllegalStateException if the {@code lastName}
     *                               or {@code firstName} equals null.
     */
    public String getFullName() {
        if (lastName == null || firstName == null) {
            throw new IllegalStateException("First name or last name equal null");
        }
        return new StringBuilder().append(lastName).append(", ").append(firstName).toString();
    }
}
