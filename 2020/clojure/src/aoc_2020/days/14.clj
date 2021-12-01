(ns aoc-2020.days.14)

(defn parse-bitmask [bitmask]
  (keep-indexed (fn [i v] (case v \X [i :float] \1 [i :set] \0 [i :clear])) (reverse bitmask)))

(defn parse [file]
  (->> (str "../resources/day-14/" file ".txt")
       slurp
       (re-seq #"(\w+)\[?(\d+)?]? = ([\w\d]+)")
       (mapv (fn [[_ instr loc v]] (case instr
                                  "mask" [:mask nil (parse-bitmask v)]
                                  "mem" [:mem (read-string loc) (read-string v)])))))

(defn mem? [[task _ _]] (= :mem task))

(defn apply-mask [mask val] 
  (reduce (fn [b [i s]] 
            (case s :set (bit-set b i) :clear (bit-clear b i) :float b)) val mask))

(defn run-bit-mask [[mask mem] [task loc? val]]
  (case task 
    :mask [val mem]
    :mem [mask (assoc mem loc? (apply-mask mask val))]))


(defn p1
  ([] (p1 "input"))
  ([file] (->> (parse file)
               (reduce run-bit-mask [{} {}])
               second
               vals
               (reduce +))))

(defn apply-address-mask [mask val]
  (reduce (fn [bs [i s]]
            (case s 
              :clear bs
              :set (map #(bit-set % i) bs) 
              :float (concat (map #(bit-set % i) bs) (map #(bit-clear % i) bs)))) [val] mask))

(defn run-mem-mask [[mask mem] [task loc? val]]
  (case task
    :mask [val mem]
    :mem (let [locs (apply-address-mask mask loc?)
               mem  (reduce (fn [mem loc] (assoc mem loc val)) mem locs)]
           [mask mem])))

(defn p2
  ([] (p2 "input"))
  ([file] (->> (parse file)
               (reduce run-mem-mask [{} {}])
               second
               vals
               (reduce +))))
