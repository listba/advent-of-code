(ns aoc-2021.days.08 
  (:require [aoc-2021.util :as util]
            [clojure.set]))

(defn parse [file]
  (->> file
       (util/parse-lines "08")
       (mapv #(split-at 10 (re-seq #"\w+" %)))
       (mapv (fn [[l r]] [(group-by count (set l)) r]))))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [length-count (->> file parse (map last) flatten (mapv count) frequencies)]
            (->> (select-keys length-count [7 4 3 2]) vals (reduce +)))))

(defn map-digits [input]
  (let [d1 (set (first (get input 2)))
        d7 (set (first (get input 3)))
        d4 (set (first (get input 4)))
        d8 (set (first (get input 7)))
        _5s (map set (get input 5))
        _6s (map set (get input 6))
        {[d9] true _6s false} (group-by (partial clojure.set/subset? d4) _6s)
        {[d0] true [d6] false} (group-by (partial clojure.set/subset? d1) _6s)
        {[d5] true _5s false} (group-by (partial clojure.set/superset? d6) _5s)
        {[d3] true [d2] false} (group-by (partial clojure.set/subset? d1) _5s)]
        {d1 1 d2 2 d3 3 d4 4 d5 5 d6 6 d7 7 d8 8 d9 9 d0 0}))
        
(defn match-output [digit-map output]
(->> output (map set) (map (partial get digit-map)) (apply str) Long/parseLong))

(defn p2
  ([] (p2 "input"))
  ([file] (let [data (parse file)]
            (reduce (fn [total [input output]]
                      (-> input map-digits (match-output output) (+ total))) 0 data))))