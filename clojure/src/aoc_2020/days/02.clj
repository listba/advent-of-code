(ns aoc-2020.days.02
  (:require [aoc-2020.util :as util]))

; (re-find #"(\S+):(\d+)" line)
; 8-11 x: mdpvkqsxvzxgtvb

(defn parse [input]
  (->> input
       (str "../resources/")
       slurp
       (re-seq #"[^\n]+")
       ; parse out to min max char password
       (map #(re-find #"(\d+)-(\d+) ([a-zA-Z]): ([a-zA-Z]+)" %))))

(defn p1
  []
  (->> "day-02/input.txt"
       parse
       (filter (fn [[_ minStr maxStr c p]] 
              (let [pMin    (read-string minStr)
                    pMax    (read-string maxStr)
                    cCount  (count (re-seq (re-pattern c) p))]
                (<= pMin cCount pMax))))
      count))

(defn p2
  []
  (->> "day-02/input.txt"
       parse
       (filter (fn [[_ _1 _2 cStr p]]
                 (let [fst  (get p (- (read-string _1) 1))
                       snd  (get p (- (read-string _2) 1))
                       c    (first (char-array cStr))]
                   (or 
                    (and (= fst c) (not= snd c)) 
                    (and (not= fst c) (= snd c))))))
       count))