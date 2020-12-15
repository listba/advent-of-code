(ns aoc-2020.days.15)

(defn parse [input]
  (->> input (re-seq #"\d+") (map-indexed (fn [i x] [(read-string x) i]))))

(defn game
  ([input turns] (let [nums        (parse input)
                 spoken      (reduce (fn [m [n i]] (assoc m n  (list (inc i)))) {} nums)
                 last-spoken (first (last nums))
                 rng       (range (inc (count nums)) (inc turns))
                 result (reduce (fn [[spoken last] turn] 
                                  (let [[x y] (get spoken last)]
                                    (if (nil? y) [(update spoken 0 #(conj % turn)) 0]
                                        (let [speak (- x y)
                                              _ (if (= 0 (mod turn (/ turns 10))) (println (str "turn " turn " last " last " x,y" [x y])))]
                                          [(update spoken speak #(conj % turn)) speak])))) [spoken last-spoken] rng)] 
                   (second result))))

(defn p1
  ([] (p1 "2,20,0,4,1,17"))
  ([input] (game input 2020)))

(defn p2
  ([] (p2 "2,20,0,4,1,17"))
  ([input] (game input 30000000)))