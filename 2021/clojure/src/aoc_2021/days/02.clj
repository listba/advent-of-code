(ns aoc-2021.days.02 
  (:require [aoc-2021.util :as util]))


(defn parse [file]
  (->> file
       (util/parse "02")
       (map (fn [line]
              (let [[[_ x y]] (re-seq #"([a-zA-Z]+) (\d+)" line)]
                [x (read-string y)])))))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file
               parse
               (reduce (fn [[horizontal depth] [direction x]]
                         (case direction
                           "forward" [(+ horizontal x) depth]
                           "down"    [horizontal (+ depth x)]
                           "up"      [horizontal (- depth x)])) [0 0])
               (apply *))))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
               parse
               (reduce (fn [[horizontal depth aim] [direction x]]
                         (case direction
                           "forward" [(+ horizontal x) (+ depth (* aim x)) aim]
                           "down"    [horizontal depth (+ aim x)]
                           "up"      [horizontal depth (- aim x)])) [0 0 0])
               (take 2)
               (apply *))))