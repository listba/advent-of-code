(ns aoc-2021.days.15 
  (:require [aoc-2021.util :as util]
            [clojure.data.priority-map :refer [priority-map]]))

(def fetch-neighbors (memoize util/fetch-neighbors))

(defn not-visited [risk-map pos path]
  (->> risk-map 
       (fetch-neighbors false pos)
       (filter (fn [[_ n]] (not (contains? path n))))))
(def not-visited-mem (memoize not-visited))

(defn dijkstra
  ([risk-map edgefn] (dijkstra risk-map edgefn [0 0] [(-> risk-map count dec) (-> risk-map first count dec)]))
  ([risk-map edgefn from to] (dijkstra risk-map edgefn to (priority-map from 0) #{}))
  ([risk-map edgefn target pq visited]
   (let [[parent score] (peek pq)]
     (cond
     (= target parent) score
     (nil? parent)  ##Inf
     :else (recur risk-map edgefn target
            (reduce (fn [pq [cost edge]] 
                      (update pq edge (fnil min ##Inf) (+ cost score))) 
                    (pop pq) (edgefn parent visited))
            (conj visited parent))
     ))))

(defn dijkstra-p1 [risk-map] (dijkstra risk-map (partial not-visited-mem risk-map)))
(defn p1 
  ([] (p1 "input"))
  ([file] (->> file
               (util/parse-digits "15")
               dijkstra-p1)))

; my mx 10
; 11 11 
; (qout 11 10) 1
; ry rx 1 1
; ny nx 1 2
; dy dx 1 1
; ny nx (10 * 1) + 1 (10 * 1)+ 2 = [11 12] 

(def fetch-neighbors-wrap (memoize util/fetch-neighbors-wrap))

(defn not-visited-p2 [risk-map [my mx] [y x] path]
  (let [[ry rx] [(mod y my) (mod x my)]    ; adjust y and x based on mod of true size
        [dy dx] [(quot y my) (quot x mx)]] ; this is the difference so that we can compute v and ny nx relative to origial coords
    (->> risk-map
         ;(util/ps (str "for " y":"x))
         ;(util/ps (str "ry:rx " ry ":" rx " dy:dx " dy ":" dx))
         (fetch-neighbors-wrap false [ry rx] [my mx])
         (keep (fn [[v [ny nx]]]
                 (let [;_ (println "-----------------")
                       ;_ (println (str "Neighbor " ny ":" nx))
                       ;_ (println (str "dy:dx " dy ":" dx))
                       ;_ (println (str (-> ry (- ny) util/abs (> 1)) ":"(-> rx (- nx) util/abs (> 1))))
                       dy (if (-> ry (- ny) util/abs (> 1)) (inc dy) dy)
                       dx (if (-> rx (- nx) util/abs (> 1)) (inc dx) dx)
                       ;dy (if (>= ry ny) dy (inc dy)) ; if ny is less than ry then we are in the next quadrant
                       ;dx (if (>= rx nx) dx (inc dx)) ; if nx is less than rx then we are in the next quadrant
                       v  (-> v (+ dy) (+ dx) (#(if (< % 10) % (inc (mod % 10))))) ; compute the value based on the delta x / y from the og coords and if its 10 we wrap around to 0
                       ny (+ (* my dy) ny)
                       nx (+ (* mx dx) nx)
                       n [ny nx]
                       ;_ (println (str "ny:nx " ny ":" nx " dy:dx " dy ":" dx))
                       ]
                   (if (contains? path n) nil [v n])))))))
(def not-visited-p2-mem (memoize not-visited-p2))

(defn dijkstra-p2 [risk-map] 
  (let [my (count risk-map)
        mx (-> risk-map first count)
        gy (-> my (* 5) dec)
        gx (-> mx (* 5) dec)
        edgefn (partial not-visited-p2-mem risk-map [my mx])
        _ (println (str "We are going to " gy ":" gx " on a board that is actually " my ":" mx))
        _ (mapv println (edgefn [1 5] #{}))
        ]
    (dijkstra risk-map edgefn [0 0] [gy gx])))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
               (util/parse-digits "15")
               dijkstra-p2)))

