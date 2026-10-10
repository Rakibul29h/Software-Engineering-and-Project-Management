# Java Multithreading: Static vs Non-Static Variables

## 1. Introduction
This experiment compares static and non-static variables in Java using multiple threads. It demonstrates race conditions, lost updates, and thread safety using `AtomicLong`.

## 2. Analysis Questions

### 1. What is the difference between static and non-static variables?
A static variable belongs to the class and is shared by all instances. A non-static variable belongs to an individual object, so each instance has its own copy.

### 2. Why do all threads share the same static counter?
All threads access the same static variable because it belongs to the class, not to individual objects.

### 3. Why does each thread have its own non-static counter?
Each thread uses a separate object, so each object maintains its own instance counter.

### 4. Why is `join()` required?
`join()` ensures all worker threads finish before the final counter values are calculated.

### 5. Why can the unsynchronized static count be lower than expected?
Incrementing a regular `long` is not an atomic operation. Concurrent updates can overwrite each other, causing lost increments.

### 6. Does increasing the number of threads always increase the percentage difference?
No. More threads may increase contention, but the percentage difference depends on thread scheduling, hardware, and runtime conditions.

### 7. Why can repeated runs produce different results?
Thread scheduling and execution order can vary between runs, resulting in different numbers of lost updates.

### 8. What changes when `AtomicLong` is used?
`AtomicLong` provides atomic operations, preventing lost updates when threads increment the shared counter correctly.

### 9. How can all threads share one instance counter?
Create one counter object and pass the same object to every thread. Use synchronization or an atomic instance variable to prevent race conditions.

## 3. Observations

Expected count:

`Expected Count = Number of Threads × Increments per Thread`

Percentage difference:

`Percentage Difference = ((Expected Count - Actual Count) / Expected Count) × 100`

Record the actual results for different thread counts and compare them. The unsynchronized static counter may lose updates, while the thread-safe counter should match the expected count.

## 4. Conclusion

The experiment shows that static and non-static variables differ in ownership, not thread safety. Unsynchronized concurrent updates can cause race conditions and lost increments. Increasing the number of threads does not guarantee a continuously increasing percentage difference because results depend on runtime conditions.

Using `AtomicLong` ensures thread-safe counter updates, so the actual count should equal the expected count, giving a **0% percentage difference** when all increments complete successfully.
