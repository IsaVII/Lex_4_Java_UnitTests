![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Arrays, Collections & Unit Testing

---

## Table of Contents

1. [Introduction to Arrays](#1-introduction-to-arrays)
2. [Introduction to Unit Testing (JUnit 5)](#2-introduction-to-unit-testing)
3. [The Java Collections Framework](#3-the-java-collections-framework-jcf)

### 1. Introduction to Arrays

### What is an Array?

- An array is a **fixed-size** data structure that stores multiple elements of the same data type.
- Elements are stored in **contiguous memory locations**, which means they are placed right next to each other in the
  computer's memory.
- Once created, the size of an array **cannot be changed**.
- Arrays use **zero-based indexing**, meaning the first position is `0`.
- The first element is at index `0`.
- The last element is at index `length - 1`.

To understand how an array works, imagine a row of boxes where each box has a number (the **index**) and contains a
value.

Example: `int[] numbers = {10, 20, 30, 40, 50};`

| Index     |   0    |   1    |   2    |   3    |   4    |
|:----------|:------:|:------:|:------:|:------:|:------:|
| **Value** | **10** | **20** | **30** | **40** | **50** |

- **Index 0**: The first element (10).
- **Index 4**: The last element (50), which is `length - 1`.
- **Length**: The total number of elements in the array (5).

---

### Declaration and Initialization

```java

import java.util.Arrays;

public class ArrayDemo {
    void main() {

        // 1. Declaration
        int[] numbers;  // Declares an array of integers
        String[] names; // Declares an array of Strings

        // 2. Initialization with Size
        // An array is a **fixed-size** data structure and cannot be resized once initialized.
        numbers = new int[3]; // Creates an array of size 3 with default values [0, 0, 0]

        //   Visual representation of 'numbers' array:
        //   | Index | 0 | 1 | 2 |
        //   |-------|---|---|---|
        //   | Value | 0 | 0 | 0 |

        // 3. Combined Declaration and Initialization
        String[] stringArray = new String[3]; // [null, null, null]

        // 4. Initializing with Specific Values
        int[] numbersArr = {2, 4, 6, 8}; // Shortened syntax (initializes and assigns values)

        // 5. Accessing and Modifying Elements
        String[] students = {"Fredrik", "Erik", "Jonas"};

        //    Visual representation of 'students' array:
        //    | Index |     0     |    1   |    2    |
        //    |-------|-----------|--------|---------|
        //    | Value | "Fredrik" | "Erik" | "Jonas" |

        IO.println(students[0]); // Output: Fredrik
        IO.println(students[2]); // Output: Jonas

        // [WARNING]
        // Accessing an index outside the range 0 to length - 1 throws an ArrayIndexOutOfBoundsException.
        // IO.println(students[3]); // This would throw an exception

        // Two-Dimensional Array (2D Array)
        // A 2D array can be thought of as an array of arrays, or a table with rows and columns.
        int[][] matrix = {
                {1, 2, 3}, // Row 0
                {4, 5, 6}, // Row 1
                {7, 8, 9}  // Row 2
        };

        /*
           Visual representation of 'matrix' (2D array):
           Column:   0   1   2
           Row 0:  | 1 | 2 | 3 |
           Row 1:  | 4 | 5 | 6 |
           Row 2:  | 7 | 8 | 9 |
        */

        // Accessing elements in a 2D array:
        // Use matrix[rowIndex][columnIndex]
        int element = matrix[1][2]; // Accesses Row 1, Column 2 -> 6
        IO.println("Element at [1][2]: " + element);

        // Modifying elements:
        matrix[0][0] = 10; // Changes Row 0, Column 0 from 1 to 10


    }

    public static void arrayIterationExample() {
        int[] numbers = {5, 2, 3, 4, 7};

        IO.println("Iterating using index-based for loop:");
        for (int i = 0; i < numbers.length; i++) {
            if (i == 2) {
                numbers[i] = 200; // Modifying the element at index 2
            }
        }
        // Arrays is a utility class in Java that provides methods for working with arrays.
        // .toString() converts the array into a readable String format: [5, 2, 200, 4, 7]
        IO.println(Arrays.toString(numbers));

        IO.println("Iterating using enhanced for loop:");
        for (int number : numbers) {
            // Modifying the loop variable does NOT change the actual array element.
            if (number == 5) {
                number = 5000; // Only changes the local 'number' variable (a copy)
            }
        }
        IO.println(Arrays.toString(numbers));
    }
}
```

|                     | Basic `for` loop                                                               | Enhanced `for` loop (for-each)                                 |
|:--------------------|:-------------------------------------------------------------------------------|:---------------------------------------------------------------|
| **Access to Index** | Yes (`i`)                                                                      | No                                                             |
| **Modification**    | Can modify elements directly in the array                                      | Cannot modify elements (works with a copy)                     |
| **Direction**       | Forward, backward, or custom steps                                             | Forward only                                                   |
| **Complexity**      | More verbose, requires manual index management                                 | Concise and easy to read                                       |
| **Use Case**        | Use when you need the index, want to modify the array, or need complex control | Use for simple reading of elements where index is not required |

---

```java

import java.util.Arrays;

public class ArrayDemo {

    void main() {
        // ... (existing code for basic array concepts)
    }

    /**
     * Manual array expansion.
     * Since arrays have a fixed size, we must:
     * 1. Create a new, larger array.
     * 2. Copy elements from the old array to the new one.
     * 3. Assign the new element.
     */
    public static void expandingArray() {
        int[] originalArray = {2, 5, 8, 1, 9};
        int newElement = 40;

        // Step 1: Create a new array with size + 1
        int[] expandedArray = new int[originalArray.length + 1];
        /*
           Visual representation of 'expandedArray' (initially):
           | Index | 0 | 1 | 2 | 3 | 4 | 5 |
           |-------|---|---|---|---|---|---|
           | Value | 0 | 0 | 0 | 0 | 0 | 0 |
        */

        // Step 2: Copy elements manually using a for-loop
        for (int i = 0; i < originalArray.length; i++) {
            expandedArray[i] = originalArray[i];
        }

        /*
           Visual representation of 'expandedArray' (after Step 2):
           | Index | 0 | 1 | 2 | 3 | 4 | 5 |
           |-------|---|---|---|---|---|---|
           | Value | 2 | 5 | 8 | 1 | 9 | 0 |
        */

        // Step 3: Add the new element to the last position
        expandedArray[expandedArray.length - 1] = newElement;

        /*
           Visual representation of 'expandedArray' (after Step 3):
           | Index | 0 | 1 | 2 | 3 | 4 |  5  |
           |-------|---|---|---|---|---|-----|
           | Value | 2 | 5 | 8 | 1 | 9 | 40  |
        */

        IO.println("Expanded: " + Arrays.toString(expandedArray));
    }

    /**
     * Demonstrates array expansion using the built-in Arrays.copyOf() method.
     * This utility handles the creation of the new array and copying of elements in one step.
     */
    public static void expandingArrayUsingCopyOf() {
        int[] originalArray = {2, 5, 8, 1, 9};
        int newElement = 40;

        // Arrays.copyOf() creates a new array and copies existing elements
        int[] expandedArray = Arrays.copyOf(originalArray, originalArray.length + 1);

        /*
           Visual representation of 'expandedArray' (after Arrays.copyOf):
           | Index | 0 | 1 | 2 | 3 | 4 | 5 |
           |-------|---|---|---|---|---|---|
           | Value | 2 | 5 | 8 | 1 | 9 | 0 |
        */

        // Add the new element to the last position
        expandedArray[expandedArray.length - 1] = newElement;

        /*
           Visual representation of 'expandedArray' (final state):
           | Index | 0 | 1 | 2 | 3 | 4 |  5  |
           |-------|---|---|---|---|---|-----|
           | Value | 2 | 5 | 8 | 1 | 9 | 40  |
        */

        IO.println("Expanded (Arrays.copyOf): " + Arrays.toString(expandedArray));
    }
}
```

---

### Practice: Find Maximum Number

**Question:** Write a pseudocode first, then write a Java program that finds the maximum number in a given array of
integers using a **linear search (simple iteration)** approach.

**Initial Array:**
`int[] numbers = {12, 5, 45, 18, 33, 24};`

| Index | 0  | 1 | 2  | 3  | 4  | 5  |
|-------|----|---|----|----|----|----|
| Value | 12 | 5 | 45 | 18 | 33 | 24 |

---

### Arrays Utility Class

The `java.util.Arrays` class is a utility class that provides several static methods for manipulating arrays (such as
sorting, searching, comparing, and filling elements).

| Method                                | Description                                                                                                                   |
|:--------------------------------------|:------------------------------------------------------------------------------------------------------------------------------|
| `Arrays.toString(array)`              | Returns a string representation of the array's contents.                                                                      |
| `Arrays.sort(array)`                  | Sorts the specified array into ascending numerical/alphabetical order.                                                        |
| `Arrays.binarySearch(array, key)`     | Searches the specified array for the specified value using the binary search algorithm. (The array **must** be sorted first). |
| `Arrays.equals(array1, array2)`       | Returns `true` if the two specified arrays of the same type are equal to one another.                                         |
| `Arrays.fill(array, value)`           | Assigns the specified value to each element of the specified array.                                                           |
| `Arrays.copyOf(array, newLength)`     | Copies the specified array, truncating or padding with default values (if necessary) so the copy has the specified length.    |
| `Arrays.copyOfRange(array, from, to)` | Copies the specified range of the specified array into a new array.                                                           |
| `Arrays.asList(T... a)`               | Returns a fixed-size list backed by the specified array.                                                                      |
| `Arrays.hashCode(array)`              | Returns a hash code based on the contents of the specified array.                                                             |
| `Arrays.stream(array)`                | Returns a sequential Stream with the specified array as its source.                                                           |

For more information, refer to the [official Java documentation on the Arrays class](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html).
---

## 2. Introduction to Unit Testing

Unit testing is a software testing method where individual units or components of a software are tested. The purpose is
to validate that each unit of the software code performs as expected.

- **What is a "Unit"?** A unit is the smallest testable part of any software. In modular programming, this is
  typically a single method or a function. In some cases, it could also be a whole class.
- **Isolation:** Each unit is tested in isolation from the rest of the application. This ensures that a failure in one
  unit is not caused by an issue in another. If the unit depends on external components (like a database or a web
  service), these are often replaced by "mocks" or "stubs" to maintain isolation.
- **Inputs and Outputs:** Testing a unit usually involves providing specific inputs and verifying that the resulting
  output matches the expected result.

### Why Use Unit Testing?

- **Early Bug Detection:** Catch bugs at an early stage of development.
- **Code Quality:** Encourages writing modular and maintainable code.
- **Documentation:** Tests serve as documentation for how a piece of code is supposed to behave.
- **Safe Refactoring:** You can change code with confidence, knowing that existing tests will catch any regressions.

### Thinking in Use Cases

Before writing any test code, it is essential to think about the different scenarios (use cases) that your method or
class might encounter. A good testing strategy involves covering three main categories:

1. **Happy Path (Expected Behavior):**
    - This is the "normal" scenario where everything goes as expected.
    - Example: If you have a method `add(int a, int b)`, the happy path is testing it with positive integers like
      `add(2, 3)` and expecting `5`.

2. **Negative Cases (Invalid Inputs/Errors):**
    - These scenarios test how your code handles invalid or unexpected data.
    - Example: Testing a `divide(int a, int b)` method with `b = 0`. The code should handle this gracefully, perhaps by
      throwing an `ArithmeticException`.

3. **Edge Cases (Boundaries):**
    - These are scenarios at the extreme ends of the input range.
    - Example: If a method accepts an array of size 1 to 100, edge cases would be an array with exactly 1 element and an
      array with exactly 100 elements. Testing with an empty array (0 elements) is also an important boundary.

4. **Corner Cases (Complex Scenarios):**
    - These occur when multiple parameters or conditions are at their limits simultaneously.

By thinking in use cases, you ensure that your tests are comprehensive and that your code is robust enough to handle
real-world situations.

---

### Implementing JUnit 5 in Java

To use JUnit 5 in your Java application, you need to add the following dependencies to your project.

#### For Maven (`pom.xml`):

```xml

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### For Gradle (`build.gradle`):

```gradle
test {
    useJUnitPlatform()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.0'
}
```

---

### JUnit 5 Annotations

Annotations are used to provide metadata to the test framework.

| Annotation             | Description                                                                                                               |
|:-----------------------|:--------------------------------------------------------------------------------------------------------------------------|
| `@Test`                | Marks a method as a test method.                                                                                          |
| `@BeforeEach`          | Runs before each test method in the class.                                                                                |
| `@AfterEach`           | Runs after each test method in the class.                                                                                 |
| `@BeforeAll`           | Runs once before all test methods (must be static).                                                                       |
| `@AfterAll`            | Runs once after all test methods (must be static).                                                                        |
| `@TestMethodOrder(..)` | Defines the order in which the test methods in this class will be executed (e.g., `MethodOrderer.OrderAnnotation.class`). |
| `@Order(n)`            | Defines the execution order for a test method (used with `OrderAnnotation`).                                              |
| `@DisplayName("..")`   | Declares a custom display name for the test class or method.                                                              |
| `@Disabled`            | Disables a test class or method.                                                                                          |

### Common Assertions

Assertions are methods used to verify that the actual result of a test matches the expected result.

| Method                                | Description                                            |
|:--------------------------------------|:-------------------------------------------------------|
| `assertEquals(expected, actual)`      | Asserts that two values are equal.                     |
| `assertNotEquals(expected, actual)`   | Asserts that two values are not equal.                 |
| `assertTrue(condition)`               | Asserts that a condition is true.                      |
| `assertFalse(condition)`              | Asserts that a condition is false.                     |
| `assertNull(object)`                  | Asserts that an object is null.                        |
| `assertNotNull(object)`               | Asserts that an object is not null.                    |
| `assertThrows(Exception.class, exec)` | Asserts that an execution throws a specific exception. |

---

### Example:

```java

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayDemoTest {

    @Test
    @DisplayName("Find max in a normal positive array (Happy Path)")
    void findMaxNumberWithPositiveNumbers() {
        // Arrange
        int[] numbers = {12, 5, 45, 18, 33, 24};
        int expected = 45;

        // Act
        int actual = ArrayDemo.findMaxNumber(numbers);

        // Assert (Verify)
        assertEquals(expected, actual, "The maximum should be 45");
    }

    @Test
    @DisplayName("Find max in an array with negative numbers")
    void findMaxNumberWithNegativeNumbers() {
        // Arrange
        int[] numbers = {-12, -5, -45, -18, -33, -24};
        int expected = -5;

        // Act
        int actual = ArrayDemo.findMaxNumber(numbers);

        // Assert (Verify)
        assertEquals(expected, actual, "The maximum should be -5");
    }

    @Test
    @DisplayName("Test findMaxNumber with null array (Negative Case)")
    void findMaxNumberWithNullArray() {
        // Arrange
        int[] numbers = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ArrayDemo.findMaxNumber(numbers), "Should throw IllegalArgumentException for null array");
    }

}
```

---

## 3. The Java Collections Framework (JCF)

The **Java Collections Framework (JCF)** is a set of classes and interfaces that implement commonly reusable collection
data structures. It provides a standard architecture for representing and manipulating collections of objects.

### Why Use Collections Instead of Arrays?

| Feature         | Arrays                                   | Collections (e.g., `ArrayList`)                |
|:----------------|:-----------------------------------------|:-----------------------------------------------|
| **Size**        | Fixed size (cannot change once created). | Dynamic size (grows and shrinks as needed).    |
| **Type**        | Can store both primitives and objects.   | Can store **only objects**.                    |
| **Convenience** | Limited built-in methods.                | Rich set of methods for sorting, searching.    |
| **Memory**      | More memory-efficient for simple data.   | Slightly more overhead due to object wrappers. |

---

### Wrapper Classes

Since collections work only with **objects** and not with primitives (`int`, `double`, `boolean`, etc.), Java provides *
*Wrapper Classes**.

A Wrapper class "wraps" a primitive value into an object.

| Primitive Type | Wrapper Class |
|:---------------|:--------------|
| `byte`         | `Byte`        |
| `short`        | `Short`       |
| `int`          | `Integer`     |
| `long`         | `Long`        |
| `float`        | `Float`       |
| `double`       | `Double`      |
| `char`         | `Character`   |
| `boolean`      | `Boolean`     |

#### Autoboxing and Unboxing

- **Autoboxing:** Automatically converting a primitive to its wrapper object (e.g., `int` to `Integer`).
- **Unboxing:** Automatically converting a wrapper object back to a primitive (e.g., `Integer` to `int`).

```java
Integer numberObject = 10; // Autoboxing
int numberPrimitive = numberObject; // Unboxing
```

---

### ArrayList

The `ArrayList` is the most commonly used implementation of the `List` interface. It uses a **dynamic array** to store
elements.

#### Key Features:

- **Dynamic Sizing:** It can grow and shrink automatically.
- **Ordered:** It maintains the **insertion order**.
- **Duplicates Allowed:** It allows **duplicate elements**.
- **Null Values:** It allows `null` values.
- **Not Thread-Safe:** It doesn't handle multiple "threads" (tasks) working on it at the exact same time. If two parts
  of your program try to change the list simultaneously, it might crash or lose data. To make it safe for multi-tasking,
  you can use `Collections.synchronizedList(new ArrayList<>())`.

```java
import java.util.ArrayList;
import java.util.Collections;

public class CollectionsDemo {

    void main() {
        // 1. Declaration and Initialization
        // Syntax: ArrayList<Type> listName = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        // 2. Adding Elements
        names.add("Erik");
        names.add("Fredrik");
        names.add("Jonas");
        names.add("Erik"); // Duplicates are allowed

        // 3. Accessing Elements (using index)
        IO.println("First name: " + names.get(0)); // Output: Erik

        // 4. Modifying Elements
        names.set(2, "Marcus"); // Replaces "Jonas" with "Marcus"

        // 5. Removing Elements
        names.remove("Erik"); // Removes the first occurrence of "Erik"
        names.remove(1);      // Removes element at index 1

        // 6. Size of the ArrayList
        IO.println("Size: " + names.size());

        // 7. Checking if an element exists
        boolean containsFredrik = names.contains("Fredrik");

        // 8. Sorting
        Collections.sort(names);

        // 9. Iterating
        for (String name : names) {
            IO.println(name);
        }
    }
}
```

---

### Common Collection Classes

1. **`HashSet`**: Stores elements in a hash table. It is the best-performing implementation but makes no guarantees
   about the iteration order.
2. **`TreeSet`**: Stores elements in a red-black tree. It orders its elements based on their natural values or a custom
   comparator.
3. **`HashMap`**: Stores key-value pairs. Keys must be unique. Fast performance for basic operations (`get` and `put`).

---

### Collections Utility Class

The `java.util.Collections` class consists exclusively of static methods that operate on or return collections. It is
the "Arrays" equivalent for collections.

| Method                                | Description                                                                     |
|:--------------------------------------|:--------------------------------------------------------------------------------|
| `Collections.sort(list)`              | Sorts the elements in the specified list into ascending order.                  |
| `Collections.reverse(list)`           | Reverses the order of the elements in the specified list.                       |
| `Collections.shuffle(list)`           | Randomly permutes the list (useful for games or randomized testing).            |
| `Collections.fill(list, obj)`         | Replaces all elements in the list with the specified object.                    |
| `Collections.min(collection)`         | Returns the minimum element of the given collection.                            |
| `Collections.max(collection)`         | Returns the maximum element of the given collection.                            |

---

### Objects Utility Class

The `java.util.Objects` class consists of `static` utility methods for operating on objects. These methods include
`null`-safe or `null`-tolerant methods for computing the hash code of an object, returning a string for an object, and
comparing two objects.

| Method                           | Description                                                                                |
|:---------------------------------|:-------------------------------------------------------------------------------------------|
| `Objects.equals(a, b)`           | Returns `true` if the arguments are equal to each other and `false` otherwise (null-safe). |
| `Objects.hashCode(obj)`          | Returns the hash code of a non-null argument and 0 for a null argument.                    |
| `Objects.toString(obj, nullMsg)` | Returns the result of calling `toString` on the object, or a default message if null.      |
| `Objects.requireNonNull(obj)`    | Checks that the specified object reference is not null (throws `NullPointerException`).    |
| `Objects.isNull(obj)`            | Returns `true` if the provided reference is `null`.                                        |
| `Objects.nonNull(obj)`           | Returns `true` if the provided reference is non-null.                                      |

---


```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CollectionsDemo {

    void main() {
        IO.println("--- Collections Utility Examples ---");
        collectionsExamples();

        IO.println("\n--- Objects Utility Examples ---");
        objectsExamples();
    }

    public static void collectionsExamples() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));

        // 1. Reversing
        Collections.reverse(numbers);
        IO.println("Reversed: " + numbers);

        // 2. Shuffling
        Collections.shuffle(numbers);
        IO.println("Shuffled: " + numbers);

        // 3. Finding Min and Max
        IO.println("Min: " + Collections.min(numbers));
        IO.println("Max: " + Collections.max(numbers));

        // 4. Sorting
        Collections.sort(numbers);
        IO.println("Sorted: " + numbers);

        // 5. Binary Search (Requires sorted list)
        int index = Collections.binarySearch(numbers, 30);
        IO.println("Index of 30: " + index);
    }

    public static void objectsExamples() {
        String name1 = "Erik";
        String name2 = null;

        // 1. Null-safe equality check
        IO.println("Equals (Erik, null): " + Objects.equals(name1, name2));

        // 2. Null-safe toString
        IO.println("ToString: " + Objects.toString(name2, "Default Name"));

        // 3. Check for null
        IO.println("IsNull: " + Objects.isNull(name2));

        // 4. Require non-null (Throws exception if null)
        try {
            Objects.requireNonNull(name2, "Name cannot be null!");
        } catch (NullPointerException e) {
            IO.println("Exception: " + e.getMessage());
        }
    }
}
```

---

### Overriding `equals()` and `hashCode()`

When you create your own classes (like `Person`, `Car`, or `Account`) and store them in a collection, Java needs to
know how to compare them. By default, Java uses the `==` operator, which only checks if two objects are the exact same
instance in memory.

To compare objects based on their **data** (e.g., if two people have the same ID), you must override the `equals()` and
`hashCode()` methods.

- **`equals(Object o)`**: Defines what makes two objects "equal" based on their fields.
- **`hashCode()`**: Returns an integer value (a "hash") that represents the object. Objects that are equal **must** have
  the same hash code.

Before using these methods in collections, let's look at how they work step-by-step.

```java
// A simple Person class (without custom equals/hashCode yet)
public class Person {
    int id;
    String name;
    String email;
}

