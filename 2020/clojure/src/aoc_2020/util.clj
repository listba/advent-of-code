(ns aoc-2020.util)

(defn parse [input]
  (->> input
       (str "../resources/")
       slurp
       (re-seq #"[^\n]+")))

(defn parse-nums [input]
  (->> input
       parse
       (map read-string)))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))