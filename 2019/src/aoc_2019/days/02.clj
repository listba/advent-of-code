(ns aoc-2019.days.02
  (:require [aoc-2019.intcode :as ic]))

(defn pgm [input x y] 
  (as-> input $
    (str "resources/day-02/" $)
    (ic/parseInput $)
    (assoc $ 1 x 2 y)
    (ic/Intcode $)
    (last $)))

(defn p1 
  ([] (p1 "input.txt" 12 2))
  ([x y] (p1 "input.txt" 12 2))
  ([input x y] (pgm input x y)))

(defn p2 
  ([] (p2 "input.txt" 19690720))
  ([input search] 
    (first (for [noun (range 1 100) 
          verb (range 1 100)
            :when (= search (pgm input noun verb))]
            (str noun verb)))))