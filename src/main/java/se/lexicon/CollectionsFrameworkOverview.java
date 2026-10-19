package se.lexicon;

import java.util.*;

// Run this class (main()) to get a guided, printed walkthrough of Part 3:
// the Java Collections Framework. Each method below is one topic from the slides.
public class CollectionsFrameworkOverview {

    static void main() {
        arraysVsCollections();
        wrapperClassesAndAutoboxing();
        arrayListBasics();
        hashSetVsTreeSet();
        hashMapBasics();
        collectionsUtilityClass();
        objectsUtilityClass();
        equalsAndHashCode();
    }

    // 1. Why collections exist at all: arrays are fixed-size, collections grow/shrink.
    static void arraysVsCollections() {
        IO.println("=== 1. Arrays vs Collections ===");
        IO.println("Array: fixed size, can hold primitives (int, double...) AND objects.");
        IO.println("Collection (e.g. ArrayList): dynamic size, can only hold OBJECTS.");
        IO.println("Rule of thumb: don't know the size up front, or need it to grow? Use a collection.");
        IO.println("");
    }

    // 2. Collections can't store 'int', 'double', etc. - only objects. Wrapper classes
    // bridge that gap. Java converts automatically (autoboxing/unboxing) behind the scenes.
    static void wrapperClassesAndAutoboxing() {
        IO.println("=== 2. Wrapper Classes & Autoboxing ===");

        Integer boxed = 10;      // autoboxing: int -> Integer, done automatically
        int unboxed = boxed;     // unboxing: Integer -> int, done automatically

        IO.println("int 10 autoboxed into Integer: " + boxed);
        IO.println("Integer unboxed back into int: " + unboxed);
        IO.println("Every primitive has a wrapper: int->Integer, double->Double, boolean->Boolean, etc.");
        IO.println("");

        IO.println("--- Why List<Integer> works but List<int> does NOT compile ---");
        IO.println("Generics only accept REFERENCE types. At runtime a List is backed by Object[] -");
        IO.println("every slot stores a POINTER to an object on the heap. A primitive int is just raw");
        IO.println("bits (4 bytes), with no object identity - there is no pointer to put in that slot.");
        IO.println("Integer IS an object: it wraps an int inside a real object, so it CAN be pointed to.");

        List<Integer> okList = new ArrayList<>();
        okList.add(5); // compiler rewrites this to: okList.add(Integer.valueOf(5))  <- autoboxing
        int first = okList.get(0); // compiler rewrites this to: okList.get(0).intValue() <- unboxing
        IO.println("List<Integer> works: add(5) secretly calls Integer.valueOf(5); get(0) secretly calls .intValue()");
        IO.println("first = " + first);

        // The line below is what the compiler would reject - kept as text only, since
        // actually writing "List<int>" is a compile error, not a runtime one:
        // List<int> badList = new ArrayList<>();   // error: unexpected type, int is not a reference type
        IO.println("List<int> badList = new ArrayList<>();  // <-- this line literally cannot compile");
        IO.println("");
    }

    // 3. ArrayList: the default "growable array". Ordered, allows duplicates, allows null.
    static void arrayListBasics() {
        IO.println("=== 3. ArrayList ===");

        ArrayList<String> names = new ArrayList<>();
        names.add("Erik");
        names.add("Fredrik");
        names.add("Erik"); // duplicates allowed
        IO.println("After adding Erik, Fredrik, Erik: " + names);

        names.set(1, "Marcus"); // overwrite index 1
        IO.println("After names.set(1, \"Marcus\"): " + names);

        names.remove("Erik"); // removes only the FIRST matching "Erik"
        IO.println("After removing one \"Erik\": " + names);

        IO.println("Contains \"Marcus\"? " + names.contains("Marcus"));
        IO.println("Size: " + names.size());
        IO.println("Key traits: keeps insertion order, allows duplicates and null, not thread-safe.");
        IO.println("");
    }

    // 4. HashSet vs TreeSet: both forbid duplicates, differ in STRUCTURE, ORDER, and SPEED.
    static void hashSetVsTreeSet() {
        IO.println("=== 4. HashSet vs TreeSet ===");

        Set<String> hashSet = new HashSet<>(List.of("banana", "apple", "cherry"));
        Set<String> treeSet = new TreeSet<>(List.of("banana", "apple", "cherry"));

        IO.println("HashSet (fast, order NOT guaranteed): " + hashSet);
        IO.println("TreeSet (slower, always sorted):       " + treeSet);
        IO.println("Both reject duplicates - adding \"apple\" again changes nothing.");
        IO.println("");

        IO.println("--- HashSet internals ---");
        IO.println("Backed by a HashMap<E, Object> - your elements are literally used as HashMap keys.");
        IO.println("add/remove/contains jump straight to a bucket via hashCode() -> O(1) average time.");
        IO.println("Trade-off: no ordering at all, because bucket position depends on hash value, not");
        IO.println("insertion order or natural order. The iteration order you SEE can even change after");
        IO.println("the set resizes internally.");
        hashSet.add(null); // HashSet allows exactly one null element
        IO.println("HashSet allows one null: " + hashSet);

        IO.println("");
        IO.println("--- TreeSet internals ---");
        IO.println("Backed by a red-black tree (a self-balancing binary search tree).");
        IO.println("Every add/remove/contains walks down the tree comparing elements -> O(log n), slower");
        IO.println("than HashSet's O(1), but the trade gives you elements ALWAYS sorted during iteration.");
        IO.println("Sorted by natural order (Comparable) by default, or by a Comparator you provide:");

        TreeSet<String> byLength = new TreeSet<>(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()));
        byLength.addAll(List.of("banana", "apple", "kiwi", "fig"));
        IO.println("TreeSet sorted by String length: " + byLength);

