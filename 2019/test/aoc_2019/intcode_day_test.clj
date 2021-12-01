(ns aoc-2019.intcode-day-test
  (:require [clojure.test :refer :all]
    [aoc-2019.days.02 :as d02]
    [aoc-2019.days.05 :as d05]
    [aoc-2019.days.07 :as d07]
    [aoc-2019.days.09 :as d09]))

(deftest intcode-02-p1
  (testing "intcode day 02 p1"
    (is (= 3166704 (d02/p1)))))
    
(deftest intcode-02-p2
  (testing "intcode day 02 p2"
    (is (= "8018" (d02/p2)))))
    
    

(deftest intcode-05-p1
(testing "intcode day 05 p1"
  (let [result (get (d05/p1) 9)]
    (is (= 6761139 result)))))

(deftest intcode-05-p2
  (testing "intcode day 05 p2"
    (let [result (first (d05/p2))]
      (is (= 9217546 result)))))
    
    
(deftest intcode-07-p1
  (testing "intcode day 07 p1"
    (let [{result :result input :input} (d07/p1)]
      (is (= 844468 result))
      (is (= '(0 2 3 4 1) input)))))
    
(deftest intcode-07-p2
  (testing "intcode day 07 p2"
    (let [{result :result input :input} (d07/p2)]
      (is (= 4215746 result))
      (is (= '(6 5 8 7 9) input)))))


(deftest intcode-09-p1
  (testing "intcode day 09 p1"
    (let [result (first (d09/p1))]
      (is (= 2941952859 result)))))

(deftest intcode-09-p2
  (testing "intcode day 09 p2"
    (let [result (first (d09/p2))]
      (is (= 66113 result)))))