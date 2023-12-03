(ns aoc-2023.days.02 
  (:require [aoc-2023.util :as util]
            [clojure.string :as string]))


(defn parse-game ([[game-str & sets]]
                  (let [game-id (->> game-str (re-seq #"\d+") first read-string)
                        sets (map #(->> (re-seq #"(\d+)\s(\w+)" %)
                                        (map (fn [[_ n c]] {(keyword c) (read-string n)}))
                                        (apply merge)) 
                                  sets)]
                    {:game-id game-id :sets sets})))

(defn parse ([file]
             (->> file 
                  (util/parse-lines "02") 
                  (map #(re-seq #"[\w\d][\w\d\s,]+[:;]?" %))
                  (map #(parse-game %)))))

(defn cc ([set color cmax] (>= cmax (color set 0))))
(defn check-game ([[r-max g-max b-max] {sets :sets}]
                  (reduce (fn [valid set]
                            (and valid 
                                 (cc set :red r-max)
                                 (cc set :green g-max)
                                 (cc set :blue b-max))) 
                          true sets)))
(defn p1 
  ([] (p1 "input"))
  ([file] (->> file 
               parse 
               (filter #(check-game [12 13 14] %)) 
               (map #(:game-id %))
               (apply +))))

(defn min-cubes ([{sets :sets}]
                 (reduce (fn [min-needed set]
                           (map #(apply max %) (util/zip min-needed [(:red set 0) (:green set 0) (:blue set 0)]))) 
                         [0 0 0] sets)))
(map #(apply max %) (util/zip [1 2 3] [3 2 1]))
(defn p2 
  ([] (p2 "input"))
  ([file] (->> file 
               parse 
               (map #(min-cubes %)) 
               (map #(apply * %))
               (apply +))))


(str "p1: " (p1 "sample"))
(str "p2: " (p2 "sample"))

(str "p1: " (p1 "input"))
(str "p2: " (p2 "input"))