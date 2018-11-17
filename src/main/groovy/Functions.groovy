import groovy.transform.EqualsAndHashCode
import groovy.transform.Memoized 
/**
 * Created by mtumilowicz on 2018-11-17.
 */
class Functions {

    static int extractId(Person person) {
        person.counter++
        return person.id
    }

    @Memoized
    static int extractIdMemo(Person person) {
        person.counter++
        return person.id
    }
}

@EqualsAndHashCode(includes = "id")
class Person {
    int id
    int counter
}
