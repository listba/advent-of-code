(ns aoc-2020.days.12)

(defn parse [file]
  (->> (str "../resources/day-12/" file ".txt")
       slurp
       (re-seq #"(\w)(\d+)")
       (mapv (fn [[_ i v]] [(keyword i) (read-string v)]))))

(defn rotate [deg state] (update state :dir #(mod (+ % deg) 360)))

(defn move [dist {:keys [dir] :as state}] (let [k (case dir 0 :N  90 :E  180 :S  270 :W)] (update state k (partial + dist))))

(defn follow [state ins]
  (loop [state state 
         [[i v] & ins] ins]
    (let [state (case i
                  :R (rotate v state)
                  :L (rotate (- v) state)
                  :F (move v state)
                  (update state i (partial + v)))]
      (if (nil? ins) state (recur state ins)))))

(defn p1
  ([] (p1 "input"))
  ([file] (let [ins (parse file)
                state {:dir 90 :N 0 :E 0 :S 0 :W 0}
                {:keys [N,E,S,W]} (follow state ins)]
            (+ (Math/abs (- N S)) (Math/abs (- E W))))))


(defn rotate-waypoint [deg {:keys [N E S W]}]
  (case deg 
    90   {:N W  :E N  :S E  :W S}
    180  {:N S  :E W  :S N  :W E}
    270  {:N E  :E S  :S W  :W N}
    -90  {:N E  :E S  :S W  :W N}
    -180 {:N S  :E W  :S N  :W E}
    -270 {:N W  :E N  :S E  :W S}))

(defn move-ship [[SN SE SS SW] {:keys [N E S W]} units]
  [(+ SN (* N units)) (+ SE (* E units)) (+ SS (* S units)) (+ SW (* W units))])

(defn follow-waypoint [ship waypoint ins]
  (loop [ship ship
         waypoint waypoint
         [[i v] & ins] ins]
    (let [[ship waypoint] (case i
                  :R [ship (rotate-waypoint v waypoint)]
                  :L [ship (rotate-waypoint (- v) waypoint)]
                  :F [(move-ship ship waypoint v) waypoint]
                  [ship (update waypoint i (partial + v))])]
      (if (nil? ins) [ship waypoint] (recur ship waypoint ins)))))

(defn p2
  ([] (p2 "input"))
  ([file] (let [ins (parse file)
                ship [0 0 0 0]
                waypoint {:N 1 :E 10 :S 0 :W 0}
                [[N E S W] _] (follow-waypoint ship waypoint ins)]
            (+ (Math/abs (- N S)) (Math/abs (- E W))))))