(ns aoc-2019.days.06)

(defn parse-input 
  [input]
  (->> input
      (str "resources/day-06/")
      slurp
      (re-seq #"[^\n]+")
      (map #(re-seq #"[^)]+" %))))

(defn get-orbit-map [parsed] (group-by first parsed))
(defn get-orbitors-map [parsed] (group-by second parsed))

(defn count-orbits 
  ([orbits] 
    (count-orbits (first (keys orbits)) 0 orbits))
  ([start orbit-count orbits]
    (let [subOrbits (doall (map second (get orbits start)))
          sub-count (count subOrbits)
          next (+ 1 orbit-count)] 
          (if (= sub-count 0)
            orbit-count
            (+ orbit-count (->> subOrbits
              (map #(count-orbits % next orbits))
              (reduce +)))))))

(defn find-route  
  [o-map from to]
  (let [next (first (map first (get o-map from)))]
        (if (= next to)
          [next]
           (conj (find-route o-map next to) next)
          )))

(defn transfer 
  [you san]
  (->> you
    (map #(list (.indexOf san %) %))
    (filter #(not= -1 (first %)))
    first
    (#(+ (first %) (.indexOf you (second %))))
))

(defn p1 
  ([] (p1 "input.txt"))
  ([input]
    (let [parsed (parse-input input)
          orbit-map (get-orbit-map parsed)
          result (count-orbits "COM" 0 orbit-map)]
      result)))

  (defn p2 
    ([] (p2 "input.txt"))
    ([input]
      (let [parsed (parse-input input)
          orbitors-map (get-orbitors-map parsed)
          you (reverse (find-route orbitors-map "YOU" "COM"))
          san (reverse (find-route orbitors-map "SAN" "COM"))
          xfer (transfer you san)]
      xfer)))