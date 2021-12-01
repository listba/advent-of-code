(ns aoc-2019.days.01 
  (:require [clojure.string :as str]))

(defn parse [input]
  (->> input
    (str "resources/day-01/")
    slurp
    (re-seq #"[^\n]+")
    (map read-string)))

(defn calcFuel
  [module]
  (let [fuel (- (int (/ module 3)) 2)]
    (if (>= 0 fuel)
      0
      (+ fuel (calcFuel fuel)))))

(defn p1 
  []
    (->> "input.txt"
        parse
        (map #(as-> % $ (/ $ 3) (int $) (- $ 2)))
        (reduce +)))

(defn p2 
  [] 
    (->> "input.txt"
      parse
      (map calcFuel)
      (reduce +)))