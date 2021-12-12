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

(defn get-connections-single [{:keys [paths small]} cave visited]
  (let [small-visited (->> visited (filter (partial contains? small)) set)]
    (->> cave
         (get paths)
         (filter #(not (contains? small-visited %))))))

(defn get-paths 
  ([cave-system conn-search] (trampoline get-paths cave-system conn-search "start" []))
  ([cave-system conn-search cave seen]
                 (let [seen (conj seen cave)
                       conns (conn-search cave-system cave seen)]
                   (cond
                     (= cave "end") [seen]
                     (empty? conns) []
                     :else #(reduce (fn [paths conn] (concat paths (trampoline get-paths cave-system conn-search conn seen))) [] conns)))))

(defn p1 
  ([] (p1 "input"))
  ([file] (-> file
              parse
              (get-paths get-connections-single)
              count)))

(defn get-connections-double [{:keys [paths small]} cave visited]
  (let [small-freq     (->> visited (filter (partial contains? small)) frequencies)
        max-small-freq (reduce max 0 (vals small-freq))
        small-visited  (if (= 2 max-small-freq) (-> small-freq keys set) #{})]
    (->> cave
         (get paths)
         (filter #(not (contains? small-visited %))))))

(defn p2 
  ([] (p2 "input"))
  ([file] (-> file
              parse
              (get-paths get-connections-double)
              count)))