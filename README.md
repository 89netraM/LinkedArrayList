# LinkedArrayList

A LinkedArrayList is a linked list of arrays of whatever you want.

Hopefully they're better at something than other list types.

## How it works

* The list keeps one reference to the first and last block.
* Blocks have references the the next and previous block.
* All but the last block must always be full.
* All blocks have the same size.

`[++++][++++][++--]`  
Illustration

## FixedArrayList

My LinkedArrayLists doesn't use plain arrays for the blocks in the linked list.
It uses what I call FixedArrayLists. That is an ArrayList optimised for a
predetermined maximum length by being circular. Because of that they get a
big O of at most Θ(n/2) for insertions/deletions and Θ(1) in either end. 

## Big O table

|                            | LinkedList  | ArrayList | FixedArrayList | LinkedArrayList |
|----------------------------|------------:|----------:|---------------:|----------------:|
| Indexing                   |    `Θ(n/2)` |    `Θ(1)` |         `Θ(1)` |      `O(n/b/2)` |
| Insert/delete at beginning |      `Θ(1)` |    `Θ(n)` |         `Θ(1)` |        `O(n/b)` |
| Insert/delete at end       |      `Θ(1)` |   `Θ(1)`* |         `Θ(1)` |          `O(1)` |
| Insert/delete at index     |    `Θ(n/2)` |    `Θ(n)` |       `Θ(n/2)` |  `O(n/b + b/2)` |

*Amortized

LinkedList and ArrayList source: [Linked list - Wikipedia](https://en.wikipedia.org/wiki/Linked_list#Linked_lists_vs._dynamic_arrays).  