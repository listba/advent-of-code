(ns aoc-2023.days.01
  (:require [aoc-2023.util :as util]
            [clojure.string :as str]))


;; scans a line left to right to extract any numbers
;; digits are extracted directly
;; sequences that spell out a single digit in english (excluding zero) 
;; should be extracted as their numerical representation
;; order relative to the other numbers should be maintained
;; eg 1eightwo7 should = 1827 (note eight and two share a t)
(defn extract-numbers
  ([input]
   (first (reduce
           (fn [[result prefix] cur]
             (let [prefix (str prefix cur)
                   next (cond
                          (util/charNum? cur)             cur
                          (str/ends-with? prefix "one")   "1"
                          (str/ends-with? prefix "two")   "2"
                          (str/ends-with? prefix "three") "3"
                          (str/ends-with? prefix "four")  "4"
                          (str/ends-with? prefix "five")  "5"
                          (str/ends-with? prefix "six")   "6"
                          (str/ends-with? prefix "seven") "7"
                          (str/ends-with? prefix "eight") "8"
                          (str/ends-with? prefix "nine")  "9"
                          :else                           "")]
               [(str result next) prefix])) 
           ["" ""] input))))

; calibration value is defined as a two digit number made from the first and last digit in a string of numbers
; eg "1234" = 14
;    "7"    = 77
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



