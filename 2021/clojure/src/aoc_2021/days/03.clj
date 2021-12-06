(ns aoc-2021.days.03 
  (:require [aoc-2021.util :as util]))

(defn idx-freq [input index]
  (->> input (map #(nth % index)) frequencies (sort-by val)))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [bits (util/parse-lines "03" file)] 
                (->> (count (first bits))
                     range
                     (map #(idx-freq bits %))
                     (reduce (fn [[epsilon gamma] [[lcb _] [mcb _]]] [(str epsilon lcb) (str gamma mcb)]) ["2r" "2r"])
                     (map read-string)
                     (apply *)))))
                
(defn oxy-filter [input index]
  (let [[[_ xc] [y yc]] (idx-freq input index)] (if (= xc yc) \1 y)))

(defn co2-filter [input index]
  (let [[[x xc] [_ yc]] (idx-freq input index)] (if (= xc yc) \0 x)))

(defn p2 
  ([] (p2 "input"))
  ([file] (let [bits (util/parse-lines "03" file)]
            (->> (count (first bits))
                 range
                 (reduce (fn [[oxy-bits co2-bits] col]
                           (let [oxy-bit (oxy-filter oxy-bits col)
                                 co2-bit (co2-filter co2-bits col)]
                             [(filter #(= oxy-bit (nth % col)) oxy-bits) (filter #(= co2-bit (nth % col)) co2-bits)]))
                         [bits bits])
                 (map #(->> % (apply str) (str "2r") read-string))
                 (apply *)))))