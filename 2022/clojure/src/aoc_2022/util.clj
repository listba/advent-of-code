(ns aoc-2022.util 
  (:require [clojure.string :as string]))

(defn not-empty? [xs] (not (empty? xs)))

(defn p [x] (println x) x) ; little helper to debug in threading macros
(defn mp [xs] (mapv println xs) xs)
(defn ps [s xs] (println s) xs)

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

(defn parse-digits [day file]
  (->> (parse-lines day file)
       (mapv #(->> % (re-seq #"\d") (mapv read-string)))))

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

(defn fetch-neighbors [diags? [y x] chart]
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