(ns aoc-2022.days.04 
  (:require [aoc-2022.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn parse [file]
  (->> file
       (util/parse-lines "04")
       (mapv #(->> % (re-seq #"\d+") (mapv read-string)))))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file
               parse
               (filter (fn [[a b x y]]
                         (or
                          (and (<= a x) (>= b y))
                          (and (<= x a) (>= y b)))))
               (count))))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
               parse
               (filter (fn [[a b x y]]
                         (or
                          (and (<= a x) (>= b x))
                          (and (<= x a) (>= y a)))))
               (count))))


(str "p1: " (p1) " p2: " (p2))