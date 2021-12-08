 # Day 08 notes

 needed to write some things down in order to think through todays problems

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

solved:
cdfgeb  = 6 (missing a from 1)
fbcad   = 3 (only 5 digit with both a & b from 1)
Givens:
1 7 4 8 are the only digits in their class (2, 3, 4, and 7 digits respectively)
2 3 5 are all 5 digits
0 6 9 are all 6 digits

6 can be found by identifying the only 6 digit seq missing one of the characters from 1
3 can be found by identifying the only 5 digit seq with both characters from 1

