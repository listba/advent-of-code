(ns aoc-2021.days.08 
  (:require [aoc-2021.util :as util]
            [clojure.set]))

(def sample "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

(defn parse [file]
  (->> file
       (util/parse-lines "08")
       (mapv #(split-at 10 (re-seq #"\w+" %)))
       (mapv (fn [[l r]] [(group-by count (set l)) r]))))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [length-count (->> file parse (map last) flatten (mapv count) frequencies)]
            (->> (select-keys length-count [7 4 3 2]) vals (reduce +)))))


    ;; acedgfb: 8
    ;; cdfbe: 5
    ;; gcdfa: 2
    ;; fbcad: 3
    ;; dab: 7
    ;; cefabd: 9
    ;; cdfgeb: 6
    ;; eafb: 4
    ;; cagedb: 0
    ;; ab: 1

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
        {[d3] true [d2] false} (group-by (partial clojure.set/subset? d1) _5s)
        _ (println "--------------------------------------")
        _ (println input)
        _ (println (str "1: " (reduce str d1) " 2: " (reduce str d2) " 4: " (reduce str d4) " 8: " (reduce str d8)))
        _ (println (str "9: " (reduce str d9) " 0: " (reduce str d0) " 6: " (reduce str d6)))
        _ (println (str "5: " (reduce str d5) " 3: " (reduce str d3) " 2: " (reduce str d2)))
        ]
        {d1 1 d2 2 d3 3 d4 4 d5 5 d6 6 d7 7 d8 8 d9 9 d0 0}))
(defn match-output [digit-map output]
(->> output util/p (mapv (partial get digit-map)) (apply str) Long/parseLong))

(defn p2 
  ([] (p2 "input"))
  ([file] (let [data (parse file)]
            (reduce (fn [total [input output]] 
            (let [digit-map (map-digits input)]
            (+ total (match-output digit-map (map set output))))) 0 data))))