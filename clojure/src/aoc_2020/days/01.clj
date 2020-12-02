(ns aoc-2020.days.01 
  (:require [aoc-2020.util :as util]))

(defn p1 
  []
    (let [vals (util/parse "day-01/input.txt")]
      (first (for [x vals
                   y vals
                   :when (== 2020 (+ x y))] 
               {:x x :y y :result (* x y)}))))

(defn p2
  []
  (let [vals (util/parse "day-01/input.txt")]
    (first (for [x vals
                 y vals
                 z vals
                 :when (== 2020 (+ x y z))]
             {:x x :y y :z z :result (* x y z)}))))