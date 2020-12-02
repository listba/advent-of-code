(ns aoc-2020.core
  (:require 
    [aoc-2020.days.01] )
  (:gen-class))

(defn run 
  [day part args]
    (let [stmnt (str "aoc-2020.days."day"/p"part)]
      (println (str "-------------- DAY "day" Part "part" --------------\n"))
      (println (time (-> stmnt read-string eval (apply args))))))

(defn -main
  ([] (time (do 
        (->> [["01" 1] ["01" 2]]
            (map #(apply -main %))
            (seq)) (println "-------------- TOTAL --------------"))))
  ([day part & args] (run day part args)))
