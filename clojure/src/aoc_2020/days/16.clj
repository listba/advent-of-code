(ns aoc-2020.days.16
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-rule [rule] 
  (->> rule 
       (re-seq #"([\w\s]+): (\d+)-(\d+) or (\d+)-(\d+)")
       (map (fn [[_ name min1 max1 min2 max2]] [name (read-string min1) (read-string max1) (read-string min2) (read-string max2)]))
       first))

(defn parse-ticket [ticket] 
  (->> ticket (re-seq #"\d+") (map read-string)))

(defn parse [file]
  (->> (str "../resources/day-16/" file ".txt")
       slurp
       (#(str/split % #"[\n]{2}"))
       (map (partial re-seq #"[^\n]+"))
       ((fn [[rules,[_ mine],[_ & others]]]
          [(map parse-rule rules) (parse-ticket mine) (map parse-ticket others)]))))

(defn find-invalid [rules ticket]
  (filter (fn [v] (reduce (fn [invalid [_ min1 max1 min2 max2]]
                            (and invalid (not (<= min1 v max1)) (not (<= min2 v max2)))) true rules)) ticket))
(defn p1
  ([] (p1 "input"))
  ([file] (let [[rules,mine,others] (parse file)]
            (->> others
                 (keep (partial find-invalid rules))
                 (reduce concat)
                 (reduce +)))))

(defn match-rules [rules ticket]
  (map (fn [v] (filter (fn [[name min1 max1 min2 max2]] (or (<= min1 v max1) (<= min2 v max2))) rules)) ticket))

(defn find-distinct [[first-ticket & ticket-rules]] 
  (let [len (count first-ticket)]
    (map (fn [i] (reduce (fn [s ticket] (set/intersection s (into #{} (nth ticket i)))) (into #{} (nth first-ticket i)) ticket-rules)) (range 0 len))))

(defn elim [trs]
  (if (>= 1 (count trs)) trs
      (let [{ones true more false} (group-by (fn [[i tr]] (= 1 (count tr))) trs)
            left (map (fn [[i tr]]
                        [i (reduce (fn [s [_ o]]
                                     (set/difference s o)) tr ones)]) more)]
        (concat ones (trampoline elim left)))))

(defn p2
  ([] (p2 "input"))
  ([file] (let [[rules,mine,others] (parse file)]
            (->> others
                 (filter (fn [ticket] (let [result (find-invalid rules ticket)] (empty? result))))
                 (mapv (partial match-rules rules))
                 (find-distinct)
                 (map-indexed (fn [i r] [i r]))
                 (elim)
                 (map (fn [[i s]] [i (first s)]))
                 (filter (fn [[_ [s]]] (str/starts-with? s "departure")))
                 (map (fn [[i]] (nth mine i)))
                 (reduce *)))))