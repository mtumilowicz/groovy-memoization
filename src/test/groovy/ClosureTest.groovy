import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-17.
 */
class ClosureTest  extends Specification {

    def "closure without memoization"() {
        given:
        def counter = 0

        def closure = {
            counter++
        }

        when:
        closure()
        closure()
        closure()
        closure()

        then:
        counter == 4
    }
    
    def "closure memoization"() {
        given:
        def counter = 0
        
        def closure = {
            counter++
        }.memoize()
        
        when:
        closure()
        closure()
        closure()
        closure()
        
        then:
        counter == 1
    }

    def "String closure memoization"() {
        given:
        def counter = 0

        def closure = {
            counter++
            it
        }.memoize()

        when:
        closure("a")
        closure("a")
        closure("a")
        closure("b")

        then:
        counter == 2
    }

    def "closure from method memoization"() {
        given:
        def memoizedExtractId = Functions.&extractId.memoize()
        
        def person1 = new Person(id: 1, counter: 0)
        def person1Copy = new Person(id: 1, counter: 0)
        def person2 = new Person(id: 2, counter: 0)
        def person3 = new Person(id: 3, counter: 0)

        when:
        memoizedExtractId(person1)
        memoizedExtractId(person1)
        memoizedExtractId(person1Copy)
        memoizedExtractId(person1)

        and:
        memoizedExtractId(person2)
        memoizedExtractId(person2)

        then:
        person1.counter == 1
        person1Copy.counter == 0
        person2.counter == 1
        person3.counter == 0
    }
}
