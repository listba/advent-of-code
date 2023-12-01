(ns aoc-2022.days.12 
  (:require [aoc-2022.util :as util]
            [clojure.data.priority-map :refer [priority-map]]))


(defn dijkstra
  ([grid edgefn] (dijkstra grid edgefn [0 0] [(-> grid count dec) (-> grid first count dec)]))
  ([grid edgefn from to] (dijkstra grid edgefn to (priority-map from 0) #{} []))
  ([grid edgefn target pq visited path]
   (let [[parent score] (peek pq)]
     (cond
     (= target parent) [score (conj path parent)]
     (nil? parent)  ##Inf
     :else (recur grid edgefn target
            (reduce (fn [pq [cost edge]] 
                      (update pq edge (fnil min ##Inf) (+ cost score))) 
                    (pop pq) (edgefn parent visited))
            (conj visited parent)
                  (conj path parent))
     ))))

(def fetch-neighbors (memoize util/fetch-neighbors))

(defn within-1 [c n] (<= 1 (util/abs (- (int c) (int n)))))
;; (defn find-edges [grid pos path]
;;   (->> grid 
;;        (fetch-neighbors false pos)
;;        (filter (fn [[_ n]] (not (contains? path n))))))

(defn find-edges [grid pos path]
  (let [;_ (println grid)
        ;_ (println pos)
        ;_ (println path)
        neighbors (fetch-neighbors false pos grid)
        c-elv (get-in grid pos)
        _ (println (str pos": " (vec neighbors)))
        avail (keep (fn [[n-elv n]]
            (do
              (println (str "checking " n))
              (println (str "contains? " (contains? path n)))
              (cond
                (contains? path n)               nil
                (within-1 c-elv n-elv)           [1 n]
                (and (= c-elv \S) (= n-elv \a))  [1 n]
                (and (= c-elv \S) (= n-elv \b))  [1 n]
                (and (= c-elv \z) (= n-elv \E))  [1 n]
                (and (= c-elv \y) (= n-elv \E))  [1 n]
                :else                            nil))
            ) neighbors)
        _  (println (str "path: " path))
         _ (println (str pos": " (vec neighbors)))
        _ (println "----------------------")]
    avail))
(int \s)
(def grid [[\S \a \b]
           [\d \c \c]
           [\e \E \f]])

(comment 
  (find-edges grid [0 0] #()))

(def find-edges-mem (memoize find-edges))

(comment 
  (let [grid (->> "sample" (util/parse-grid "12"))
        start (->> grid (util/search-in #(= \S %)) first (take 2) vec)
        end (->> grid (util/search-in #(= \E %)) first (take 2) vec)
        [score path] (dijkstra grid (partial find-edges-mem grid) start end)
        _ (println path)]
    (count path)))


(defn p1 
  ([] (p1 "input"))
  ([file] ))

(defn p2 
  ([] (p2 "input"))
  ([file] ))


(str "p1: " (p1) " p2: " (p2))