(ns aoc-2021.days.08 
  (:require [aoc-2021.util :as util]))

(def sample "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

(defn parse [file]
  (->> file
       (util/parse-lines "08")
       (mapv #(split-at 10 (re-seq #"\w+" %)))
       (mapv (fn [[l r]] [(group-by count l) r]))))

(defn map-digits [input]
  (let [_1 (first (get input 2))
        _7 (first (get input 3))
        _4 (first (get input 4))
        _8 (first (get input 7))]
        ))
(defn p1 
  ([] (p1 "input"))
  ([file] (let [length-count (->> file parse (map last) flatten (mapv count) frequencies)]
            (->> (select-keys length-count [7 4 3 2]) vals (reduce +)))))

(defn p2 
  ([] (p2 "input"))
  ([file] ))