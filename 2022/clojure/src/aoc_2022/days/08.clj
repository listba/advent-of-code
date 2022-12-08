(ns aoc-2022.days.08 
  (:require [aoc-2022.util :as util]
            [clojure.set :refer [union]]))

; search for visible trees in a given line
; if row? is false then we are working on a transposed line and need to flip the x & y
; offset is used to compute the real x (or y if we a col) for visibility searching from the other side of the line
(defn find-outer-visible
  ([row? pivot line]
   (union
    (find-outer-visible 0 row? pivot line) 
    (find-outer-visible (-> line count (- 1)) row? pivot (-> line rseq vec))))  ; reverse the line & set offsetto search from the other end
  ([offset row? pivot line]
   (->> line
        (reduce-kv (fn [[tallest trees] k v]
                     (let [idx (util/abs (- k offset))
                           [x y] (if row? 
                                   [idx pivot]
                                   [pivot idx])]
                       (if (< tallest v)
                         [v (conj trees [x y])]
                         [tallest trees])))
                   [-1 #{}])
        last)))

(defn tree-outer-visibility [row?] (map-indexed (partial find-outer-visible row?)))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [rows (util/parse-digits "08" file)
                cols (util/transpose rows)]
            (count (clojure.set/union
                    (transduce (tree-outer-visibility true) union rows)
                    (transduce (tree-outer-visibility false) union cols))))))

(defn update-score [tree score] (update tree :score (partial * score)))

(defn visibility-from-tree
  ([] [])
  ([[{height :height :as tree} & line]]
   (->> line
        (reduce (fn [c {nh :height}]
                  (if (> height nh)
                    (+ 1 c)
                    (reduced (+ 1 c)))) 0)
        (update-score tree))))

(defn tree-line-visibility [tree-line] (map visibility-from-tree (partition-all (count tree-line) 1 tree-line)))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
               (util/parse-digits "08")
               (util/map-indexed-in (fn [coord v] {:height v :coord coord :score 1}))
               (map tree-line-visibility)
               (map reverse)
               (map tree-line-visibility)
               (util/transpose)
               (map tree-line-visibility)
               (map reverse)
               (map tree-line-visibility)
               (flatten)
               (sort-by :score >)
               first)))


(str "p1: " (p1) " p2: " (p2))