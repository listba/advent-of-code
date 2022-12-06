    (ns aoc-2022.days.06 
      (:require [aoc-2022.util :as util]))

(defn first-unique [size signal]
  (->> signal 
       (partition size 1)
       (map set)
       (keep-indexed (fn [idx window] (if (== size (count window)) [idx window] nil)))
       first))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file (util/read-file "06") (first-unique 4) first (+ 4))))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file (util/read-file "06") (first-unique 14) first (+ 14))))

(str "p1: " (p1) " p2: " (p2))