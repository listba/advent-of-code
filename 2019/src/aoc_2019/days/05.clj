(ns aoc-2019.days.05
  (:require [aoc-2019.intcode :as ic]))

(defn p 
  [input param] 
  (as-> input $ 
    (str "resources/day-05/"$)
    (ic/parseInput $) 
    (ic/Intcode $ [param])
    (vec $)))

(defn p1 
  ([] (p1 "input.txt" 1))
  ([input param] (p input param)))

  (defn p2 
    ([] (p1 "input.txt" 5))
    ([input param] (p input param)))