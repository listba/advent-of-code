(ns aoc-2021.days.05 
  (:require [aoc-2021.util :as util]))

(defn parse [file]
  (->> file
       (util/parse-lines "05")
       (mapv #(->> % (re-seq #"\d+") (map read-string) (partition 2)))))

(defn range* [a b] (if (< a b) (range a (inc b)) (reverse (range b (inc a)))))

(defn plot-line [plot-diags sea-map [[x1 y1] [x2 y2]]]
  (cond
    (and (= x1 x2) (= y1 y2)) (update sea-map (str x1 "_" y1) (fnil inc 0))
    (= x1 x2) (reduce (fn [sea-map y] (update sea-map (str x1 "_" y) (fnil inc 0))) sea-map (range* y1 y2))
    (= y1 y2) (reduce (fn [sea-map x] (update sea-map (str x "_" y1) (fnil inc 0))) sea-map (range* x1 x2))
    plot-diags (reduce (fn [sea-map [x y]] (update sea-map (str x "_" y) (fnil inc 0))) sea-map (map vector (range* x1 x2) (range* y1 y2)))
    :else sea-map)) ;; we are not plotting diaganols for part 1

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file parse (reduce (partial plot-line false) {}) vals (filter #(< 1 %)) count)))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file parse (reduce (partial plot-line true) {}) vals (filter #(< 1 %)) count)))