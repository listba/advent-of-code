(ns aoc-2020.days.01 
  (:require [clojure.string :as str]
            [aoc-2020.util :as util]))

(defn parse [input]
  (->> input
    (str "resources/day-01/")
    slurp
    (re-seq #"[^\n]+")
    (map read-string)))

(defn p1 
  []
    (let [vals (parse "input.txt")]
      (first (for [x vals
                   y (drop 1 vals)
                   :when (== 2020 (+ x y))] 
               {:x x :y y :result (* x y)}))))

(defn p2
  []
  (let [vals (parse "input.txt")]
    (first (for [x vals
           y (drop 1 vals)
           z (drop 2 vals)
           :when (== 2020 (+ x y z))]
       {:x x :y y :z z :result (* x y z)}))))