(ns aoc-2021.util)

(defn not-empty? [xs] (not (empty? xs)))

(defn p [x] (println x) x) ; little helper to debug in threading macros

(defn transpose [m] (apply mapv vector m))

(defn parse [day file]
  (->> (str "../resources/day-" day "/" file ".txt")
       slurp
       (re-seq #"[^\n]+")))

(defn parse-nums [day file]
  (->> (parse day file)
       (map read-string)))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))