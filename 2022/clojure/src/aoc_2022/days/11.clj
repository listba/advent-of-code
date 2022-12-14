(ns aoc-2022.days.11
  (:require [aoc-2022.util :as util]))


(defn parse-op [[_ _ _ _ op rhs]]
  (let [op (if (= "*" op) * +)]
    (if (= "old" rhs)
      (fn [x] (op x x))
      (fn [x] (op x (read-string rhs))))))

(defn parse-monkey
  [monkeys [monkey items op test iftrue iffalse]]
  (let [monkey (->> monkey (re-find #"\d+") keyword)
        items (->> items (re-seq #"\d+") (mapv read-string))
        op (->> op (re-seq #"[^ ]+") parse-op)
        div (->> test (re-find #"[\d]+") read-string)
        if-true (->> iftrue (re-find #"[\d]+") keyword)
        if-false (->> iffalse (re-find #"[\d]+") keyword)]
    (conj monkeys {monkey {:monkey monkey :items items :op op :div div :if-true if-true :if-false if-false :inspections 0}})))

(defn add-worry-fn
  [worry-reducer monkeys {:keys [monkey op div if-true if-false]}]
  (let [worry-fn
        (fn [monkeys oldval]
          (let [newval (op oldval)
                reduced-worry (worry-reducer newval)
                testResult   (mod reduced-worry div)
                throw-to (if (== 0 testResult) if-true if-false)]
            (update-in monkeys [throw-to :items] #(conj % reduced-worry))))]
    (assoc-in monkeys [monkey :worry-fn] worry-fn))) ; Add fn to monkey

(defn add-p1-worry-fns [monkeys]
  (let [worry-reducer (fn [worry] (quot worry 3))]
    (reduce (partial add-worry-fn worry-reducer) monkeys (vals monkeys))))

(defn add-p2-worry-fns [monkeys]
  (let [supermod (->> monkeys vals (map :div) (apply *))
        worry-reducer (fn [worry] (mod worry supermod))]
    (reduce (partial add-worry-fn worry-reducer) monkeys (vals monkeys))))

(defn inspect-item [monkey-id monkeys item]
  (let [worry-fn (get-in monkeys [monkey-id :worry-fn])]
    (worry-fn monkeys item)))

(defn play-round-for-monkey [monkeys monkey-id]
  (let [items (get-in monkeys [monkey-id :items])]
    (as-> monkeys $
      (update-in $ [monkey-id :inspections] (partial + (count items))) 
      (assoc-in $ [monkey-id :items] [])
      (reduce (partial inspect-item monkey-id) $ items))))

(defn play-round [monkeys monkey-ids]
  (reduce play-round-for-monkey monkeys monkey-ids))

(defn play [rounds monkeys] (reduce play-round monkeys (->> monkeys keys (repeat rounds))))

(defn monkey-business [file rounds worry-fn-adder]
  (->> file
       (util/split-blank-lines "11")
       (map (partial re-seq #"[^\n]+"))
       (reduce parse-monkey {})
       worry-fn-adder
       (play rounds)
       vals
       (map :inspections)
       (sort >=)
       (take 2)
       (apply *)))

(defn p1
  ([] (p1 "input"))
  ([file] (monkey-business file 20 add-p1-worry-fns)))

(defn p2
  ([] (p2 "input"))
  ([file] (monkey-business file 10000 add-p2-worry-fns)))

(str "p1: " (p1) " p2: " (p2))