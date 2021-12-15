(ns aoc-2021.days.14 
  (:require [aoc-2021.util :as util]))

(defn parse [file]
  (let [[template rules] (util/split-blank-lines "14" file)
        final (last template)
        template (->> template
                      (partition 2 1)
                      (mapv vec)
                      (reduce (fn [pairs [a b]] (merge-with + pairs {(str a b) 1})) {}))
        rules (->> rules
                   (re-seq #"\w+")
                   (partition 2)
                   (mapv (fn [[[a b] c]]
                           [(str a b)
                            (merge-with +                               ; we merge since ab could = ac or cb
                                        {(str a b) -1}                  ; subtract ab since its getting split
                                        {(str a c) 1 (str c b) 1})])))] ; add ac and ab
    [template rules final]))

(defn apply-rules [template rules] 
  (->> rules
       (reduce (fn [changes [rule deltas]] 
                 (let [v (get template rule 0)] 
                   (reduce-kv (fn [changes k dv]
                                (merge-with + changes {k (* v dv)}))
                              changes deltas))) {})
       (merge-with + template)
       (into {} (filter #(-> % val (> 0))))))

(defn calc-value [final template] 
  (->> template
       (reduce-kv (fn [chars [a _] v] (merge-with + chars {a v})) {final 1})
       vals
       util/min-max
       reverse
       (apply -)))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [[template rules final] (parse file)]
            (->> (range 10)
                 (reduce (fn [template _] (apply-rules template rules)) template)
                 (calc-value final)))))

(defn p2 
  ([] (p2 "input"))
  ([file] (let [[template rules final] (parse file)]
            (->> (range 40)
                 (reduce (fn [template _] (apply-rules template rules)) template)
                 (calc-value final)))))