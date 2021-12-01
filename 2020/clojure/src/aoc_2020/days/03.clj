(ns aoc-2020.days.03
  (:require [aoc-2020.util :as util]))

(defn tobog [x y terrain] 
  (->> terrain
       (drop y)
       (take-nth y)
       (map-indexed (fn [idx row]
                      (let [len (-> row count)
                            pos (-> idx (+ 1) (* x) (mod len))
                            spot (subs row pos (+ 1 pos))]
                        (if (= "#" spot) 1 0))))
       (reduce +)))

(defn p1
  []
  (->> "day-03/input.txt" util/parse (tobog 3 1)))

(defn parse-slopes [input] (map (fn [[_ x y]] [(read-string x),(read-string y)]) (re-seq #"(\d+):(\d+)" input)))

(defn p2
  ([] (p2 "1:1 3:1 5:1 7:1 1:2"))
  ([ input ]
   (let [terrain (util/parse "day-03/input.txt")
         slopes (parse-slopes input)
         results (map (fn [[x y]] (tobog x y terrain)) slopes)
         _ (println slopes)
         _ (println results)]
     (reduce * results))))