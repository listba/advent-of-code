(ns aoc-2020.days.05)

(defn midpoint-up [x y] (int (Math/ceil (/ (+ x y) 2))))
(defn midpoint-down [x y] (quot (+ x y) 2))

; (println (str "r: " r " rs: " rs " " min "-" max))
(defn find-pos
  [[r & rs] min max]
  (cond
    (nil? rs) (if (or (= \F r) (= \L r)) min max)
    (or (= \F r) (= \L r)) (find-pos rs min (midpoint-down min max))
    (or (= \B r) (= \R r)) (find-pos rs (midpoint-up min max) max)))

(defn parse [input]
  (->> input
       (str "../resources/")
       slurp
       (re-seq #"[^\n]+")
       (map #(re-matches #"([FB]+)([LR]+)" %))
       (map (fn [[_ rows cols]] (let [row (find-pos (char-array rows) 0 127)
                                      col (find-pos (char-array cols) 0 7)]
                                  (-> row (* 8) (+ col)))))))

(defn p1
  []
  (->> (parse "day-05/input.txt")  (apply max)))

(defn p2
  []
  (->> (parse "day-05/input.txt") 
       sort
       (#(reduce (fn [[seat prev] cur] (if (= 2 (- cur prev)) [(- cur 1) cur] [seat cur])) [nil (first %)] %))
       first))