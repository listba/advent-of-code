(ns aoc-2021.util)

(defn not-empty? [xs] (not (empty? xs)))

(defn p [x] (println x) x) ; little helper to debug in threading macros

(defn transpose [m] (apply mapv vector m))


(defn read-file [day file]
  (slurp (str "../resources/day-" day "/" file ".txt")))

(defn parse-lines [day file]
  (->> (read-file day file)
       (re-seq #"[^\n]+")))

(defn parse-nums [day file] 
(->> (read-file day file)
     (re-seq #"\d+")
     (map read-string)))

(defn parse-numlines [day file]
  (->> (parse-lines day file)
       (map read-string)))

(defn abs [x]
    (if (> x 0) x (* -1 x)))
    
(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))