(ns aoc-2019.days.07
  (:require [aoc-2019.intcode :as ic]
            [aoc-2019.util :as util]
            [clojure.core.async :refer [go chan mult tap >! <!! <! go-loop] :as async]))

(defn p1
  ([] (p1 "input.txt"))
  ([input]
   (let
    [_input (str "resources/day-07/" input)
     mem (ic/parseInput _input)]
     (->> 
      (range 0 5)
      util/permutations
      (map (fn [seq]
             (let
              [code (reduce
                     (fn [_2 _1]
                       (let [in (chan) out (chan)]
                         (go (>! in _1) (>! in _2))
                         (ic/Intcode mem in out)
                         (->> (<!! (async/into [] out))
                              reverse
                              (drop 1)
                              (first))))
                     0
                     seq)] {:result code :input seq})))
      (sort-by :result)
      last))))

(defn sequence-feedback-new
  [[a b c d e :as seq] mem]
  (let [buf 10 
        out-e (chan buf) 
        mult-e (mult out-e)
        in-a (tap mult-e (chan buf)) 
        result (tap mult-e (chan buf))
        in-b (chan buf) 
        in-c (chan buf)
        in-d (chan buf) 
        in-e (chan buf)]
    (ic/Intcode mem in-a in-b)
    (ic/Intcode mem in-b in-c)
    (ic/Intcode mem in-c in-d)
    (ic/Intcode mem in-d in-e)
    (ic/Intcode mem in-e out-e)
    (go (>! in-e e))
    (go (>! in-d d))
    (go (>! in-c c))
    (go (>! in-b b))
    (go (>! out-e a)
        (>! out-e 0))
    (->> (<!! (async/into [] result))
         reverse
         second
         (#(hash-map :result % :input seq)))))

(defn p2 
  ([] (p2 "input.txt"))
  ([input]
  (let [_input (str "resources/day-07/" input)
        mem (ic/parseInput _input)]
    (->> (range 5 10)
      util/permutations
      (map (fn [seq] (sequence-feedback-new seq mem)))
      (into '())
      (sort-by :result)
      last))))