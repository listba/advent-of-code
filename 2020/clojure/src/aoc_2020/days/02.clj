(ns aoc-2020.days.02)

(defn parse [input]
  (->> input
       (str "../resources/")
       slurp
       (re-seq #"(\d+)-(\d+) ([a-zA-Z]): ([a-zA-Z]+)")))

(defn p1
  []
  (->> "day-02/input.txt"
       parse
       (filter (fn [[_ min-str max-str c p]] 
              (let [_min    (read-string min-str)
                    _max    (read-string max-str)
                    cCount  (count (re-seq (re-pattern c) p))]
                (<= _min cCount _max))))
      count))

(defn p2
  []
  (->> "day-02/input.txt"
       parse
       (filter (fn [[_ _1 _2 cStr p]]
                 (let [fst  (get p (- (read-string _1) 1))
                       snd  (get p (- (read-string _2) 1))
                       c    (first (char-array cStr))]
                   (and 
                    (or (= fst c) (= snd c)) 
                    (not= fst snd)))))
       count))