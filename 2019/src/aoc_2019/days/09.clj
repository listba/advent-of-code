(ns aoc-2019.days.09
  (:require [aoc-2019.intcode :as ic]))

(defn p1 
  ([] (p1 "input.txt" 1))
  ([pgm input] 
    (let [pgm (str "resources/day-09/" pgm)
          mem (ic/parseInput pgm)]
      (ic/Intcode mem [input]))))

(defn p2 
  ([] (p2 "input.txt" 2))
  ([pgm input] 
    (let [pgm (str "resources/day-09/" pgm)
          mem (ic/parseInput pgm)]
      (ic/Intcode mem [input]))))