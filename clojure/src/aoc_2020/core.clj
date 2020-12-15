(ns aoc-2020.core
  (:require
   [clojure.term.colors :as colors]
   [aoc-2020.days.01] [aoc-2020.days.02]
   [aoc-2020.days.03] [aoc-2020.days.04]
   [aoc-2020.days.05] [aoc-2020.days.06]
   [aoc-2020.days.07] [aoc-2020.days.08]
   [aoc-2020.days.09] [aoc-2020.days.10]
   [aoc-2020.days.11] [aoc-2020.days.12]
   [aoc-2020.days.13] [aoc-2020.days.14])
  (:gen-class))


(defn run  
  [day part args]
  (let [stmnt (str "aoc-2020.days." day "/p" part)]
    (println (str "-------------- " (colors/cyan (str "DAY " day " Part " part)) "--------------"))
    (println  "Logs: ")
    (println (str (colors/bold "Result: ") (colors/green (time (-> stmnt read-string eval (apply args))))))))

(defn -main
  ([] (time (do 
        (->> [["01" 1] ["01" 2]
              ["02" 1] ["02" 2]
              ["03" 1] ["03" 2]
              ["04" 1] ["04" 2]
              ["05" 1] ["05" 2]
              ["06" 1] ["06" 2]
              ["07" 1] ["07" 2]
              ["08" 1] ["08" 2]
              ["09" 1] ["09" 2]
              ["10" 1] ["10" 2]
              ["11" 1] ["11" 2]
              ["12" 1] ["12" 2]
              ["12" 1] ["12" 2]
              ["13" 1] ["13" 2]
              ["14" 1] ["14" 2]]
            (map #(apply -main %))
            (seq)) (println "-------------- TOTAL --------------"))))
  ([day part & args] (run day part args))
  ([day] (do (run day 1 nil) (run day 2 nil))))