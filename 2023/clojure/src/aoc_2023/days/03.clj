(ns aoc-2023.days.03 
  (:require [aoc-2023.util :as util]))


(defn find-numbers
  "Finds all continuous sequences of digits in each line and mark the valus and its coordinates"
  ([grid]
   (let [length (->> grid first count (dec))
         end? (fn [x] (= length x))
         new-num {:coords [] :value ""}
         add-cell (fn [{:keys [coords value]} coord digit] 
                    {:coords (concat coords [coord]) :value (read-string (str value digit))})]
     (reduce-kv (fn [nums y row]
                  (concat nums 
                          (first
                           (reduce-kv
                            (fn [[line-nums cur-num] x cell]
                              (cond
                                (and (end? x) (util/digit? cell)) [(concat line-nums [(add-cell cur-num [y x] cell)]) new-num]
                                (util/digit? cell)                [line-nums (add-cell cur-num [y x] cell)]
                                (not= cur-num new-num)            [(concat line-nums [cur-num]) new-num]
                                :else                             [line-nums cur-num])) 
                            [[] new-num] row)))
                  )[] grid))))


(defn part-number? 
  "A part number is defined as a sequence of digits that is adjacent to 1 or more symbols
   (ie not a digit & not a .)"
  [grid {coords :coords}]
  (->> coords 
       (map #(util/fetch-neighbors-values true % grid)) 
       (apply concat) 
       (filter #(and (not= % \.) (not (util/digit? %)))) 
       (util/not-empty?)))
(defn p1 
  ([] (p1 "input"))
  ([file] (let [grid (util/parse-grid "03" file)]
             (->> grid
                  (find-numbers)
                  (filter #(part-number? grid %))
                  (map #(:value %))
                  (apply +)))))

(defn potential-gear? [grid {:keys [value coords]}]
  (->> coords 
       (map #(util/fetch-neighbors true % grid)) 
       (apply concat) 
       (filter (fn [[v]] (= \* v))) 
       (distinct)
       ((fn [v] (cond 
                  (= 1 (count v)) {:value value :gear (->> v first second)} 
                  :else nil)))))
(defn p2 
  ([] (p2 "input"))
  ([file] (let [grid (util/parse-grid "03" file)]
            (->> grid
                 (find-numbers)
                 (keep #(potential-gear? grid %))
                 (group-by :gear)
                 (keep (fn [[_ parts]] (cond 
                                       (= 2 (count parts)) (->> parts (map #(:value %)) (apply *)))))
                 (apply +)
                 ))))

(str "p1: " (p1) " p2: " (p2))


(str "p1: " (p1 "sample"))
(str "p2: " (p2 "sample"))

(pr-str "p1: " (p1 "input"))
(pr-str "p2: " (p2 "input"))
