# groovy-memoization
Overview of groovy memoization mechanism.

# preface
In computing, memoization is an 
optimization technique used primarily to speed up 
computer programs by storing the results of expensive 
function calls and returning the cached result when 
the same inputs occur again.

We could memoize closure and method:
* closure - invoking `memoize()` on closure
    ```
    def closure = {
        counter++
    }.memoize()    
    ```
    other `memoize` methods:
    * `memoizeAtMost` - will generate a new closure which 
    caches at most n values
    * `memoizeAtLeast` - will generate a new closure which 
    caches at least n values
    * `memoizeBetween` - will generate a new closure which 
    caches at least n values and at most n values
* method - annotating with `@Memoized`
    ```
    @Memoized
    static int extractIdMemo(Person person) {
        person.counter++
        return person.id
    }    
    ```
    parameters:
    * `int protectedCacheSize() default 0;` - Number of cached 
    return values to protect from garbage collection.
    * `int maxCacheSize() default 0;` - The maximum size the cache 
    can grow to.
    
**We should use memoization only for pure functions.**

# project description
We provide example for:
* closure memoization (`ClosureTest`)
    ```
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
    ```
* function memoization (`FunctionsTest`)
    ```
    @EqualsAndHashCode(includes = "id")
    class Person {
        int id
        int counter
    }
    ```
    ```
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
    ```