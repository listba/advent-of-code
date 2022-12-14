(ns aoc-2022.days.10 
  (:require [aoc-2022.util :as util]))

(defn prgm
  ([cmds] (prgm cmds [] 1))
  ([[[cmd v] & cmds] history x]
   (let [[states x] 
         (if (= "addx" cmd)
           [[x x] (+ x (read-string v))] 
           [[x] x])]
     (if (nil? cmds)
       (conj (apply conj history states) x) 
       (recur cmds (apply conj history states) x)))))

(defn draw-line [line]
  (map-indexed 
   (fn [i x] (if (and (<= x (inc i)) (>= x (dec i))) "#" ".")) line))

(defn parse [file] (->> file (util/parse-lines "10") (map (partial re-seq #"[^ ]+"))))

(defn p1 
  ([] (p1 "input"))
  ([file]
   (->> file
        (util/parse-lines "10")
        (map (partial re-seq #"[^ ]+"))
        prgm
        (keep-indexed (fn [i x] (if (== 0 (mod (- i 19) 40)) (* (inc i) x) nil)))
        (reduce +))))

(defn p2 
  ([] (p2 "input"))
  ([file]
   (->> file
        parse
        prgm
        (partition 40)
        (mapv draw-line)
        (mapv println)
        (reduce str))))


(str "p1: " (p1) " p2: " (p2))