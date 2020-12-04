(ns aoc-2020.days.04
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

;; parses each passport
;; I feel like there is a better way to parse this
(defn parse [input]
  (as-> input $
    (str "../resources/" $)
    (slurp $)
    (str/split $ #"[\n]{2}")
    (map (fn [ppStr] 
           (as-> ppStr $$
                (str/split $$ #"[\n\s]")
                (map #(str/split % #":") $$)
                (reduce (fn [p [k v]] (assoc p (keyword k) v))  {} $$))) $)))

(defn validate-passport-simple
  [pp] (match [pp]
         [{:ecl _ :pid _ :eyr _ :hcl _ :byr _ :iyr _ :cid _ :hgt _}] true
         [{:ecl _ :pid _ :eyr _ :hcl _ :byr _ :iyr _ :hgt _}] true
         :else false))

(defn validate-year [min max yr] (as-> yr $
                                      (re-matches #"[0-9]+" $)
                                      (if $ (as-> $ $$ (read-string $$) (<= min $$ max)))))
(defn validate-height [hgt] (let [res (re-matches #"([0-9]+)(cm|in)" hgt)
                                  _ (println (str "hgt " res))]
                              (match [res]
                                [[_ a "cm"]] (<= 150 (read-string a) 193)
                                [[_ a "in"]] (<= 59 (read-string a) 76)
                                :else false)))

(defn validate-passport-fields
  [ {ecl :ecl 
     pid :pid 
     eyr :eyr 
     hcl :hcl 
     byr :byr 
     iyr :iyr 
     hgt :hgt}] (and 
                 (validate-year 1920 2002 byr)
                 (validate-year 2010 2020 iyr)
                 (validate-year 2020 2030 eyr)
                 (re-matches #"#[0-9a-f]{6}" hcl)
                 (re-matches #"(amb|blu|brn|gry|grn|hzl|oth)" ecl)
                 (re-matches #"[0-9]{9}" pid)
                 (validate-height hgt)))

(defn p1
  []
  (->> "day-04/input.txt"
       parse
       (filter validate-passport-simple)
       (count)))

(defn p2
  []
  (->> "day-04/input.txt"
       parse
       (filter validate-passport-simple)
       (filter validate-passport-fields)
       (count)))