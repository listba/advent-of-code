(ns aoc-2021.days.12 
  (:require [aoc-2021.util :as util]))

(defn add-connection [cave-paths s e]
  (cond
    (= e "start") cave-paths ; don't add connection if the end is "start"
    (= s "end")   cave-paths ; don't add connection if the start is "end"
    :else         (update cave-paths s (fnil #(conj % e) []))))

(defn build-cave-data [cave-paths] 
  (let [caves (filter (partial not= "start") (keys cave-paths))
        small (->> caves (filter (partial every? #(Character/isLowerCase %))) set)
        large (->> caves (filter (partial every? #(Character/isUpperCase %))) set)]
    { :paths cave-paths :caves caves :small small :large large }))

(defn parse [file] 
  (->> file 
       (util/parse-lines "12")
       (mapv (partial re-seq #"\w+"))
       (reduce (fn [cave-paths [s e]]
                 (-> cave-paths
                     (add-connection s e)
                     (add-connection e s))) {})
       build-cave-data))

(defn get-connections [{:keys [paths small]} cave visited]
  (let [small-visited (->> visited (filter (partial contains? small)) set)]
    (->> cave
         (get paths)
         (filter #(not (contains? small-visited %))))))

(defn get-paths 
  ([cave-system] (trampoline get-paths cave-system "start" []))
  ([cave-system cave seen]
                 (let [conns (get-connections cave-system cave seen)
                       seen (conj seen cave)]
                   (cond
                     (= cave "end") [seen]
                     (empty? conns) []
                     :else #(reduce (fn [paths conn] (concat paths (trampoline get-paths cave-system conn seen))) [] conns)))))

(defn p1 
  ([] (p1 "input"))
  ([file] (-> file
              parse
              get-paths
              count)))

(defn p2 
  ([] (p2 "input"))
  ([file] ))