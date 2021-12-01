(ns aoc-2021.days.01 
  (:require [aoc-2021.util :as util]))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [[x & xs] (util/parse-nums (str "day-01/" file ".txt"))]
            (first (reduce (fn [[sum prev] cur]
                             (if (< prev cur) [(+ 1 sum) cur] [sum cur])) [0 x] xs)))))

(defn p2 
  ([] (p2 "input"))
  ([file] (let [[x y z & xs] (util/parse-nums (str "day-01/" file ".txt"))]
            (first (reduce (fn [[sum x y z] cur]
                             (if (< (+ x y z) (+ y z cur)) [(+ 1 sum) y z cur] [sum y z cur])) [0 x y z] xs)))))