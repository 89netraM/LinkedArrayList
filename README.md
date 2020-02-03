# LinkedArrayList

A LinkedArrayList is a linked list of arrays of whatever you want.

Hopefully they're better at something than other list types.

## FixedArrayList

My LinkedArrayLists doesn't use plain arrays for the blocks in the linked list.
It uses what I call FixedArrayLists. That is an ArrayList optimised for a
predetermined maximum length by being circular. Because of that they get a
big O of at most Θ(n/2) for insertions/deletions and Θ(1) in either end. 

## Big O table

|                            | LinkedList  | ArrayList | FixedArrayList | LinkedArrayList |
|----------------------------|------------:|----------:|---------------:|----------------:|
| Indexing                   |        Θ(i) |      Θ(1) |           Θ(1) |     Coming soon |
| Insert/delete at beginning |        Θ(1) |      Θ(n) |           Θ(1) |     Coming soon |
| Insert/delete at end       |        Θ(1) |     Θ(1)* |           Θ(1) |     Coming soon |
| Insert/delete at index     |        Θ(i) |  Θ(n - i) |         Θ(n/2) |     Coming soon |

*Amortized

LinkedList and ArrayList source: [Linked list - Wikipedia](https://en.wikipedia.org/wiki/Linked_list#Linked_lists_vs._dynamic_arrays).  