(ns aoc-2020.days.04
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn parse [input]
  (->> input 
       (str "../resources/") 
       slurp 
       ((fn [str] (for [doc (str/split str #"[\n]{2}")]   ;; blank lines split up passports
                    (->> (str/split doc #"[\n\s]")        ;; single newline or space splits up passport fields
                         (map #(str/split % #":"))        ;; fields are k/v seperated by a :
                         (reduce (fn [p [k v]] (assoc p (keyword k) v)) {}))))))) ;; keywordize each field name and collapse each passport into a map

(defn validate-passport-present
  [pp] (match [pp]
         [{:ecl _ :pid _ :eyr _ :hcl _ :byr _ :iyr _ :cid _ :hgt _}] true
         [{:ecl _ :pid _ :eyr _ :hcl _ :byr _ :iyr _ :hgt _}] true
         :else false))


(defn validate-year [min max yr]
  (some->>  (re-matches #"[\d]+" yr)
            (read-string)
            (#(<= min % max))))

(defn validate-height [hgt] 
  (let [res (re-matches #"([\d]+)(cm|in)" hgt)]
    (match [res]
      [[_ a "cm"]] (<= 150 (read-string a) 193)
      [[_ a "in"]] (<= 59 (read-string a) 76)
      :else false)))

(defn validate-passport-fields
  [{:keys [ecl pid eyr hcl byr iyr hgt]}]
  (and
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
       (filter validate-passport-present)
       (count)))

(defn p2
  []
  (->> "day-04/input.txt"
       parse
       (filter validate-passport-present)
       (filter validate-passport-fields)
       (count)))