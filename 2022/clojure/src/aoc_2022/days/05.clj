(ns aoc-2022.days.05 
  (:require [aoc-2022.util :as util]))

(defn add-crate [crate-map [col crate]] 
  ;(print (str col ":" crate))
  (update crate-map col #(conj % crate)))

(defn add-crate-old [[col crate] crate-map] 
  ;(print (str col ":" crate))
  (update crate-map col #(conj % crate)))

(def index-crates (comp
                   (keep-indexed (fn [idx c]
                                   (println (str idx ":" c))
                                   (if (and (<= (int \A) (int c)) (>= (int \Z) (int c))) 
                                     [(-> idx (quot 4) (+ 1)) c] nil)))
                   ;(map print)
                   ))

(defn index-crates-old [rows]
  (keep-indexed (fn [idx c]
                  (if (and (<= (int \A) (int c)) (>= (int \Z) (int c))) 
                    [(-> idx (quot 4) (+ 1)) c] nil)) rows))

(defn process-row [row crate-map]
  (->> row index-crates-old (reduce (fn [crate-map crate] (add-crate-old crate crate-map)) crate-map)))

(def init-map (comp (map read-string) (map (fn [i] {i []} ))))

(defn generate-crate-map-new [[axis & crates]] 
  (->> (re-seq #"\d+" axis)
       (transduce init-map conj {})
       (util/ps crates)
       (#(transduce (comp (map index-crates-old) (map println)) add-crate % crates))))

  
(defn generate-crate-map-old [[axis & crates]]
  (let [initial-map (transduce init-map conj {} (re-seq #"\d+" axis))]
    (reduce (fn [crate-map row] (process-row row crate-map)) initial-map crates)))
;; (defn generate-crate-map [[axis & crates]]
;;   (let [initial-map (->> axis (re-seq #"\d+") (map read-string) (reduce (fn [b i] (conj b (sorted-map i []))) (sorted-map)))]
;;     (reduce (fn [crate-map row] (process-row row crate-map)) initial-map crates)))

(defn parse [file]
  (->> file
       (util/split-blank-lines "05")
       (map (partial re-seq #"[^\n]+"))
       (#(let [[crate-chart raw-moves] %
               crate-map (generate-crate-map-old (reverse crate-chart))
               ;_ (println crate-map)
               moves (map (fn [move] ( ->> move (re-seq #"\d+") (map read-string))) raw-moves)] 
           [crate-map moves]))))

(defn pull-crates [from [crate-map crates]]
  (reduce (fn [[crate-map crates] crate-idx]
            [(update crate-map crate-idx pop) 
             (->> [crate-idx] (get-in crate-map) last (conj crates))]) 
          [crate-map []] (repeat crates from)))

(defn push-crates [multi-crate? to [crate-map crates]]
  (reduce (fn [crate-map crate]
            (update crate-map to #(conj % crate))) crate-map (if multi-crate? (reverse crates) crates)))

(defn move-crates [multi-crate? crate-map moves]
  (reduce (fn [crate-map [crates from to]]
            (->> [crate-map crates]
                 (pull-crates from)
                 (push-crates multi-crate? to))) crate-map moves))

(defn p1 
  ([] (p1 "input"))
  ([file] (let [[crate-map moves] (parse file)]
            (->> (move-crates false crate-map moves)
                 (vals)
                 (map last)
                 (apply str)))))

(defn p2 
  ([] (p2 "input"))
  ([file] (let [[crate-map moves] (parse file)]
            (->> (move-crates true crate-map moves)
                 (vals)
                 (map last)
                 (apply str)))))

(str "p1: " (p1) " p2: " (p2))