package seedu.address.model.person;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

import java.util.Set;

/**
 * Represents a Buyer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Buyer extends Person {

    /**
     * Creates a Buyer with the specified details.
     *
     * @param name The name of the buyer.
     * @param phone The phone number of the buyer.
     * @param email The email address of the buyer.
     * @param tags The tags associated with the buyer.
     * @param appointment The appointment associated with the buyer.
     * @param property The property associated with the buyer.
     */
    public Buyer(Name name, Phone phone, Email email, Set<Tag> tags, Appointment appointment, Property property) {
        super(name, phone, email, tags, Role.BUYER, appointment, property); // Specify role as BUYER
    }

    @Override
    public String toString() {
        return super.toString(); // Include all properties from Person
    }
}