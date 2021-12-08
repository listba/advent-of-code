(ns aoc-2021.days.07 
  (:require [aoc-2021.util :as util]))

(defn median [xs] (let [mid (quot (count xs) 2)]
(-> xs sort (nth mid))))

(defn mean [xs] (/ (reduce + xs) (count xs)))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [crabs (util/parse-nums "07" file)
                waypoint (median crabs)]
            (reduce (fn [fuel crab] (+ fuel (util/abs (- crab waypoint)))) 0 crabs))))

(defn calc-fuel [crabs waypoint] 
(reduce (fn [fuel crab]
          (let [diff (util/abs (- crab waypoint))]
            (+ fuel (/ (* diff (inc diff)) 2)))) 0 crabs))

(defn p2
  ([] (p2 "input"))
  ([file] (let [crabs (util/parse-nums "07" file)
                f1 (int (mean crabs))]
            (min (calc-fuel crabs f1) (calc-fuel crabs (inc f1))))))