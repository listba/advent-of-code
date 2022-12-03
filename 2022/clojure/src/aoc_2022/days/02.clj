(ns aoc-2022.days.02 
  (:require [aoc-2022.util :as util]))

; A Rock ; B Paper ; C Scissors
; X Rock ; Y Paper ; Z Scissors
(def match-score-p1 {"A" {"X" 4 "Y" 8 "Z" 3}
                     "B" {"X" 1 "Y" 5 "Z" 9}
                     "C" {"X" 7 "Y" 2 "Z" 6}})

; A:Rock  X->Scissors (L) 0+3; Y->Rock (D) 3+1; Z->Paper (W) 6+2
; B:Paper X->Rock (L) 0+1; Y->Paper (D) 3+2; Z->Scissors (W) 6+1
; C:Scissors X->Paper (L) 0+2; Y->Scissors (D) 3+3; Z->Rock (W) 6+1
(def match-score-p2 {"A" { "X" 3 "Y" 4 "Z" 8}   
                     "B" { "X" 1  "Y" 5 "Z" 9}
                     "C" { "X" 2 "Y" 6 "Z" 7}})

(defn parse [file] (->> file
                        (util/read-file "02")
                        (re-seq #"\w")
                        (partition 2)))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file
           parse
           (map (fn [[op me]] ((match-score-p1 op) me)))
           (apply +))))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
           parse
           (map (fn [[op me]] ((match-score-p2 op) me)))
           (apply +))))

(str "p1: " (p1) " p2: " (p2))