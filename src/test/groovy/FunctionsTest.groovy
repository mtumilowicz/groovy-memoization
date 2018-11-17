import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-17.
 */
class FunctionsTest extends Specification {

    def "extractId"() {
        given:
        def person1 = new Person(id: 1, counter: 0)
        def person1Copy = new Person(id: 1, counter: 0)
        def person2 = new Person(id: 2, counter: 0)
        def person3 = new Person(id: 3, counter: 0)

        when:
        Functions.extractId(person1)
        Functions.extractId(person1)
        Functions.extractId(person1Copy)
        Functions.extractId(person1)

        and:
        Functions.extractId(person2)
        Functions.extractId(person2)

        then:
        person1.counter == 3
        person1Copy.counter == 1
        person2.counter == 2
        person3.counter == 0
    }
    
    def "extractIdMemo"() {
        given:
        def person1 = new Person(id: 1, counter: 0)
        def person1Copy = new Person(id: 1, counter: 0)
        def person2 = new Person(id: 2, counter: 0)
        def person3 = new Person(id: 3, counter: 0)
        
        when:
        Functions.extractIdMemo(person1)
        Functions.extractIdMemo(person1)
        Functions.extractIdMemo(person1Copy)
        Functions.extractIdMemo(person1)
        
        and:
        Functions.extractIdMemo(person2)
        Functions.extractIdMemo(person2)
        
        then:
        person1.counter == 1
        person1Copy.counter == 0
        person2.counter == 1
        person3.counter == 0
    }
}
