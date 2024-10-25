package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;

public class AddListingCommandTest {

    @Test
    public void constructor_nulllistingName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(null, PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))));
    }

    @Test
    public void constructor_nullPrice_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), null,
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))));
    }

    @Test
    public void constructor_nullArea_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                null, PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))));
    }

    @Test
    public void constructor_nullAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), null, PASIR_RIS.getRegion(), ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))));
    }

    @Test
    public void constructor_nullRegion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), null, ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))));
    }

    @Test
    public void constructor_nullSeller_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), null,
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))));
    }

    @Test
    public void constructor_nullBuyers_success() throws CommandException {
        ModelStubAcceptingListingAdded modelStub = new ModelStubAcceptingListingAdded();
        CommandResult commandResult = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), ALICE.getName(),
                null).execute(modelStub);

        assertEquals(String.format(AddListingCommand.MESSAGE_SUCCESS, Messages.format(PASIR_RIS)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(PASIR_RIS), modelStub.listingsAdded);
    }

    @Test
    public void execute_listingAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingListingAdded modelStub = new ModelStubAcceptingListingAdded();

        CommandResult commandResult = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName()))).execute(modelStub);

        assertEquals(String.format(AddListingCommand.MESSAGE_SUCCESS, Messages.format(PASIR_RIS)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(PASIR_RIS), modelStub.listingsAdded);
    }

    @Test
    public void execute_duplicateListing_throwsCommandException() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), ALICE.getName(),
                new HashSet<>(List.of(DANIEL.getName(), GEORGE.getName())));
        ModelStub modelStub = new ModelStubWithListing(PASIR_RIS);

        assertThrows(CommandException.class,
                AddListingCommand.MESSAGE_DUPLICATE_LISTING, () -> addListingCommand.execute(modelStub));
    }

    // equals and toString methods

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getListingsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListingsFilePath(Path listingsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListings(ReadOnlyListings listings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyListings getListings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Person getPersonByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListing(Listing target, Listing editedListing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredListingList(Predicate<Listing> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single listing.
     */
    private class ModelStubWithListing extends AddListingCommandTest.ModelStub {
        private final Listing listing;

        ModelStubWithListing(Listing listing) {
            requireNonNull(listing);
            this.listing = listing;
        }

        @Override
        public boolean hasListing(Listing listing) {
            requireNonNull(listing);
            return this.listing.isSameListing(listing);
        }

    }

    /**
     * A Model stub that always accept the listing being added.
     */
    private class ModelStubAcceptingListingAdded extends AddListingCommandTest.ModelStub {
        final ArrayList<Listing> listingsAdded = new ArrayList<>();
        final ArrayList<Person> persons = new ArrayList<>();

        @Override
        public boolean hasListing(Listing listing) {
            requireNonNull(listing);
            return listingsAdded.stream().anyMatch(listing::isSameListing);
        }

        @Override
        public void addListing(Listing listing) {
            requireNonNull(listing);
            listingsAdded.add(listing);
        }

        @Override
        public ReadOnlyListings getListings() {
            return new Listings();
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            return javafx.collections.FXCollections.observableList(listingsAdded);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(persons);
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            return this.getFilteredPersonList()
                    .stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }
}
