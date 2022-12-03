(ns aoc-2022.days.03 
  (:require [aoc-2022.util :as util]
            clojure.set))

(defn calculate-priority
  [bags]
  (let [bad-part (->> bags (map set) (apply clojure.set/intersection))
        val (-> bad-part vec first int)]
    (if (< val 97) (- val 38) (- val 96))))

(defn calculate-priorities [groups]
   (->> groups (map calculate-priority) (apply +)))

(defn p1 
  ([] (p1 "input"))
  ([file]
   (->> file
        (util/parse-lines "03")
        (map #(let [mid (-> % count (/ 2))] (partition mid %)))
        calculate-priorities)))

(defn p2 
  ([] (p2 "input"))
  ([file]
   (->> file
        (util/parse-lines "03")
        (partition 3)
        calculate-priorities)))

(str "p1: " (p1) " p2: " (p2))