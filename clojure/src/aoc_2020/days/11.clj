(ns aoc-2020.days.11)

(defn parse [file]
  (->> (str "../resources/day-11/" file ".txt")
       slurp
       (re-seq #"[^\n]+")
       (mapv (fn [row] (->> (re-seq #"." row)   
                            (mapv (fn [seat] (if (= "L" seat) [:empty :occupied] [:floor :floor]) ))))) ; lets cheat and run the firts turn while parsing since we know the initial state is all empty
       ((fn [seats] {:turn 1 :done false :x (count (first seats)) :y (count seats)  :seats seats}))))

(defn get-neighbors-p1 [sx sy {:keys [seats turn]}]
  (for [x (range (dec sx) (+ 2 sx)) 
        y (range (dec sy) (+ 2 sy)) 
        :when (not= [sx sy] [x y]) ] 
    (get-in seats [y x turn])))

(defn check-empty-p1 [neighbors]
  (-> neighbors (frequencies) (get :occupied 0) ((fn [r] (if (= 0 r) [:occupied false] [:empty true])))))

(defn check-occupied-p1 [neighbors]
  (-> neighbors (frequencies) (get :occupied 0) ((fn [r] (if (>= r 4) [:empty false] [:occupied true])))))


(defn cycle [{:keys [seats x y turn] :as state} empty-fn occ-fn neigh-fn]
  (->> (for [sx (range 0 x) sy (range 0 y)]
         (let [ seat (get-in seats [sy sx turn])
               [next same] (case seat
                             :floor [:floor true]
                             :empty (empty-fn (neigh-fn sx sy state))
                             :occupied (occ-fn (neigh-fn sx sy state)))]
           [next same sx sy]))
       (reduce (fn [state [next same sx sy]] (-> state
                                              (update-in [:seats sy sx] #(conj % next))
                                              (update :done #(and same %))))
               (assoc state :done true)) ; flip done
       (#(update % :turn inc))))

(defn print-board [{:keys [seats x y turn] :as state}]
  (mapv (fn [row] (println (mapv #(-> % last (case :occupied "#" :empty "L" :floor ".")) row))) seats)
  "")

(defn count-state [{:keys [seats x y turn]}]
  (frequencies (for [sx (range 0 x) sy (range 0 y)] (get-in seats [sy sx turn]))))

(defn p1
  ([] (p1 "input"))
  ([file] (let [state (parse file)
                result (loop [{:keys [done] :as state} state]
                         (if done 
                           (count-state state) 
                           (recur (cycle state check-empty-p1 check-occupied-p1 get-neighbors-p1))))] result)))


(defn get-first [sx sy x y {:keys [seats turn]}] 
  (loop [sx sx sy sy]
    (let [sx (+ sx x)
          sy (+ sy y)
          seat (get-in seats [sy sx turn])]
      (case seat
        nil nil
        :empty :empty
        :occupied :occupied
        :floor (recur sx sy)))))

(defn get-neighbors-p2 [sx sy state]
  (for [x [-1 0 1]
        y [-1 0 1]
        :when (not= 0 x y)]
    (get-first sx sy x y state)))

(defn check-empty-p2 [neighbors]
  (-> neighbors (frequencies) (get :occupied 0) ((fn [r] (if (= 0 r) [:occupied false] [:empty true])))))

(defn check-occupied-p2 [neighbors]
  (-> neighbors (frequencies) (get :occupied 0) ((fn [r] (if (>= r 5) [:empty false] [:occupied true])))))

(defn p2
  ([] (p2 "input"))
  ([file] (let [state (parse file)
                result (loop [{:keys [done] :as state} state]
                         (if done
                           (count-state state)
                           (recur (cycle state check-empty-p2 check-occupied-p2 get-neighbors-p2))))] result)))