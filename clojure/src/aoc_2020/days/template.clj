(comment
  (ns aoc-2020.days.00)

(defn parse [input]
  (->> (str "../resources/" input)
        slurp))

(defn p1 []
  (parse "day-00/input.txt"))

(defn p2 []
  (parse "day-00/input.txt"))
  
)