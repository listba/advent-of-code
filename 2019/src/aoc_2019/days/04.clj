(ns aoc-2019.days.04)

(defn inc? [pass]
  (->> pass
    (partition 2 1)
    (every? (fn [[a b]] (<= (int a) (int b))))))

(defn p1
  ([] (p1 172851 675870))
  ([s f] 
    (->> (range s f)
      (map str)
      (filter inc?)
      (filter #(re-seq #"(\d)\1" %))
      count
    )))

(defn p2
  ([] (p2 172851 675870))
  ([s f] 
    (->> (range s f)
      (map str)
      (filter inc?)
      (filter #(re-seq #"(\d)\1" (clojure.string/replace % #"(\d)\1\1+" "")))
      count
    )))