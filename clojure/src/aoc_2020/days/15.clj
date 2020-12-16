(ns aoc-2020.days.15)

(defn parse [input]
  (->> input (re-seq #"\d+") (map-indexed (fn [i x] [(read-string x) i]))))

(defn game
  ([input turns] (let [nums   (parse input)
                       spoken (reduce (fn [m [n i]] (assoc m n  (inc i))) {} nums)
                       rng    (range (inc (count nums)) turns)
                       result (reduce (fn [[spoken heard] turn]
                                        (let [x (get spoken heard)
                                              spoken (assoc spoken heard turn)]
                                          (if (nil? x) [spoken 0]
                                              (let [speak (- turn x)
                                                    _ (if (= 0 (mod turn (/ turns 10))) (println (str "turn " turn " heard " heard " x " x)))]
                                                [spoken speak])))) [spoken 0] rng)] 
                   (second result))))

(defn p1
  ([] (p1 "2,20,0,4,1,17"))
  ([input] (game input 2020))) ; 758

(defn p2
  ([] (p2 "2,20,0,4,1,17"))
  ([input] (game input 30000000))) ; 814