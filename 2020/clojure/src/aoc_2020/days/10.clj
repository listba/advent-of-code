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
                  (case (- c p)
                    1 [c (inc _1) _2 _3]
                    2 [c _1 (inc _2) _3]
                    3 [c _1 _2 (inc _3)])) 
                [0 0 0 1]) ;; initilize the _3 count to account for your special adapter of max+3
        ((fn [[_ _1 _ _3]] (* _1 _3))))))

(def count-configs-recur 
  (memoize (fn [[x & xs]]
             (if (nil? xs) 1
                 (->> xs
                      (take-while #(-> % (- x) (<= 3)))
                      (map-indexed (fn [i _] i))
                      (reduce (fn [r i] (+ r (trampoline count-configs-recur (drop i xs)))) 0))))))

(defn count-configs-linear 
  [nums]
  (-> (reduce (fn [m c]
                 (assoc m c (+ (get m (- c 1) 0)
                               (get m (- c 2) 0)
                               (get m (- c 3) 0)))) 
               {0 1} nums)   ;; initialize map to include base case of 0
       (get (last nums)))) 

(defn p2
  ([] (p2 "input"))
  ([file] 
   (-> (parse file)
       (count-configs-linear))))