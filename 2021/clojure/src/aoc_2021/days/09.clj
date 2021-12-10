(ns aoc-2021.days.09 
  (:require [aoc-2021.util :as util]))

(defn parse [file]
  (->> file
       (util/parse-lines "09")
       (mapv #(->> % (re-seq #"\d") (mapv read-string)))))


(defn calc-basin [sea-map y x]
  (let [v (get-in sea-map [y x])
        comp (fnil (fn [x] (and (< v x) (not= 9 x))) 0)
        basin (reduce (fn [basin [y x]]
              (if (comp (get-in sea-map [y x]))
                (concat (calc-basin sea-map y x) basin)
                basin))
            [[y x]] [[(dec y) x] [(inc y) x] [y (dec x)] [y (inc x)]])]
    basin))

(defn check-loc [sea-map y x]
  (let [v (get-in sea-map [y x])
        u (get-in sea-map [(dec y) x])
        d (get-in sea-map [(inc y) x])
        l (get-in sea-map [y (dec x)])
        r (get-in sea-map [y (inc x)])]
    (if (reduce (fn [b n] (and b (< v n))) true (remove nil? [u d l r]))
      [y x v (inc v) (count (set (calc-basin sea-map y x)))]
      [y x v 0 0])))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [sea-map (parse file)
                yr (range (count sea-map))
                xr (range (count (first sea-map)))]
                (reduce (fn [t [_ _ _ v _]] (+ v t)) 0 (for [y yr x xr] (check-loc sea-map y x))))))


(defn p2 
  ([] (p2 "input"))
  ([file] (let [sea-map (parse file)
                yr (range (count sea-map))
                xr (range (count (first sea-map)))]
                (->> (for [y yr x xr] (check-loc sea-map y x))
                     (map last)
                     (filter (partial not= 0))
                     sort
                     reverse
                     (take 3)
                     (reduce *)))))