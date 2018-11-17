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
}
