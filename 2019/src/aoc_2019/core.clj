(ns aoc-2019.core
  (:require 
    [aoc-2019.days.01] [aoc-2019.days.02] [aoc-2019.days.03]
    [aoc-2019.days.04] [aoc-2019.days.05] [aoc-2019.days.06]
    [aoc-2019.days.07] [aoc-2019.days.08] [aoc-2019.days.09])
  (:gen-class))

(defn run 
  [day part args]
    (let [stmnt (str "aoc-2019.days."day"/p"part)]
      (println (str "-------------- DAY "day" Part "part" --------------\n"))
      (println (time (-> stmnt read-string eval (apply args))))))

(defn -main
  ([] (time (do 
        (->> [["01" 1] ["01" 2]
              ["02" 1] ["02" 2]
              ;["03" 1] ["03" 2]
              ["04" 1] ["04" 2]
              ["05" 1] ["05" 2]
              ["06" 1] ["06" 2]
              ["07" 1] ["07" 2]
              ["08" 1] ["08" 2]
              ["09" 1] ["09" 2]]
            (map #(apply -main %))
            (seq)) (println "-------------- TOTAL --------------"))))
  ([day part & args] (run day part args)))
