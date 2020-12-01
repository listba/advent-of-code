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
      (->> vals
           (map (fn [fst] 
                  (let [res (filter (fn [snd] (== 2020 (+ fst snd))) vals)]
                    (map (fn [snd] (* fst snd)) res))))
           (filter not-empty))))

(defn p2
  []
  (let [vals (parse "input.txt")]
    (->> vals
         (map (fn [fst]
            (->> vals 
                 (map (fn [snd]
                    (let [res (filter (fn [trd] (== 2020 (+ fst snd trd))) vals)]
                      (map (fn [trd] (* fst snd trd)) res))))
                 (filter not-empty))))
         (filter not-empty))))