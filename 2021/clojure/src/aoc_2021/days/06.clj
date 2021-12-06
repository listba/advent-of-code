(ns aoc-2021.days.06 
  (:require [aoc-2021.util :as util]))


(defn simulate-fish [fish] (if (= 0 fish) [6 8] [(dec fish)]))


(defn fish-cycle [fishes] 
  (->> fishes
        (map simulate-fish)
        flatten))


(defn iterate* [f x] (reductions (fn [dx _] (f dx)) x (range)))


(defn p1 
  ([] (p1 "input"))
  ([file] (->> (util/read-file "06" file)
                (re-seq #"\d+")
                (map read-string)
                (iterate* fish-cycle)
                (take 81)
                last
                count)))
  
(defn p2 
  ([] (p2 "input"))
  ([file] ))