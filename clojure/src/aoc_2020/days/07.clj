(ns aoc-2020.days.07)

(defn parse [input]
  (->> (str "../resources/" input)
       slurp
       (re-seq #"[^\n\\.]+")
       (map (fn [rule] 
              (let [[_ k v] (re-matches #"([a-z]+ [a-z]+) bags contain (.*)" rule)
                    v (re-seq #"[^,]+" v)
                    v (map #(let [[_ n b _] (re-matches #"[ ]?([0-9]+) ([\w]+ [\w]+) (bag|bags)" %)] [n b]) v)]
                [k v])))
       (into {})))

(defn bag-search [cur bag rules] 
  (let [_ 1](cond 
     (nil? cur) false
     (= bag cur) true 
     :else (->> (get rules cur)
                (reduce (fn [r [_ next]] (or r (bag-search next bag rules))) false)))))

(defn p1 
  ([] (p1 "shiny gold"))
  ([bag] (let [rules (parse "day-07/input.txt")
               keys (->> rules keys (filter (partial not= bag)))
               res (map (fn [key] (bag-search key bag rules)) keys)]
           (count (filter (partial = true) res)))))


(defn bag-count [bag rules]
  (if (nil? bag) 
    0
    (->> (get rules bag)
         (reduce (fn [r [c next]] 
                   (let [c (if (nil? c) 0 (read-string c))] 
                     (+ r c (* c (bag-count next rules))))) 0))))

(defn p2
  ([] (p2 "shiny gold"))
  ([bag] (let [rules (parse "day-07/input.txt")
               res (bag-count bag rules)] 
           res)))