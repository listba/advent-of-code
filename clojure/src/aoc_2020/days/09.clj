(ns aoc-2020.days.09)

(defn parse [file]
(->> (str "../resources/day-09/" file ".txt")
        slurp
     (re-seq #"[\d]+")
     (mapv read-string)))

(defn first-invalid [preamble nums]
  (->> (drop preamble nums)
       (map-indexed (fn [i n]
                      (let [sums (for [x (->> nums (drop i) (take preamble))
                                       y (->> nums (drop i) (take preamble))
                                       :when (and (not= x y) (= n (+ x y)))] [x y])]
                        [i n (first sums)])))
       (filter (fn [[_ _ r]] (nil? r)))
       first))

(defn p1 
  ([] (p1 "input" "25"))
  ([file preamble] 
   (let [preamble (read-string preamble)
         nums (parse file)]
     (first-invalid preamble nums))))

(defn p2 
  ([] (p2 "input" "25"))
  ([file preamble] 
   (let [preamble (read-string preamble)
         nums (parse file)
         [_ invalid _] (first-invalid preamble nums)
         _ (println (str "Invalid number is: " invalid))] 
     (->> nums
          (keep-indexed (fn [i n]
                         (let [[s j r] (reduce (fn [[s j _] x]
                                                 (cond
                                                   (< s invalid) [(+ s x) (inc j) false]
                                                   (= x invalid) (reduced [0 0 false])
                                                   (= s invalid) (reduced [s j true])
                                                   :else [s j false])) [0 0 false] (drop i nums))
                               rng (->> nums (drop i) (take j))]
                           (if (= r true ) rng nil))))
          first
          ((fn [rng] (+ (apply min rng) (apply max rng))))))))