        try {
            treeSet.add(null); // TreeSet cannot compare null to anything -> NullPointerException
        } catch (NullPointerException e) {
            IO.println("TreeSet.add(null) throws NullPointerException - it cannot compare null to anything.");
        }

        IO.println("");
        IO.println("Rule of thumb: need 'no duplicates, fast' -> HashSet. Need 'no duplicates, sorted' -> TreeSet.");
        IO.println("");
    }

    // 5. HashMap: key -> value storage, keys unique, fast lookup by key.
    static void hashMapBasics() {
        IO.println("=== 5. HashMap ===");

        Map<String, Integer> ages = new HashMap<>();
        ages.put("Erik", 30);
        ages.put("Fredrik", 25);
        ages.put("Erik", 31); // overwrites the previous value for "Erik", keys are unique

        IO.println("Map after two puts for \"Erik\" (second overwrites first): " + ages);
        IO.println("Get \"Fredrik\": " + ages.get("Fredrik"));
        IO.println("Get missing key \"Jonas\": " + ages.get("Jonas") + " (null, no exception)");
        IO.println("");

        IO.println("--- What actually happens inside put()/get() ---");
        IO.println("A HashMap is an array of 'buckets'. Each key decides its own bucket via hashCode().");

        for (Map.Entry<String, Integer> entry : ages.entrySet()) {
            String key = entry.getKey();
            int keyHash = key.hashCode();                     // step 1: raw hashCode() of the key
            int spread = keyHash ^ (keyHash >>> 16);            // step 2: HashMap's own bit-mixing step
            int capacity = 16;                                   // default HashMap capacity
            int bucketIndex = spread & (capacity - 1);           // step 3: index = spread % capacity (power of 2)

            // Map.Entry defines its OWN hashCode as: key.hashCode() ^ value.hashCode()
            int entryHash = entry.hashCode();

            IO.println("key=\"" + key + "\" hashCode()=" + keyHash
                    + " -> spread=" + spread
                    + " -> bucket[" + bucketIndex + "] (of " + capacity + ")"
                    + " | entry.hashCode()=" + entryHash
                    + " (== key.hashCode() ^ value.hashCode() = " + (keyHash ^ entry.getValue().hashCode()) + ")");
        }

        IO.println("Lookup speed depends on hashCode() spreading keys evenly across buckets.");
        IO.println("A bad hashCode() (e.g. always returning 1) forces every key into ONE bucket,");
        IO.println("turning 'O(1) HashMap' into an O(n) linked list in practice.");
        IO.println("");
    }

    // 6. Collections (the utility class, plural) - static helper methods for any List/Set/Map.
    static void collectionsUtilityClass() {
        IO.println("=== 6. Collections utility class ===");

        List<Integer> numbers = new ArrayList<>(List.of(30, 10, 50, 20, 40));
        IO.println("Start:    " + numbers);

        Collections.sort(numbers);
        IO.println("Sorted:   " + numbers);

        Collections.reverse(numbers);
        IO.println("Reversed: " + numbers);

        IO.println("Min: " + Collections.min(numbers) + ", Max: " + Collections.max(numbers));
        IO.println("");
    }

    // 7. Objects (the utility class, singular) - null-safe helpers for your own objects.
    static void objectsUtilityClass() {
        IO.println("=== 7. Objects utility class ===");

        String a = "Erik";
        String b = null;

        IO.println("Objects.equals(\"Erik\", null): " + Objects.equals(a, b) + " (no NullPointerException!)");
        IO.println("Objects.toString(null, \"default\"): " + Objects.toString(b, "default"));
        IO.println("Objects.isNull(b): " + Objects.isNull(b));
        IO.println("");
    }

    // 8. equals()/hashCode(): why you must override both together for your own classes.
    static void equalsAndHashCode() {
        IO.println("=== 8. equals() and hashCode() ===");

        record Person(int id, String name) {}

        Person p1 = new Person(1, "Erik");
        Person p2 = new Person(1, "Erik"); // same data, different object in memory

        IO.println("p1 == p2 (same memory address)? " + (p1 == p2));
        IO.println("p1.equals(p2) (same data, record auto-generates equals/hashCode)? " + p1.equals(p2));

        Set<Person> people = new HashSet<>();
        people.add(p1);
        IO.println("HashSet already has an equal Person? " + people.contains(p2));
        IO.println("Lesson: without a correct equals()+hashCode() pair, contains()/remove() silently fail");
        IO.println("for custom classes, because Java falls back to comparing memory addresses.");
    }
}
