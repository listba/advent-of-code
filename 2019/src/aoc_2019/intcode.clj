(ns aoc-2019.intcode
  (:require [clojure.core.async :as async
             :refer [>! <! <!! go go-loop chan close! put!]]))

(defn parseInput
  [file]
  (->> file
       slurp
       clojure.string/trim
       (re-seq #"[^,]+")
       (map #(BigInteger. %))
       (vec)))

(def default (bigint 0))
(defn alloc [mem i] (vec (->> default bigint repeat (take i) (concat mem))))

(defn fetch
  ([{mem :mem ptr :ptr}] (fetch mem ptr))
  ([mem ptr]
   (let [size (dec (count mem))
         need (- ptr size)
          ;_ (if (> need 0) (println (str "mem capacity reached expanding by " need " ptr:"ptr" cur:"size )))
         ]
     (if (> need 0)
       {:mem (alloc mem need) :ptr ptr :val default}
       {:mem mem :ptr ptr :val (get mem ptr)}))))

(defn get-param
  [{mem :mem mode :mode ptr :ptr rbase :rbase}]
  (let [{mem :mem val :val :as r} (fetch mem ptr)]
          ;_ (println (str "reading param at "ptr" in mode "mode ))]
    (case mode
      1 r ; immediate mode we are done
      0 (fetch mem val) ; pos mode read a second time using the val just read as the pointer
      2 (fetch mem (+ rbase val))))) ; rbase mode read a second time using val+rbase as the pointer

(def op-pmap {:99 0
              :3 1 :4 1 :9 1
              :5 2 :6 2
              :1 3 :2 3 :7 3 :8 3})

(defn op-params
  [op]
  (try ((keyword (str op)) op-pmap)
       (catch Throwable e (throw (Throwable. (str "Unknown opcode " op))))))

(defn pmode [pcode idx] (mod (int (/ pcode (Math/pow 10 idx))) 10))

(defn parse-opcode
  [iptr rbase mem]
  (let [{opdata :val mem :mem} (fetch mem iptr)
        opcode  (int (mod opdata 100))
        pcode   (int (/ opdata 100))
        p-count (op-params opcode)
        results (->> (range p-count)
                     (reductions
                      (fn [mem i] (get-param (assoc mem :ptr (+ 1 i iptr) :mode (pmode pcode i) :rbase rbase)))
                      {:mem mem}) ; initial value is just current memory state
                     (drop 1)
                     (vec))
        mem (if (= 0 p-count) mem (:mem (last results))) ; update mem if there were any params incase we needed to expand it
        params (map #(dissoc % :mem) results)] ; filter out mem from params for debugging clarity
    {:opcode opcode :params params :niptr (+ iptr p-count 1) :mem mem}))

(defn Intcode
  ([mem] (Intcode mem []))
  ([mem input] (let [in (chan) out (chan)]
                 (go (async/onto-chan in input)) ; push contents of vector into input chan
                 (Intcode mem in out)
                 (<!! (async/into [] out))))     ; block until intcode halts and return output as a vector  
  ([mem input output]
   (go-loop [mem mem iptr 0 rbase 0]
     (let [{opcode :opcode params :params iptr :niptr mem :mem} (parse-opcode iptr rbase mem)
            ;_ (println (str "OP" opcode "->" iptr " " (vec params)))
           [mem iptr rbase]
           (cond
            ; Halt
             (= opcode 99) (do (put! output (get mem 0)) [mem iptr rbase])
            ; store input
             (= opcode 3) (as-> (<! input) $
                            (assoc mem (:ptr (first params)) $)
                            [$ iptr rbase])
            ; Output Diagnostic
             (= opcode 4) (let [o (:val (first params))]
                            (>! output o)
                            [mem iptr rbase])
            ; jump if true
             (= opcode 5) (if (not= 0 (:val (first params)))
                            [mem (:val (second params)) rbase]
                            [mem iptr rbase])
            ; jump if false
             (= opcode 6) (if (= 0 (:val (first params)))
                            [mem (:val (second params)) rbase]
                            [mem iptr rbase])
            ; update rbase
             (= opcode 9) (as-> params $
                            (first $)
                            (:val $)
                            (+ rbase $)
                            [mem iptr $])
            ; add / mult / < / = 
             (= 3 (count params))
             (let
              [a  (:val (first params))
               b  (:val (second params))
               rp (:ptr (last params))
               rv (case opcode
                    1 (+ a b)            ; additon
                    2 (* a b)            ; multiplication
                    7 (if (< a b) 1 0)   ; less than
                    8 (if (= a b) 1 0))] ; equals
               [(assoc mem rp rv) iptr rbase])
            ; Unknown
             :else (do (close! output) (throw (Throwable. (str "Unknown op code " opcode "params" params)))))]
       (if (= 99 opcode)
         (close! output)
         (recur mem iptr rbase))))))