(ns aoc-2021.days.06 
  (:require [aoc-2021.util :as util]))

(defn iterate* [f x] (reductions (fn [dx _] (f dx)) x (range)))

(defn parse-fishes [file]
  (->> (util/read-file "06" file)
       (re-seq #"\d+")
       (map read-string)
       frequencies))

(defn simulate-fish [fish-map [age count]]
  (if (= 0 age)
    (-> fish-map (assoc 8 count) (update 6 (fnil (partial + count) 0)))
    (assoc fish-map (dec age) count)))

(defn fish-cycle [fish-map]
  (->> fish-map 
  (sort-by key #(> %1 %2))
  (reduce simulate-fish {})))

(defn run-cycles [file cycles] (->> (parse-fishes file) (iterate* fish-cycle) (take (inc cycles)) last vals (apply +)))

(defn p1 
  ([] (p1 "input"))
  ([file] (run-cycles file 80)))
  
(defn p2 
  ([] (p2 "input"))
  ([file] (run-cycles file 256)))