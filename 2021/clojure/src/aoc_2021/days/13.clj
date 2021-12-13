(ns aoc-2021.days.13 
  (:require [aoc-2021.util :as util]
            [clojure.string :as string]))


(defn parse [file] 
  (let [[dots folds] (-> (util/read-file "13" file) (string/split #"\n\n"))
        dots (->> dots (re-seq #"\d+") (mapv read-string) (partition 2) (mapv vec))
        folds (->> folds (re-seq #"([xy])=(\d+)") (map (fn [[_ axis f]] [(if (= "x" axis) 0 1) (read-string f)])))]
    [dots folds]))

(defn calc-fold [f v] (if (> v f) (- (* 2 f) v) v))

(defn fold-paper [dots [axis fold-index]]
  (distinct (mapv #(update % axis (partial calc-fold fold-index)) dots)))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [[dots folds] (parse file)]
            (count (fold-paper dots (first folds))))))

(defn make-grid [x y v] (->> v (repeat (* x y)) (partition x) (mapv vec)))

(defn make-paper [dots] (let [mx (->> dots (map first) (reduce max))
                              my (->> dots (map second) (reduce max))
                              empty-paper  (make-grid (inc mx) (inc my) ".")]
                          (reduce (fn [paper [x y]] (assoc-in paper [y x] "#")) empty-paper dots)))
(defn p2 
  ([] (p2 "input"))
  ([file] (let [[dots folds] (parse file)]
            (->> folds
                 (reduce fold-paper dots)
                 make-paper
                 (mapv println)))))