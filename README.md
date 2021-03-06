[![Build Status](https://travis-ci.com/mtumilowicz/groovy-memoization.svg?branch=master)](https://travis-ci.com/mtumilowicz/groovy-memoization)

# groovy-memoization
Overview of groovy memoization mechanism.

# preface
In computing, memoization is an 
optimization technique used primarily to speed up 
computer programs by storing the results of expensive 
function calls and returning the cached result when 
the same inputs occur again.

# groovy memoization
We could memoize a closure and a method:
* closure - invoke `memoize()` on the closure
    ```
    def closure = {
        ...
    }.memoize()    
    ```
    other `memoize` methods:
    * `memoizeAtMost` - will generate a new closure which 
    caches at most n values
    * `memoizeAtLeast` - will generate a new closure which 
    caches at least n values
    * `memoizeBetween` - will generate a new closure which 
    caches at least n values and at most n values
* method - annotate with `@Memoized`
    ```
    @Memoized
    static int method(...) {
        ...
    }    
    ```
    parameters:
    * `int protectedCacheSize() default 0;` - Number of cached 
    return values to protect from garbage collection.
    * `int maxCacheSize() default 0;` - The maximum size the cache 
    can grow to.
    
**We should use memoization only for pure functions.**

Good example of where memoization could be applied is fibonacci 
sequence:
```
f(n) = f(n-1) + f(n-2)
f(n-1) = f(n-2) + f(n-3)
```
so `f(n-2)` should be memoized

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
    @Memoized
    static int extractIdMemo(Person person) {
        person.counter++
        return person.id
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