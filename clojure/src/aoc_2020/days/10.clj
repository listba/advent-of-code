(ns aoc-2020.days.10)

(defn parse [file]
  (->> (str "../resources/day-10/" file ".txt")
       slurp
       (re-seq #"\d+")
       (map read-string)
       sort))

(defn p1
  ([] (p1 "input"))
  ([file] 
   (->> (parse file)
        (reduce (fn [[p _1 _2 _3] c]
                  (let [diff (- c p)]
                    (cond
                      (= 1 diff) [c (inc _1) _2 _3]
                      (= 2 diff) [c _1 (inc _2) _3]
                      (= 3 diff) [c _1 _2 (inc _3)]))) [0 0 0 0])
        ((fn [[_ _1 _ _3]] (* _1 (inc _3)))))))

(def count-configs-mem 
  (memoize (fn [[x & xs]]
             (if (nil? xs) 1
                 (->> xs
                      (take-while #(-> % (- x) (<= 3)))
                      (map-indexed (fn [i _] i))
                      (reduce (fn [r i] (+ r (trampoline count-configs-mem (drop i xs)))) 0))))))

(defn p2
  ([] (p2 "input"))
  ([file] 
   (-> (parse file)
       (conj 0)
       (count-configs-mem))))