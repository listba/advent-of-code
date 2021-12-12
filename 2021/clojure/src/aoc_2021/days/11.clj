(ns aoc-2021.days.11 
  (:require [aoc-2021.util :as util]))

; I think this could be moved into utils
(defn fetch-neighbors [diags? [y x] graph]
  (let [N  [(dec y) x]
        NE [(dec y) (inc x)]
        E  [y (inc x)]
        SE [(inc y) (inc x)]
        S  [(inc y) x]
        SW [(inc y) (dec x)]
        W  [y (dec x)]
        NW [(dec y) (dec x)]]
    (->> (if diags? [N NE E SE S SW W NW] [N E S W])
        ;;  (util/ps (str "Neighbors for " y " " x))
        ;;  (util/p)
         (keep (fn [[ny nx]]
                 (some->> [ny nx]
                          (get-in graph)
                          ((fn [x] [x [ny nx]]))))))))

(defn debug-flash [octopus neighbors] (println (str "Flashing " octopus " neighbors " (mapv identity neighbors))) neighbors)

(defn flash-octopus [octopi octopus]
  (->> octopi
       (fetch-neighbors true octopus)
       ;;  (debug-flash octopus)
       (reduce (fn [octopi [v n]] (if (= 0 v) octopi (update-in octopi n inc))) octopi)
       (util/assoc-in>> 0 octopus)))

(defn coord-map [graph] (for [y (range (count graph))
                               x (range (count (first graph)))]
                           [(get-in graph [y x]) [y x]]))

(defn flash-cycle [octopi] 
  (let [pending-flash (->> octopi coord-map (filter (fn [[v _]] (> v 9))))]
    (if (empty? pending-flash)
      octopi
      (->> pending-flash
           (map second)
           (reduce (fn [octopi octopus]
                     (flash-octopus octopi octopus)) octopi)
           recur))))

(defn flash-count [flashes octopi] [(->> octopi flatten (filter (partial = 0)) count (+ flashes)) octopi])
(defn octopus-cycle
  ([octopi] (last (octopus-cycle 0 octopi)))
  ([flashes octopi]
   (->> octopi
        (mapv #(mapv inc %))
        flash-cycle
        (flash-count flashes))))

(defn octopus-cycles [cycles octopi]
  (reduce (fn [[flash-count octopi] _] (octopus-cycle flash-count octopi)) [0 octopi] (range cycles)))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file 
               (util/parse-digits "11") 
               util/mp
               (octopus-cycles 100)
               ;;second
               (util/ps "After 100 cycle")
               ((fn [[fc o]] (map println o) fc)))))

; verifies all octopi are set to 0
(defn all-flash? [octopi] (-> octopi flatten frequencies keys (= `(0)))) 

(defn first-all-flash
  ([octopi] (first-all-flash octopi 0))
  ([octopi cycle] 
    (if (all-flash? octopi)
      [cycle octopi]
      (recur (octopus-cycle octopi) (inc cycle)))))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file 
               (util/parse-digits "11") 
               util/mp
               first-all-flash
               first)))