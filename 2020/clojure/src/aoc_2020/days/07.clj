(ns aoc-2020.days.07
  (:require [clojure.string :as str]))

(defn parse [input]
  (->> (str "../resources/" input)
       slurp
       (re-seq #"[^\n\\.]+")
       (map (fn [rule] 
              (let [[k & v] (str/split rule #"( bags contain |, )")]
                [k (keep (comp next (partial re-find #"([\d]+) ([\w]+ [\w]+)")) v)])))
       (into {})))

(def bag-search 
  (memoize (fn [cur bag rules]
             (if (= bag cur) true
                 (->> (get rules cur)
                      (reduce (fn [r [_ next]] (or r (bag-search next bag rules))) false))))))

(defn p1 
  ([] (p1 "shiny gold"))
  ([bag] (let [rules (parse "day-07/input.txt")
               keys (->> rules keys (filter (partial not= bag)))
               res (map (fn [key] (bag-search key bag rules)) keys)]
           (count (filter (partial = true) res)))))

(defn bag-count [bag rules]
  (->> (get rules bag)
       (reduce (fn [r [c next]] 
                 (let [c (if (nil? c) 0 (read-string c))] 
                   (+ r c (* c (bag-count next rules))))) 0)))

(defn p2
  ([] (p2 "shiny gold"))
  ([bag] (->> (parse "day-07/input.txt") (bag-count bag))))