public class EqualityDemo {
    
    public static void equalityExample() {
        // 1. Create two Person objects with identical data
        Person person1 = new Person();
        person1.id = 1;
        person1.name = "Mehrdad";
        person1.email = "mehrdad.javan@lexicon.se";

        Person person2 = new Person();
        person2.id = 1;
        person2.name = "Mehrdad";
        person2.email = "mehrdad.javan@lexicon.se";

        // 2. Compare using equals()
        // Every class in Java automatically inherits from the 'Object' class.
        // The default Object.equals() checks "Reference Equality" (memory address).
        boolean isEqualTwoPersons = person1.equals(person2);
        
        // Output will be FALSE. 
        // Why? Because person1 and person2 are two different objects at different memory locations.
        IO.println("isEqualTwoPersons (Default): " + isEqualTwoPersons);

        // 3. Compare with Strings
        String test1 = "TEST";
        String test2 = "TEST";

        boolean isEqualTwoStrings = test1.equals(test2);
        
        // Output will be TRUE. 
        // Why? The String class has ALREADY overridden the equals() method 
        // to compare the actual text (content) instead of the memory location.
        IO.println("isEqualTwoStrings: " + isEqualTwoStrings);
    }
}
```

#### Why override `hashCode()`?

The `hashCode()` method returns an integer (a hash) used by hash-based collections like `HashSet` and `HashMap` for fast
searching.

- **The Contract:** If two objects are equal according to `equals()`, they **must** have the same `hashCode()`.
- **Failing the rule:** If you override `equals()` but not `hashCode()`, your objects might not be found in a `HashSet`
  even if they are "equal."

```java
import java.util.Objects;

class Person {
    int id;
    String name;
    String email;

    @Override
    public boolean equals(Object o) {
        // 1. Check if comparing to the exact same instance
        if (this == o) return true;
        
        // 2. Check if the other object is null or a different class
        if (o == null || getClass() != o.getClass()) return false;
        
        // 3. Cast to Person and compare the fields
        Person person = (Person) o;
        return id == person.id && 
               Objects.equals(name, person.name) && 
               Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        // Generates a hash based on the same fields used in equals()
        return Objects.hash(id, name, email);
    }
}
```

If you don't override `equals()`, methods like `contains()` and `remove()` will not work as expected for custom objects.

```java
import java.util.ArrayList;

public class CollectionsDemo {
    
    void main() {
        ArrayList<Person> people = new ArrayList<>();
        
        Person p1 = new Person();
        p1.id = 1;
        p1.name = "Erik";
        
        people.add(p1);

        // Create a NEW object with the SAME data
        Person p2 = new Person();
        p2.id = 1;
        p2.name = "Erik";

        // Without overriding equals(), this would be FALSE
        boolean exists = people.contains(p2); 
        IO.println("Does Erik exist in the list? " + exists);
    }
}
```