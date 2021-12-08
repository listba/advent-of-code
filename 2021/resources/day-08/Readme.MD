 # Day 08 notes

 needed to write some things down in order to think through todays problems
```sh
Sample digit:
 0000
1    2
1    2
1    2
 3333
4    5
4    5
4    5
 6666
```

```sh
acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab |
cdfeb fcadb cdfeb cdbaf
ab                   | 1
dab                  | 7
eafb                 | 4
cdfbe gcdfa fbcad    | 2 3 5 
cefabd cdfgeb cagedb | 0 6 9
acedgfb              | 8

Givens:
ab      = 1
dab     = 7
eafb    = 4
acedgfb = 8
```
Givens:
* 1 7 4 8 are the only digits in their class (2, 3, 4, and 7 digits respectively)
* 2 3 5 are all 5 digits
* 0 6 9 are all 6 digits

* 9 is the only 6 length number that is a superset of 4
* 6 is the only 6 length number that is not a super set of 1
* 0 is the only remaining 6 segment

* 3 is the only 5 length superset of 1
* 5 is a subset of 6
* 2 is the only 5 len remaining

