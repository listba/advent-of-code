(ns aoc-2023.days.01
  (:require [aoc-2023.util :as util]
            [clojure.string :as str]))

(defn extract-numbers
  ([input]
   (first (reduce
           (fn [[result pref] cur]
             (let [pref (str pref cur)]
               (cond
                 (util/charNum? cur) [(str result cur) ref]
                 (str/ends-with? pref "one") [(str result "1") pref]
                 (str/ends-with? pref "two") [(str result "2") pref]
                 (str/ends-with? pref "three") [(str result "3") pref]
                 (str/ends-with? pref "four") [(str result "4") pref]
                 (str/ends-with? pref "five") [(str result "5") pref]
                 (str/ends-with? pref "six") [(str result "6") pref]
                 (str/ends-with? pref "seven") [(str result "7") pref]
                 (str/ends-with? pref "eight") [(str result "8") pref]
                 (str/ends-with? pref "nine") [(str result "9") pref]
                 :else [result pref]))) 
           ["" ""] input))))

(defn get-calibration-value 
  ([line] (read-string (str (first line) (last line)))))

(defn p1
  ([] (p1 "input"))
  ([file] (let [lines (util/parse-lines "01" file)]
            (->> lines
                 (map #(re-seq #"\d" %))
                 (map get-calibration-value)
                 (apply +)))))

(defn p2
  ([] (p2 "input"))
  ([file] (let [lines (util/parse-lines "01" file)]
            (->> lines
                 (map extract-numbers)
                 (map get-calibration-value)
                 (apply +)
                 ))))

(pr-str "p1: " (p1 "sample"))
(pr-str "p2: " (p2 "sample2"))

(pr-str "p1: " (p1 "input"))
(pr-str "p2: " (p2 "input"))



