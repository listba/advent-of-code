(ns aoc-2020.days.08
  (:require [clojure.pprint :as pprint]))

(defn parse [input]
  (->> (str "../resources/" input)
       slurp
       (re-seq #"(\w{3}) ([+|-]\d+)")
       (mapv (fn [[_ op v]] [(keyword op) (read-string v)]))))

(defn update-state 
  "updates index, accumulation and history in state"
  [idx acc {pIdx :idx :as state}]
  (-> state 
      (assoc :idx idx)                     ;; update index in state
      (assoc :acc acc)                     ;; update accumulation value in state
      (update-in [:hist pIdx] conj acc)))  ;; update history in state with prev index && acc

(defn run-code
  ([codes] (run-code codes {:idx 0 :acc 0 :hist {}}))
  ([codes state]
   (loop [codes codes
          state state]
     (let [{:keys [idx acc hist]} state
           [op v] (get codes idx)
           cHist  (get hist idx)]
       (cond
         (some? cHist) (assoc state :exit-code 1)  ;; return with error if we have already executed this instruction  
         (nil? op)     (assoc state :exit-code 0)  ;; return with success if no further instructions
         (= :nop op) (recur codes (update-state (inc idx) acc state))              ;; NoOp -> inc instruction pointer by 1
         (= :jmp op) (recur codes (update-state (+ idx v) acc state))              ;; Jump -> add value to instruction pointer
         (= :acc op) (recur codes (update-state (inc idx) (+ acc v) state)))))))   ;; Accumulate -> Add value to global acc and inc instruction pointer by 1  

(defn pp 
  "pretty prints instruction set with accumulation history"
  [codes hist] 
  (let [result (map-indexed
                (fn [idx [op v]]
                  (let [col1 (str idx)
                        col2 (str op "" v)
                        col3 (str (get hist idx))]
                    {:index col1 :instruction col2 :result col3})) codes)]
    (clojure.pprint/print-table result)))
  
(defn p1 
  ([] (p1 false))
  ([debug]
   (let [codes (parse "day-08/input.txt")
         {:keys [idx acc hist exit-code]} (run-code codes)
         _ (if debug (pp codes hist))]
     {:idx idx :acc acc :exit-code exit-code})))

(defn code-swap 
  "swaps jmp<->nop instructions 1 at a time"
  [codes]
  (keep-indexed (fn [idx [op v]]
                  (cond (= :jmp op) [(assoc codes idx [:nop v]) (str "idx: :jmp " v " -> :nop " v)]
                        (= :nop op) [(assoc codes idx [:jmp v]) (str "idx: :nop " v " -> :jmp " v)]
                        :else nil))
                codes))

(defn p2 
  ([] (p2 false))
  ([debug]
   (let [codes (parse "day-08/input.txt")
         swapped-codes (code-swap codes)]
     (loop [swapped-codes swapped-codes]
       (let [ [[new-codes change] & swapped-codes] swapped-codes
             _ (if debug (println (str "Trying " change)))
             {:keys [acc exit-code hist]} (run-code new-codes)]
         (cond 
           (= 0 exit-code) (do (if debug (pp new-codes hist)) {:acc acc :change change})
           (nil? swapped-codes) {:result "no valid swaps found"}
           :else (recur swapped-codes)))))))