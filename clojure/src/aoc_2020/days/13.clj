(ns aoc-2020.days.13)

(defn parse-p1 [file]
  (->> (str "../resources/day-13/" file ".txt")
       slurp
       (re-seq #"\d+")
       (map read-string)))

; (59-(939%59)
(defn calc-next [time id] [id (- id (mod time id))])

(defn p1
  ([] (p1 "input"))
  ([file] (let [[time & busses] (parse-p1 file)]
            (->> busses
                 (map (partial calc-next time))
                 (sort-by second)
                 first
                 (apply *)))))

(defn parse-p2 [file]
  (->> (str "../resources/day-13/" file ".txt")
       slurp
       (re-seq #"[^\n]+")
       (second)
       (re-seq #"[^,]+")
       (keep-indexed (fn [i n] (if (= n "x") nil [i (read-string n)])))))


;; time is the current time offset of the previous busses
;; i is the running LCM between the previous busses
;; t is the offset of the next buss
;; b is the "id" of the netx buss
;; increment time by the current offset til we find the time that works for the current bus
;; then return time offset and multiple the bus times the increment to get the next LCM
(defn find-overlap [[time i] [t b]]
  (->> (iterate (partial + i) time)
       (filter #(= 0 (mod (+ % t) b)))
       first
       ((fn [time] [time (* i b)]))))

(defn p2
  ([] (p2 "input"))
  ([file] (let [busses (parse-p2 file)]
            (->> busses
                 (reduce (fn [cur next] (find-overlap cur next)))
                 first))))