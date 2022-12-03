(ns aoc-2022.days.01 
  (:require [aoc-2022.util :as util]
            [clojure.string :as str]))

(defn parse-food [file]
  (let [contents (util/read-file "01" file)
        elves (str/split contents #"\n\n")]
    (map #(->> %
               (re-seq #"\d+")
               (map read-string)) elves)))

(defn p1 
  ([] (p1 "input"))
  ([file] 
   (->> file
        parse-food
        (map (partial apply +))
        (sort)
        (last))))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
               parse-food
               (map (partial apply +))
               (sort >=)
               (take 3)
               (apply +))))

(str "p1: " (p1) " p2: " (p2))