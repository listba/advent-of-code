(ns aoc-2020.days.06
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse [input]
  (->> (str "../resources/" input)
       slurp
       (#(str/split % #"[\n]{2}"))                       ;; groups are seperated by \n\n (blank line)
       (map #(->> (str/split % #"[\n]") (map set)))))    ;; people within a group are seperated by \n

(defn p1 []
  (->> (parse "day-06/input.txt")
       (map (fn [group] (->> (reduce set/union group) count)))   ;; count the number of uniquie answers in each group
       (reduce +)))

(defn p2 []
  (->> (parse "day-06/input.txt")
       (map (fn [group] (->> (reduce set/intersection group) count)))
       (reduce +)))