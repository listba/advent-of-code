(ns aoc-2021.days.10 
  (:require [aoc-2021.util :as util]))

(defn parse [file] (->> file (util/parse-lines "10") (map seq)))
(def brace-match { \[ \] \( \) \{ \} \< \> })
(def brace-error-value { \) 3 \] 57 \} 1197 \> 25137 })
(defn score-errors [line]
 (conj (reduce
        (fn [[score parsed] cur]
          (cond
            (contains? brace-match cur) [score (conj parsed cur)]          ; if its an opening brace prepend on to list of parsed
            (= cur (get brace-match (first parsed))) [score (rest parsed)] ; if prev has a matching closing brace then pop it off the list and continue
            :else (reduced [(+ score (get brace-error-value cur))]))                ; mismatched brace add score and stop
          )[0 `()] line) line))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file parse (map score-errors) (map first) (reduce +))))

(def brace-value { \) 1 \] 2 \} 3 \> 4 })
(defn fix-line
  ([line] (fix-line line [] `()))
  ([[cur & line] fixes parsed]
    (cond
      (and (nil? cur) (empty? parsed))  fixes
      (contains? brace-match cur) (fix-line line fixes (conj parsed cur))          ; if its an opening brace prepend on to list of parsed
      (= cur (get brace-match (first parsed))) (fix-line line fixes (rest parsed)) ; if prev has a matching closing brace then pop it off the list and continue
      :else  (fix-line line (conj fixes (get brace-match (first parsed))) (rest parsed)))                ; mismatched brace add fix and continue
    ))

(defn score-fixes [fixes] (reduce  (fn [score fix] (+ (* 5 score) (get brace-value fix))) 0 fixes))

(defn mid-score [scores] (-> scores count (quot 2) (drop scores) first))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file 
               parse
               (map score-errors)
               (keep (fn [[error-score _ line]] (if (= 0 error-score) line)))
               (mapv fix-line)
               (mapv score-fixes)
               sort
               mid-score)))