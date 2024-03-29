(ns aoc-2021.days.04 
  (:require [aoc-2021.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn check-board
  [picks [board tboard]]
  (let [set-picks (set picks)]
    (->> (concat board tboard) (filterv #(every? set-picks %)) util/not-empty?)))

(defn score-board
  [picks [board]]
  (let [set-picks (set picks)
        set-board (set (reduce concat board))]
    (->> (clojure.set/difference set-board set-picks)
         (apply +)
         (* (first picks)))))

(defn parse [file]
  (let [input  (util/read-file "04" file)
        [picks & rest] (str/split input #"\n\n")
        picks (->> picks (re-seq #"\d+") (map read-string))
        boards (mapv #(->> % (re-seq #"\d+") (map read-string) (partition 5) (map vec)) rest)
        tboards (map util/transpose boards)]
    [picks (mapv vector boards tboards)]))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [[picks boards] (parse file)]
            (reduce (fn [picks next]
                      (let [picks (conj picks next)
                            winning-boards (filter #(check-board picks %) boards)]
                        (if (empty? winning-boards) picks  (reduced (score-board picks (first winning-boards))))))
                    (take 4 picks) (drop 4 picks)))))

(defn p2
  ([] (p2 "input"))
  ([file] (let [[picks boards] (parse file)]
            (reduce (fn [[picks boards] next]
                      (let [picks (conj picks next)
                      remaining (filter #(not (check-board picks %)) boards)]
                       (if (empty? remaining) (reduced (score-board picks (first boards))) [picks remaining])))
                    [(take 4 picks) boards] (drop 4 picks)))))