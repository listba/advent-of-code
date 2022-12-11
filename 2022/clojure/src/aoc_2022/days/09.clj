(ns aoc-2022.days.09 
  (:require [aoc-2022.util :as util]
            [clojure.core.match :refer [match]]))

(defn move-head 
  [[x y] [direction dist]] 
  (let [rng (range 1 (inc dist))]
    (match direction
      "R" (map (fn [i] [(+ x i) y]) rng)
      "L" (map (fn [i] [(- x i) y]) rng)
      "D" (map (fn [i] [x (+ y i)]) rng)
      "U" (map (fn [i] [x (- y i)]) rng))))

(defn neighbors [x y] #{[(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]
                        [(dec x)       y] [x       y] [(inc x)       y]
                        [(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]})

(defn move-knot
  ([[tail-hist knots] head]
   (move-knot tail-hist [] knots head))
  ( [tail-hist moved [[ckx cky] & rem] [pkx pky]] 
   (let [knot (cond
                ; Knot is within one of previous so no need to move
                (contains? (neighbors pkx pky) [ckx cky]) [ckx cky]
                 ; Same column move 1 in that direction
                (and (< ckx pkx) (= cky pky))             [(inc ckx) cky]
                (and (> ckx pkx) (= cky pky))             [(dec ckx) cky]
                (and (= ckx pkx) (< cky pky))             [pkx (inc cky)]
                (and (= ckx pkx) (> cky pky))             [pkx (dec cky)]
                 ; more than 2 away so move diagonally
                (and (< ckx pkx) (< cky pky))             [(inc ckx) (inc cky)]
                (and (> ckx pkx) (> cky pky))             [(dec ckx) (dec cky)] 
                (and (< ckx pkx) (> cky pky))             [(inc ckx) (dec cky)]
                (and (> ckx pkx) (< cky pky))             [(dec ckx) (inc cky)])]
     (if (nil? rem)
       [(conj tail-hist knot) (conj moved knot)]
       (recur tail-hist (conj moved knot) rem knot)))))
 
(defn move-rope
  [[tail-hist tails head] move]
  (let [head-moves (move-head head move)
        new-head (last head-moves)]
     (conj (reduce move-knot [tail-hist tails] (move-head head move)) new-head)))


(defn solve [file knots]
  (->> file
       (util/parse-lines "09")
       (map #(re-seq #"[^ ]+" %))
       (map (fn [[direction distance]] [direction (read-string distance)]))
       (reduce move-rope [#{[0 0]} (-> knots (repeat [0 0]) vec) [0 0]])
       first
       (count)))

(defn p1 
  ([] (p1 "input"))
  ([file] (solve file 1)))

(defn p2 
  ([] (p2 "input"))
  ([file] (solve file 9)))


(str "p1: " (p1) " p2: " (p2))