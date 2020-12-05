(ns aoc-2020.core
  (:require
   [clojure.term.colors :as colors]
   [aoc-2020.days.01] [aoc-2020.days.02]
   [aoc-2020.days.03] [aoc-2020.days.04]
   [aoc-2020.days.05])
  (:gen-class))


(defn run 
  [day part args]
    (let [stmnt (str "aoc-2020.days."day"/p"part)]
      (println (str "-------------- "(colors/cyan (str "DAY " day " Part "part)) "--------------"))
      (println  "Logs: ")
      (println (str (colors/bold "Result: ") (colors/green (time (-> stmnt read-string eval (apply args))))))))

(defn -main
  ([] (time (do 
        (->> [["01" 1] ["01" 2]
              ["02" 1] ["02" 2]
              ["03" 1] ["03" 2]
              ["04" 1] ["04" 2]
              ["05" 1] ["05" 2]]
            (map #(apply -main %))
            (seq)) (println "-------------- TOTAL --------------"))))
  ([day part & args] (run day part args)))