(ns aoc-2020.days.17)

(defn parse [file]
  (->> (str "../resources/day-00/" file ".txt")
       slurp))

(defn p1
  ([] (p1 "input"))
  ([file] (parse file)))

(defn p2
  ([] (p2 "input"))
  ([file] (parse file)))