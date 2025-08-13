package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods.
 * I have already provided an implementation for the first method.
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl() {
        personList = PersonGenerator.getInstance().generate(1000);
    }


    static DataStorage getInstance() {
        return INSTANCE; // Returning the singleton instance
    }

    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        List<Person> result = new ArrayList<>(); // Creating a list to store filtered Person objects
        for (Person person : personList) { // Iterating over each Person object in personList
            if (filter.test(person)) { // Testing if the Person object satisfies the filter predicate
                result.add(person); // Adding the Person object to the result list if it satisfies the predicate
            }
        }
        return result; // Returning the list of filtered Person objects
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        for (Person person : personList) { // Iterating over each Person object in personList
            if (filter.test(person)) { // Testing if the Person object satisfies the filter predicate
                return person; // Returning the first Person object that satisfies the predicate
            }
        }
        return null; // Returning null if no Person object satisfies the predicate
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString) {
        for (Person person : personList) { // Iterating over each Person object in personList
            if (filter.test(person)) { // Testing if the Person object satisfies the filter predicate
                return personToString.apply(person); // Returning the mapped String for the first matching Person
            }
        }
        return null; // Returning null if no Person object satisfies the predicate
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {
        List<String> result = new ArrayList<>(); // Creating a list to store mapped Strings
        for (Person person : personList) { // Iterating over each Person object in personList
            if (filter.test(person)) { // Testing if the Person object satisfies the filter predicate
                result.add(personToString.apply(person)); // Adding the mapped String to the result list
            }
        }
        return result; // Returning the list of mapped Strings for matching Person objects
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer) {
        for (Person person : personList) { // Iterating over each Person object in personList
            if (filter.test(person)) {  // Testing if the Person object satisfies the filter predicate
                consumer.accept(person); // Performing the specified action on the matching Person object
            }
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator) {
        List<Person> result = new ArrayList<>(personList); // Creating a copy of personList
        result.sort(comparator); // Sorting the copied list using the provided Comparator
        return result; // Returning the sorted list of Person objects
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator) {
        List<Person> result = findMany(filter); // Creating a filtered list of Person objects using findMany
        result.sort(comparator); // Sorting the filtered list using the provided Comparator
        return result; // Returning the sorted list of matching Person objects
    }
}
