(ns aoc-2021.days.09 
  (:require [aoc-2021.util :as util]))

(defn parse [file]
  (->> file
       (util/parse-lines "09")
       (mapv #(->> % (re-seq #"\d") (mapv read-string)))))

(defn calc-basin [sea-map v coord]
  (->> sea-map
       (util/fetch-neighbors false coord)
       (reduce (fn [basin [nv ncoord]]
                 (if (and (< v nv) (not= 9 nv))
                   (concat (calc-basin sea-map nv ncoord) basin)
                   basin))
               [coord])))

(defn check-loc [sea-map coord]
  (let [v (get-in sea-map coord)
        neighbors (util/fetch-neighbors false coord sea-map)]
    (if (reduce (fn [b [n _]] (and b (< v n))) true neighbors)
      [coord v (inc v) (count (set (calc-basin sea-map v coord)))]
      [coord v 0 0])))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [sea-map (parse file)
                yr (range (count sea-map))
                xr (range (count (first sea-map)))]
                (reduce (fn [t [_ _ v _]] (+ v t)) 0 (for [y yr x xr] (check-loc sea-map [y x]))))))


(defn p2 
  ([] (p2 "input"))
  ([file] (let [sea-map (parse file)
                yr (range (count sea-map))
                xr (range (count (first sea-map)))]
                (->> (for [y yr x xr] (check-loc sea-map [y x]))
                     (map last)
                     (filter (partial not= 0))
                     sort
                     reverse
                     (take 3)
                     (reduce *)))))