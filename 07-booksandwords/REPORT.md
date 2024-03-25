<h5>1. What is your hash function like for hash table solution (if you implemented hash table).</h5>

```java
private int customHashCode(K key) {
    int hash = 0;
    String keyString = key.toString();
    for (int i = 0; i < keyString.length(); i++) {
        hash = 31 * hash + keyString.charAt(i);
    }
    return hash;
}
```
<h5>2. For binary search trees (if you implemented it), how does your implementation get the top-100 list?</h5>
2.1 Convert BST to Sorted Array: I use `toSortedArray()` method of the `KeyValueBSearchTree` class to convert the BST into a sorted array: 

`Pair<String, Integer>[] sortedWords = wordCounts.toSortedArray();`

2.2 Print Top-100 Words
```java
for (int i = 0; i < Math.min(sortedWords.length, 100); i++) {
            String word = String.format("%-20s", sortedWords[i].getKey()).replace(' ', '.');
            System.out.format("%4d. %s %6d%n", i + 1, word, sortedWords[i].getValue());
        }
```
<h5>3. What can you say about the correctness of your implementation? Any issues, bugs or problems you couldn't solve? Any idea why the problem persists and what could perhaps be the solution?</h5>
I encountered several problems while doing this exercise. The first one is that my HashTable and BST data structures are based on Exercise 4, but in Exercise 4 we compare Key, and in this exercise we need to compare Value, which resulted in my output in the first test. The top100 results are all words starting with the letter Z. The second is that the original quicksort algorithm took a lot of time to run. I checked some improved versions online and found that using hoare partition greatly shortened my calculation time. These are the two main problems I encountered. I also encountered many minor problems when writing this exercise, but they were fixed soon.

<h5>4. What can you say about the time complexity of your implementation? How efficient is the code in reading and managing the words and their counts? How efficient is your code in getting the top-100 list? Which sorting algorithm are you using? What is the time complexity of that algorithm?</h5>
I am using quicksort for the sorting, so my time complexity is O(n log n) in average case. Though I use Hoare's Algorithm, an enhanced version compare to original quicksort, but the time complexity is still the same.<br>
It costs me 2 sec 203 ms to run the whole correctness test on my pc. It took me around 100 ms to get the top-100 list, the majority of time is for counting words.

<h5>5. What did you find the most difficult things to understand and implement in this programming task? Why? </h5>
Definitely the time it costs, I never thought the original quicksort algorithm is that slow, it took me 30 sec to finish the whole test. Because we were taught in the class that quicksort is a very fast algorithm, so I focus more on my hash function which turns out has no influence on the time costs. After I use some tools to inspect my code and finally found the cause of slowness is the quicksort algorithm, Lomuto's algorithm, more precisely. Then I look up the internet and found Hoare's Algorithm, an enhanced version of quicksort, on average, Hoare's algorithm needs three times fewer swaps as compared to Lomuto's algorithm. After replace it to Hoare's Algorithm, it only took me 2.3 sec to finish the whole task!

<h5>6. What did you learn doing this?</h5>
Don’t give up easily when encountering difficulties, learn how to ask the **right** questions, and learn to use tools and the Internet to find answers.