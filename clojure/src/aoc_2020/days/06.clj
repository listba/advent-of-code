(ns aoc-2020.days.06
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse [input]
  (->> (str "../resources/" input)
       slurp
       (#(str/split % #"[\n]{2}"))
       (map #(->> (str/split % #"[\n]") (map set)))))

(defn p1
  []
  (->> (parse "day-06/input.txt")
       (map (fn [group] (->> (reduce set/union group) count)))
       (reduce +)))

(defn p2
  []
  (->> (parse "day-06/input.txt")
       (map (fn [group] (->> (reduce set/intersection group) count)))
       (reduce +)))