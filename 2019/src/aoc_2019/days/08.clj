(ns aoc-2019.days.08)

(defn parseImage 
  [w l input]
  (->> input
    (str "resources/day-08/")
    slurp
    (into '())
    (partition (* w l))))

(defn check-layer [idx layer]
  (let [_0s (->> layer (filter #{\0}) count)
        _1s (->> layer (filter #{\1}) count)
        _2s (->> layer (filter #{\2}) count)]
    {:layer idx :0s _0s :1s _1s :2s _2s :checksum (* _1s _2s)} ))

(defn check-layers [layers] (map-indexed check-layer layers))

(defn compute-checksum 
  [layers]
  (->> layers
    check-layers
    (sort-by :0s)
    first
    doall))

(defn merge-px [[lp ip]]
  (cond 
    (= ip \0) \0
    (= ip \1) \1
    (= ip \2) lp))

(defn merge-layer [data] 
  (map merge-px data))

(defn render-image
  [width layers]
  (->> layers
    (reduce 
      (fn [image layer]
        (merge-layer (map vector image layer))))
    (map #(str (if (not= \1 %) \space %)))
    (partition width)
    (map reverse)
    (map #(apply str %))
    reverse))

(defn p1 
  ([] (p1 "input.txt" 25 6))
  ([input w l]
    (->> input 
      (parseImage w l)
      (compute-checksum))))

  (defn p2 
    ([] (p2 "input.txt" 25 6))
    ([input w l]
      (->> input 
        (parseImage w l)
        (render-image w)
        (map #(str % "\n"))
        (apply str))))