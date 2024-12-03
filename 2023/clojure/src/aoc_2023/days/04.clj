(ns aoc-2023.days.04 
  (:require [aoc-2023.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn parse [file] (let [lines (util/parse-lines "04" file)
                         cards (map #(str/split % #"[\|:]") lines)
                         get-numbers #(re-seq #"\d+" %)]
                     (map (fn [[_ winning mine]] [(get-numbers winning) (get-numbers mine)]) cards)))
(defn score-card [[winning mine]] 
  (->> (clojure.set/intersection (set winning) (set mine)) (count) (#(cond 
                                                                       (not= 0 %) (int (Math/pow 2 (dec %)))
                                                                       :else 0))))
(defn p1 
  ([] (p1 "input"))
  ([file] (->> file parse (map score-card) (apply +))))

(defn p2 
  ([] (p2 "input"))
  ([file] ))

(str "p1: " (p1) " p2: " (p2))

(str "p1: " (p1 "sample"))
(str "p2: " (p2 "sample"))

(pr-str "p1: " (p1 "input"))
(pr-str "p2: " (p2 "input"))