(ns aoc-2023.util 
  (:require [clojure.string :as string]))

(defn zip [& colls]
  (partition (count colls) (apply interleave colls)))

(defn not-empty? [xs] (seq xs))
(defn charNum? [char] (->> char str read-string number?))
(defn digit? [c] (and (>= 0 (compare \0 c)) 
                      (>= 0 (compare c \9))))
(defn split>> [re s] (string/split s re))

(defn p [x] (println x) x) ; little helper to debug in threading macros
(defn mp [xs] (mapv println xs) xs)
(defn ps [s xs] (println s) xs)


; Apply a function f to a 2d vector
(defn map-in [f rows] (map (fn [row] (map (fn [cell] (f cell)) row)) rows))
(defn map-indexed-in [f rows] (map-indexed (fn [y row] (map-indexed (fn [x cell] (f [y x] cell)) row)) rows))

;; provided a filter function and a 2D vector 
;; return any cells that match along with their location
;; in the form ([y x value])
(defn search-in [f grid]
  (let [results 
        (keep-indexed
         (fn [y row]
           (not-empty
            (keep-indexed (fn [x cell] (if (f cell) [y x cell] nil)) row))) grid)]
    (apply concat results)))

(defn update-in>> [f k g] (update-in g k f))
(defn assoc-in>> [v k g] (assoc-in g k v))

(defn transpose [m] (apply mapv vector m))

(defn read-file [day file]
  (slurp (str "../resources/day-" day "/" file ".txt")))

(defn split-blank-lines [day file] (-> (read-file day file) (string/split #"\n\n")))

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


(defn parse-grid 
  ([day file] (parse-grid day file #(mapv char %))) ;; defaults to single characters
  ([day file sequencer]
   (->> (parse-lines day file)
        (mapv sequencer))))


(defn parse-grid-digits [day file]
  (parse-grid day file #(->> % (re-seq #"\d") (mapv read-string))))

(defn abs [x]
    (if (> x 0) x (* -1 x)))
    
(defn min-max [xs] 
  (reduce (fn [[_min _max] x] [(min _min x) (max _max x)]) [(first xs) (first xs)] xs))

(defn permutations [colls]
  (if (= 1 (count colls))
    (list colls)
    (for [head colls
          tail (permutations (disj (set colls) head))]
      (cons head tail))))


(defn fetch-neighbors 
  "Returns value and coordinate pairs `[nv [ny nx]]` of any all neighboring cells
   given a 2D vector (`chart`) for a given cells coordinates `[y x]`
   if `diags?` is false only cardinal neighbors are returned `N,E,S,W`"
  [diags? [y x] chart] 
  (let [N  [(dec y) x]
        NE [(dec y) (inc x)]
        E  [y (inc x)]
        SE [(inc y) (inc x)]
        S  [(inc y) x]
        SW [(inc y) (dec x)]
        W  [y (dec x)]
        NW [(dec y) (dec x)]]
    (->> (if diags? [N NE E SE S SW W NW] [N E S W])
         (keep (fn [[ny nx]]
                 (some->> [ny nx]
                          (get-in chart)
                          ((fn [x] [x [ny nx]]))))))))

(defn fetch-neighbors-values 
  "calls `fetch-neighbors` but only returns the cell values of the neighbors"
  [diags? coords chart] 
  (map first (fetch-neighbors diags? coords chart)))