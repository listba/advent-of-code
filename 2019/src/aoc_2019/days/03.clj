(ns aoc-2019.days.03)

(defn parseInstruct
  [instruct]
  {:dir (subs instruct 0 1) 
   :len (Integer/parseInt (subs instruct 1))})

(defn mapRoute
  ([wire] (mapRoute {:x 0 :y 0 :z 0 :s 0} wire))
  ([{x :x y :y z :z, s :s} [instruct & tail]]
    (let [{dir :dir len :len} (parseInstruct instruct)
          cords 
          (->> 
            (range 1 (+ 1 len))
            (map #(let [_x (case dir 
                              "U" x 
                              "D" x 
                              "R" (+ x %) 
                              "L" (- x %))
                        _y (case dir 
                              "U" (+ y %) 
                              "D" (- y %) 
                              "R" y 
                              "L" y)
                        _z (+ (Math/abs _x) (Math/abs _y))
                        _s (+ s %)
                        k (keyword (str _x","_y))]
                    [k {:x _x :y _y :z _z :s _s}]))
              (reduce concat))
          end (last cords)]
        (if (= 0 (count tail))
          cords
          (concat cords (mapRoute end tail))))))

(defn parseInput
  [input]
  (->> input
    (str "resources/day-03/")
    slurp
    (re-seq #"[^\n]+")
    (map #(->> % (re-seq #"[^,\n]+") mapRoute (apply hash-map)))))

  
(defn p1 
  ([] (p1 "input.txt"))
  ([input]
    (let [[w1 w2] (parseInput input)]
      (->> 
        (for [k (keys w1) :when (and (contains? w2 k) (< 0 (:z (k w2))))] (:z (k w2)))
        (apply min) 
        (str "result ")))))

  (defn p2 
    ([] (p2 "input.txt"))
    ([input]
      (let [[w1 w2] (parseInput input)]
        (->> 
          (for [k (keys w1) :when (and (contains? w2 k) (< 0 (:s (k w2))))] (+ (:s (k w1)) (:s (k w2))))
          (apply min) 
          (str "result ")))))