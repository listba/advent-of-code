(ns aoc-2023.day-tests
  (:require [clojure.test :refer :all]
            [aoc-2023.days.01 :as d01] [aoc-2023.days.02 :as d02]
            [aoc-2023.days.03 :as d03] [aoc-2023.days.04 :as d04]
            [aoc-2023.days.05 :as d05] [aoc-2023.days.06 :as d06]
            [aoc-2023.days.07 :as d07] [aoc-2023.days.08 :as d08]
            [aoc-2023.days.09 :as d09] [aoc-2023.days.10 :as d10]
            [aoc-2023.days.11 :as d11] [aoc-2023.days.12 :as d12]
            [aoc-2023.days.13 :as d13] [aoc-2023.days.14 :as d14]
            [aoc-2023.days.15 :as d15] [aoc-2023.days.16 :as d16]
            [aoc-2023.days.17 :as d17] [aoc-2023.days.18 :as d18]
            [aoc-2023.days.19 :as d19] [aoc-2023.days.20 :as d20]
            [aoc-2023.days.21 :as d21] [aoc-2023.days.22 :as d22]
            [aoc-2023.days.23 :as d23] [aoc-2023.days.24 :as d24]
            [aoc-2023.days.25 :as d25]))

(deftest day-01-p1
  (testing "day 01 p1"
    (is (= 55172 (d01/p1)))))

(deftest day-01-p2
  (testing "day 01 p2"
    (is (= 54925 (d01/p2)))))


(deftest day-02-p1
  (testing "day 02 p1"
    (is (= 2449 (d02/p1)))))

(deftest day-02-p2
  (testing "day 02 p2"
    (is (= 63981 (d02/p2)))))


(deftest day-03-p1
  (testing "day 03 p1"
    (is (= 557705 (d03/p1)))))

(deftest day-03-p2
  (testing "day 03 p2"
    (is (= 84266818 (d03/p2)))))


;; (deftest day-04-p1
;;   (testing "day 04 p1"
;;     (is (= 456 (d04/p1)))))

;; (deftest day-04-p2
;;   (testing "day 04 p2"
;;     (is (= 808 (d04/p2)))))

;; (deftest day-05-p1
;;   (testing "day 05 p1"
;;     (is (= (sort "VWLCWGSDQ") (sort (d05/p1))))))

;; (deftest day-05-p2
;;   (testing "day 05 p2"
;;     (is (= (sort "TCGLQSLPW") (sort (d05/p2))))))

;; (deftest day-06-p1
;;   (testing "day 06 p1"
;;     (is (= 1965 (d06/p1)))))

;; (deftest day-06-p2
;;   (testing "day 06 p2"
;;     (is (= 2773 (d06/p2)))))

;; (deftest day-07-p1
;;   (testing "day 07 p1"
;;     (is (= 1845346 (d07/p1)))))

;; (deftest day-07-p2
;;   (testing "day 07 p2"
;;     (is (= 3636703 (d07/p2)))))

;; (deftest day-08-p1
;;   (testing "day 08 p1"
;;     (is (= 1843 (d08/p1)))))

;; (deftest day-08-p2
;;   (testing "day 08 p2"
;;     (is (= 180000 (d08/p2)))))



;; (deftest day-09-p1
;;   (testing "day 01 p1"
;;     (is (= 6498 (d09/p1)))))

;; (deftest day-09-p2
;;   (testing "day 09 p2"
;;     (is (= 2531 (d09/p2)))))

;; (deftest day-10-p1
;;   (testing "day 10 p1"
;;     (is (= 15360 (d10/p1)))))


;; (deftest day-11-p1
;;   (testing "day 11 p1"
;;     (is (= 56120 (d11/p1)))))

;; (deftest day-11-p1
;;   (testing "day 11 p2"
;;     (is (= 24389045529 (d11/p2)))))